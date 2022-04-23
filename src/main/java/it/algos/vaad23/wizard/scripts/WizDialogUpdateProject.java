package it.algos.vaad23.wizard.scripts;

import com.vaadin.flow.component.checkbox.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.wrapper.*;
import it.algos.vaad23.wizard.enumeration.*;
import static it.algos.vaad23.wizard.scripts.WizCost.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;
import java.util.function.*;

/**
 * Project sette
 * Created by Algos
 * User: gac
 * Date: lun, 11-apr-2022
 * Time: 21:02
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WizDialogUpdateProject extends WizDialog {

    protected LinkedHashMap<String, Checkbox> mappaCheckbox;

    private Consumer<LinkedHashMap<String, Checkbox>> confirmHandler;

    public WizDialogUpdateProject() {
        super();
    }// end of constructor

    /**
     * Legenda iniziale <br>
     * Viene sovrascritta nella sottoclasse che deve invocare PRIMA questo metodo <br>
     */
    @Override
    protected void creaTopLayout() {
        String message;
        topLayout = fixSezione(TITOLO_MODIFICA_PROGETTO, "green");
        this.add(topLayout);

        message = "Aggiorna il modulo 'vaad23' di questo progetto";
        topLayout.add(htmlService.getSpan(new WrapSpan().message(message).weight(AEFontWeight.bold)));
    }

    /**
     * Sezione centrale con la selezione dei flags <br>
     * Crea i checkbox di controllo <br>
     * Spazzola (nella sottoclasse) la Enumeration per aggiungere solo i checkbox adeguati: <br>
     * newProject
     * updateProject
     * newPackage
     * updatePackage
     * Spazzola la Enumeration e regola a 'true' i checkBox secondo il flag 'isAcceso' <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void creaCheckBoxLayout() {
        Checkbox check;
        checkBoxLayout = fixSezione("Flags di regolazione");
        mappaCheckbox = new LinkedHashMap<>();

        for (AEWizProject wiz : AEWizProject.getAllEnums()) {
            check = new Checkbox(wiz.getCaption());
            check.setValue(wiz.isUpdate());
            mappaCheckbox.put(wiz.name(), check);
            checkBoxLayout.add(check);
        }

        this.add(checkBoxLayout);
    }

    @Override
    protected void creaBottomLayout() {
        super.creaBottomLayout();
        this.add(spanConferma);
    }

    protected void creaBottoni() {
        String message;
        super.creaBottoni();

        cancelButton.getElement().setAttribute("theme", "secondary");
        confirmButton.getElement().setAttribute("theme", "primary");
        confirmButton.setEnabled(true);
        message = String.format("Confermando vengono aggiornati i files selezionati");
        spanConferma = new HorizontalLayout();
        spanConferma.add(htmlService.getSpan(new WrapSpan().message(message).color(AETypeColor.rosso)));
    }

    /**
     * Apertura del dialogo <br>
     */
    public void open(final Consumer<LinkedHashMap<String, Checkbox>> confirmHandler) {
        this.confirmHandler = confirmHandler;
        this.getElement().getStyle().set("background-color", "#ffffff");

        super.open();
    }


    /**
     * Esce dal dialogo con due possibilità (a seconda del flag) <br>
     * 1) annulla <br>
     * 2) esegue <br>
     */
    protected void esceDalDialogo(boolean esegue) {
        this.close();
        if (esegue) {
            confirmHandler.accept(mappaCheckbox);
        }
    }

}

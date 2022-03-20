package it.algos.vaad23.backend.packages.utility.log;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.router.*;
import it.algos.vaad23.backend.boot.*;
import it.algos.vaad23.ui.views.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mer, 16-mar-2022
 * Time: 19:47
 * <p>
 */
@PageTitle("Logger")
@Route(value = "logger", layout = MainLayout.class)
public class LoggerView extends AlgosView {

    private TextField filter;

    private ComboBox comboLivello;

    private ComboBox comboType;


    private LoggerBackend backend;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Si usa un @Qualifier(), per specificare la classe che incrementa l'interfaccia repository <br>
     * Si usa una costante statica, per essere sicuri di scriverla uguale a quella di xxxRepository <br>
     * Regola il service specifico di persistenza dei dati e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public LoggerView(@Autowired final LoggerBackend crudBackend) {
        super(crudBackend, Logger.class, false);
        this.backend = crudBackend;

        crud.getGrid().setColumns("livello", "type", "evento", "descrizione", "company", "user", "classe", "metodo", "linea");

        String larLevel = "5em";
        String larType = "9em";
        String larEvento = "15em";
        String larDesc = "30em";
        String larCompany = "8em";
        String larClasse = "8em";
        String larMetodo = "8em";
        String larLinea = "8em";

        crud.getGrid().getColumnByKey("livello").setWidth(larLevel).setFlexGrow(0).setHeader("#");
        crud.getGrid().getColumnByKey("type").setWidth(larType).setFlexGrow(0);
        crud.getGrid().getColumnByKey("evento").setWidth(larEvento).setFlexGrow(0);
        crud.getGrid().getColumnByKey("descrizione").setWidth(larDesc).setFlexGrow(1);
        crud.getGrid().getColumnByKey("company").setWidth(larCompany).setFlexGrow(0);
        crud.getGrid().getColumnByKey("user").setWidth(larCompany).setFlexGrow(0);
        crud.getGrid().getColumnByKey("classe").setWidth(larClasse).setFlexGrow(0);
        crud.getGrid().getColumnByKey("metodo").setWidth(larMetodo).setFlexGrow(0);
        crud.getGrid().getColumnByKey("linea").setWidth(larLinea).setFlexGrow(0);

        // colonne visibili
        if (!VaadVar.usaCompany) {
            crud.getGrid().getColumnByKey("company").setVisible(false);
            crud.getGrid().getColumnByKey("user").setVisible(false);
        }
    }

}// end of crud @Route view class
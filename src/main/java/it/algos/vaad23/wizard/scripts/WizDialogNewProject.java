package it.algos.vaad23.wizard.scripts;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.io.*;
import java.util.*;
import java.util.function.*;


/**
 * Project my-project
 * Created by Algos
 * User: gac
 * Date: lun, 12-ott-2020
 * Time: 12:24
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WizDialogNewProject extends WizDialog {

    private static final String LABEL_COMBO_UNO = "Progetti vuoti esistenti (nella directory IdeaProjects)";

    private static final String LABEL_COMBO_DUE = "Tutti i progetti esistenti (nella directory IdeaProjects)";

    protected Consumer<String> confirmHandler;

    protected Consumer annullaHandler;

    private HorizontalLayout spanConferma;

    public WizDialogNewProject() {
        super();
    }// end of constructor


    /**
     * Legenda iniziale <br>
     * Viene sovrascritta nella sottoclasse che deve invocare PRIMA questo metodo <br>
     */
    @Override
    protected void creaTopLayout() {
        String message;
        topLayout = fixSezione("Nuovo progetto", "green");
        this.add(topLayout);

        message = "Creazione di un nuovo project. Devi prima creare un new project IntelliJIdea (minuscolo).";
        topLayout.add(htmlService.getSpan(new WrapSpan().message(message).weight(AEFontWeight.bold)));

        message = "Di tipo 'MAVEN' senza selezionare archetype. Rimane il POM vuoto, ma verrà sovrascritto.";
        topLayout.add(htmlService.getSpan(new WrapSpan().message(message).weight(AEFontWeight.bold)));

        message = "Seleziona il progetto dalla lista sottostante.";
        topLayout.add(htmlService.getSpan(new WrapSpan().message(message).weight(AEFontWeight.bold).color(AETypeColor.rosso)));

        message = "Nel terminale del nuovo progetto run: npm install";
        topLayout.add(htmlService.getSpan(new WrapSpan().message(message).weight(AEFontWeight.bold).color(AETypeColor.rosso)));

        //        message = "Aggiungi il nuovo progetto alla enumeration AEProgetto.";
        //        topLayout.add(htmlService.getSpan(new WrapSpan().message(message).weight(AEFontWeight.bold).color(AETypeColor.rosso)));
        //
        //        message = "Per i nuovi progetti directory/modulo coincidono col nome del progetto.";
        //        topLayout.add(htmlService.getSpan(new WrapSpan().message(message).weight(AEFontWeight.bold).color(AETypeColor.blu)));
        //
        //        message = "Possono essere differenziati nella enumeration AEProgetto.";
        //        topLayout.add(htmlService.getSpan(new WrapSpan().message(message).weight(AEFontWeight.bold).color(AETypeColor.blu)));
    }


    /**
     * Sezione centrale con la scelta del progetto <br>
     * Spazzola la directory 'ideaProjects' <br>
     * Recupera i possibili progetti 'vuoti' <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void creaSelezioneLayout() {
        selezioneLayout = fixSezione("Selezione...");
        this.add(selezioneLayout);

        String message;
        String pathDirectory = System.getProperty("user.dir");
        pathDirectory = fileService.findPathDirectory(pathDirectory, "operativi");
        //        List<File> progetti = fileService.getEmptyProjects(pathDirectory);
        List<File> progetti = fileService.getAllProjects(pathDirectory);

        fieldComboProgettiNuovi = new ComboBox<>();
        fieldComboProgettiNuovi.setRequired(true);
        fieldComboProgettiNuovi.setClearButtonVisible(true);
        // Choose which property from Department is the presentation value
        fieldComboProgettiNuovi.setItemLabelGenerator(File::getName);
        fieldComboProgettiNuovi.setWidth("24em");
        fieldComboProgettiNuovi.setAllowCustomValue(false);
        fieldComboProgettiNuovi.setLabel(LABEL_COMBO_UNO);

        fieldComboProgettiNuovi.setItems(progetti);
        if (progetti.size() == 1) {
            fieldComboProgettiNuovi.setValue(progetti.get(0));
            sincroProjectNuovi(progetti.get(0));
        }
        fieldComboProgettiNuovi.addValueChangeListener(event -> sincroProjectNuovi(event.getValue()));

        HorizontalLayout rigaLayout = new HorizontalLayout();
        rigaLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        rigaLayout.add(fieldComboProgettiNuovi);

        if (progetti.size() > 0) {
            selezioneLayout.add(rigaLayout);
        }
        else {
            selezioneLayout.add(htmlService.getSpan(new WrapSpan().message(LABEL_COMBO_UNO)));
            message = "Non ci sono progetti vuoti nella directory.";
            selezioneLayout.add(htmlService.getSpan(new WrapSpan().message(message).weight(AEFontWeight.bold).color(AETypeColor.rosso)));
            sincroProjectNuovi(null);
        }
    }

    protected void creaCheckBoxLayout() {
    }

    @Override
    protected void creaBottomLayout() {
        super.creaBottomLayout();
        this.add(spanConferma);
    }

    protected void creaBottoni() {
        super.creaBottoni();

        cancelButton.getElement().setAttribute("theme", "secondary");
        confirmButton.getElement().setAttribute("theme", "primary");

        spanConferma = new HorizontalLayout();
    }


    private void sincroProjectNuovi(File valueFromProject) {
        String message;
        boolean progettoSelezionato = valueFromProject != null;
        String project = progettoSelezionato ? valueFromProject.getName() : TRE_PUNTI;

        confirmButton.setEnabled(progettoSelezionato);

        spanConferma.removeAll();
        message = String.format("Confermando viene creato un 'modulo' vaadin23 nel progetto %s", project);
        if (progettoSelezionato) {
            spanConferma.add(htmlService.getSpan(new WrapSpan().message(message).color(AETypeColor.rosso)));
        }
        else {
            spanConferma.add(htmlService.getSpan(new WrapSpan().message(message).style(AEFontStyle.italic)));
        }
    }

    /**
     * Apertura del dialogo <br>
     */
    public void open(final Consumer<String> confirmHandler) {
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
            confirmHandler.accept(fieldComboProgettiNuovi.getValue().getPath());
        }
    }

    /**
     * Chiamato alla dismissione del dialogo <br>
     * <p>
     * Recupera il nome del progetto selezionato dal combobox <br>
     * Inserisce il valore base di nameTargetProjectUpper <br>
     * <p>
     * Elabora, con AEWizCost.set() tutti i valori 'derivati' di AEWizCost <br>
     * Regola i flag acceso=true/false della Enumeration AEWizCost <br>
     * Verranno usati da: <br>
     * WizElaboraNewProject, WizElaboraUpdateProject,WizElaboraNewPackage, WizElaboraUpdatePackage <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    @Override
    protected boolean regolaAEWizCost() {
        File pathProjectFile;
        String pathProject = VUOTA;
        String projectNameUpper = VUOTA;

        //        //--recupera il nome del progetto selezionato dal combobox (obbligatorio)
        //        pathProjectFile = fieldComboProgettiNuovi != null ? fieldComboProgettiNuovi.getValue() : null;
        //        if (pathProjectFile == null || text.isEmpty(pathProjectFile.getAbsolutePath())) {
        //            return false;
        //        }
        //
        //        //--inserisce il path completo del progetto selezionato nella Enumeration
        //        //--dal path completo deriva il valore di directory/modulo -> nameTargetProjectModulo
        //        //--mentre il nome (maiuscolo) del progetto deve essere inserito -> nameTargetProjectUpper
        //        //--perché potrebbe essere diverso (Es. vaadwiki -> Wiki)
        //        pathProject = pathProjectFile.getAbsolutePath() + SLASH;
        //
        //        //--recupera il nome (maiuscolo) del progetto presente nel textEditField (obbligatorio)
        //        projectNameUpper = fieldProjectNameUpper != null ? text.primaMaiuscola(fieldProjectNameUpper.getValue()) : null;
        //
        //        //--recupera i flag selezionati a video
        //        for (AEWizCost aeCost : wizService.getAll()) {
        //            if (mappaWizBox != null && mappaWizBox.get(aeCost.name()) != null) {
        //                aeCost.setAcceso(mappaWizBox.get(aeCost.name()).is());
        //            }
        //        }
        //
        //        //--regola il flag per far costruire il modulo base
        //        AEWizCost.pathTargetProjectModulo.setAcceso(true);
        //
        //        //--regola i flags per far costruire il modulo specifico
        //        AEModulo.fileMain.setAcceso(true);
        //        AEModulo.fileCost.setAcceso(true);
        //        AEModulo.fileBoot.setAcceso(true);
        //        AEModulo.fileData.setAcceso(true);
        //
        //        //--inserisce un valore fondamentale di nameTargetProjectModulo che DI NORMA è uguale a nameTargetProjectUpper
        //        AEWizCost.nameTargetProjectModulo.setValue(text.primaMinuscola(projectNameUpper));
        //
        //        //--inserisce i valori fondamentali (3) e poi regola tutti i valori automatici derivati
        return super.fixValoriInseriti(pathProject, projectNameUpper, VUOTA);
    }

    /**
     * Chiamato alla dismissione del dialogo <br>
     * Resetta i valori regolabili della Enumeration AEDir <br>
     * Elabora tutti i valori della Enumeration AEDir dipendenti dal nome del progetto <br>
     * Verranno usati da WizElaboraNewProject e WizElaboraUpdateProject <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected boolean regolaAEDir() {
        boolean status = true;
        String projectName;
        super.regolaAEDir();

        //        if (fieldComboProgettiNuovi != null && fieldComboProgettiNuovi.getValue() != null) {
        //            projectName = fieldComboProgettiNuovi.getValue().getName();
        //            status = status && AEDir.modificaProjectAll(projectName);
        //        }

        return status;
    }


}

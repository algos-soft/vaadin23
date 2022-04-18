package it.algos.vaad23.wizard.enumeration;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;

import java.util.*;


/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 04-nov-2020
 * Time: 18:56
 */
public enum AEWizProject {

    config("Directory CONFIG di risorse on-line esterne al JAR (vaadin23)", true, "config", AECopy.dirAddingOnly),

    documentation("Directory DOC di documentazione (vaadin23)", true, "doc", AECopy.dirAddingOnly),

    frontend("Directory FRONTEND del Client (vaadin23) [need riavvio]", false, "frontend", AECopy.dirAddingOnly),

    links("Directory LINKS a siti web utili (vaadin23)", true, "links", AECopy.dirAddingOnly),

    snippets("Directory SNIPPETS di codice suggerito (vaadin23)", true, "snippets", AECopy.dirAddingOnly),

    flow("Directory BASE Vaad23 (Wizard compreso)", false, "src/main/java/it/algos/vaad23", AECopy.dirDeletingAll),

    //    projectNew("Directory modulo del nuovo progetto (...)", false, VUOTA, AECopy.dirAddingOnly),

    resources("Directory RESOURCES (vaadin23)", false, "src/main/resources", AECopy.dirAddingOnly),

    property("File application.PROPERTIES (sources)", false, "src/main/resources/application.properties",
            AECopy.sourceSovrascriveSempreAncheSeEsiste, "properties"
    ),

    banner("File BANNER di SpringBoot (sources) [need riavvio]", false, "src/main/resources/banner.txt", AECopy.sourceSoloSeNonEsiste,
            "banner"
    ),

    git("File GIT di esclusione (sources)", false, ".gitignore", AECopy.sourceSoloSeNonEsiste, "git"),

    pom("File POM.xml di Maven (sources)", false, "pom.xml", AECopy.sourceSovrascriveSempreAncheSeEsiste, "pom"),

    read("File README con note di testo (sources)", false, "README.md", AECopy.sourceSoloSeNonEsiste, "readme"),

    test("Directory Test (vaadin23)", false, "src/test/java/it/algos", AECopy.dirAddingOnly),
    application("Main class java", false, "src/main/java/it/algos/@PROJECTUPPER@Application.java",
            AECopy.sourceSovrascriveSempreAncheSeEsiste, "application"
    ),
    boot("xxxBoot: con fixMenuRoutes()", true, "src/main/java/it/algos/@PROJECT@/backend/boot/@PROJECTUPPER@Boot.java",
            AECopy.sourceSovrascriveSempreAncheSeEsiste, "boot"
    ),
    cost("xxxCost: costanti statiche del programma", true, "src/main/java/it/algos/@PROJECT@/backend/boot/@PROJECTUPPER@Cost.java",
            AECopy.sourceSovrascriveSempreAncheSeEsiste, "cost"
    ),
    vers("xxxVers: versioni specifiche del programma", true, "src/main/java/it/algos/@PROJECT@/backend/boot/@PROJECTUPPER@Vers.java",
            AECopy.sourceSovrascriveSempreAncheSeEsiste, "vers"
    ),
    ;


    private String caption;

    private boolean accesoInizialmente;

    private String copyDest;

    private String fileSource;

    private AECopy copy;

    private boolean acceso;


    AEWizProject(final String caption, final boolean accesoInizialmente, final String copyDest, final AECopy copy) {
        this(caption, accesoInizialmente, copyDest, copy, VUOTA);
    }

    AEWizProject(final String caption, final boolean accesoInizialmente, final String copyDest, final AECopy copy, final String fileSource) {
        this.caption = caption;
        this.accesoInizialmente = accesoInizialmente;
        this.acceso = accesoInizialmente;
        this.copyDest = copyDest;
        this.copy = copy;
        this.fileSource = fileSource;
    }

    public static List<AEWizProject> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    public static List<AEWizProject> getAllNewProject() {
        ArrayList<AEWizProject> lista = new ArrayList<>();
        lista.add(AEWizProject.config);
        lista.add(AEWizProject.frontend);
        lista.add(AEWizProject.flow);
        lista.add(AEWizProject.pom);
        lista.add(AEWizProject.application);
        lista.add(AEWizProject.resources);
        lista.add(AEWizProject.property);
        lista.add(AEWizProject.boot);

        return lista;
    }


    /**
     * Ripristina il valore di default <br>
     */
    public static void reset() {
        for (AEWizProject aeCheck : AEWizProject.values()) {
            aeCheck.acceso = false;
        }
    }


    public boolean is() {
        return acceso && copyDest != null && copyDest.length() > 0;
    }


    public String getCaption() {
        return caption;
    }

    public boolean isAccesoInizialmente() {
        return accesoInizialmente;
    }

    public String getCopyDest() {
        return copyDest;
    }

    public AECopy getCopy() {
        return copy;
    }

    public String getFileSource() {
        return fileSource;
    }
}


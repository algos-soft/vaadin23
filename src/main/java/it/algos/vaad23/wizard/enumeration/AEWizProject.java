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

    config("Directory CONFIG di risorse on-line esterne al JAR (VaadFlow)", true, "config", AECopy.dirAddingOnly),

    documentation("Directory DOC di documentazione (VaadFlow)", true, "doc", AECopy.dirAddingOnly),

    frontend("Directory FRONTEND del Client (VaadFlow)", true, "frontend", AECopy.dirAddingOnly),

    links("Directory LINKS a siti web utili (VaadFlow)", true, "links", AECopy.dirAddingOnly),

    snippets("Directory SNIPPETS di codice suggerito (VaadFlow)", true, "snippets", AECopy.dirAddingOnly),

    flow("Directory BASE di VaadFlow (Wizard compreso)", true, "src/main/java/it/algos/vaad23", AECopy.dirAddingOnly),

    projectNew("Directory modulo del nuovo progetto (...)", true, VUOTA, AECopy.dirAddingOnly),

    resources("Directory RESOURCES (VaadFlow)", true, "src/main/resources", AECopy.dirAddingOnly),

    property("File application.PROPERTIES (sources)", true, "src/main/resources/application.properties",
            AECopy.sourceSovrascriveSempreAncheSeEsiste, "properties"
    ),

    banner("File BANNER di SpringBoot (sources)", true, "src/main/resources/banner.txt", AECopy.sourceSovrascriveSempreAncheSeEsiste,
            "banner"
    ),

    git("File GIT di esclusione (sources)", true, ".gitignore", AECopy.sourceSovrascriveSempreAncheSeEsiste, "git"),

    pom("File POM.xml di Maven (sources)", true, "pom.xml", AECopy.sourceSovrascriveSempreAncheSeEsiste, "pom"),

    read("File README con note di testo (sources)", true, "README.md", AECopy.sourceSovrascriveSempreAncheSeEsiste, "readme"),

    test("Directory Test (VaadFlow)", false, "src/test/java/it/algos", AECopy.dirAddingOnly),
    application("Main class java", true, "src/main/java/it/algos/@PROJECTUPPER@Application.java",
            AECopy.sourceSovrascriveSempreAncheSeEsiste, "application"
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


    public static List<String> getAllStringValues() {
        List<String> listaValues = new ArrayList<>();

        getAllEnums().forEach(font -> listaValues.add(font.toString()));
        return listaValues;
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


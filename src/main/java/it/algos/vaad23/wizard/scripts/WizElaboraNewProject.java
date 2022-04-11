package it.algos.vaad23.wizard.scripts;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.interfaces.*;
import it.algos.vaad23.backend.wrapper.*;
import it.algos.vaad23.wizard.enumeration.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;


/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 13-apr-2020
 * Time: 05:31
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WizElaboraNewProject extends WizElabora {

    public static final String SOURCE_PREFIX = "src/main/java/it/algos/";

    public static final String SOURCE_SUFFFIX = "/wizard/sources/";

    public static final String VAADIN_PROJECT = "vaadin23";

    public static final String VAADIN_MODULE = "vaad23";

    private String srcVaadin23;

    private String destNewProject;

    private String newProject;

    public void esegue(final String pathNewProject) {
        String message;
        srcVaadin23 = System.getProperty("user.dir") + SLASH;
        destNewProject = pathNewProject + SLASH;
        newProject = fileService.estraeDirectoryFinaleSenzaSlash(destNewProject).toLowerCase();
        logger.info(new WrapLog().type(AETypeLog.spazio));

        AEToken.reset();
        AEToken.setCrono();
        AEToken.targetProject.set(newProject);
        AEToken.targetProjectUpper.set(textService.primaMaiuscola(newProject));

        for (AEWizProject wiz : AEWizProject.getAllEnums()) {
            if (wiz.is()) {
                switch (wiz.getCopy().getType()) {
                    case directory -> directory(wiz);
                    case file -> file(wiz);
                    case source -> source(wiz);
                }
            }
            else {
                message = String.format("New project: %s%snon abilitato", textService.primaMinuscola(wiz.getCaption()), FORWARD);
                logger.info(new WrapLog().message(message).type(AETypeLog.wizard));
            }
        }

        //--elimina la directory 'sources' che deve restare unicamente nel progetto 'vaadin23' e non nei derivati
        if (AEWizProject.flow.is()) {
            if (fileService.deleteDirectory(destNewProject + SOURCE_PREFIX + VAADIN_MODULE + SOURCE_SUFFFIX)) {
                message = String.format("Cancellata la directory 'sources' nel progetto %s", newProject);
                logger.info(new WrapLog().message(message).type(AETypeLog.wizard));
            }
        }
    }

    public void directory(final AEWizProject wiz) {
        String message;
        String srcPath = srcVaadin23 + wiz.getCopyDest();
        String destPath = destNewProject + wiz.getCopyDest();

        fileService.copyDirectory(wiz.getCopy(), srcPath, destPath);
        message = String.format("New project: %s%s%s", textService.primaMinuscola(wiz.getCaption()), FORWARD, wiz.getCopy().getDescrizione());
        logger.info(new WrapLog().message(message).type(AETypeLog.wizard));
    }

    public void file(final AEWizProject wiz) {
    }


    public void source(final AEWizProject wiz) {
        String dest = wiz.getCopyDest();
        String nomeFile = wiz.getFileSource();
        String sorcePath = srcVaadin23 + SOURCE_PREFIX + VAADIN_MODULE + SOURCE_SUFFFIX + nomeFile;
        sorcePath += sorcePath.endsWith(TXT_SUFFIX) ? VUOTA : TXT_SUFFIX;
        String sourceText = fileService.leggeFile(sorcePath);
        sourceText = AEToken.replaceAll(sourceText);
        String destPath = destNewProject + dest;
        destPath = AEToken.replaceAll(destPath);
        file(wiz.getCopy(), destPath, sourceText);
        fileService.scriveFile(destPath, sourceText, false);
    }

    public void file(final AECopy copy, final String destPath, final String sourceText) {
        String message;
        AIResult result;
        boolean esiste = fileService.isEsisteFile(destPath);

        switch (copy) {
            case sourceSovrascriveSempreAncheSeEsiste -> {
                result = fileService.scriveFile(destPath, sourceText, true);
                if (result.isValido()) {
                    if (esiste) {
                        message = String.format("Il file %s esisteva già ed è stato modificato", destPath);
                        logger.info(new WrapLog().type(AETypeLog.wizard).message(message));
                    }
                    else {
                        message = String.format("Il file %s non esisteva ed è stato creato", destPath);
                        logger.info(new WrapLog().type(AETypeLog.wizard).message(message));
                    }
                }
                else {
                    logger.warn(new WrapLog().type(AETypeLog.wizard).message(result.getMessage()));
                }
            }
            case sourceCheckFlagSeEsiste -> {}
            case sourceSoloSeNonEsiste -> {
                if (esiste) {
                    message = String.format("Il file %s esisteva già e non è stato modificato", destPath);
                    logger.info(new WrapLog().type(AETypeLog.wizard).message(message));
                }
                else {
                    result = fileService.scriveNewFile(destPath, sourceText);
                    if (result.isValido()) {
                        message = String.format("Il file %s non esisteva ed è stato creato", destPath);
                        logger.info(new WrapLog().type(AETypeLog.wizard).message(message));
                    }
                    else {
                        logger.warn(new WrapLog().type(AETypeLog.wizard).message(result.getMessage()));
                    }
                }
            }
            default -> {}
        }
    }

}

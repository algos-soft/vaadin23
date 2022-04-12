package it.algos.vaad23.wizard.scripts;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import static it.algos.vaad23.wizard.scripts.WizCost.*;
import static it.algos.vaad23.wizard.scripts.WizElaboraNewProject.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project sette
 * Created by Algos
 * User: gac
 * Date: lun, 11-apr-2022
 * Time: 17:18
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WizElaboraFeedBack extends WizElabora {

    public void esegue() {
        boolean esisteSrc = false;
        boolean esisteDest = false;
        String dirOperativi = "operativi/";
        String srcWizardProject = System.getProperty("user.dir");
        String currentProject = fileService.estraeClasseFinaleSenzaJava(srcWizardProject).toLowerCase();
        String destBaseVaadin23 = textService.levaCoda(srcWizardProject, currentProject);

        destBaseVaadin23 += dirOperativi + "vaadin23" + SLASH;

        //        String currentProject = fileService.estraeClasseFinaleSenzaJava(srcWizardProject).toLowerCase();

        //        String srcVaadin23 = System.getProperty("user.dir");
        //        String message;
        //        String module = "xxx";
        //        String pathSrc = System.getProperty("user.dir");
        //        String pathWizardProject = srcWizardProject + "src/main/java/it/algos/vaad23/wizard/";

        String srcWizard = String.format("%s%s%s%s%s%s", srcWizardProject, SLASH, SOURCE_PREFIX, VAADIN_MODULE, SLASH, DIR_WIZARD);
        String destWizard = String.format("%s%s%s%s%s", destBaseVaadin23, SOURCE_PREFIX, VAADIN_MODULE, SLASH, DIR_WIZARD);
        //        String nomeFile = wiz.getFileSource();
        //        String sorcePath = srcVaadin23 + SOURCE_PREFIX + VAADIN_MODULE + SOURCE_SUFFFIX + nomeFile;

        esisteSrc = fileService.isEsisteDirectory(srcWizard);
        esisteDest = fileService.isEsisteDirectory(destWizard);

        if (esisteSrc && esisteDest) {
            fileService.copyDirectory(AECopy.dirAddingOnly, srcWizard, destWizard);
        }
        else {
            int add = 87888888;
        }

        //        //        String destPath = destNewProject + wiz.getCopyDest();
        //        //
        //        //        fileService.copyDirectory(wiz.getCopy(), srcPath, destPath);
        //        //        message = String.format("New project: %s%s%s", textService.primaMinuscola(wiz.getCaption()), FORWARD, wiz.getCopy().getDescrizione());
        //        //        logger.info(new WrapLog().message(message).type(AETypeLog.wizard));
        //
        //        for (AEWizProject wiz : AEWizProject.getAllEnums()) {
        //            if (wiz.is()) {
        //                switch (AEWizProject.getAllNewProject()) {
        //                    case directory -> directory(wiz);
        //                    case file -> file(wiz);
        //                    case source -> source(wiz);
        //                }
        //            }
        //            else {
        //                message = String.format("New project: %s%snon abilitato", textService.primaMinuscola(wiz.getCaption()), FORWARD);
        //                logger.info(new WrapLog().message(message).type(AETypeLog.wizard));
        //            }
        //        }
    }

}

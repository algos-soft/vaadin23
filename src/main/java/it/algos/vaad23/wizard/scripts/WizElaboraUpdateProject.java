package it.algos.vaad23.wizard.scripts;

import com.vaadin.flow.component.checkbox.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.wrapper.*;
import it.algos.vaad23.wizard.enumeration.*;
import static it.algos.vaad23.wizard.scripts.WizElaboraNewProject.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;

/**
 * Project vaadbio
 * Created by Algos
 * User: gac
 * Date: mer, 13-apr-2022
 * Time: 06:49
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WizElaboraUpdateProject extends WizElabora {

    private String srcVaadin23;

    private String destNewProject;

    private String newProject;

    public void esegue(final LinkedHashMap<String, Checkbox> mappaCheckbox) {
        String message;
        AEWizProject wiz;
        String dirOperativi = "operativi/";
        destNewProject = System.getProperty("user.dir");
        String currentProject = fileService.estraeClasseFinaleSenzaJava(destNewProject).toLowerCase();
        srcVaadin23 = textService.levaCoda(destNewProject, currentProject);
        srcVaadin23 += dirOperativi + "vaadin23" + SLASH;
        destNewProject += SLASH;

        logger.info(new WrapLog().type(AETypeLog.spazio));

        AEToken.reset();
        AEToken.setCrono();
        AEToken.targetProject.set(currentProject);
        AEToken.targetProjectUpper.set(textService.primaMaiuscola(currentProject));

        for (String key : mappaCheckbox.keySet()) {
            if (mappaCheckbox.get(key).getValue()) {
                wiz = AEWizProject.valueOf(key);
                if (wiz != null) {
                    switch (wiz.getCopy().getType()) {
                        case directory -> directory(wiz);
                        case file -> file(wiz);
                        case source -> source(wiz);
                    }
                }
            }
        }

        //        //--elimina la directory 'sources' che deve restare unicamente nel progetto 'vaadin23' e non nei derivati
        //        if (AEWizProject.flow.is()) {
        //            if (fileService.deleteDirectory(destNewProject + SOURCE_PREFIX + VAADIN_MODULE + SOURCE_SUFFFIX)) {
        //                message = String.format("Cancellata la directory 'sources' nel progetto %s", newProject);
        //                logger.info(new WrapLog().message(message).type(AETypeLog.wizard));
        //            }
        //        }

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
        fileService.scriveFile(destPath, sourceText, true);
    }

}

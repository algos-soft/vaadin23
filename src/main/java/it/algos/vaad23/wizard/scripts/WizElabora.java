package it.algos.vaad23.wizard.scripts;

import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.interfaces.*;
import it.algos.vaad23.backend.service.*;
import it.algos.vaad23.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: sab, 09-apr-2022
 * Time: 07:26
 */
public abstract class WizElabora {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public LogService logger;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public TextService textService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public FileService fileService;


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

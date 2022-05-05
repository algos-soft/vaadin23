package it.algos.vaad23.backend.service;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.boot.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.exception.*;
import it.algos.vaad23.backend.wrapper.*;
import org.apache.commons.io.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;


/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: dom, 28-giu-2020
 * Time: 15:10
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AAbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AAnnotationService.class); <br>
 * 3) @Autowired private AArrayService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FileService extends AbstractService {

    public static final String PARAMETRO_NULLO = "Il parametro in ingresso è nullo";

    public static final String PATH_NULLO = "Il path in ingresso è nullo";

    public static final String PATH_VUOTO = "Il path in ingresso è vuoto";

    public static final String PATH_NOT_ABSOLUTE = "Il primo carattere del path NON è uno '/' (slash)";

    public static final String NON_ESISTE_FILE = "Il file non esiste";

    public static final String PATH_SENZA_SUFFIX = "Manca il 'suffix' terminale";

    public static final String PATH_FILE_ESISTENTE = "Esiste già il file";

    public static final String NON_E_FILE = "Non è un file";

    public static final String DIRECTORY_NOT_FILE = "Directory e non file";

    public static final String DIRECTORY_MANCANTE = "Mancava la directory per il file, ma è stata creata";

    public static final String CREATO_FILE = "Il file è stato creato";

    public static final String NON_CREATO_FILE = "Il file non è stato creato";

    public static final String NON_COPIATO_FILE = "Il file non è stato copiato";

    public static final String CANCELLATO_FILE = "Il file è stato cancellato";

    public static final String NON_CANCELLATO_FILE = "Il file non è stato cancellato";

    public static final String CANCELLATA_DIRECTORY = "La directory è stata cancellata";

    public static final String NON_CANCELLATA_DIRECTORY = "La directory non è stata cancellata";

    public static final String NON_ESISTE_DIRECTORY = "La directory non esiste";

    public static final String CREATA_DIRECTORY = "La directory è stata creata";

    public static final String NON_CREATA_DIRECTORY = "La directory non è stata creata";

    public static final String NON_E_DIRECTORY = "Non è una directory";

    public static final String DIR_PROGETTO = "/src/";

    public static final String DIR_PROGETTO_VUOTO = "/src/main/java/";

    /**
     * versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


    /**
     * Controlla l'esistenza di una directory
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo e iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     * Una volta costruita la directory, getPath() e getAbsolutePath() devono essere uguali
     *
     * @param directoryToBeChecked con path completo che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se il file esiste
     */
    public AResult checkDirectory(final File directoryToBeChecked) {
        AResult result = checkFileDirectory("checkDirectory", directoryToBeChecked);
        if (result.isErrato()) {
            return result;
        }
        String message;

        if (directoryToBeChecked.exists()) {
            if (directoryToBeChecked.isDirectory()) {
                message = String.format("Trovata la directory %s", directoryToBeChecked.getAbsolutePath());
                logger.info(new WrapLog().exception(new AlgosException(message)).type(AETypeLog.file));
                return result.validMessage(message);
            }
            else {
                message = String.format("%s%s%s", NON_E_DIRECTORY, FORWARD, directoryToBeChecked.getAbsolutePath());
                logger.error(new WrapLog().exception(new AlgosException(message)).type(AETypeLog.file));
                return result.errorMessage(message);
            }
        }
        else {
            message = String.format("%s%s%s", NON_ESISTE_DIRECTORY, FORWARD, directoryToBeChecked.getAbsolutePath());
            logger.info(new WrapLog().exception(new AlgosException(message)).type(AETypeLog.file));
            return result.errorMessage(message);
        }
    }

    /**
     * Controlla l'esistenza di una directory <br>
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo e iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * Controlla che getPath() e getAbsolutePath() siano uguali <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     * Una volta costruita la directory, getPath() e getAbsolutePath() devono essere uguali
     *
     * @param absolutePathDirectoryToBeChecked path completo della directory che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se la directory esiste
     */
    public AResult checkDirectory(final String absolutePathDirectoryToBeChecked) {
        AResult result = checkPath("checkDirectory", absolutePathDirectoryToBeChecked);
        if (result.isErrato()) {
            return result;
        }

        return checkDirectory(new File(absolutePathDirectoryToBeChecked));
    }


    /**
     * Controlla l'esistenza di una directory <br>
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * Controlla che getPath() e getAbsolutePath() siano uguali <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     * Una volta costruita la directory, getPath() e getAbsolutePath() devono essere uguali
     *
     * @param absolutePathDirectoryToBeChecked path completo della directory che DEVE cominciare con '/' SLASH
     *
     * @return true se la directory esiste, false se non sono rispettate le condizioni della richiesta
     */
    public boolean isEsisteDirectory(final String absolutePathDirectoryToBeChecked) {
        return checkDirectory(absolutePathDirectoryToBeChecked).isValido();
    }


    /**
     * Controlla l'esistenza di un file <br>
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo e iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * Controlla che getPath() e getAbsolutePath() siano uguali <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param fileToBeChecked con path completo che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se il file esiste
     */
    public AResult checkFile(final File fileToBeChecked) {
        AResult result = checkFileDirectory("checkFile", fileToBeChecked);
        if (result.isErrato()) {
            return result;
        }
        String message;

        if (fileToBeChecked.exists()) {
            if (fileToBeChecked.isFile()) {
                message = String.format("Trovato il file %s", fileToBeChecked.getAbsolutePath());
                logger.info(new WrapLog().exception(new AlgosException(message)).type(AETypeLog.file));
                return result.validMessage(message);
            }
            else {
                message = String.format("%s%s%s", NON_E_FILE, FORWARD, fileToBeChecked.getAbsolutePath());
                logger.error(new WrapLog().exception(new AlgosException(message)).type(AETypeLog.file));
                return result.errorMessage(message);
            }
        }
        else {
            if (this.isNotSuffix(fileToBeChecked.getAbsolutePath())) {
                message = String.format("%s al file %s", PATH_SENZA_SUFFIX, fileToBeChecked.getAbsolutePath());
                logger.error(new WrapLog().exception(new AlgosException(message)).usaDb().type(AETypeLog.file));
                return result.errorMessage(message);
            }

            if (!fileToBeChecked.exists()) {
                message = String.format("%s%s%s", NON_ESISTE_FILE, FORWARD, fileToBeChecked.getAbsolutePath());
                logger.info(new WrapLog().exception(new AlgosException(message)).usaDb().type(AETypeLog.file));
                return result.errorMessage(message);
            }

            message = String.format("Errore generico nel check del file %s", fileToBeChecked.getAbsolutePath());
            logger.error(new WrapLog().exception(new AlgosException(message)).usaDb().type(AETypeLog.file));
            return result.errorMessage(message);
        }
    }


    /**
     * Controlla l'esistenza di un file <br>
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param absolutePathFileWithSuffixToBeChecked path completo del file che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se il file esiste
     */
    public AResult checkFile(final String absolutePathFileWithSuffixToBeChecked) {
        String message;
        AResult result = checkPath("checkFile", absolutePathFileWithSuffixToBeChecked);
        if (result.isErrato()) {
            return result;
        }

        if (this.isSlashFinale(absolutePathFileWithSuffixToBeChecked)) {
            message = String.format("%s%s%s", DIRECTORY_NOT_FILE, FORWARD, absolutePathFileWithSuffixToBeChecked);
            logger.error(new WrapLog().exception(new AlgosException(message)).usaDb().type(AETypeLog.file));
            return result.errorMessage(message);
        }

        return checkFile(new File(absolutePathFileWithSuffixToBeChecked));
    }


    /**
     * Controlla l'esistenza di un file <br>
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param absolutePathFileWithSuffixToBeChecked path completo del file che DEVE cominciare con '/' SLASH
     *
     * @return true se il file esiste, false se non sono rispettate le condizioni della richiesta
     */
    public boolean isEsisteFile(final String absolutePathFileWithSuffixToBeChecked) {
        return checkFile(absolutePathFileWithSuffixToBeChecked).isValido();
    }


    /**
     * Controlla l'esistenza di un file <br>
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param pathDirectoryToBeChecked path completo della directory che DEVE cominciare con '/' SLASH
     * @param fileName                 da controllare
     *
     * @return true se il file esiste, false se non sono rispettate le condizioni della richiesta
     */
    public boolean isEsisteFile(final String pathDirectoryToBeChecked, final String fileName) {
        return isEsisteFile(pathDirectoryToBeChecked + SLASH + fileName);
    }


    /**
     * Crea una nuova directory
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo e iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param directoryToBeCreated con path completo che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se il file è stato creato
     */
    public AResult creaDirectory(final File directoryToBeCreated) {
        AResult result = checkFileDirectory("creaDirectory", directoryToBeCreated);
        if (result.isErrato()) {
            return result;
        }
        String message;

        if (!this.isNotSuffix(directoryToBeCreated.getAbsolutePath())) {
            message = String.format("%s%s%s", NON_E_DIRECTORY, FORWARD, directoryToBeCreated.getAbsolutePath());
            logger.error(new WrapLog().exception(new AlgosException(message)).type(AETypeLog.file));
            return result.errorMessage(message);
        }

        try {
            directoryToBeCreated.mkdirs();
            message = String.format("%s%s%s", CREATA_DIRECTORY, FORWARD, directoryToBeCreated.getAbsolutePath());
            logger.info(new WrapLog().exception(new AlgosException(message)).type(AETypeLog.file));
            return result.validMessage(message);
        } catch (Exception unErrore) {
            message = String.format("%s%s%s", NON_CREATA_DIRECTORY, FORWARD, directoryToBeCreated.getAbsolutePath());
            logger.error(new WrapLog().exception(unErrore).usaDb().message(message));
            return result.errorMessage(message);
        }
    }


    /**
     * Crea una nuova directory
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo e iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param absolutePathDirectoryToBeCreated path completo della directory che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se il file è stato creato
     */
    public AResult creaDirectory(final String absolutePathDirectoryToBeCreated) {
        AResult result = checkPath("creaDirectory", absolutePathDirectoryToBeCreated);
        if (result.isErrato()) {
            return result;
        }

        return creaDirectory(new File(absolutePathDirectoryToBeCreated));
    }


    /**
     * Crea un nuovo file
     * <p>
     * Il file DEVE essere costruita col path completo, altrimenti assume che sia nella directory in uso corrente
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo e iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     * Se manca la directory, viene creata dal System <br>
     *
     * @param fileToBeCreated con path completo che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se il file è stato creato
     */
    public AResult creaFile(final File fileToBeCreated) {
        AResult result = checkFileDirectory("creaFile", fileToBeCreated);
        if (result.isErrato()) {
            return result;
        }
        String message;

        if (this.isNotSuffix(fileToBeCreated.getAbsolutePath())) {
            message = String.format("%s%s%s", PATH_SENZA_SUFFIX, FORWARD, fileToBeCreated.getAbsolutePath());
            logger.error(new WrapLog().exception(new AlgosException(message)).type(AETypeLog.file));
            return result.errorMessage(message);
        }

        try {
            fileToBeCreated.createNewFile();
            message = String.format("%s%s%s", CREATO_FILE, FORWARD, fileToBeCreated.getAbsolutePath());
            logger.info(new WrapLog().exception(new AlgosException(message)).type(AETypeLog.file));
            return result.validMessage(message);
        } catch (Exception unErrore) {
            return creaDirectoryParentAndFile(fileToBeCreated);
        }
    }


    /**
     * Crea un nuovo file
     * <p>
     * Il file DEVE essere costruita col path completo, altrimenti assume che sia nella directory in uso corrente
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     * Se manca la directory, viene creata dal System <br>
     *
     * @param absolutePathFileWithSuffixToBeCreated path completo del file che DEVE cominciare con '/' SLASH e compreso il suffisso
     *
     * @return testo di errore, vuoto se il file è stato creato
     */
    public AResult creaFile(final String absolutePathFileWithSuffixToBeCreated) {
        AResult result = checkPath("creaFile", absolutePathFileWithSuffixToBeCreated);
        if (result.isErrato()) {
            return result;
        }

        return creaFile(new File(absolutePathFileWithSuffixToBeCreated));
    }


    /**
     * Creazioni di una directory 'parent' <br>
     * Se manca il path completo alla creazione di un file, creo la directory 'parent' di quel file <br>
     * Riprovo la creazione del file <br>
     */
    public AResult creaDirectoryParentAndFile(final File fileToBeCreated) {
        AResult result = AResult.build().method("creaDirectoryParentAndFile").target(fileToBeCreated.getAbsolutePath());
        String message;
        String parentDirectoryName = VUOTA;
        File parentDirectoryFile;
        boolean parentDirectoryCreata = false;

        if (fileToBeCreated != null) {
            parentDirectoryName = fileToBeCreated.getParent();
            parentDirectoryFile = new File(parentDirectoryName);
            parentDirectoryCreata = parentDirectoryFile.mkdirs();
        }

        if (parentDirectoryCreata) {
            message = String.format("%s%s%s", DIRECTORY_MANCANTE, FORWARD, parentDirectoryName + SLASH);
            logger.info(new WrapLog().exception(new AlgosException(message)).type(AETypeLog.file));
            try {
                fileToBeCreated.createNewFile();
                message = String.format("%s%s%s", CREATO_FILE, FORWARD, fileToBeCreated.getAbsolutePath());
                logger.info(new WrapLog().exception(new AlgosException(message)).type(AETypeLog.file));
                return result.validMessage(message);
            } catch (Exception unErrore) {
                message = String.format("Errore nel path per la creazione del file %s", fileToBeCreated.getAbsolutePath());
                logger.error(new WrapLog().exception(unErrore).message(message).type(AETypeLog.file));
                return result.errorMessage(message);
            }
        }
        else {
            message = String.format("Non sono riuscito a creare la directory necessaria per il file %s", fileToBeCreated.getAbsolutePath());
            logger.error(new WrapLog().exception(new AlgosException(message)).type(AETypeLog.file));
            return result.errorMessage(message);
        }
    }


    /**
     * Cancella una directory
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo e iniziare con uno 'slash' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param directoryToBeDeleted con path completo che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se la directory è stata cancellata
     */
    public AResult deleteDirectory(final File directoryToBeDeleted) {
        AResult result = checkFileDirectory("deleteDirectory", directoryToBeDeleted);
        if (result.isErrato()) {
            return result;
        }
        String message;

        if (!directoryToBeDeleted.exists()) {
            message = String.format("%s%s%s", NON_ESISTE_DIRECTORY, FORWARD, directoryToBeDeleted.getAbsolutePath());
            logger.warn(AETypeLog.file, new AlgosException(message));
            return result.errorMessage(message);
        }

        if (directoryToBeDeleted.delete()) {
            message = String.format("%s%s%s", CANCELLATA_DIRECTORY, FORWARD, directoryToBeDeleted.getAbsolutePath());
            logger.info(new WrapLog().exception(new AlgosException(message)).type(AETypeLog.file));
            return result.validMessage(message);
        }
        else {
            try {
                FileUtils.deleteDirectory(directoryToBeDeleted);
                message = String.format("%s%s%s", CANCELLATA_DIRECTORY, FORWARD, directoryToBeDeleted.getAbsolutePath());
                logger.info(new WrapLog().exception(new AlgosException(message)).type(AETypeLog.file));
                return result.validMessage(message);
            } catch (Exception unErrore) {
                message = String.format("%s%s%s", NON_CANCELLATA_DIRECTORY, FORWARD, directoryToBeDeleted.getAbsolutePath());
                logger.info(new WrapLog().message(message).type(AETypeLog.file));
                logger.error(new WrapLog().exception(new AlgosException(unErrore)).usaDb().type(AETypeLog.file));
                return result.errorMessage(unErrore.getMessage());
            }
        }
    }


    /**
     * Cancella una directory
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo e iniziare con uno 'slash' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param absolutePathDirectoryToBeDeleted path completo della directory che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se la directory è stata cancellata
     */
    public AResult deleteDirectory(final String absolutePathDirectoryToBeDeleted) {
        AResult result = checkPath("deleteDirectory", absolutePathDirectoryToBeDeleted);
        if (result.isErrato()) {
            return result;
        }

        return deleteDirectory(new File(absolutePathDirectoryToBeDeleted));
    }


    /**
     * Cancella un file
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo e iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param fileToBeDeleted con path completo che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se il file è stato creato
     */
    public AResult deleteFile(final File fileToBeDeleted) {
        AResult result = checkFileDirectory("deleteFile", fileToBeDeleted);
        if (result.isErrato()) {
            return result;
        }
        String message;

        if (this.isNotSuffix(fileToBeDeleted.getAbsolutePath())) {
            message = String.format("%s%s%s", PATH_SENZA_SUFFIX, FORWARD, fileToBeDeleted.getAbsolutePath());
            logger.error(new WrapLog().exception(new AlgosException(message)).type(AETypeLog.file));
            return result.errorMessage(message);
        }

        if (!fileToBeDeleted.exists()) {
            message = String.format("%s%s%s", NON_ESISTE_FILE, FORWARD, fileToBeDeleted.getAbsolutePath());
            logger.warn(AETypeLog.file, new AlgosException(message));
            return result.errorMessage(message);
        }

        if (fileToBeDeleted.delete()) {
            message = String.format("%s%s%s", CANCELLATO_FILE, FORWARD, fileToBeDeleted.getAbsolutePath());
            logger.info(new WrapLog().exception(new AlgosException(message)).type(AETypeLog.file));
            return result.validMessage(message);
        }
        else {
            message = String.format("%s%s%s", NON_CANCELLATO_FILE, FORWARD, fileToBeDeleted.getAbsolutePath());
            logger.warn(AETypeLog.file, new AlgosException(message));
            return result.errorMessage(message);
        }
    }


    /**
     * Cancella un file
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo e iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param absolutePathFileWithSuffixToBeCanceled path completo del file che DEVE cominciare con '/' SLASH e compreso il suffisso
     *
     * @return testo di errore, vuoto se il file è stato cancellato
     */
    public AResult deleteFile(final String absolutePathFileWithSuffixToBeCanceled) {
        AResult result = checkPath("deleteFile", absolutePathFileWithSuffixToBeCanceled);
        if (result.isErrato()) {
            return result;
        }

        return deleteFile(new File(absolutePathFileWithSuffixToBeCanceled));
    }

    //    /**
    //     * Copia un file
    //     * <p>
    //     * Se manca il file sorgente, non fa nulla <br>
    //     * Se manca la directory di destinazione, la crea <br>
    //     * Se esiste il file destinazione, non fa nulla <br>
    //     *
    //     * @param srcPath  nome completo del file sorgente
    //     * @param destPath nome completo del file destinazione
    //     *
    //     * @return true se il file è stato copiato
    //     */
    //    @Deprecated
    //    public boolean copyFile(final String srcPath, final String destPath) {
    //        return copyFileStr(srcPath, destPath) == VUOTA;
    //    }

    //    /**
    //     * Copia un file sovrascrivendolo se già esistente
    //     * <p>
    //     * Se manca il file sorgente, non fa nulla <br>
    //     * Se esiste il file destinazione, lo cancella prima di copiarlo <br>
    //     *
    //     * @param srcPath  nome completo del file sorgente
    //     * @param destPath nome completo del file destinazione
    //     *
    //     * @return true se il file è stato copiato
    //     */
    //    public boolean copyFileDeletingAll(String srcPath, String destPath) {
    //        if (!isEsisteFile(srcPath)) {
    //            return false;
    //        }
    //
    //        if (isEsisteFile(destPath)) {
    //            deleteFile(destPath);
    //        }
    //
    //        return copyFileStr(srcPath, destPath) == VUOTA;
    //    }

    //    /**
    //     * Copia un file solo se non già esistente
    //     * <p>
    //     * Se manca il file sorgente, non fa nulla <br>
    //     * Se esiste il file destinazione, non fa nulla <br>
    //     *
    //     * @param srcPath  nome completo del file sorgente
    //     * @param destPath nome completo del file destinazione
    //     *
    //     * @return true se il file è stato copiato
    //     */
    //    public boolean copyFileOnlyNotExisting(String srcPath, String destPath) {
    //        return copyFileStr(srcPath, destPath) == VUOTA;
    //    }


    /**
     * Copia un file
     * <p>
     * Se manca il file sorgente, non fa nulla <br>
     * Se manca la directory di destinazione, la crea <br>
     * Se esiste il file destinazione, non fa nulla <br>
     *
     * @param srcPath  nome completo del file sorgente
     * @param destPath nome completo del file destinazione
     *
     * @return testo di errore, vuoto se il file è stato copiato
     */
    public String copyFileStr(String srcPath, String destPath) {
        String risposta = VUOTA;
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);

        if (!isEsisteFile(srcPath)) {
            return NON_ESISTE_FILE;
        }

        if (isEsisteFile(destPath)) {
            return PATH_FILE_ESISTENTE;
        }

        try { // prova ad eseguire il codice
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            return NON_COPIATO_FILE;
        }

        return risposta;
    }


    /**
     * Copia una directory <br>
     * <p>
     * Controlla che siano validi i path di riferimento <br>
     * Controlla che esista la directory sorgente da copiare <br>
     * Se manca la directory sorgente, non fa nulla <br>
     * Se non esiste la directory di destinazione, la crea <br>
     * Se esiste la directory di destinazione ed è AECopyDir.soloSeNonEsiste, non fa nulla <br>
     * Se esiste la directory di destinazione ed è AECopyDir.deletingAll, la cancella e poi la copia <br>
     * Se esiste la directory di destinazione ed è AECopyDir.addingOnly, la integra aggiungendo file/cartelle <br>
     * Nei messaggi di avviso, accorcia il destPath eliminando i livelli precedenti alla directory indicata <br>
     *
     * @param typeCopy modalità di comportamento se esiste la directory di destinazione
     * @param srcPath  nome completo della directory sorgente
     * @param destPath nome completo della directory destinazione
     *
     * @return true se la directory è stata copiata
     */
    public AResult copyDirectory(AECopy typeCopy, String srcPath, String destPath) {
        return copyDirectory(typeCopy, srcPath, destPath, VUOTA);
    }


    /**
     * Copia un file <br>
     * <p>
     * Controlla che siano validi i path di riferimento <br>
     * Controlla che esista il path del file sorgente  <br>
     * Se manca il file sorgente, non fa nulla <br>
     * Se esiste il file di destinazione ed è AECopyFile.soloSeNonEsiste, non fa nulla <br>
     * Se esiste il file di destinazione ed è AECopyDir.sovrascriveSempreAncheSeEsiste, lo sovrascrive <br>
     * Se esiste il file di destinazione ed è AECopyFile.checkFlagSeEsiste, controlla il flag sovraScrivibile <br>
     * Nei messaggi di avviso, accorcia il destPath eliminando i livelli precedenti alla directory indicata <br>
     *
     * @param typeCopy       modalità di comportamento se esiste il file di destinazione
     * @param srcPathDir     path della directory sorgente
     * @param destPathDir    path della directory destinazione
     * @param nomeFile       nome del file completo di suffisso
     * @param firstDirectory da cui iniziare il path per il messaggio di avviso
     */
    public AResult copyFile(final AECopy typeCopy, final String srcPathDir, final String destPathDir, final String nomeFile) {
        AResult result = AResult.build().method("copyFile").target(nomeFile);
        String message;
        String srcPath = srcPathDir + nomeFile;
        String destPath = destPathDir + nomeFile;
        String path = this.findPathBreve(destPathDir, VUOTA);

        if (typeCopy == null) {
            message = "Manca il type AECopy";
            logger.warn(AETypeLog.file, new AlgosException(message));
            return result.errorMessage(message);
        }
        result = result.type(typeCopy.getDescrizione());

        if (textService.isEmpty(nomeFile)) {
            message = "Manca il nome del file";
            logger.warn(AETypeLog.file, new AlgosException(message));
            return result.errorMessage(message);
        }

        if (textService.isEmpty(destPathDir)) {
            message = "Manca il nome della directory di destinazione";
            logger.warn(AETypeLog.file, new AlgosException(message));
            return result.errorMessage(message);
        }

        if (typeCopy != AECopy.fileSovrascriveSempreAncheSeEsiste && typeCopy != AECopy.fileSoloSeNonEsiste && typeCopy != AECopy.fileCheckFlagSeEsiste) {
            message = String.format("Il type.%s previsto non è compatibile", typeCopy);
            logger.warn(AETypeLog.file, new AlgosException(message));
            return result.errorMessage(message);
        }

        result = checkPath("copyFile", destPath).target(nomeFile).type(typeCopy.getDescrizione());
        if (result.isErrato()) {
            return result;
        }

        if (!new File(srcPath).exists()) {
            message = String.format("Non sono riuscito a trovare il file sorgente %s nella directory %s", nomeFile, srcPathDir);
            logger.warn(AETypeLog.file, new AlgosException(message));
            return result.errorMessage(message);
        }

        switch (typeCopy) {
            case fileSovrascriveSempreAncheSeEsiste:
                if (new File(destPath).exists()) {
                    message = "Il file: " + path + " esisteva già ma è stato modificato.";
                    logger.info(new WrapLog().type(AETypeLog.file).message(message));
                }
                else {
                    message = "Il file: " + path + " non esisteva ed è stato copiato.";
                    logger.info(new WrapLog().type(AETypeLog.file).message(message));
                }
                try {
                    FileUtils.copyFile(new File(srcPath), new File(destPath));
                } catch (Exception unErrore) {
                    logger.error(new WrapLog().exception(unErrore).usaDb());
                    return result.errorMessage(unErrore.getMessage());
                }
                return result.validMessage(message);
            case fileSoloSeNonEsiste:
                if (new File(destPath).exists()) {
                    message = "Il file: " + path + " esisteva già e non è stato modificato.";
                    logger.info(new WrapLog().type(AETypeLog.file).message(message).usaDb());
                    return result.validMessage(message);
                }
                else {
                    try {
                        FileUtils.copyFile(new File(srcPath), new File(destPath));
                        message = "Il file: " + path + " non esisteva ed è stato copiato.";
                        logger.info(new WrapLog().type(AETypeLog.file).message(message));
                        return result.validMessage(message);
                    } catch (Exception unErrore) {
                        logger.error(new WrapLog().exception(unErrore).usaDb());
                        return result.errorMessage(unErrore.getMessage());
                    }
                }
            case fileCheckFlagSeEsiste:
                logger.warn(AETypeLog.file, new AlgosException(SWITCH_FUTURO));
                return result.errorMessage(SWITCH_FUTURO);
            default:
                logger.warn(AETypeLog.file, new AlgosException(SWITCH));
                return result.errorMessage(SWITCH);
        }
    }


    /**
     * Copia una directory <br>
     * <p>
     * Controlla che siano validi i path di riferimento <br>
     * Controlla che esista la directory sorgente da copiare <br>
     * Se manca la directory sorgente, non fa nulla <br>
     * Se non esiste la directory di destinazione, la crea <br>
     * Se esiste la directory di destinazione ed è AECopyDir.soloSeNonEsiste, non fa nulla <br>
     * Se esiste la directory di destinazione ed è AECopyDir.deletingAll, la cancella e poi la copia <br>
     * Se esiste la directory di destinazione ed è AECopyDir.addingOnly, la integra aggiungendo file/cartelle <br>
     * Nei messaggi di avviso, accorcia il destPath eliminando i livelli precedenti alla directory indicata <br>
     *
     * @param typeCopy       modalità di comportamento se esiste la directory di destinazione
     * @param srcPath        nome completo della directory sorgente
     * @param destPath       nome completo della directory destinazione
     * @param firstDirectory da cui iniziare il path per il messaggio di avviso
     *
     * @return true se la directory  è stata copiata
     */
    public AResult copyDirectory(AECopy typeCopy, String srcPath, String destPath, String firstDirectory) {
        AResult result = AResult.errato();
        boolean copiata = false;
        boolean esisteDest;
        String message = VUOTA;
        String tag;
        String path;

        if (textService.isValid(firstDirectory)) {
            path = this.findPathBreve(destPath, firstDirectory);
        }
        else {
            path = this.estraeDirectoryFinaleSenzaSlash(destPath);
        }

        if (textService.isEmpty(srcPath) || textService.isEmpty(destPath)) {
            tag = textService.isEmpty(srcPath) ? "srcPath" : "destPath";
            message = String.format("Manca il '%s' della directory da copiare.", tag);
            result.setErrorMessage(message);
        }
        else {
            if (isEsisteDirectory(srcPath)) {
                switch (typeCopy) {
                    case dirSoloSeNonEsiste:
                        copiata = copyDirectoryOnlyNotExisting(srcPath, destPath);
                        if (copiata) {
                            message = String.format("La directory '%s' non esisteva ed è stata copiata.", path);
                        }
                        else {
                            message = String.format("La directory '%s' esisteva già e non è stata toccata.", path);
                        }
                        result = AResult.valido(message);

                        message = VUOTA;
                        break;
                    case dirDeletingAll:
                        esisteDest = isEsisteDirectory(destPath);
                        copiata = copyDirectoryDeletingAll(srcPath, destPath);
                        if (copiata) {
                            if (esisteDest) {
                                message = String.format("La directory '%s' esisteva già ma è stata sostituita.", path);
                            }
                            else {
                                message = String.format("La directory '%s' non esisteva ed è stata creata.", path);
                            }
                            result = AResult.valido(message);
                            message = VUOTA;
                        }
                        else {
                            if (esisteDest) {
                                message = String.format("Non sono riuscito a sostituire la directory '%s'.", path);
                            }
                            else {
                                message = String.format("Non sono riuscito a creare la directory '%s'.", path);
                            }
                            result.setErrorMessage(message);
                        }
                        break;
                    case dirAddingOnly:
                        esisteDest = isEsisteDirectory(destPath);
                        copiata = copyDirectoryAddingOnly(srcPath, destPath);
                        if (copiata) {
                            if (esisteDest) {
                                message = String.format("La directory '%s' esisteva già ma è stata integrata.", path);
                            }
                            else {
                                message = String.format("La directory '%s' non esisteva ed è stata creata.", path);
                            }
                            result = AResult.valido(message);
                        }
                        else {
                            if (esisteDest) {
                                message = String.format("Non sono riuscito a integrare la directory '%s'.", path);
                            }
                            else {
                                message = String.format("Non sono riuscito a creare la directory '%s'.", path);
                            }
                            result.setErrorMessage(message);
                        }
                        break;
                    default:
                        copiata = copyDirectoryAddingOnly(srcPath, destPath);
                        logger.warn(AETypeLog.file, new AlgosException(SWITCH));
                        break;
                }
            }
            else {
                message = String.format("Manca la directory '%s' da copiare.", srcPath);
                result.setErrorMessage(message);
            }
        }

        if (!copiata && textService.isValid(message)) {
            logger.error(AETypeLog.file, new AlgosException(SWITCH));
        }

        return result;
    }


    /**
     * Copia una directory
     * <p>
     * Se manca la directory sorgente, non fa nulla <br>
     * Se manca la directory di destinazione, la crea <br>
     * Se esiste la directory di destinazione, non fa nulla <br>
     *
     * @param srcPath  nome completo della directory sorgente
     * @param destPath nome completo della directory destinazione
     *
     * @return true se la directory  è stata copiata
     */
    @Deprecated
    public boolean copyDirectory(String srcPath, String destPath) {
        return copyDirectoryAddingOnly(srcPath, destPath);
    }


    /**
     * Copia una directory sostituendo integralmente quella eventualmente esistente <br>
     * <p>
     * Se manca la directory sorgente, non fa nulla <br>
     * Se manca la directory di destinazione, la crea <br>
     * Se esiste la directory di destinazione, la cancella prima di ricopiarla <br>
     * Tutte i files e le subdirectories originali vengono cancellata <br>
     *
     * @param srcPath  nome parziale del path sorgente
     * @param destPath nome parziale del path destinazione
     * @param dirName  nome della directory da copiare
     *
     * @return true se la directory  è stata copiata
     */
    public boolean copyDirectoryDeletingAll(String srcPath, String destPath, String dirName) {
        return copyDirectoryDeletingAll(srcPath + dirName, destPath + dirName);
    }


    /**
     * Copia una directory sostituendo integralmente quella eventualmente esistente <br>
     * <p>
     * Se manca la directory sorgente, non fa nulla <br>
     * Se manca la directory di destinazione, la crea <br>
     * Se esiste la directory di destinazione, la cancella prima di ricopiarla <br>
     * Tutte i files e le subdirectories originali vengono cancellata <br>
     *
     * @param srcPath  nome completo della directory sorgente
     * @param destPath nome completo della directory destinazione
     *
     * @return true se la directory  è stata copiata
     */
    public boolean copyDirectoryDeletingAll(String srcPath, String destPath) {
        File srcDir = new File(srcPath);
        File destDir = new File(destPath);

        if (!isEsisteDirectory(srcPath)) {
            return false;
        }

        if (isEsisteDirectory(destPath)) {
            try {
                FileUtils.forceDelete(new File(destPath));
            } catch (Exception unErrore) {
            }
        }

        if (isEsisteDirectory(destPath)) {
            return false;
        }
        else {
            try {
                FileUtils.copyDirectory(srcDir, destDir);
                return true;
            } catch (Exception unErrore) {
            }
        }

        return false;
    }


    /**
     * Copia una directory solo se non esisteva <br>
     * <p>
     * Se manca la directory sorgente, non fa nulla <br>
     * Se manca la directory di destinazione, la crea <br>
     * Se esiste la directory di destinazione, non fa nulla <br>
     *
     * @param srcPath  nome completo della directory sorgente
     * @param destPath nome completo della directory destinazione
     *
     * @return true se la directory  è stata copiata
     */
    public boolean copyDirectoryOnlyNotExisting(String srcPath, String destPath) {
        if (!isEsisteDirectory(srcPath)) {
            return false;
        }

        if (isEsisteDirectory(destPath)) {
            return false;
        }

        return copyDirectoryDeletingAll(srcPath, destPath);
    }

    //    /**
    //     * Copia una directory aggiungendo files e subdirectories a quelli eventualmente esistenti <br>
    //     * Lascia inalterate subdirectories e files già esistenti <br>
    //     * <p>
    //     * Se manca la directory sorgente, non fa nulla <br>
    //     * Se manca la directory di destinazione, la crea <br>
    //     * Se esiste la directory destinazione, aggiunge files e subdirectories <br>
    //     * Tutti i files e le subdirectories esistenti vengono mantenuti <br>
    //     * Tutte le aggiunte sono ricorsive nelle subdirectories <br>
    //     *
    //     * @param srcPath  nome parziale del path sorgente
    //     * @param destPath nome parziale del path destinazione
    //     * @param dirName  nome della directory da copiare
    //     *
    //     * @return true se la directory  è stata copiata
    //     */
    //    public boolean copyDirectoryAddingOnly(String srcPath, String destPath, String dirName) {
    //        return copyDirectoryAddingOnly(srcPath + dirName, destPath + dirName);
    //    }


    /**
     * Copia una directory aggiungendo files e subdirectories a quelli eventualmente esistenti <br>
     * Lascia inalterate subdirectories e files già esistenti <br>
     * <p>
     * Se manca la directory sorgente, non fa nulla <br>
     * Se manca la directory di destinazione, la crea <br>
     * Se esiste la directory destinazione, aggiunge files e subdirectories <br>
     * Tutti i files e le subdirectories esistenti vengono mantenuti <br>
     * Tutte le aggiunte sono ricorsive nelle subdirectories <br>
     *
     * @param srcPath  nome completo della directory sorgente
     * @param destPath nome completo della directory destinazione
     *
     * @return true se la directory  è stata copiata
     */
    public boolean copyDirectoryAddingOnly(String srcPath, String destPath) {
        boolean copiata = false;
        File srcDir = new File(srcPath);
        File destDir = new File(destPath);

        if (!isEsisteDirectory(srcPath)) {
            return false;
        }

        try {
            FileUtils.copyDirectory(srcDir, destDir);
            copiata = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return copiata;
    }


    /**
     * Scrive un file
     * Se non esiste, lo crea
     *
     * @param pathFileToBeWritten nome completo del file
     * @param text                contenuto del file
     */
    public AResult scriveNewFile(String pathFileToBeWritten, String text) {
        AResult result;
        String message;

        if (isEsisteFile(pathFileToBeWritten)) {
            message = String.format("Il file: %s esisteva già e non è stato modificato", pathFileToBeWritten);
            result = AResult.errato(message);
        }
        else {
            creaFile(pathFileToBeWritten);
            sovraScriveFile(pathFileToBeWritten, text);
            message = String.format("Il file: %s non esisteva ed è stato creato", pathFileToBeWritten);
            result = AResult.valido(message);
        }

        return result;
    }


    /**
     * Scrive un file
     * Se non esiste, lo crea
     *
     * @param pathFileToBeWritten nome completo del file
     * @param testo               contenuto del file
     * @param sovrascrive         anche se esiste già
     */
    public AResult scriveFile(AECopy typeCopy, String pathFileToBeWritten, String testo) {
        return scriveFile(typeCopy, pathFileToBeWritten, testo, VUOTA);
    }

    /**
     * Scrive un file
     * Se non esiste, lo crea
     *
     * @param pathFileToBeWritten nome completo del file
     * @param testo               contenuto del file
     * @param sovrascrive         anche se esiste già
     * @param directory           da cui iniziare il path per il messaggio di avviso
     * @param stampaInfo          flag per usare il logger
     */
    public AResult scriveFile(AECopy typeCopy, String pathFileToBeWritten, String testo, String firstDirectory) {
        AResult result = AResult.errato();
        String message = VUOTA;
        String path;

        if (textService.isValid(firstDirectory)) {
            path = this.findPathBreve(pathFileToBeWritten, firstDirectory);
        }
        else {
            path = pathFileToBeWritten.substring((pathFileToBeWritten.lastIndexOf(SLASH) + SLASH.length()));
        }

        switch (typeCopy) {
            case fileCheckFlagSeEsiste, sourceCheckFlagSeEsiste -> {
            }
            case fileSoloSeNonEsiste, sourceSoloSeNonEsiste -> {
                if (isEsisteFile(pathFileToBeWritten)) {
                    message = String.format("Il file '%s' esisteva già e non è stato modificato", path);
                    result.setErrorMessage(message);
                }
                else {
                    sovraScriveFile(pathFileToBeWritten, testo);
                    message = String.format("Il file '%s' non esisteva ed è stato creato", path);
                    result = AResult.valido(message);
                }
            }
            case fileSovrascriveSempreAncheSeEsiste, sourceSovrascriveSempreAncheSeEsiste -> {
                if (isEsisteFile(pathFileToBeWritten)) {
                    message = String.format("Il file '%s' esisteva già ma è stato modificato", path);
                }
                else {
                    message = String.format("Il file '%s' non esisteva ed è stato creato", path);
                }
                sovraScriveFile(pathFileToBeWritten, testo);
                result = AResult.valido(message);
            }
            default -> {
            }
        }

        return result;
    }


    /**
     * Sovrascrive un file
     *
     * @param pathFileToBeWritten nome completo del file
     * @param text                contenuto del file
     */
    public boolean sovraScriveFile(String pathFileToBeWritten, String text) {
        boolean status = false;
        File fileToBeWritten;
        FileWriter fileWriter = null;

        fileToBeWritten = new File(pathFileToBeWritten);
        creaDirectoryParentAndFile(fileToBeWritten);

        try {
            fileWriter = new FileWriter(fileToBeWritten);
            fileWriter.write(text);
            fileWriter.flush();
            status = true;
        } catch (Exception unErrore) {
            int a = 87;
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (Exception unErrore) {
            }
        }

        return status;
    }


    /**
     * Legge un file
     *
     * @param pathFileToBeRead nome completo del file
     */
    public String leggeFile(String pathFileToBeRead) {
        String testo = VUOTA;
        String aCapo = CAPO;
        String currentLine;

        //-- non va, perché se arriva it/algos/Alfa.java becca anche il .java
        //        nameFileToBeRead=  nameFileToBeRead.replaceAll("\\.","/");

        try (BufferedReader br = new BufferedReader(new FileReader(pathFileToBeRead))) {
            while ((currentLine = br.readLine()) != null) {
                testo += currentLine;
                testo += "\n";
            }

            testo = textService.levaCoda(testo, aCapo);
        } catch (Exception unErrore) {
            logger.error(new WrapLog().exception(new AlgosException(unErrore)).usaDb().type(AETypeLog.file));
        }

        return testo;
    }


    /**
     * Legge un file CSV <br>
     * Prima lista (prima riga): titoli
     * Liste successive (righe successive): valori
     *
     * @param pathFileToBeRead nome completo del file
     *
     * @return lista di liste di valori, senza titoli
     */
    public List<List<String>> leggeListaCSV(final String pathFileToBeRead) {
        return leggeListaCSV(pathFileToBeRead, VIRGOLA, CAPO);
    }


    /**
     * Legge un file CSV <br>
     * Prima lista (prima riga): titoli
     * Liste successive (righe successive): valori
     *
     * @param pathFileToBeRead nome completo del file
     * @param sepColonna       normalmente una virgola
     * @param sepRiga          normalmente un \n
     *
     * @return lista di liste di valori, senza titoli
     */
    public List<List<String>> leggeListaCSV(final String pathFileToBeRead, final String sepColonna, final String sepRiga) {
        List<List<String>> lista = new ArrayList<>();
        List<String> riga = null;
        String[] righe;
        String[] colonne;

        String testo = leggeFile(pathFileToBeRead);

        if (textService.isValid(testo)) {
            righe = testo.split(sepRiga);
            if (righe != null && righe.length > 0) {
                for (String rigaTxt : righe) {
                    riga = null;
                    colonne = rigaTxt.split(sepColonna);
                    if (colonne != null && colonne.length > 0) {
                        riga = new ArrayList<>();
                        for (String colonna : colonne) {
                            riga.add(colonna);
                        }
                    }
                    if (riga != null) {
                        lista.add(riga);
                    }
                }
            }
        }

        return arrayService.isAllValid(lista) ? lista.subList(1, lista.size()) : lista;
    }


    /**
     * Legge un file CSV <br>
     * Prima lista (prima riga): titoli
     * Liste successive (righe successive): valori
     *
     * @param pathFileToBeRead nome completo del file
     *
     * @return lista di mappe di valori
     */
    public List<LinkedHashMap<String, String>> leggeMappaCSV(final String pathFileToBeRead) {
        return leggeMappaCSV(pathFileToBeRead, VIRGOLA, CAPO);
    }


    /**
     * Legge un file CSV <br>
     * Prima lista (prima riga): titoli
     * Liste successive (righe successive): valori
     *
     * @param pathFileToBeRead nome completo del file
     * @param sepColonna       normalmente una virgola
     * @param sepRiga          normalmente un \n
     *
     * @return lista di mappe di valori
     */
    public List<LinkedHashMap<String, String>> leggeMappaCSV(final String pathFileToBeRead, final String sepColonna, final String sepRiga) {
        List<LinkedHashMap<String, String>> lista = new ArrayList<>();
        LinkedHashMap<String, String> mappa = null;
        String[] righe;
        String[] titoli;
        String[] colonne;

        String testo = leggeFile(pathFileToBeRead);
        if (textService.isValid(testo)) {
            righe = testo.split(sepRiga);
            titoli = righe[0].split(sepColonna);

            if (righe != null && righe.length > 0) {
                for (int k = 1; k < righe.length; k++) {
                    mappa = null;
                    colonne = righe[k].split(sepColonna);
                    if (colonne != null && colonne.length > 0) {
                        mappa = new LinkedHashMap<>();
                        for (int j = 0; j < colonne.length; j++) {
                            if (j < colonne.length) {
                                mappa.put(titoli[j], colonne[j]);
                            }
                        }
                    }
                    if (mappa != null) {
                        lista.add(mappa);
                    }
                }
            }
        }

        return lista;
    }


    /**
     * Estrae le sub-directories da una directory <br>
     *
     * @param pathDirectoryToBeScanned nome completo della directory
     */
    public List<String> getSubDirectoriesAbsolutePathName(final String pathDirectoryToBeScanned) {
        List<String> subDirectoryName = new ArrayList<>();
        List<File> subDirectory = getSubDirectories(pathDirectoryToBeScanned);

        if (subDirectory != null) {
            for (File file : subDirectory) {
                subDirectoryName.add(file.getAbsolutePath());
            }
        }

        return subDirectoryName;
    }


    /**
     * Estrae le sub-directories da una directory <br>
     *
     * @param pathDirectoryToBeScanned nome completo della directory
     */
    public List<String> getSubDirectoriesName(final String pathDirectoryToBeScanned) {
        List<String> subDirectoryName = new ArrayList<>();
        List<File> subDirectory = getSubDirectories(pathDirectoryToBeScanned);

        if (subDirectory != null) {
            for (File file : subDirectory) {
                subDirectoryName.add(file.getName());
            }
        }

        return subDirectoryName;
    }


    /**
     * Estrae le sub-directories da una directory <br>
     *
     * @param pathDirectoryToBeScanned nome completo della directory
     */
    public List<String> getSubDirectoriesName(final File fileSorgente) {
        List<String> subDirectoryName = new ArrayList<>();
        List<File> subDirectory = getSubDirectories(fileSorgente);

        if (subDirectory != null) {
            for (File file : subDirectory) {
                subDirectoryName.add(file.getName());
            }
        }

        return subDirectoryName;
    }


    /**
     * Estrae le sub-directories da una directory <br>
     *
     * @param pathDirectoryToBeScanned nome completo della directory
     */
    public List<File> getSubDirectories(final String pathDirectoryToBeScanned) {
        return getSubDirectories(new File(pathDirectoryToBeScanned));
    }


    /**
     * Estrae le sub-directories da una directory <br>
     *
     * @param directoryToBeScanned della directory
     *
     * @return lista di sub-directory SENZA files
     */
    public List<File> getSubDirectories(final File directoryToBeScanned) {
        List<File> subDirectory = new ArrayList<>();
        File[] allFiles = null;

        if (directoryToBeScanned != null) {
            allFiles = directoryToBeScanned.listFiles();
        }

        if (allFiles != null) {
            subDirectory = new ArrayList<>();
            for (File file : allFiles) {
                if (file.isDirectory()) {
                    subDirectory.add(file);
                }
            }
        }

        return subDirectory;
    }


    /**
     * Estrae le sub-directories da un sotto-livello di una directory <br>
     * La dirInterna non è, ovviamente, al primo livello della directory altrimenti chiamerei getSubDirectories <br>
     *
     * @param pathDirectoryToBeScanned della directory
     * @param dirInterna               da scandagliare
     *
     * @return lista di sub-directory SENZA files
     */
    public List<File> getSubSubDirectories(final String pathDirectoryToBeScanned, final String dirInterna) {
        return getSubSubDirectories(new File(pathDirectoryToBeScanned), dirInterna);
    }


    /**
     * Estrae le sub-directories da un sotto-livello di una directory <br>
     *
     * @param directoryToBeScanned della directory
     * @param dirInterna           da scandagliare
     *
     * @return lista di sub-directory SENZA files
     */
    public List<File> getSubSubDirectories(final File directoryToBeScanned, String dirInterna) {
        String subDir = directoryToBeScanned.getAbsolutePath();

        if (subDir.endsWith(SLASH)) {
            subDir = textService.levaCoda(subDir, SLASH);
        }

        if (dirInterna.startsWith(SLASH)) {
            dirInterna = textService.levaTesta(dirInterna, SLASH);
        }

        String newPath = subDir + SLASH + dirInterna;
        File subFile = new File(newPath);

        return getSubDirectories(subFile);
    }


    /**
     * Controlla se una sotto-directory esiste <br>
     *
     * @param directoryToBeScanned della directory
     * @param dirInterna           da scandagliare
     *
     * @return true se esiste
     */
    public boolean isEsisteSubDirectory(final File directoryToBeScanned, final String dirInterna) {
        if (directoryToBeScanned.getAbsolutePath().endsWith(SLASH)) {
            return isEsisteDirectory(directoryToBeScanned.getAbsolutePath() + dirInterna);
        }
        else {
            return isEsisteDirectory(directoryToBeScanned.getAbsolutePath() + SLASH + dirInterna);
        }
    }


    /**
     * Controlla se una sotto-directory è piena <br>
     *
     * @param directoryToBeScanned della directory
     * @param dirInterna           da scandagliare
     *
     * @return true se è piena
     */
    public boolean isPienaSubDirectory(final File directoryToBeScanned, final String dirInterna) {
        return arrayService.isAllValid(getSubSubDirectories(directoryToBeScanned, dirInterna));
    }


    /**
     * Controlla se una sotto-directory è vuota <br>
     *
     * @param directoryToBeScanned della directory
     * @param dirInterna           da scandagliare
     *
     * @return true se è vuota
     */
    public boolean isVuotaSubDirectory(final File directoryToBeScanned, final String dirInterna) {
        return arrayService.isEmpty(getSubSubDirectories(directoryToBeScanned, dirInterna));
    }


    /**
     * Elimina l'ultima directory da un path <br>
     * <p>
     * Esegue solo se il path è valido <br>
     * Elimina spazi vuoti iniziali e finali <br>
     *
     * @param pathIn in ingresso
     *
     * @return path ridotto in uscita
     */
    public String levaDirectoryFinale(final String pathIn) {
        String pathOut = pathIn.trim();

        if (textService.isValid(pathOut) && pathOut.endsWith(SLASH)) {
            pathOut = textService.levaCoda(pathOut, SLASH);
            pathOut = textService.levaCodaDa(pathOut, SLASH) + SLASH;
        }

        return pathOut.trim();
    }

    /**
     * Recupera l'ultima directory da un path <br>
     * <p>
     * Esegue solo se il path è valido <br>
     * Elimina spazi vuoti iniziali e finali <br>
     *
     * @param pathIn in ingresso
     *
     * @return directory finale del path
     */
    public String estraeDirectoryFinaleSenzaSlash(final String pathIn) {
        String pathOut = pathIn.trim();

        if (textService.isValid(pathOut) && pathOut.endsWith(SLASH)) {
            pathOut = textService.levaCoda(pathOut, SLASH);
            pathOut = pathOut.substring(pathOut.lastIndexOf(SLASH) + 1);
        }

        return pathOut.trim();
    }

    /**
     * Recupera l'ultima directory da un path <br>
     * <p>
     * Esegue solo se il path è valido <br>
     * Elimina spazi vuoti iniziali e finali <br>
     *
     * @param pathIn in ingresso
     *
     * @return directory finale del path, comprensiva di SLASH
     */
    public String estraeDirectoryFinale(final String pathIn) {
        String pathOut = pathIn.trim();

        if (textService.isValid(pathOut) && pathOut.endsWith(SLASH)) {
            pathOut = textService.levaCoda(pathOut, SLASH);
            pathOut = pathOut.substring(pathOut.lastIndexOf(SLASH) + 1) + SLASH;
        }

        return pathOut.trim();
    }

    /**
     * Recupera l'ultima classe da un path <br>
     * <p>
     * Elimina spazi vuoti iniziali e finali <br>
     *
     * @param pathIn in ingresso
     *
     * @return classe finale del path
     */
    public String estraeClasseFinale(final String pathIn) {
        String pathOut = pathIn.trim();

        if (textService.isValid(pathOut)) {
            pathOut = textService.levaCoda(pathOut, SLASH);
            if (pathOut.contains(SLASH)) {
                pathOut = pathOut.substring(pathOut.lastIndexOf(SLASH) + SLASH.length());
            }
            if (pathOut.contains(PUNTO)) {
                pathOut = pathOut.substring(pathOut.lastIndexOf(PUNTO) + PUNTO.length());
            }
        }

        return pathOut.trim();
    }

    /**
     * Recupera l'ultima classe da un path <br>
     * <p>
     * Elimina spazi vuoti iniziali e finali <br>
     *
     * @param pathIn in ingresso
     *
     * @return classe finale del path
     */
    public String estraeClasseFinaleSenzaJava(final String pathIn) {
        String pathOut = textService.levaCoda(pathIn, JAVA_SUFFIX);
        return estraeClasseFinale(pathOut);
    }

    /**
     * Estrae il path che contiene la directory indicata <br>
     * <p>
     * Esegue solo se il path è valido
     * Se la directory indicata è vuota, restituisce il path completo <br>
     * Se la directory indicata non esiste nel path, restituisce una stringa vuota <br>
     * Elimina spazi vuoti iniziali e finali
     *
     * @param pathIn            in ingresso
     * @param directoryFindPath di cui ricercare il path che la contiene
     *
     * @return path completo fino alla directory selezionata
     */
    public String findPathDirectory(String pathIn, String directoryFindPath) {
        String pathOut = pathIn.trim();
        String message;

        if (textService.isEmpty(pathIn)) {
            message = "Nullo il path in ingresso";
            logger.warn(AETypeLog.file, new AlgosException(message));
            return pathOut;
        }

        if (textService.isEmpty(directoryFindPath)) {
            message = "Nulla la directory in ingresso";
            logger.warn(AETypeLog.file, new AlgosException(message));
            return pathOut;
        }

        if (pathOut.contains(directoryFindPath)) {
            pathOut = pathOut.substring(0, pathOut.indexOf(directoryFindPath));
        }
        else {
            pathOut = VUOTA;
            message = "Non esiste la directory indicata nel path indicato";
            logger.warn(AETypeLog.file, new AlgosException(message));
        }

        return pathOut.trim();
    }


    /**
     * Estrae il path parziale da una directory indicata, escludendo il percorso iniziale <br>
     * <p>
     * La directory indicata è la prima con quel nome <br>
     * Esegue solo se il path è valido
     * Se la directory indicata non esiste nel path, restituisce tutto il path completo <br>
     * Elimina spazi vuoti iniziali e finali
     *
     * @param pathIn    in ingresso
     * @param directory da cui iniziare il path
     *
     * @return path parziale da una directory
     */
    public String findPathBreve(String pathIn, String directory) {
        String pathBreve = pathIn;
        String pathCanonical;
        String prefix = "../";

        if (textService.isEmpty(directory)) {
            return pathIn;
        }

        pathCanonical = findPathCanonical(pathIn, directory);
        if (textService.isValid(pathCanonical)) {
            pathBreve = prefix + pathCanonical;
            pathBreve = textService.levaCoda(pathBreve, SLASH);
            pathBreve = textService.levaCoda(pathBreve, JAVA_SUFFIX);
        }

        return pathBreve;
    }

    /**
     * Estrae il path parziale da una directory indicata, escludendo il percorso iniziale <br>
     * <p>
     * La directory indicata è l'ultima con quel nome <br>
     * Esegue solo se il path è valido
     * Se la directory indicata non esiste nel path, restituisce tutto il path completo <br>
     * Elimina spazi vuoti iniziali e finali
     *
     * @param pathIn    in ingresso
     * @param directory da cui iniziare il path
     *
     * @return path parziale da una directory
     */
    public String findPathBreveDa(String pathIn, String directory) {
        String pathBreve = pathIn;
        String pathCanonical;
        String prefix = "../";

        if (textService.isEmpty(directory)) {
            return pathIn;
        }

        pathCanonical = findPathCanonicalDa(pathIn, directory);
        if (textService.isValid(pathCanonical)) {
            pathBreve = prefix + pathCanonical;
            pathBreve = textService.levaCoda(pathBreve, SLASH);
            pathBreve = textService.levaCoda(pathBreve, JAVA_SUFFIX);
        }

        return pathBreve;
    }

    /**
     * Estrae il path canonico da una directory indicata <br>
     * <p>
     * La directory indicata è l'ultima con quel nome <br>
     * Esegue solo se il path è valido
     * Se la directory indicata non esiste nel path, restituisce tutto il path completo <br>
     * Elimina spazi vuoti iniziali e finali
     *
     * @param pathIn    in ingresso
     * @param directory da cui iniziare il path
     *
     * @return path parziale da una directory
     */
    public String findPathCanonical(String pathIn, String directory) {
        String path = VUOTA;

        if (textService.isEmpty(pathIn) || textService.isEmpty(directory)) {
            return pathIn;
        }

        if (pathIn.contains(directory)) {
            path = pathIn.substring(pathIn.lastIndexOf(directory));
            if (path.startsWith(SLASH)) {
                path = path.substring(1);
            }
        }

        return path;
    }


    /**
     * Estrae il path canonico da una directory indicata <br>
     * <p>
     * La directory indicata è l'ultima con quel nome <br>
     * Esegue solo se il path è valido
     * Se la directory indicata non esiste nel path, restituisce tutto il path completo <br>
     * Elimina spazi vuoti iniziali e finali
     *
     * @param pathIn    in ingresso
     * @param directory da cui iniziare il path
     *
     * @return path parziale da una directory
     */
    public String findPathCanonicalDa(String pathIn, String directory) {
        String path = VUOTA;

        if (textService.isEmpty(pathIn) || textService.isEmpty(directory)) {
            return pathIn;
        }

        if (pathIn.contains(directory)) {
            path = pathIn.substring(pathIn.lastIndexOf(directory) + directory.length());
            if (path.startsWith(SLASH)) {
                path = path.substring(1);
            }
        }

        return path;
    }

    //    /**
    //     * Elabora un path eliminando i livelli iniziali indicati. <br>
    //     * <p>
    //     * Esegue solo se il testo è valido <br>
    //     * Elimina spazi vuoti iniziali e finali <br>
    //     * Aggiunge un prefisso indicativo -> '../' <br>
    //     *
    //     * @param testoIn    ingresso
    //     * @param numLivelli iniziali da eliminare nel path
    //     *
    //     * @return path semplificato
    //     */
    //    @Deprecated
    //    public String pathBreve(final String testoIn, int numLivelli) {
    //        String path = text.testoDopoTagRipetuto(testoIn, SLASH, numLivelli);
    //        String prefix = "../";
    //
    //        if (!path.equals(testoIn)) {
    //            path = text.isValid(path) ? prefix + path : path;
    //        }
    //
    //        return path;
    //    }


    /**
     * Recupera i progetti da una directory <br>
     * Controlla che la sotto-directory sia di un project e quindi contenga la cartella 'src' e questa non sia vuota <br>
     *
     * @param pathDirectoryToBeScanned nome completo della directory
     */
    public List<File> getAllProjects(final String pathDirectoryToBeScanned) {
        List<File> listaProjects = null;
        List<File> listaDirectory = getSubDirectories(new File(pathDirectoryToBeScanned));

        if (listaDirectory != null && listaDirectory.size() > 0) {
            listaProjects = new ArrayList<>();

            for (File file : listaDirectory) {
                if (isEsisteSubDirectory(file, DIR_PROGETTO)) {
                    listaProjects.add(file);
                }
            }
        }

        return listaProjects;
    }


    /**
     * Recupera i progetti vuoti da una directory <br>
     * Controlla che la sotto-directory sia di un project e quindi contenga la cartella 'src.main.java' <br>
     * Controlla che il progetto sia vuoto; deve essere vuota la cartella 'src.main.java' <br>
     *
     * @param pathDirectoryToBeScanned nome completo della directory
     */
    public List<File> getEmptyProjects(String pathDirectoryToBeScanned) {
        List<File> listaEmptyProjects = null;
        List<File> listaProjects = getAllProjects(pathDirectoryToBeScanned);

        if (listaProjects != null) {
            listaEmptyProjects = new ArrayList<>();
            for (File file : listaProjects) {
                if (isVuotaSubDirectory(file, DIR_PROGETTO_VUOTO)) {
                    listaEmptyProjects.add(file);
                }
            }
        }

        return listaEmptyProjects;
    }

    public boolean isContieneProgettoValido(String pathDirectoryProject) {
        return !isVuotaSubDirectory(new File(pathDirectoryProject), DIR_PROGETTO_VUOTO);
    }

    /**
     * Crea una lista di tutte le Entity esistenti nel modulo indicato <br>
     */
    public List<String> getModuleSubFilesEntity(String moduleName) throws AlgosException {
        String tagIniziale = "src/main/java/it/algos/";
        String tagFinale = "/backend/packages";

        return getAllSubFilesEntity(tagIniziale + moduleName + tagFinale);
    }

    /**
     * Crea una lista di tutte le Entity esistenti nella directory packages <br>
     */
    public List<String> getAllSubFilesEntity(String path) throws AlgosException {
        List<String> listaCanonicalNamesOnlyEntity = new ArrayList<>();
        List<String> listaNamesOnlyFilesJava = getAllSubFilesJava(path);
        String simpleName;

        if (arrayService.isAllValid(listaNamesOnlyFilesJava)) {
            for (String canonicalName : listaNamesOnlyFilesJava) {
                //--estrae la parte significativa
                simpleName = canonicalName.substring(canonicalName.lastIndexOf(PUNTO) + PUNTO.length());

                //--scarta Enumeration
                if (simpleName.startsWith("AE")) {
                    continue;
                }

                //--scarta 'logic', 'form', 'service', 'view', 'grid'
                if (simpleName.endsWith("Logic") || simpleName.endsWith("Form") || simpleName.endsWith("Service") || simpleName.endsWith("View") || simpleName.endsWith("Grid")) {
                    continue;
                }

                listaCanonicalNamesOnlyEntity.add(canonicalName);
            }
        }

        return listaCanonicalNamesOnlyEntity;
    }

    /**
     * Crea una lista di soli files java ricorsiva nelle sub-directory <br>
     *
     * @return canonicalName con i PUNTI di separazione e NON lo SLASH
     */
    public List<String> getAllSubFilesJava(String path) throws AlgosException {
        List<String> listaCanonicalNamesOnlyFilesJava = new ArrayList<>();
        List<String> listaPathNamesOnlyFiles = getAllSubPathFiles(path);
        String tag = ".it.";
        String canonicalName;

        if (arrayService.isAllValid(listaPathNamesOnlyFiles)) {
            for (String pathName : listaPathNamesOnlyFiles) {
                if (pathName.endsWith(JAVA_SUFFIX)) {
                    canonicalName = textService.levaCoda(pathName, JAVA_SUFFIX);
                    canonicalName = canonicalName.replaceAll(SLASH, PUNTO);
                    canonicalName = findPathCanonical(canonicalName, tag);
                    canonicalName = canonicalName.substring(1);
                    listaCanonicalNamesOnlyFilesJava.add(canonicalName);
                }
            }
        }

        return listaCanonicalNamesOnlyFilesJava;
    }

    /**
     * Crea una lista di soli files ricorsiva nelle sub-directory <br>
     *
     * @return lista
     */
    public List<String> getAllSubPathFiles(String path) throws AlgosException {
        List<String> listaPathNamesOnlyFiles = new ArrayList<>();
        List<String> listaAllPathNames;
        File unaDirectory = new File(path);
        Path start = Paths.get(unaDirectory.getAbsolutePath());

        try {
            listaAllPathNames = recursionSubPathNames(start);
        } catch (Exception unErrore) {
            throw AlgosException.crea(unErrore);
        }

        if (arrayService.isAllValid(listaAllPathNames)) {
            for (String pathName : listaAllPathNames) {
                if (isEsisteFile(pathName)) {
                    listaPathNamesOnlyFiles.add(pathName);
                }
            }
        }

        return listaPathNamesOnlyFiles;
    }

    /**
     * Crea una lista (path completo) di tutti i files della directory package del modulo indicato <br>
     *
     * @return lista dei path completi
     */
    public List<String> getPathModuloPackageFiles(final String nomeModulo) throws AlgosException {
        String pathPackage = VUOTA;

        pathPackage += System.getProperty("user.dir") + SLASH;
        pathPackage += nomeModulo + SLASH;

        return getAllSubPathFiles(pathPackage);
    }

    /**
     * Crea una lista (path completo) di tutti i files della directory package del modulo corrente <br>
     *
     * @return lista dei path completi
     */
    public List<String> getPathAllPackageFiles() throws AlgosException {
        List<String> lista = new ArrayList<>();
        String nomeModulo = VUOTA;

        if (textService.isEmpty(nomeModulo)) {
            logger.error(new AlgosException("Manca il nome del modulo"));
        }
        lista.addAll(getPathModuloPackageFiles(nomeModulo));

        nomeModulo = VaadVar.projectNameModulo;
        if (textService.isEmpty(nomeModulo)) {
            logger.error(new AlgosException("Manca il nome del modulo"));
        }
        lista.addAll(getPathModuloPackageFiles(VaadVar.projectNameModulo));

        return lista;
    }


    /**
     * Crea una lista (path completo) di tutti i files della directory package del modulo corrente <br>
     * Senza il suffisso JAVA_SUFFIX <br>
     *
     * @return lista dei path completi
     */
    public List<String> getPathBreveAllPackageFiles() throws AlgosException {
        List<String> listaTroncata = new ArrayList<>();
        List<String> listaEstesa = getPathAllPackageFiles();

        for (String path : listaEstesa) {
            listaTroncata.add(textService.levaCoda(path, JAVA_SUFFIX));
        }

        return listaTroncata;
    }

    /**
     * Path completo di un file esistente nella directory package <br>
     *
     * @return path completo del file
     */
    public String getPath(String simpleName) throws AlgosException {
        String pathCompleto = VUOTA;
        List<String> lista = getPathBreveAllPackageFiles();

        for (String path : lista) {
            if (path.endsWith(simpleName)) {
                pathCompleto = path;
                break;
            }
        }

        return pathCompleto;
    }


    /**
     * Crea una lista (canonicalName) di tutti i files della directory package del modulo indicato <br>
     *
     * @return lista dei canonicalName
     */
    public List<String> getCanonicalModuloPackageFiles(final String nomeModulo) throws AlgosException {
        List<String> listaCanonical = new ArrayList<>();
        List<String> listaPath = getPathModuloPackageFiles(nomeModulo);
        String canonicalName;

        for (String nome : listaPath) {
            canonicalName = textService.levaTestoPrimaDi(nome, DIR_PROGETTO_VUOTO);
            canonicalName = canonicalName.replaceAll(SLASH, PUNTO);
            listaCanonical.add(canonicalName);
        }

        return listaCanonical;
    }

    /**
     * Crea una lista (canonicalName) di tutti i files della directory package del modulo corrente <br>
     *
     * @return lista dei canonicalName
     */
    public List<String> getCanonicalAllPackageFiles() throws AlgosException {
        List<String> lista = new ArrayList<>();

        lista.addAll(getCanonicalModuloPackageFiles(VaadVar.projectNameModulo));

        return lista;
    }


    /**
     * Nome 'canonicalName' di un file  esistente nella directory package <br>
     *
     * @return canonicalName del file
     */
    public String getCanonicalName(String simpleName) throws AlgosException {
        String canonicalName = VUOTA;
        List<String> lista;
        String classeFinalePrevista;
        String classeFinalePath;

        if (textService.isEmpty(simpleName)) {
            if (simpleName == null) {
                logger.error(new AlgosException("Il parametro in ingresso è nullo"));
            }
            logger.error(new AlgosException("Il parametro in ingresso è vuoto"));
        }

        if (simpleName.endsWith(JAVA_SUFFIX)) {
            simpleName = textService.levaCoda(simpleName, JAVA_SUFFIX);
        }
        simpleName = textService.primaMaiuscola(simpleName);

        lista = getPathBreveAllPackageFiles();
        if (lista == null || lista.size() < 1) {
            logger.error(new AlgosException("Non sono riuscito a creare la lista dei files del package"));
        }

        classeFinalePrevista = estraeClasseFinale(simpleName);
        for (String path : lista) {
            classeFinalePath = estraeClasseFinale(path);
            if (classeFinalePath.equals(classeFinalePrevista)) {
                canonicalName = textService.levaTestoPrimaDi(path, DIR_PROGETTO_VUOTO);
                canonicalName = canonicalName.replaceAll(SLASH, PUNTO);
                break;
            }
        }

        if (textService.isEmpty(canonicalName)) {
            logger.error(new AlgosException(String.format("Nel package non esiste la classe %s", simpleName)));
        }

        return canonicalName;
    }


    /**
     * Crea una lista di files/directory ricorsiva nelle sub-directory <br>
     *
     * @return path name completo
     */
    public List<String> recursionSubPathNames(Path start) throws IOException {
        List<String> collect;

        try (Stream<Path> stream = Files.walk(start, Integer.MAX_VALUE)) {
            collect = stream
                    .map(String::valueOf)
                    .sorted()
                    .collect(Collectors.toList());
        }

        return collect;
    }

    /**
     * Crea una lista di tutte le Entity esistenti nella directory packages <br>
     * Considera sia il modulo vaadflow14 sia il progetto corrente <br>
     */
    public List<Class> getAllEntityClass() {
        List<Class> lista = new ArrayList<>();

        return lista;
    }

    /**
     * Sposta un file da una directory ad un'altra <br>
     * Esegue solo se il path sorgente esiste <br>
     * Esegue solo se il path destinazione NON esiste <br>
     * Viene cancellato il file sorgente <br>
     *
     * @param pathFileToBeRead  posizione iniziale del file da spostare
     * @param pathFileToBeWrite posizione iniziale del file da spostare
     *
     * @return testo di errore, vuoto se il file è stato spostato
     */
    public boolean spostaFile(String pathFileToBeRead, String pathFileToBeWrite) {
        return spostaFileStr(pathFileToBeRead, pathFileToBeWrite) == VUOTA;
    }


    /**
     * Sposta un file da una directory ad un'altra <br>
     * Esegue solo se il path sorgente esiste <br>
     * Esegue solo se il path destinazione NON esiste <br>
     * Viene cancellato il file sorgente <br>
     *
     * @param pathFileToBeRead  posizione iniziale del file da spostare
     * @param pathFileToBeWrite posizione iniziale del file da spostare
     *
     * @return testo di errore, vuoto se il file è stato spostato
     */
    public String spostaFileStr(String pathFileToBeRead, String pathFileToBeWrite) {
        String status;

        if (textService.isValid(pathFileToBeRead) && textService.isValid(pathFileToBeWrite)) {
            status = copyFileStr(pathFileToBeRead, pathFileToBeWrite);
        }
        else {
            return PATH_NULLO;
        }

        if (status.equals(VUOTA)) {
            //            status = deleteFile(pathFileToBeRead).isValido();
        }

        return status;
    }


    /**
     * Controlla se il primo carattere della stringa passata come parametro è quello previsto <br>
     *
     * @param testoIngresso          da elaborare
     * @param primoCarattereExpected da controllare
     *
     * @return true se il primo carattere NON è uno quello previsto
     */
    public boolean isNotPrimoCarattere(final String testoIngresso, final String primoCarattereExpected) {
        boolean status = false;
        String primoCarattereEffettivo;

        if (textService.isValid(testoIngresso)) {
            primoCarattereEffettivo = testoIngresso.substring(0, 1);
            if (!primoCarattereEffettivo.equals(primoCarattereExpected)) {
                status = true;
            }
        }

        return status;
    }

    /**
     * Controlla se l'ultimo carattere della stringa passata come parametro è quello previsto <br>
     *
     * @param testoIngresso           da elaborare
     * @param ultimoCarattereExpected da controllare
     *
     * @return true se l'ultimo carattere è quello previsto
     */
    public boolean isUltimoCarattere(final String testoIngresso, final String ultimoCarattereExpected) {
        boolean status = false;
        String ultimoCarattereEffettivo;

        if (textService.isValid(testoIngresso)) {
            ultimoCarattereEffettivo = testoIngresso.substring(testoIngresso.length() - 1);
            if (ultimoCarattereEffettivo.equals(ultimoCarattereExpected)) {
                status = true;
            }
        }

        return status;
    }


    /**
     * Controlla se il primo carattere della stringa passata come parametro è uno 'slash' <br>
     *
     * @param testoIngresso da elaborare
     *
     * @return true se NON è uno 'slash'
     */
    public boolean isNotSlashIniziale(final String testoIngresso) {
        return isNotPrimoCarattere(testoIngresso, SLASH);
    }

    /**
     * Controlla se l'ultimo carattere della stringa passata come parametro è uno 'slash' <br>
     *
     * @param testoIngresso da elaborare
     *
     * @return true se NON è uno 'slash'
     */
    public boolean isSlashFinale(final String testoIngresso) {
        return isUltimoCarattere(testoIngresso, SLASH);
    }


    /**
     * Controlla la stringa passata come parametro termina con un 'suffix' (3 caratteri terminali dopo un punto) <br>
     *
     * @param testoIngresso da elaborare
     *
     * @return true se MANCA il 'suffix'
     */
    public boolean isNotSuffix(String testoIngresso) {
        boolean status = true;
        String quartultimoCarattere;
        int gap = 4;
        int max;
        String tagPatchProperties = ".properties";
        String tagPatchGitIgnore = ".gitignore";
        String tagPatchJava = ".java";
        String tagPatchMd = ".md";

        if (textService.isValid(testoIngresso)) {
            max = testoIngresso.length();
            quartultimoCarattere = testoIngresso.substring(max - gap, max - gap + 1);
            if (quartultimoCarattere.equals(PUNTO)) {
                status = false;
            }
        }

        if (testoIngresso.endsWith(tagPatchProperties)) {
            status = false;
        }

        if (testoIngresso.endsWith(tagPatchGitIgnore)) {
            status = false;
        }

        if (testoIngresso.endsWith(tagPatchJava)) {
            status = false;
        }

        if (testoIngresso.endsWith(tagPatchMd)) {
            status = false;
        }

        return status;
    }

    public AResult checkFileDirectory(final String methodName, final File fileDirectoryToBeChecked) {
        AResult result = AResult.build().method(methodName).target(fileDirectoryToBeChecked.getAbsolutePath());
        String message;

        if (fileDirectoryToBeChecked == null) {
            logger.error(new WrapLog().exception(new AlgosException(PARAMETRO_NULLO)).usaDb().type(AETypeLog.file));
            return result.errorMessage(PARAMETRO_NULLO);
        }

        if (textService.isEmpty(fileDirectoryToBeChecked.getName())) {
            logger.error(new WrapLog().exception(new AlgosException(PATH_NULLO)).usaDb().type(AETypeLog.file));
            return result.errorMessage(PATH_NULLO);
        }

        if (!fileDirectoryToBeChecked.getPath().equals(fileDirectoryToBeChecked.getAbsolutePath())) {
            message = String.format("%s%s%s", PATH_NOT_ABSOLUTE, FORWARD, fileDirectoryToBeChecked.getAbsolutePath());
            logger.error(new WrapLog().exception(new AlgosException(message)).usaDb().type(AETypeLog.file));
            return result.errorMessage(message);
        }

        return result;
    }

    public AResult checkPath(final String methodName, final String absolutePathToBeChecked) {
        AResult result = AResult.build().method(methodName).target(absolutePathToBeChecked);

        if (absolutePathToBeChecked == null) {
            logger.error(new WrapLog().exception(new AlgosException(PATH_NULLO)).usaDb().type(AETypeLog.file));
            return result.errorMessage(PATH_NULLO);
        }

        if (absolutePathToBeChecked.length() == 0) {
            logger.error(new WrapLog().exception(new AlgosException(PATH_VUOTO)).usaDb().type(AETypeLog.file));
            return result.errorMessage(PATH_VUOTO).target("(vuoto)");
        }

        if (this.isNotSlashIniziale(absolutePathToBeChecked)) {
            logger.error(new WrapLog().exception(new AlgosException(PATH_NOT_ABSOLUTE)).usaDb().type(AETypeLog.file));
            return result.errorMessage(PATH_NOT_ABSOLUTE);
        }

        return result;
    }


    /**
     * Recupera una lista di files (sub-directory escluse) dalla directory <br>
     *
     * @param pathDirectory da spazzolare
     *
     * @return lista di files
     */
    public List<File> getFiles(String pathDirectory) {
        List<File> lista = new ArrayList();
        File unaDirectory = new File(pathDirectory);
        File[] array = unaDirectory.listFiles();

        for (File unFile : array) {
            if (unFile.isFile()) {
                lista.add(unFile);
            }
        }

        return lista;
    }


    /**
     * Recupera una lista di nomi di files (sub-directory escluse) dalla directory <br>
     * Elimina il suffisso '.java' finale <br>
     *
     * @param pathDirectory da spazzolare
     *
     * @return lista di files
     */
    public List<String> getFilesNames(String pathDirectory) {
        List<String> lista = new ArrayList();
        List<File> listaFiles = getFiles(pathDirectory);
        String nome;

        for (File unFile : listaFiles) {
            if (unFile.isFile()) {
                nome = unFile.getName();
                nome = textService.levaCoda(nome, JAVA_SUFFIX);
                lista.add(nome);
            }
        }

        return lista;
    }

}
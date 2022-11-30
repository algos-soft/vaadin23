package it.algos.integration.service;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 13-mar-2022
 * Time: 08:11
 * <p>
 * Unit test di una classe di servizio (di norma) <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {SimpleApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@Tag("service")
@DisplayName("File service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FileServiceTest extends SpringTest {

    static boolean FLAG_CREAZIONE_INIZIALE = true;

    //    private static String DIRECTORY_IDEA = "/Users/gac/Documents/IdeaProjects/";

    //    private static String NOME_FILE_UNO = "Mantova.txt";

    private static String DIRECTORY_TEST = "/Users/gac/Desktop/test/";

    private static String DIR_START = "Prova/";

    private static String DIR_UNO = "Pippo/";


    private static String DIR_DUE = "Mantova/";

    private static String DIR_TRE = "Possibile/";

    private static String DIR_QUATTRO = "Probabile/";

    private static String FILE_UNO = "Pluto.txt";

    private static String FILE_DUE = "Secondo.ccs";

    private static String FILE_TRE = "Genova.java";

    private static String FILE_QUATTRO = "Paperino.txt";

    private static String FILE_CINQUE = "Topolino.txt";

    private static String FILE_SEI = "Omicron.txt";

    private static String FILE_SETTE = "Lambda.txt";

    private static String FILE_OTTO = "Sigma.txt";

    private static String FILE_NOVE = "Zeta.txt";

    private static String SOURCE = "Sorgente";

    private static String DEST = "Destinazione";

    private static String PATH_SOURCE = DIRECTORY_TEST + SOURCE + SLASH;

    private static String PATH_START = DIRECTORY_TEST + DIR_START + SLASH;

    private static String PATH_DIR_UNO = PATH_SOURCE + DIR_UNO;

    private static String PATH_DIR_DUE = PATH_DIR_UNO + DIR_DUE;

    private static String PATH_DIR_TRE = PATH_DIR_DUE + DIR_TRE;


    private static String PATH_FILE_UNO = PATH_SOURCE + FILE_UNO;

    private static String PATH_FILE_DUE = PATH_SOURCE + FILE_DUE;

    private static String PATH_FILE_TRE = PATH_DIR_UNO + FILE_TRE;

    private static String PATH_FILE_QUATTRO = PATH_DIR_DUE + FILE_QUATTRO;

    private static String PATH_FILE_CINQUE = PATH_DIR_TRE + FILE_CINQUE;


    private static String FILE_AGGIUNTO_TRE = DIR_TRE + "FileSorgenteAggiunto.ccs";

    private static String DIRECTORY_NON_ESISTENTE = DIRECTORY_TEST + "Genova/";

    private static String DIRECTORY_DA_COPIARE = DIRECTORY_TEST + "NuovaDirectory/";

    private static String DIRECTORY_MANCANTE = DIRECTORY_TEST + "CartellaCopiata/";

    private static String FILE_AGGIUNTO = DIRECTORY_MANCANTE + "TerzaPossibilita.htm";


    private static String FILE_ESISTENTE_CON_MAIUSCOLA_SBAGLIATA = "pluto.rtf";

    private static String FILE_NO_SUFFIX = DIRECTORY_TEST + "Topolino";

    private static String FILE_NON_ESISTENTE = DIRECTORY_TEST + "Topolino.txt";

    private static String PATH_FILE_NO_PATH = "Users/gac/Desktop/test/Pluto.rtf";

    private static String PATH_DIRECTORY_NO_PATH = "Users/gac/Desktop/test/Mantova/";

    private static String PATH_FILE_NO_GOOD = "/Users/gac/Desktop/test/Pa perino/Topolino.abc";

    private static String PATH_FILE_ANOMALO = DIRECTORY_TEST + "Pluto.properties";

    private static String PATH_DIRECTORY_ESISTENTE_CON_MAIUSCOLA_SBAGLIATA = "/Users/gac/desktop/test/Pippo/";

    private static String PATH_FILE_DELETE = "/Users/gac/Desktop/test/NonEsiste/Minni.txt";

    //    private static String MODULO_PROVA = "vaadtest/";

    private static String VALIDO = "TROVATO";

    private static String ESISTE_FILE = " isEsisteFile() ";

    private static String ESISTE_DIRECTORY = " isEsisteDirectory() ";

    private static String CREA_FILE = " creaFile() ";

    private static String CREA_DIRECTORY = " creaDirectory() ";

    private static String DELETE_FILE = " deleteFile() ";

    private static String DELETE_DIRECTORY = " deleteDirectory() ";


    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    private FileService service;

    private File unFile;

    private String nomeFile;

    private String nomeCompletoFile;

    private String nomeDirectory;

    private String nomeCompletoDirectory;

    private List<File> listaDirectory;

    private List<File> listaFile;


    private String file1 = "Alfa.txt";

    private String file2 = "Beta.txt";

    private String file3 = "Gamma.txt";

    private String file4 = "Delta.txt";

    private String file5 = "Lambda.txt";

    private String file6 = "Omega.txt";

    private String sub1 = "Sub1";

    private File src;

    private File dest;

    private File srcSub1;

    private File destSub1;

    private String dirSource;

    private String dirDest;

    private String pathDirUno;

    private String pathDirDue;

    private String pathDirTre;

    private String pathDirQuattro;

    private String pathFileUno;

    private String pathFileDue;

    private String pathFileTre;

    private String pathFileQuattro;

    private String pathFileCinque;

    private String pathFileSei;

    private String pathFileSette;

    private String pathFileOtto;

    private String pathFileNove;

    private String srcTesto = "Testo di un file della cartella sorgente";

    private String destTesto = "Testo iniziale che verrà modificato";

    private String destTestoFisso = "Testo di un file della cartella di destinazione che non viene modificato";

    private File srcA_uguale;

    private File srcB;

    private File srcC_diverso;

    private File src_sub_uguale;

    private File src_sub_diverso;

    private File destA_uguale;

    private File destC_diverso;

    private File destD;

    private File dest_sub_uguale;

    private File dest_sub_diverso;

    //--path
    //--esiste directory
    //--manca slash iniziale
    protected static Stream<Arguments> DIRECTORY() {
        return Stream.of(
                Arguments.of(null, false, false),
                Arguments.of(VUOTA, false, false),
                Arguments.of(DIRECTORY_TEST, true, false),
                Arguments.of(PATH_SOURCE, true, false),
                Arguments.of(PATH_DIR_UNO, true, false),
                Arguments.of(PATH_DIR_DUE, true, false),
                Arguments.of(PATH_DIR_TRE, true, false),
                Arguments.of("Users/gac/Documents/IdeaProjects/operativi/vaadin23/src/", false, true),
                Arguments.of("/Users/gac/Documents/IdeaProjects/operativi/vaadin23/src/", true, false),
                Arguments.of("/Users/gac/Desktop/test/sorgente/Pippo/Mantova", true, false)
        );
    }


    //--path
    //--esiste
    protected static Stream<Arguments> FILE() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(VUOTA, false),
                Arguments.of("nonEsiste", false),
                Arguments.of(FILE_NO_SUFFIX, false),
                Arguments.of(FILE_NON_ESISTENTE, false),
                Arguments.of(PATH_FILE_NO_PATH, false),
                Arguments.of(PATH_DIR_UNO, false),
                Arguments.of(PATH_FILE_UNO, true),
                Arguments.of(FILE_ESISTENTE_CON_MAIUSCOLA_SBAGLIATA, false),
                Arguments.of(DIR_UNO, false),
                Arguments.of("/Users/gac/Desktop/test/Mantova/", false),
                Arguments.of("/Users/gac/Desktop/test/Mantova", false),
                Arguments.of("/Users/gac/Desktop/test/Mantova.", false),
                Arguments.of("/Users/gac/Desktop/test/Mantova.tx", false),
                Arguments.of("/Users/gac/Desktop/test/Mantova.txt", false),
                Arguments.of("/Users/gac/Documents/IdeaProjects/operativi/vaadin23/src/vaadin23", false),
                Arguments.of("Users/gac/Documents/IdeaProjects/operativi/vaadin23/src/vaadin23.iml", false),
                Arguments.of("/Users/gac/Documents/IdeaProjects/operativi/vaadin23/src/vaadin23.iml", true),
                Arguments.of("/Users/gac/Desktop/test/Pippo", false)
        );
    }


    //--pathDir sorgente
    //--pathDir destinazione
    //--nome file
    //--flag copiato
    protected static Stream<Arguments> COPY_FILE_ONLY() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, VUOTA, false),
                Arguments.of(VUOTA, VUOTA, FILE_UNO, false),
                Arguments.of(VUOTA, VUOTA, VUOTA, false),
                Arguments.of(DIR_TRE, DIR_DUE, FILE_UNO, false),
                Arguments.of(DIR_TRE, DIR_DUE, VUOTA, false),
                Arguments.of(DIR_TRE, DIR_DUE, VUOTA, false),
                Arguments.of(PATH_DIR_DUE, PATH_DIR_TRE, FILE_UNO, true),
                Arguments.of(DIRECTORY_TEST + DIR_DUE, DIRECTORY_TEST + DIR_TRE, FILE_UNO, true),
                Arguments.of(DIRECTORY_TEST + DIR_DUE, DIRECTORY_TEST + DIR_TRE, FILE_QUATTRO, true)
        );
    }


    //--pathDir sorgente
    //--pathDir destinazione
    //--nome file
    //--flag copiato
    protected static Stream<Arguments> COPY_FILE_DELETE() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, VUOTA, false),
                Arguments.of(VUOTA, VUOTA, FILE_UNO, false),
                Arguments.of(VUOTA, VUOTA, VUOTA, false),
                Arguments.of(DIR_TRE, DIR_DUE, FILE_UNO, false),
                Arguments.of(DIR_TRE, DIR_DUE, VUOTA, false),
                Arguments.of(DIR_TRE, DIR_DUE, VUOTA, false),
                Arguments.of(PATH_DIR_DUE, PATH_DIR_TRE, FILE_UNO, true),
                Arguments.of(DIRECTORY_TEST + DIR_DUE, DIRECTORY_TEST + DIR_TRE, FILE_UNO, true),
                Arguments.of(DIRECTORY_TEST + DIR_DUE, DIRECTORY_TEST + DIR_TRE, FILE_TRE, true),
                Arguments.of(DIRECTORY_TEST + DIR_DUE, DIRECTORY_TEST + DIR_TRE, FILE_QUATTRO, true)
        );
    }

    //--type copy
    //--pathDir sorgente
    //--pathDir destinazione
    //--directory copiata
    protected static Stream<Arguments> COPY_DIRECTORY_TYPES() {
        return Stream.of(
                Arguments.of(null, VUOTA, VUOTA, false),
                Arguments.of(AECopy.fileOnly, VUOTA, VUOTA, false),
                Arguments.of(AECopy.dirOnly, VUOTA, VUOTA, false),
                Arguments.of(AECopy.dirOnly, VUOTA, DEST, false),
                Arguments.of(AECopy.dirOnly, SOURCE, VUOTA, false),
                Arguments.of(AECopy.dirOnly, DIRECTORY_MANCANTE, DIRECTORY_TEST + DEST, true),
                Arguments.of(AECopy.dirOnly, DIRECTORY_TEST + DIR_DUE, DIRECTORY_TEST + DIR_DUE, false),
                Arguments.of(AECopy.dirOnly, DIRECTORY_TEST + DIR_DUE, DIRECTORY_TEST + DIR_TRE, false)
        );
    }


    //--pathDir sorgente
    //--pathDir destinazione
    //--directory copiata
    protected static Stream<Arguments> COPY_DIRECTORY_ONLY() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, false),
                Arguments.of(VUOTA, VUOTA, false),
                Arguments.of(VUOTA, VUOTA, false),
                Arguments.of(VUOTA, DEST, false),
                Arguments.of(SOURCE, VUOTA, false),
                Arguments.of(DIRECTORY_MANCANTE, DIRECTORY_TEST + DEST, true),
                Arguments.of(DIRECTORY_TEST + DIR_DUE, DIRECTORY_TEST + DIR_DUE, false),
                Arguments.of(DIRECTORY_TEST + DIR_DUE, DIRECTORY_TEST + DIR_TRE, false)
        );
    }

    //--pathDir sorgente
    //--pathDir destinazione
    //--directory copiata
    protected static Stream<Arguments> COPY_DIRECTORY_SEMPRE() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, false),
                Arguments.of(VUOTA, VUOTA, false),
                Arguments.of(VUOTA, VUOTA, false),
                Arguments.of(VUOTA, DEST, false),
                Arguments.of(SOURCE, VUOTA, false),
                Arguments.of(DIRECTORY_MANCANTE, DIRECTORY_TEST + DEST, true),
                Arguments.of(DIRECTORY_TEST + DIR_UNO, DIRECTORY_TEST + DIR_DUE, true),
                Arguments.of(DIRECTORY_TEST + DIR_DUE, DIRECTORY_TEST + DIR_TRE, true)
        );
    }

    //--pathDir sorgente
    //--pathDir destinazione
    //--directory copiata
    protected static Stream<Arguments> COPY_DIRECTORY_ADD_ONLY() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, false),
                Arguments.of(VUOTA, VUOTA, false),
                Arguments.of(VUOTA, VUOTA, false),
                Arguments.of(VUOTA, DEST, false),
                Arguments.of(SOURCE, VUOTA, false),
                Arguments.of(DIRECTORY_MANCANTE, DIRECTORY_TEST + DEST, true),
                Arguments.of(DIRECTORY_TEST + DIR_UNO, DIRECTORY_TEST + DIR_DUE, true),
                Arguments.of(DIRECTORY_TEST + DIR_DUE, DIRECTORY_TEST + DIR_TRE, true)
        );
    }

    //--pathDir sorgente
    //--pathDir destinazione
    //--directory copiata
    protected static Stream<Arguments> COPY_DIRECTORY_MODIFICA() {
        return Stream.of(
                //                Arguments.of(VUOTA, VUOTA, false),
                //                Arguments.of(VUOTA, VUOTA, false),
                //                Arguments.of(VUOTA, VUOTA, false),
                //                Arguments.of(VUOTA, DEST, false),
                //                Arguments.of(SOURCE, VUOTA, false),
                Arguments.of(DIRECTORY_MANCANTE, DIRECTORY_TEST + DEST, true),
                Arguments.of(DIRECTORY_TEST + DIR_UNO, DIRECTORY_TEST + DIR_DUE, true),
                Arguments.of(DIRECTORY_TEST + DIR_DUE, DIRECTORY_TEST + DIR_TRE, true)
        );
    }

    /**
     * Execute only once before running all tests <br>
     * Esegue una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpAll() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();

        //--reindirizzo l'istanza della superclasse
        service = fileService;

        //reset iniziale
        cancellaAllTest();
        //        creaCartellaSorgente(DIRECTORY_TEST + DIR_START);
        new File(DIRECTORY_TEST).mkdirs();

        if (FLAG_CREAZIONE_INIZIALE) {
            creazioneListe();
            creazioneDirectory();
            creazioneFiles();
        }
    }


    /**
     * Creazioni di servizio per essere sicuri che ci siano tutti i files/directories richiesti <br>
     */
    private void creazioneListe() {
        listaDirectory = new ArrayList<>();
        listaDirectory.add(new File(PATH_DIR_UNO));
        listaDirectory.add(new File(PATH_DIR_DUE));
        listaDirectory.add(new File(PATH_DIR_TRE));

        listaFile = new ArrayList<>();
        listaFile.add(new File(PATH_FILE_UNO));
        listaFile.add(new File(PATH_FILE_DUE));
        listaFile.add(new File(PATH_FILE_TRE));
        listaFile.add(new File(PATH_FILE_QUATTRO));
        listaFile.add(new File(PATH_FILE_CINQUE));
        //        listaFile.add(new File(DIRECTORY_TEST + FILE_ESISTENTE_CON_MAIUSCOLA_SBAGLIATA));
    }


    /**
     * Creazioni di servizio per essere sicuri che ci siano tutti i files/directories richiesti <br>
     * Alla fine verranno cancellati tutti <br>
     */
    private void creazioneDirectory() {
        if (arrayService.isAllValid(listaDirectory)) {
            for (File directory : listaDirectory) {
                directory.mkdirs();
            }
        }
    }


    /**
     * Creazioni di servizio per essere sicuri che ci siano tutti i files/directories richiesti <br>
     * Alla fine verranno cancellati tutti <br>
     */
    private void creazioneFiles() {
        boolean creato = false;

        if (arrayService.isAllValid(listaFile)) {
            for (File unFile : listaFile) {
                try { // prova ad eseguire il codice
                    creato = unFile.createNewFile();
                } catch (Exception unErrore) { // intercetta l'errore
                    if (service.creaDirectoryParentAndFile(unFile).equals(VUOTA)) {
                        listaDirectory.add(new File(unFile.getParent()));
                    }
                }
            }
        }
    }


    /**
     * Qui passa prima di ogni test <br>
     * Invocare PRIMA il metodo setUpEach() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        unFile = null;
    }


    @ParameterizedTest
    @MethodSource(value = "DIRECTORY")
    @Order(1)
    @DisplayName("1 - Check di una directory")
        //--path
        //--esiste directory
        //--manca slash iniziale
    void checkDirectory(final String path, final boolean previstoBooleano) {
        System.out.println("1 - Check di una directory");
        System.out.println(VUOTA);

        sorgente = path;
        ottenutoRisultato = service.checkDirectory(sorgente);
        assertNotNull(ottenutoRisultato);
        printRisultato(ottenutoRisultato);
        assertEquals(previstoBooleano, ottenutoRisultato.isValido());
    }

    @ParameterizedTest
    @MethodSource(value = "DIRECTORY")
    @Order(2)
    @DisplayName("2 - Esistenza di una directory")
        //--path
        //--esiste directory
        //--manca slash iniziale
    void isEsisteDirectory(final String path, final boolean previstoBooleano) {
        System.out.println("2 - Esistenza di una directory");
        System.out.println(VUOTA);

        sorgente = path;
        ottenutoBooleano = service.isEsisteDirectory(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
    }

    @ParameterizedTest
    @MethodSource(value = "FILE")
    @Order(3)
    @DisplayName("3 - Check di un file")
        //--path
        //--esiste directory
        //--manca slash iniziale
    void checkFile(final String path, final boolean previstoBooleano) {
        System.out.println("3 - Check di un file");
        System.out.println(VUOTA);

        sorgente = path;
        ottenutoRisultato = service.checkFile(sorgente);
        assertNotNull(ottenutoRisultato);
        assertEquals(previstoBooleano, ottenutoRisultato.isValido());
        printRisultato(ottenutoRisultato);
    }


    @ParameterizedTest
    @MethodSource(value = "FILE")
    @Order(4)
    @DisplayName("4 - Esistenza di un file")
        //--path
        //--esiste directory
        //--manca slash iniziale
    void isEsisteFile(final String sorgente, final boolean previstoBooleano) {
        System.out.println("4 - Esistenza di un file");
        System.out.println(VUOTA);

        ottenutoBooleano = service.isEsisteFile(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
    }


    @Test
    @Order(5)
    @DisplayName("5 - Creo e cancello una directory")
    void directory() {
        System.out.println("5 - Creo e cancello una directory");
        System.out.println(VUOTA);

        sorgente = DIRECTORY_TEST + "test4522/";
        System.out.println(String.format("Nome (completo) della directory: %s", sorgente));
        System.out.println(VUOTA);

        System.out.println("A - Controlla l'esistenza (isEsisteDirectory)");
        ottenutoBooleano = service.isEsisteDirectory(sorgente);
        assertFalse(ottenutoBooleano);
        System.out.println("Prima non esiste");
        System.out.println(VUOTA);

        System.out.println("B - Crea la directory (creaDirectory)");
        ottenutoRisultato = service.creaDirectory(sorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        System.out.println("La directory è stata creata");
        System.out.println(VUOTA);

        System.out.println("C - Ricontrolla l'esistenza (isEsisteDirectory)");
        ottenutoBooleano = service.isEsisteDirectory(sorgente);
        assertTrue(ottenutoBooleano);
        System.out.println("La directory esiste");
        System.out.println(VUOTA);

        System.out.println("D - Cancella la directory (deleteDirectory)");
        ottenutoRisultato = service.deleteDirectory(sorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        System.out.println("La directory è stata cancellata");
        System.out.println(VUOTA);

        System.out.println("E - Controllo finale (isEsisteDirectory)");
        ottenutoBooleano = service.isEsisteDirectory(sorgente);
        assertFalse(ottenutoBooleano);
        System.out.println("La directory non esiste");
        System.out.println(VUOTA);
    }


    @Test
    @Order(6)
    @DisplayName("6 - Creo e cancello un file in una directory 'stabile'")
    void fileRoot() {
        System.out.println("6 - Creo e cancello un file in una directory 'stabile'");
        System.out.println("Il file viene creato VUOTO");
        System.out.println(VUOTA);

        sorgente = "/Users/gac/Desktop/Mantova.txt";
        System.out.println(String.format("Nome (completo) del file: %s", sorgente));
        System.out.println(VUOTA);

        System.out.println("A - Controlla l'esistenza (isEsisteFile)");
        ottenutoBooleano = service.isEsisteFile(sorgente);
        assertFalse(ottenutoBooleano);
        System.out.println("Prima non esiste");
        System.out.println(VUOTA);

        System.out.println("B - Creo il file (creaFile)");
        ottenutoRisultato = service.creaFile(sorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        System.out.println("Il file è stato creato");
        System.out.println(VUOTA);

        System.out.println("C - Ricontrolla l'esistenza (isEsisteFile)");
        ottenutoBooleano = service.isEsisteFile(sorgente);
        assertTrue(ottenutoBooleano);
        System.out.println("Il file esiste");
        System.out.println(VUOTA);

        System.out.println("D - Cancello il file (deleteFile)");
        ottenutoRisultato = service.deleteFile(sorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        System.out.println("Il file è stato cancellato");
        System.out.println(VUOTA);

        System.out.println("E - Controllo finale (isEsisteFile)");
        ottenutoBooleano = service.isEsisteFile(sorgente);
        assertFalse(ottenutoBooleano);
        System.out.println("Il file non esiste");
        System.out.println(VUOTA);
    }

    @Test
    @Order(7)
    @DisplayName("7 - Creo e cancello un file in una directory 'inesistente'")
    void fileSottoCartella() {
        System.out.println("7 - Creo e cancello un file in una directory 'inesistente'");
        System.out.println("Il file viene creato VUOTO");
        System.out.println(VUOTA);

        sorgente2 = DIRECTORY_TEST + "Torino/";
        sorgente3 = sorgente2 + "Padova/";
        sorgente = sorgente3 + "Mantova.txt";
        System.out.println(String.format("Nome (completo) del file: %s", sorgente));
        System.out.println(VUOTA);

        System.out.println("A - Controlla l'esistenza (isEsisteFile)");
        ottenutoBooleano = service.isEsisteFile(sorgente);
        assertFalse(ottenutoBooleano);
        System.out.println("Prima non esiste");
        System.out.println(VUOTA);

        System.out.println("B - Creo il file (creaDirectoryParentAndFile)");
        ottenutoRisultato = service.creaFile(sorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        System.out.println("Il file è stato creato");
        System.out.println(VUOTA);

        System.out.println("C - Ricontrolla l'esistenza (isEsisteFile)");
        ottenutoBooleano = service.isEsisteFile(sorgente);
        assertTrue(ottenutoBooleano);
        System.out.println("Il file esiste");
        System.out.println(VUOTA);

        System.out.println("D - Cancello il file (deleteFile)");
        ottenutoRisultato = service.deleteFile(sorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        System.out.println("Il file è stato cancellato");
        System.out.println(VUOTA);

        System.out.println("E - Controllo finale del file (isEsisteFile)");
        ottenutoBooleano = service.isEsisteFile(sorgente);
        assertFalse(ottenutoBooleano);
        System.out.println("Il file non esiste");
        System.out.println(VUOTA);

        System.out.println("F - Cancello anche la(e) cartella(e) intermedia(e) (deleteDirectory)");
        ottenutoRisultato = service.deleteDirectory(sorgente2);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        System.out.println("Cancellata la directory provvisoria");
        System.out.println(VUOTA);

        System.out.println("G - Controllo finale della directory (isEsisteDirectory)");
        ottenutoBooleano = service.isEsisteDirectory(sorgente2);
        assertFalse(ottenutoBooleano);
        System.out.println("La directory provvisoria non esiste");
        System.out.println(VUOTA);
    }

    @ParameterizedTest
    @MethodSource(value = "DIRECTORY")
    @Order(8)
    @DisplayName("8 - Controlla la slash iniziale del path")
        //--path
        //--esiste directory
        //--manca slash iniziale
    void isNotSlashIniziale(final String sorgente, final boolean nonUsato, final boolean mancaSlash) {
        System.out.println("8 - Controlla la slash iniziale del path");
        System.out.println(VUOTA);

        ottenutoBooleano = service.isNotSlashIniziale(sorgente);
        assertEquals(mancaSlash, ottenutoBooleano);
    }

    @ParameterizedTest
    @MethodSource(value = "COPY_FILE_ONLY")
    @Order(9)
    @DisplayName("9 - Copia il file solo se non esiste")
        //--pathDir sorgente
        //--pathDir destinazione
        //--nome file
        //--flag copiato
    void copyFileOnly(final String srcPathDir, final String destPathDir, final String nomeFile, final boolean copiato) {
        System.out.println("9 - Copia il file solo se non esiste");
        System.out.println(VUOTA);

        if (textService.isValid(srcPathDir) && textService.isValid(destPathDir) && textService.isValid(nomeFile)) {
            new File(srcPathDir).mkdirs();
            new File(destPathDir).mkdirs();
            try {
                new File(srcPathDir + SLASH + FILE_UNO).createNewFile();
                new File(srcPathDir + SLASH + FILE_QUATTRO).createNewFile();
                new File(destPathDir + SLASH + FILE_UNO).createNewFile();
            } catch (Exception unErrore) {
                assertFalse(true);
            }
            service.sovraScriveFile(srcPathDir + SLASH + FILE_UNO, srcTesto);
            service.sovraScriveFile(srcPathDir + SLASH + FILE_QUATTRO, srcTesto);
            service.sovraScriveFile(destPathDir + SLASH + FILE_UNO, destTestoFisso);
        }

        ottenutoRisultato = service.copyFile(AECopy.fileOnly, srcPathDir, destPathDir, nomeFile);
        assertNotNull(ottenutoRisultato);
        assertEquals(copiato, ottenutoRisultato.isValido());
        printRisultato(ottenutoRisultato);

        if (ottenutoRisultato.isValido()) {
            if (nomeFile.equals(FILE_UNO)) {
                service.leggeFile(destPathDir + SLASH + FILE_UNO).equals(srcTesto);
                assertEquals(KEY_FILE_ESISTENTE, ottenutoRisultato.getTagCode());
                assertFalse(service.isEsisteFile(destPathDir + SLASH + FILE_QUATTRO));
            }

            if (nomeFile.equals(FILE_QUATTRO)) {
                service.leggeFile(destPathDir + SLASH + FILE_UNO).equals(srcTesto);
                service.leggeFile(destPathDir + SLASH + FILE_QUATTRO).equals(destTestoFisso);
                assertEquals(KEY_FILE_CREATO, ottenutoRisultato.getTagCode());
                assertTrue(service.isEsisteFile(destPathDir + SLASH + FILE_QUATTRO));
            }
        }

        //--cancella le due cartelle
        cancellaCartelle(srcPathDir, destPathDir);
    }


    @ParameterizedTest
    @MethodSource(value = "COPY_FILE_DELETE")
    @Order(10)
    @DisplayName("10 - Copia sempre il file")
        //--pathDir sorgente
        //--pathDir destinazione
        //--nome file
        //--flag copiato
    void copyFileDelete(final String srcPathDir, final String destPathDir, final String nomeFile, final boolean copiato) {
        System.out.println("10 - Copia sempre il file");
        System.out.println(VUOTA);

        if (textService.isValid(srcPathDir) && textService.isValid(destPathDir) && textService.isValid(nomeFile)) {
            new File(srcPathDir).mkdirs();
            new File(destPathDir).mkdirs();
            try {
                new File(srcPathDir + SLASH + FILE_UNO).createNewFile();
                new File(srcPathDir + SLASH + FILE_TRE).createNewFile();
                new File(srcPathDir + SLASH + FILE_QUATTRO).createNewFile();
                new File(destPathDir + SLASH + FILE_UNO).createNewFile();
                new File(destPathDir + SLASH + FILE_TRE).createNewFile();
            } catch (Exception unErrore) {
                assertFalse(true);
            }
            service.sovraScriveFile(srcPathDir + SLASH + FILE_UNO, srcTesto);
            service.sovraScriveFile(srcPathDir + SLASH + FILE_TRE, srcTesto);
            service.sovraScriveFile(srcPathDir + SLASH + FILE_QUATTRO, srcTesto);
            service.sovraScriveFile(destPathDir + SLASH + FILE_UNO, srcTesto);
            service.sovraScriveFile(destPathDir + SLASH + FILE_TRE, destTesto);
        }

        ottenutoRisultato = service.copyFile(AECopy.fileDelete, srcPathDir, destPathDir, nomeFile);
        assertNotNull(ottenutoRisultato);
        assertEquals(copiato, ottenutoRisultato.isValido());
        printRisultato(ottenutoRisultato);

        if (ottenutoRisultato.isValido()) {
            if (nomeFile.equals(FILE_UNO)) {
                service.leggeFile(destPathDir + SLASH + FILE_UNO).equals(srcTesto);
                assertEquals(KEY_FILE_ESISTENTE, ottenutoRisultato.getTagCode());
                assertFalse(service.isEsisteFile(destPathDir + SLASH + FILE_QUATTRO));
            }
            if (nomeFile.equals(FILE_TRE)) {
                service.leggeFile(destPathDir + SLASH + FILE_TRE).equals(srcTesto);
                assertEquals(KEY_FILE_MODIFICATO, ottenutoRisultato.getTagCode());
                assertFalse(service.isEsisteFile(destPathDir + SLASH + FILE_QUATTRO));
            }
            if (nomeFile.equals(FILE_QUATTRO)) {
                service.leggeFile(destPathDir + SLASH + FILE_QUATTRO).equals(srcTesto);
                assertEquals(KEY_FILE_CREATO, ottenutoRisultato.getTagCode());
                assertTrue(service.isEsisteFile(destPathDir + SLASH + FILE_QUATTRO));
            }
        }

        //--cancella le due cartelle
        cancellaCartelle(srcPathDir, destPathDir);
    }

    //    @Test
    @Order(10)
    @DisplayName("10 - Copia un file NON esistente (AECopy.fileSoloSeNonEsiste)")
    void copyFile() {
        System.out.println("10 - Copia un file NON esistente (AECopy.fileSoloSeNonEsiste)");
        System.out.println("Il file viene creato VUOTO");
        System.out.println(VUOTA);
        String nomeFile = FILE_UNO;
        String dirSorgente = DIR_UNO;
        String dirDestinazione = DIR_TRE;
        String pathSorgente = dirSorgente + nomeFile;
        String pathDestinazione = dirDestinazione + nomeFile;

        cancellaCartelle(pathSorgente, pathDestinazione);
        System.out.println(String.format("Nome (completo) del file: %s", pathDestinazione));

        System.out.println(VUOTA);
        System.out.println("A - Inizialmente non esiste (isEsisteFile)");
        ottenutoBooleano = service.isEsisteFile(pathSorgente);
        assertFalse(ottenutoBooleano);

        System.out.println(VUOTA);
        System.out.println("B - Viene creato (creaFile)");
        ottenutoRisultato = service.creaFile(pathSorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println("C - Controlla che NON esista nella directory di destinazione (isEsisteFile)");
        ottenutoBooleano = service.isEsisteFile(pathDestinazione);
        assertFalse(ottenutoBooleano);

        System.out.println(VUOTA);
        System.out.println("D - Il file viene copiato (copyFile)");
        ottenutoRisultato = service.copyFile(AECopy.fileOnly, dirSorgente, dirDestinazione, nomeFile);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        printRisultato(ottenutoRisultato);

        System.out.println(VUOTA);
        System.out.println("E - Controlla che sia stato copiato (isEsisteFile)");
        ottenutoBooleano = service.isEsisteFile(pathDestinazione);
        assertTrue(ottenutoBooleano);

        System.out.println(VUOTA);
        System.out.println("F - Cancellazione finale del file dalla directory sorgente (deleteFile)");
        ottenutoRisultato = service.deleteFile(pathSorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println("G - Cancellazione finale del file dalla directory destinazione (deleteFile)");
        ottenutoRisultato = service.deleteFile(pathDestinazione);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
    }

    //    @Test
    @Order(11)
    @DisplayName("11 - Cerca di copiare un file GIA esistente (AECopy.fileSoloSeNonEsiste)")
    void copyFile2() {
        System.out.println("11 - Cerca di copiare un file GIA esistente (AECopy.fileSoloSeNonEsiste)");
        System.out.println("Il file viene creato VUOTO");
        System.out.println(VUOTA);

        String nomeFile = FILE_UNO;
        String dirSorgente = DIR_UNO;
        String dirDestinazione = DIR_TRE;
        String pathSorgente = dirSorgente + nomeFile;
        String pathDestinazione = dirDestinazione + nomeFile;

        cancellaCartelle(pathSorgente, pathDestinazione);
        System.out.println(String.format("Nome (completo) del file: %s", pathDestinazione));

        System.out.println(VUOTA);
        System.out.println("A - Inizialmente non esiste nella directory sorgente (isEsisteFile)");
        ottenutoBooleano = service.isEsisteFile(pathSorgente);
        assertFalse(ottenutoBooleano);

        System.out.println(VUOTA);
        System.out.println("B - Inizialmente non esiste nella directory destinazione (isEsisteFile)");
        ottenutoBooleano = service.isEsisteFile(pathDestinazione);
        assertFalse(ottenutoBooleano);

        System.out.println(VUOTA);
        System.out.println("C - Viene creato nella directory sorgente (creaFile)");
        ottenutoRisultato = service.creaFile(pathSorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println("D - Viene creato nella directory destinazione (creaFile)");
        ottenutoRisultato = service.creaFile(pathDestinazione);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println("E - Controlla che esista nella directory sorgente (isEsisteFile)");
        ottenutoBooleano = service.isEsisteFile(pathSorgente);
        assertTrue(ottenutoBooleano);

        System.out.println(VUOTA);
        System.out.println("F - Controlla che esista nella directory di destinazione (isEsisteFile)");
        ottenutoBooleano = service.isEsisteFile(pathDestinazione);
        assertTrue(ottenutoBooleano);

        System.out.println(VUOTA);
        System.out.println("G - Prova a copiare il file sovrascrivendo quello esistente (copyFile)");
        ottenutoRisultato = service.copyFile(AECopy.fileOnly, dirSorgente, dirDestinazione, nomeFile);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        printRisultato(ottenutoRisultato);

        System.out.println(VUOTA);
        System.out.println("H - Cancellazione finale del file dalla directory sorgente (deleteFile)");
        ottenutoRisultato = service.deleteFile(pathSorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println(" - Cancellazione finale del file dalla directory destinazione (deleteFile)");
        ottenutoRisultato = service.deleteFile(pathDestinazione);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
    }


    //    @Test
    @Order(12)
    @DisplayName("12 - Copia un file esistente (AECopy.fileSovrascriveSempreAncheSeEsiste)")
    void copyFile3() {
        System.out.println("12 - Copia un file esistente (AECopy.fileSovrascriveSempreAncheSeEsiste)");
        System.out.println("Il file viene creato VUOTO");
        System.out.println(VUOTA);

        String nomeFile = FILE_UNO;
        String dirSorgente = DIR_UNO;
        String dirDestinazione = DIR_TRE;
        String pathSorgente = dirSorgente + nomeFile;
        String pathDestinazione = dirDestinazione + nomeFile;

        cancellaCartelle(pathSorgente, pathDestinazione);
        System.out.println(String.format("Nome (completo) del file: %s", pathDestinazione));

        System.out.println(VUOTA);
        System.out.println("A - Inizialmente non esiste nella directory sorgente (isEsisteFile)");
        ottenutoBooleano = service.isEsisteFile(pathSorgente);
        assertFalse(ottenutoBooleano);

        System.out.println(VUOTA);
        System.out.println("B - Inizialmente non esiste nella directory destinazione (isEsisteFile)");
        ottenutoBooleano = service.isEsisteFile(pathDestinazione);
        assertFalse(ottenutoBooleano);

        System.out.println(VUOTA);
        System.out.println("C - Viene creato nella directory sorgente (creaFile)");
        ottenutoRisultato = service.creaFile(pathSorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println("D - Viene creato nella directory destinazione (creaFile)");
        ottenutoRisultato = service.creaFile(pathDestinazione);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println("E - Controlla che esista nella directory sorgente (isEsisteFile)");
        ottenutoBooleano = service.isEsisteFile(pathSorgente);
        assertTrue(ottenutoBooleano);

        System.out.println(VUOTA);
        System.out.println("F - Controlla che esista nella directory di destinazione (isEsisteFile)");
        ottenutoBooleano = service.isEsisteFile(pathDestinazione);
        assertTrue(ottenutoBooleano);

        System.out.println(VUOTA);
        System.out.println("G - Copia il file sovrascrivendo quello esistente (copyFile)");
        ottenutoRisultato = service.copyFile(AECopy.fileDelete, dirSorgente, dirDestinazione, nomeFile);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        printRisultato(ottenutoRisultato);

        System.out.println(VUOTA);
        System.out.println("H - Cancellazione finale del file dalla directory sorgente (deleteFile)");
        ottenutoRisultato = service.deleteFile(pathSorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println(" - Cancellazione finale del file dalla directory destinazione (deleteFile)");
        ottenutoRisultato = service.deleteFile(pathDestinazione);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
    }

    //    @Test
    @Order(13)
    @DisplayName("13 - Copia un file NON esistente (AECopy.fileSovrascriveSempreAncheSeEsiste)")
    void copyFile4() {
        System.out.println("13 - Copia un file NON esistente (AECopy.fileSovrascriveSempreAncheSeEsiste)");
        System.out.println("Il file viene creato VUOTO");
        System.out.println(VUOTA);

        String nomeFile = FILE_UNO;
        String dirSorgente = DIR_UNO;
        String dirDestinazione = DIR_TRE;
        String pathSorgente = dirSorgente + nomeFile;
        String pathDestinazione = dirDestinazione + nomeFile;

        cancellaCartelle(pathSorgente, pathDestinazione);
        System.out.println(String.format("Nome (completo) del file: %s", pathDestinazione));

        System.out.println(VUOTA);
        System.out.println("A - Inizialmente non esiste (isEsisteFile)");
        ottenutoBooleano = service.isEsisteFile(pathSorgente);
        assertFalse(ottenutoBooleano);

        System.out.println(VUOTA);
        System.out.println("B - Viene creato (creaFile)");
        ottenutoRisultato = service.creaFile(pathSorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println("C - Controlla che NON esista nella directory di destinazione (isEsisteFile)");
        ottenutoBooleano = service.isEsisteFile(pathDestinazione);
        assertFalse(ottenutoBooleano);

        System.out.println(VUOTA);
        System.out.println("D - Il file viene copiato (copyFile)");
        ottenutoRisultato = service.copyFile(AECopy.fileDelete, dirSorgente, dirDestinazione, nomeFile);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        printRisultato(ottenutoRisultato);

        System.out.println(VUOTA);
        System.out.println("E - Controlla che sia stato copiato (isEsisteFile)");
        ottenutoBooleano = service.isEsisteFile(pathDestinazione);
        assertTrue(ottenutoBooleano);

        System.out.println(VUOTA);
        System.out.println("F - Cancellazione finale del file dalla directory sorgente (deleteFile)");
        ottenutoRisultato = service.deleteFile(pathSorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());

        System.out.println(VUOTA);
        System.out.println("G - Cancellazione finale del file dalla directory destinazione (deleteFile)");
        ottenutoRisultato = service.deleteFile(pathDestinazione);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
    }


    @ParameterizedTest
    @MethodSource(value = "DIRECTORY")
    @Order(14)
    @DisplayName("14 - findPathBreve")
        //--path
        //--esiste directory
        //--manca slash iniziale
    void findPathBreve(final String sorgente) {
        System.out.println("14 - findPathBreve");
        System.out.println(VUOTA);

        if (textService.isValid(sorgente)) {
            ottenuto = service.findPathBreve(sorgente);
            assertTrue(textService.isValid(ottenuto));
        }

        System.out.println(VUOTA);
        System.out.print(String.format("%s%s%s", sorgente, FORWARD, ottenuto));
    }

    @ParameterizedTest
    @MethodSource(value = "DIRECTORY")
    @Order(15)
    @DisplayName("15 - lastDirectory")
        //--path
        //--esiste directory
        //--manca slash iniziale
    void estraeDirectoryFinaleSenzaSlash(final String sorgente) {
        System.out.println("15 - lastDirectory");
        System.out.println(VUOTA);

        if (textService.isValid(sorgente)) {
            ottenuto = service.lastDirectory(sorgente);
            assertTrue(textService.isValid(ottenuto));
        }

        System.out.println(VUOTA);
        System.out.print(String.format("%s%s%s", sorgente, FORWARD, ottenuto));
    }

    @ParameterizedTest
    @MethodSource(value = "COPY_DIRECTORY_TYPES")
    @Order(16)
    @DisplayName("16 - Copia dir/files")
        //--type copy
        //--pathDir sorgente
        //--pathDir destinazione
        //--directory copiata
    void copyDirectory(final AECopy typeCopy, final String srcPathDir, final String destPathDir, final boolean copiaturaPrevista) {
        System.out.println("16 - Copia dir/files");
        System.out.println(VUOTA);
        boolean esistePrima;
        boolean esisteDopo;

        //--prepare la cartella sorgente regolata alle condizioni iniziali
        creaCartellaSorgente(srcPathDir);
        //--prepare una cartella destinazione fissa
        new File(DIRECTORY_TEST + SLASH + DIR_TRE).mkdirs();
        //--controlla se esiste la cartella di destinazione prevista
        esistePrima = service.isEsisteDirectory(destPathDir);

        ottenutoRisultato = service.copyDirectory(typeCopy, srcPathDir, destPathDir);
        assertNotNull(ottenutoRisultato);
        ottenutoBooleano = ottenutoRisultato.isValido();
        printRisultato(ottenutoRisultato);
        assertEquals(copiaturaPrevista, ottenutoBooleano);

        esisteDopo = service.isEsisteDirectory(destPathDir);
        if (esistePrima) {
            assertFalse(ottenutoRisultato.isValido());
            assertEquals(copiaturaPrevista, ottenutoBooleano);
        }
        else {
            if (esisteDopo) {
                assertTrue(ottenutoRisultato.isValido());
                assertEquals(copiaturaPrevista, ottenutoBooleano);
            }
            else {
                assertFalse(ottenutoRisultato.isValido());
                assertEquals(copiaturaPrevista, ottenutoBooleano);
            }
        }

        //--cancella le due cartelle
        cancellaCartelle(srcPathDir, destPathDir, DIRECTORY_TEST + SLASH + DIR_TRE);
    }


    @ParameterizedTest
    @MethodSource(value = "COPY_DIRECTORY_ONLY")
    @Order(17)
    @DisplayName("17 - Copia la directory solo se non esiste")
        //--pathDir sorgente
        //--pathDir destinazione
        //--directory copiata
    void copyDirectoryOnly(final String srcPathDir, final String destPathDir, final boolean copiaturaPrevista) {
        System.out.println("17 - Copia la directory solo se non esiste");
        System.out.println(VUOTA);
        boolean esistePrima;
        boolean esisteDopo;

        //--prepare la cartella sorgente regolata alle condizioni iniziali
        creaCartellaSorgente(srcPathDir);
        //--prepare una cartella destinazione fissa
        new File(DIRECTORY_TEST + SLASH + DIR_TRE).mkdirs();
        try {
            new File(DIRECTORY_TEST + SLASH + DIR_TRE + FILE_NOVE).createNewFile();
        } catch (Exception unErrore) {
            assertFalse(true);
        }

        //--controlla se esiste la cartella di destinazione prevista
        esistePrima = service.isEsisteDirectory(destPathDir);

        ottenutoRisultato = service.copyDirectory(AECopy.dirOnly, srcPathDir, destPathDir);
        assertNotNull(ottenutoRisultato);
        ottenutoBooleano = ottenutoRisultato.isValido();
        printRisultato(ottenutoRisultato);
        assertEquals(copiaturaPrevista, ottenutoBooleano);

        esisteDopo = service.isEsisteDirectory(destPathDir);
        if (esistePrima) {
            assertFalse(ottenutoRisultato.isValido());
            assertEquals(copiaturaPrevista, ottenutoBooleano);
        }
        else {
            if (esisteDopo) {
                assertTrue(ottenutoRisultato.isValido());
                assertEquals(copiaturaPrevista, ottenutoBooleano);
            }
            else {
                assertFalse(ottenutoRisultato.isValido());
                assertEquals(copiaturaPrevista, ottenutoBooleano);
            }
        }

        if (copiaturaPrevista) {
            assertTrue(service.isEsisteFile(destPathDir + SLASH + FILE_UNO));
            assertFalse(service.isEsisteFile(destPathDir + SLASH + FILE_NOVE));
        }
        assertTrue(service.isEsisteFile(DIRECTORY_TEST + DIR_TRE + FILE_NOVE));

        //--cancella le due cartelle
        cancellaCartelle(srcPathDir, destPathDir, DIRECTORY_TEST + DIR_TRE);
    }


    @ParameterizedTest
    @MethodSource(value = "COPY_DIRECTORY_SEMPRE")
    @Order(18)
    @DisplayName("18 - Copia la directory sempre")
        //--pathDir sorgente
        //--pathDir destinazione
    void copyDirectoryDelete(final String srcPathDir, final String destPathDir, final boolean copiaturaPrevista) {
        System.out.println("18 - Copia la directory sempre cancellando quella preesistente");
        System.out.println(VUOTA);

        //--prepare la cartella sorgente regolata alle condizioni iniziali
        creaCartellaSorgente(srcPathDir);
        creaIfNotExistCartellaDest(destPathDir);
        //--prepare una cartella destinazione fissa
        new File(DIRECTORY_TEST + SLASH + DIR_TRE).mkdirs();
        try {
            new File(DIRECTORY_TEST + SLASH + DIR_TRE + FILE_NOVE).createNewFile();
        } catch (Exception unErrore) {
            assertFalse(true);
        }

        ottenutoRisultato = service.copyDirectory(AECopy.dirDelete, srcPathDir, destPathDir);
        assertNotNull(ottenutoRisultato);
        ottenutoBooleano = ottenutoRisultato.isValido();
        printRisultato(ottenutoRisultato);
        assertEquals(copiaturaPrevista, ottenutoBooleano);

        if (copiaturaPrevista) {
            assertTrue(service.isEsisteFile(destPathDir + SLASH + FILE_UNO));
            assertFalse(service.isEsisteFile(destPathDir + SLASH + FILE_NOVE));
            assertTrue(service.isEsisteFile(destPathDir + SLASH + FILE_DUE));
            assertFalse(service.isEsisteFile(destPathDir + SLASH + FILE_SEI));
        }
        if (destPathDir.equals(DIRECTORY_TEST + DIR_TRE + FILE_NOVE) && ottenutoRisultato.isValido()) {
            assertFalse(service.isEsisteFile(DIRECTORY_TEST + DIR_TRE + FILE_NOVE));
        }

        //--cancella le due cartelle
        cancellaCartelle(srcPathDir, destPathDir, DIRECTORY_TEST + DIR_TRE);
    }

    @ParameterizedTest
    @MethodSource(value = "COPY_DIRECTORY_ADD_ONLY")
    @Order(19)
    @DisplayName("19 - Integra la directory aggiungendo files")
        //--pathDir sorgente
        //--pathDir destinazione
        //--directory copiata
    void copyDirectoryAddOnly(final String srcPathDir, final String destPathDir, final boolean copiaturaPrevista) {
        System.out.println("19 - Integra la directory aggiungendo files");
        System.out.println(VUOTA);
        String testoAnteCopiaturaDirectory;
        String testoPostCopiaturaDirectory;

        //--prepare la cartella sorgente regolata alle condizioni iniziali
        creaCartellaSorgente(srcPathDir);
        creaIfNotExistCartellaDest(destPathDir);
        //--prepare una cartella destinazione fissa
        new File(DIRECTORY_TEST + SLASH + DIR_TRE).mkdirs();
        try {
            new File(DIRECTORY_TEST + SLASH + DIR_TRE + FILE_NOVE).createNewFile();
        } catch (Exception unErrore) {
            assertFalse(true);
        }
        testoAnteCopiaturaDirectory = service.leggeFile(destPathDir + SLASH + FILE_UNO);

        ottenutoRisultato = service.copyDirectory(AECopy.dirFilesAddOnly, srcPathDir, destPathDir);
        assertNotNull(ottenutoRisultato);
        ottenutoBooleano = ottenutoRisultato.isValido();
        printRisultato(ottenutoRisultato);
        assertEquals(copiaturaPrevista, ottenutoBooleano);

        if (copiaturaPrevista) {
            assertTrue(service.isEsisteFile(destPathDir + SLASH + FILE_UNO));
            assertTrue(service.isEsisteFile(destPathDir + SLASH + FILE_DUE));
            assertTrue(service.isEsisteFile(destPathDir + SLASH + FILE_SEI));
            if (destPathDir.equals(DIRECTORY_TEST + DIR_TRE)) {
                assertTrue(service.isEsisteFile(destPathDir + SLASH + FILE_NOVE));
            }
            else {
                assertFalse(service.isEsisteFile(destPathDir + SLASH + FILE_NOVE));
            }
            testoPostCopiaturaDirectory = service.leggeFile(destPathDir + SLASH + FILE_UNO);
            assertEquals(testoPostCopiaturaDirectory, testoAnteCopiaturaDirectory);
        }
        if (destPathDir.equals(DIRECTORY_TEST + DIR_TRE + FILE_NOVE) && ottenutoRisultato.isValido()) {
            assertFalse(service.isEsisteFile(DIRECTORY_TEST + DIR_TRE + FILE_NOVE));
        }

        //--cancella le due cartelle
        cancellaCartelle(srcPathDir, destPathDir, DIRECTORY_TEST + DIR_TRE);
    }


    @ParameterizedTest
    @MethodSource(value = "COPY_DIRECTORY_MODIFICA")
    @Order(20)
    @DisplayName("20 - Integra la directory modificando i files")
        //--pathDir sorgente
        //--pathDir destinazione
        //--directory copiata
    void copyDirectoryModifica(final String srcPathDir, final String destPathDir, final boolean copiaturaPrevista) {
        System.out.println("20 - Integra la directory modificando i files");
        System.out.println(VUOTA);
        String testoAnteCopiaturaDirectory;
        String testoPostCopiaturaDirectory;

        //--prepare la cartella sorgente regolata alle condizioni iniziali
        creaCartellaSorgente(srcPathDir);
        creaIfNotExistCartellaDest(destPathDir);
        //--prepare una cartella destinazione fissa
        new File(DIRECTORY_TEST + SLASH + DIR_TRE).mkdirs();
        try {
            new File(DIRECTORY_TEST + SLASH + DIR_TRE + FILE_NOVE).createNewFile();
        } catch (Exception unErrore) {
            assertFalse(true);
        }
        testoAnteCopiaturaDirectory = service.leggeFile(destPathDir + SLASH + FILE_UNO);

        ottenutoRisultato = service.copyDirectory(AECopy.dirFilesModifica, srcPathDir, destPathDir);
        assertNotNull(ottenutoRisultato);
        ottenutoBooleano = ottenutoRisultato.isValido();
        printRisultato(ottenutoRisultato);
        assertEquals(copiaturaPrevista, ottenutoBooleano);

        if (copiaturaPrevista) {
            assertTrue(service.isEsisteFile(destPathDir + SLASH + FILE_UNO));
            assertTrue(service.isEsisteFile(destPathDir + SLASH + FILE_DUE));
            assertTrue(service.isEsisteFile(destPathDir + SLASH + FILE_SEI));
            if (destPathDir.equals(DIRECTORY_TEST + DIR_TRE)) {
                assertTrue(service.isEsisteFile(destPathDir + SLASH + FILE_NOVE));
            }
            else {
                assertFalse(service.isEsisteFile(destPathDir + SLASH + FILE_NOVE));
            }
            testoPostCopiaturaDirectory = service.leggeFile(destPathDir + SLASH + FILE_UNO);
            assertNotEquals(testoPostCopiaturaDirectory, testoAnteCopiaturaDirectory);
            assertEquals(srcTesto, testoPostCopiaturaDirectory);
        }
        if (destPathDir.equals(DIRECTORY_TEST + DIR_TRE + FILE_NOVE) && ottenutoRisultato.isValido()) {
            assertFalse(service.isEsisteFile(DIRECTORY_TEST + DIR_TRE + FILE_NOVE));
        }

        //--cancella le due cartelle
        cancellaCartelle(srcPathDir, destPathDir, DIRECTORY_TEST + DIR_TRE);
    }


    @Test
    @Order(47)
    @DisplayName("47 - Cerco ricorsivamente i path dei files della directory")
    void getFilesPath() {
        System.out.println("47 - Cerco ricorsivamente i path dei files della directory");
        System.out.println(VUOTA);

        sorgente = DIRECTORY_TEST;
        listaStr = service.getFilesPath(sorgente);
        assertTrue(listaStr != null);
        print(listaStr, String.format(" directory '%s' e delle (eventuali) sub-directories", sorgente));

    }

    @Test
    @Order(48)
    @DisplayName("48 - Cerco ricorsivamente i nomi dei files della directory")
    void getFilesNames() {
        System.out.println("48 - Cerco ricorsivamente i nomi interni dei files della directory ");
        System.out.println(VUOTA);

        sorgente = DIRECTORY_TEST;
        listaStr = service.getFilesName(sorgente);
        assertTrue(listaStr != null);
        print(listaStr, String.format(" directory '%s' e delle (eventuali) sub-directories", sorgente));

        sorgente = DIR_DUE;
        listaStr = service.getFilesName(sorgente);
        assertTrue(listaStr != null);
        System.out.println(VUOTA);
        print(listaStr, String.format(" directory '%s' e delle (eventuali) sub-directories", sorgente));
    }


    @Test
    @Order(21)
    @DisplayName("21 - isEsisteFileZero")
    public void isEsisteFileZero() {
        nomeFile = "nonEsiste";
        unFile = new File(nomeFile);
        assertNotNull(unFile);
        System.out.println(" ");
        System.out.println("file.getName() = " + unFile.getName());
        System.out.println("file.getPath() = " + unFile.getPath());
        System.out.println("file.getAbsolutePath() = " + unFile.getAbsolutePath());
        try {
            System.out.println("file.getCanonicalPath() = " + unFile.getCanonicalPath());
        } catch (Exception unErrore) {
            System.out.println("Errore");
        }

        nomeFile = "Maiuscola";
        unFile = new File(nomeFile);
        assertNotNull(unFile);
        System.out.println(" ");
        System.out.println("file.getName() = " + unFile.getName());
        System.out.println("file.getPath() = " + unFile.getPath());
        System.out.println("file.getAbsolutePath() = " + unFile.getAbsolutePath());
        try {
            System.out.println("file.getCanonicalPath() = " + unFile.getCanonicalPath());
        } catch (Exception unErrore) {
            System.out.println("Errore");
        }

        nomeFile = "/User/pippoz";
        unFile = new File(nomeFile);
        assertNotNull(unFile);
        System.out.println(" ");
        System.out.println("file.getName() = " + unFile.getName());
        System.out.println("file.getPath() = " + unFile.getPath());
        System.out.println("file.getAbsolutePath() = " + unFile.getAbsolutePath());

        try {
            System.out.println("file.getCanonicalPath() = " + unFile.getCanonicalPath());
        } catch (Exception unErrore) {
            System.out.println("Errore");
        }

        nomeFile = "/User/pippo/Pluto.rtf";
        unFile = new File(nomeFile);
        assertNotNull(unFile);
        System.out.println(" ");
        System.out.println("file.getName() = " + unFile.getName());
        System.out.println("file.getPath() = " + unFile.getPath());
        System.out.println("file.getAbsolutePath() = " + unFile.getAbsolutePath());

        try {
            System.out.println("file.getCanonicalPath() = " + unFile.getCanonicalPath());
        } catch (Exception unErrore) {
            System.out.println("Errore");
        }
    }

    void creaCartellaSorgente(String srcPath) {
        if (!srcPath.startsWith(SLASH)) {
            return;
        }
        File fileUno;

        service.deleteDirectory(srcPath);
        String pathDirUno = srcPath + SLASH + DIR_UNO;
        String pathDirDue = pathDirUno + DIR_DUE;
        String pathDirTre = VUOTA;
        String pathDirQuattro = VUOTA;

        String pathFileUno = srcPath + SLASH + FILE_UNO;
        String pathFileDue = srcPath + SLASH + FILE_DUE;
        String pathFileTre = pathDirUno + SLASH + FILE_TRE;
        String pathFileQuattro = pathDirDue + SLASH + FILE_QUATTRO;
        String pathFileCinque = pathDirDue + SLASH + FILE_CINQUE;
        String pathFileSei = VUOTA;
        String pathFileSette = VUOTA;
        String pathFileOtto = VUOTA;
        String pathFileNove = VUOTA;

        try {
            new File(pathDirUno).mkdirs();
            new File(pathDirDue).mkdirs();

            new File(pathFileUno).createNewFile();
            new File(pathFileDue).createNewFile();
            new File(pathFileTre).createNewFile();
            new File(pathFileQuattro).createNewFile();
            new File(pathFileCinque).createNewFile();
        } catch (Exception unErrore) {
        }
        service.sovraScriveFile(pathFileUno, srcTesto);
    }

    void creaIfNotExistCartellaDest(String destPath) {
        if (service.isEsisteDirectory(destPath)) {
            return;
        }
        if (!destPath.startsWith(SLASH)) {
            return;
        }

        String pathDirUno = destPath + SLASH + DIR_UNO;
        String pathDirDue = pathDirUno + DIR_DUE;
        String pathDirTre = destPath + SLASH + DIR_TRE;
        String pathDirQuattro = pathDirUno + DIR_QUATTRO;

        String pathFileUno = destPath + SLASH + FILE_UNO;
        String pathFileTre = pathDirUno + SLASH + FILE_TRE;
        String pathFileQuattro = pathDirDue + SLASH + FILE_QUATTRO;
        String pathFileCinque = pathDirDue + SLASH + FILE_CINQUE;
        String pathFileSei = destPath + SLASH + FILE_SEI;
        String pathFileSette = pathDirTre + SLASH + FILE_SETTE;
        String pathFileOtto = pathDirDue + SLASH + FILE_OTTO;
        String pathFileNove = pathDirQuattro + SLASH + FILE_NOVE;

        try {
            new File(pathDirUno).mkdirs();
            new File(pathDirDue).mkdirs();
            new File(pathDirTre).mkdirs();
            new File(pathDirQuattro).mkdirs();

            new File(pathFileUno).createNewFile();
            new File(pathFileTre).createNewFile();
            new File(pathFileQuattro).createNewFile();
            new File(pathFileCinque).createNewFile();
            new File(pathFileSei).createNewFile();
            new File(pathFileSette).createNewFile();
            new File(pathFileOtto).createNewFile();
            new File(pathFileNove).createNewFile();
        } catch (Exception unErrore) {
        }
        service.sovraScriveFile(pathFileUno, destTesto);
    }

    void creaCartelle(String srcPath, String destPath) {
        cancellaCartelle(srcPath, destPath);
        //        fixCartellaSorgente(srcPath);

        try {
            src.mkdirs();
            dest.mkdirs();
            srcSub1.mkdirs();
            destSub1.mkdirs();

            srcA_uguale.createNewFile();
            srcB.createNewFile();
            srcC_diverso.createNewFile();
            src_sub_uguale.createNewFile();
            src_sub_diverso.createNewFile();

            destA_uguale.createNewFile();
            destC_diverso.createNewFile();
            destD.createNewFile();
            dest_sub_uguale.createNewFile();
            dest_sub_diverso.createNewFile();
        } catch (Exception unErrore) {
        }

        service.sovraScriveFile(srcA_uguale.getAbsolutePath(), srcTesto);
        service.sovraScriveFile(srcB.getAbsolutePath(), srcTesto);
        service.sovraScriveFile(srcC_diverso.getAbsolutePath(), srcTesto);
        service.sovraScriveFile(src_sub_uguale.getAbsolutePath(), srcTesto);
        service.sovraScriveFile(src_sub_diverso.getAbsolutePath(), srcTesto);

        service.sovraScriveFile(destA_uguale.getAbsolutePath(), destTestoFisso);
        service.sovraScriveFile(destC_diverso.getAbsolutePath(), destTesto);
        service.sovraScriveFile(destD.getAbsolutePath(), destTestoFisso);
        service.sovraScriveFile(dest_sub_uguale.getAbsolutePath(), srcTesto);
        service.sovraScriveFile(dest_sub_diverso.getAbsolutePath(), destTesto);
    }

    void checkCartelleOnly(String srcPath, String destPath) {
        //        fixCartelle(srcPath, destPath);

        //--file A inalterato perché esisteva già
        assertTrue(service.isEsisteFile(destPath + file1));
        ottenuto = service.leggeFile(destPath + file1);
        assertEquals(destTestoFisso, ottenuto);

        //--file B copiato pari pari perché non esisteva
        assertTrue(service.isEsisteFile(srcPath + SLASH + file2));
        assertTrue(service.isEsisteFile(destPath + SLASH + file2));
        ottenuto = service.leggeFile(destPath + SLASH + file2);
        assertEquals(srcTesto, ottenuto);

        //--file C inalterato perché esisteva già
        assertTrue(service.isEsisteFile(destPath + SLASH + file3));
        ottenuto = service.leggeFile(destC_diverso.getAbsolutePath());
        assertEquals(srcTesto, ottenuto);

        //--file D inalterato perché non esiste nella cartella sorgente
        assertTrue(service.isEsisteFile(destPath + SLASH + file4));
        ottenuto = service.leggeFile(destD.getAbsolutePath());
        assertEquals(destTestoFisso, ottenuto);

        //--subfile Lambda
        //        assertTrue(service.isEsisteFile(destSub1 + SLASH + file5));
        //        ottenuto = service.leggeFile(destSub1 + SLASH + file5);
        //        assertEquals(destTestoFisso, ottenuto);
        //
        //        //--subfile Lambda
        //        assertTrue(service.isEsisteFile(destSub1 + SLASH + file6));
        //        ottenuto = service.leggeFile(destSub1 + SLASH + file6);
        //        assertEquals(destTestoFisso, ottenuto);
    }

    void checkCartelleModifica(String srcPath, String destPath) {
        //        fixCartelle(srcPath, destPath);
    }

    private void cancellaCartelle(String srcPathDir, String destPathDir) {
        service.deleteDirectory(srcPathDir);
        service.deleteDirectory(destPathDir);
    }

    private void cancellaCartelle(String srcPathDir, String destPathDir, String destPreesistente) {
        service.deleteDirectory(srcPathDir);
        service.deleteDirectory(destPathDir);
        service.deleteDirectory(destPreesistente);
    }


    private void cancellaAllTest() {
        service.deleteDirectory(DIRECTORY_TEST);
    }

    /**
     * Qui passa al termine di ogni singolo test <br>
     */
    @AfterEach
    void tearDown() {
    }


    /**
     * Qui passa una volta sola, chiamato alla fine di tutti i tests <br>
     */
    @AfterAll
    void tearDownAll() {
        cancellaAllTest();
    }

}
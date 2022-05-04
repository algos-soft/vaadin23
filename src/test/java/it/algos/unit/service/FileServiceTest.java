package it.algos.unit.service;

import it.algos.*;
import it.algos.test.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import java.io.*;
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
@Tag("spring")
@DisplayName("File service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FileServiceTest extends SpringTest {


    private static String PATH_DIRECTORY_TEST = "/Users/gac/Desktop/test/";

    private static String PATH_DIRECTORY_UNO = PATH_DIRECTORY_TEST + "Pippo/";

    private static String PATH_DIRECTORY_DUE = PATH_DIRECTORY_TEST + "Possibile/";

    private static String PATH_DIRECTORY_TRE = PATH_DIRECTORY_TEST + "Mantova/";

    private static String PATH_DIRECTORY_NON_ESISTENTE = PATH_DIRECTORY_TEST + "Genova/";

    private static String PATH_DIRECTORY_DA_COPIARE = PATH_DIRECTORY_TEST + "NuovaDirectory/";

    private static String PATH_DIRECTORY_MANCANTE = PATH_DIRECTORY_TEST + "CartellaCopiata/";

    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    private FileService service;

    private File unFile;

    //--path
    //--esiste
    protected static Stream<Arguments> DIRECTORY() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(VUOTA, false),
                Arguments.of("/Users/gac/Desktop/test/", false),
                Arguments.of("/Users/gac/Desktop/test/Mantova", false),
                Arguments.of("/Users/gac/Desktop/test/Mantova.txt", false),
                Arguments.of("Users/gac/Documents/IdeaProjects/operativi/vaadin23/src/", false),
                Arguments.of("/Users/gac/Documents/IdeaProjects/operativi/vaadin23/src/", true),
                Arguments.of("/Users/gac/Desktop/test/Pippo/", false)
        );
    }


    //--path
    //--esiste
    protected static Stream<Arguments> FILE() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(VUOTA, false),
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
        //--esiste
    void checkDirectory(final String sorgente, final boolean previstoBooleano) {
        System.out.println("1 - Check di una directory");
        System.out.println(VUOTA);

        ottenutoRisultato = service.checkDirectory(sorgente);
        assertNotNull(ottenutoRisultato);
        assertEquals(previstoBooleano, ottenutoRisultato.isValido());
        printRisultato(ottenutoRisultato);
    }

    @ParameterizedTest
    @MethodSource(value = "DIRECTORY")
    @Order(2)
    @DisplayName("2 - Esistenza di una directory")
        //--path
        //--esiste
    void isEsisteDirectory(final String sorgente, final boolean previstoBooleano) {
        System.out.println("2 - Esistenza di una directory");
        System.out.println(VUOTA);

        ottenutoBooleano = service.isEsisteDirectory(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
    }

    @ParameterizedTest
    @MethodSource(value = "FILE")
    @Order(3)
    @DisplayName("3 - Check di un file")
        //--path
        //--esiste
    void checkFile(final String sorgente, final boolean previstoBooleano) {
        System.out.println("3 - Check di un file");
        System.out.println(VUOTA);

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
        //--esiste
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

        sorgente = "/Users/gac/Desktop/test4522/";
        System.out.println(String.format("Nome (completo) della directory: %s", sorgente));
        System.out.println(VUOTA);

        System.out.println("A - Controlla l'esistenza");
        ottenutoBooleano = service.isEsisteDirectory(sorgente);
        assertFalse(ottenutoBooleano);
        System.out.println("Prima non esiste");
        System.out.println(VUOTA);

        System.out.println("B - Crea la directory");
        ottenutoRisultato = service.creaDirectory(sorgente);
        assertTrue(textService.isEmpty(ottenuto));
        System.out.println("La directory è stata creata");
        System.out.println(VUOTA);

        System.out.println("C - Ricontrolla l'esistenza");
        ottenutoBooleano = service.isEsisteDirectory(sorgente);
        assertTrue(ottenutoBooleano);
        System.out.println("La directory esiste");
        System.out.println(VUOTA);

        System.out.println("D - Cancella la directory");
        ottenutoRisultato = service.deleteDirectory(sorgente);
        assertTrue(textService.isEmpty(ottenuto));
        System.out.println("La directory è stata cancellata");
        System.out.println(VUOTA);

        System.out.println("E - Controllo finale");
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
        System.out.println(VUOTA);

        sorgente = "/Users/gac/Desktop/Mantova.txt";
        System.out.println(String.format("Nome (completo) del file: %s", sorgente));
        System.out.println(VUOTA);

        System.out.println("A - Controlla l'esistenza");
        ottenutoBooleano = service.isEsisteFile(sorgente);
        assertFalse(ottenutoBooleano);
        System.out.println("Prima non esiste");
        System.out.println(VUOTA);

        System.out.println("B - Creo il file");
        ottenutoRisultato = service.creaFile(sorgente);
        assertTrue(textService.isEmpty(ottenuto));
        System.out.println("Il file è stato creato");
        System.out.println(VUOTA);

        System.out.println("C - Ricontrolla l'esistenza");
        ottenutoBooleano = service.isEsisteFile(sorgente);
        assertTrue(ottenutoBooleano);
        System.out.println("Il file esiste");
        System.out.println(VUOTA);

        System.out.println("D - Cancello il file");
        ottenutoRisultato = service.deleteFile(sorgente);
        assertTrue(textService.isEmpty(ottenuto));
        System.out.println("Il file è stato cancellato");
        System.out.println(VUOTA);

        System.out.println("E - Controllo finale");
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
        System.out.println(VUOTA);

        sorgente2 = "/Users/gac/Desktop/Torino/";
        sorgente3 = sorgente2 + "Padova/";
        sorgente = sorgente3 + "Mantova.txt";
        System.out.println(String.format("Nome (completo) del file: %s", sorgente));
        System.out.println(VUOTA);

        System.out.println("A - Controlla l'esistenza");
        ottenutoBooleano = service.isEsisteFile(sorgente);
        assertFalse(ottenutoBooleano);
        System.out.println("Prima non esiste");
        System.out.println(VUOTA);

        System.out.println("B - Creo il file");
        ottenutoRisultato = service.creaFile(sorgente);
        assertTrue(textService.isEmpty(ottenuto));
        System.out.println("Il file è stato creato");
        System.out.println(VUOTA);

        System.out.println("C - Ricontrolla l'esistenza");
        ottenutoBooleano = service.isEsisteFile(sorgente);
        assertTrue(ottenutoBooleano);
        System.out.println("Il file esiste");
        System.out.println(VUOTA);

        System.out.println("D - Cancello il file");
        ottenutoRisultato = service.deleteFile(sorgente);
        assertTrue(textService.isEmpty(ottenuto));
        System.out.println("Il file è stato cancellato");
        System.out.println(VUOTA);

        System.out.println("E - Controllo finale del file");
        ottenutoBooleano = service.isEsisteFile(sorgente);
        assertFalse(ottenutoBooleano);
        System.out.println("Il file non esiste");
        System.out.println(VUOTA);

        System.out.println("F - Cancello anche la(e) cartella(e) intermedia(e)");
        ottenutoRisultato = service.deleteDirectory(sorgente2);
        assertTrue(textService.isEmpty(ottenuto));
        System.out.println("Cancellata la directory provvisoria");
        System.out.println(VUOTA);

        System.out.println("G - Controllo finale della directory");
        ottenutoBooleano = service.isEsisteDirectory(sorgente2);
        assertFalse(ottenutoBooleano);
        System.out.println("La directory provvisoria non esiste");
        System.out.println(VUOTA);
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
    }

}
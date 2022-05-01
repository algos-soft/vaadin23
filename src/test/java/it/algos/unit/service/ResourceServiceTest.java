package it.algos.unit.service;

import it.algos.test.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.stream.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 13-mar-2022
 * Time: 08:12
 * <p>
 * Unit test di una classe di servizio (di norma) <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("quickly")
@DisplayName("Resource service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResourceServiceTest extends ATest {


    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    private ResourceService service;

    //--path parziale
    //--esiste
    protected static Stream<Arguments> FRONT_END() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(VUOTA, false),
                Arguments.of("styles.shared-styles.css", false),
                Arguments.of("styles/shared-styles.css", true)
        );
    }


    //--path parziale
    //--esiste
    protected static Stream<Arguments> META_INF() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(VUOTA, false),
                Arguments.of("rainbow.png", false),
                Arguments.of("img.rainbow.png", false),
                Arguments.of("img/rainbow.png", true),
                Arguments.of("src/main/resources/META-INF/resources/img.rainbow.png", false),
                Arguments.of("src/main/resources/META-INF/resources/img/rainbow.png", true),
                Arguments.of("bandiere.ca.png", false),
                Arguments.of("bandiere/ca.png", true),
                Arguments.of("src/main/resources/META-INF/resources/bandiere/ca", false),
                Arguments.of("src/main/resources/META-INF/resources/bandiere.ca.png", false),
                Arguments.of("src/main/resources/META-INF/resources/bandiere/ca.png", true)
        );
    }

    //--path parziale
    //--esiste
    protected static Stream<Arguments> CONFIG() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(VUOTA, false),
                Arguments.of("config.password.txt", false),
                Arguments.of("/config.password.txt", false),
                Arguments.of("/config/password.txt", false),
                Arguments.of("/config.password.txt", false),
                Arguments.of("/config.password.txt", false),
                Arguments.of("config/password.txt", false),
                Arguments.of("password.txt", false),
                Arguments.of("at.png", true),
                Arguments.of("africa", true),
                Arguments.of("regioni", true),
                Arguments.of("continenti", true),
                Arguments.of("mesi", true),
                Arguments.of("secoli", true)
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
        service = resourceService;
    }


    /**
     * Qui passa prima di ogni test <br>
     * Invocare PRIMA il metodo setUpEach() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }


    @ParameterizedTest
    @MethodSource(value = "FRONT_END")
    @Order(1)
    @DisplayName("1 - Legge nella directory 'frontend'")
        //--path parziale
        //--esiste
    void leggeFrontend(String path, boolean esiste) {
        System.out.println("1 - Legge nella directory 'frontend'");
        leggeFrontendBase(path, esiste);
    }


    //--path parziale
    //--esiste
    void leggeFrontendBase(String sorgente, boolean esiste) {
        System.out.println(VUOTA);
        ottenuto = service.leggeFrontend(sorgente);
        if (esiste) {
            assertTrue(textService.isValid(ottenuto));
            System.out.println(String.format("Il file %s%s%s", sorgente, FORWARD, "esiste nella cartella frontend"));
        }
        else {
            assertTrue(textService.isEmpty(ottenuto));
            System.out.println(String.format("%s%s%s", sorgente, FORWARD, "non esiste nella cartella frontend"));
        }
    }

    @ParameterizedTest
    @Order(2)
    @MethodSource(value = "META_INF")
    @DisplayName("2 - Legge nella directory META-INF")
        //--path parziale
        //--esiste
    void leggeMetaInf(String path, boolean esiste) {
        System.out.println("2 - Legge nella directory META-INF");
        leggeMetaInfBase(path, esiste);
    }

    //--path parziale
    //--esiste
    void leggeMetaInfBase(String sorgente, boolean esiste) {
        System.out.println(VUOTA);
        ottenuto = service.leggeMetaInf(sorgente);
        if (esiste) {
            assertTrue(textService.isValid(ottenuto));
            System.out.println(String.format("%s%s%s", sorgente, FORWARD, "esiste nella cartella META-INF"));
        }
        else {
            assertTrue(textService.isEmpty(ottenuto));
            System.out.println(String.format("%s%s%s", sorgente, FORWARD, "non esiste nella cartella META-INF"));
        }
    }


    @ParameterizedTest
    @Order(3)
    @MethodSource(value = "META_INF")
    @DisplayName("3 - Legge i bytes[]")
        //--path parziale
        //--esiste
    void getBytes(String path, boolean esiste) {
        System.out.println("3 - Legge i bytes[]");
        getBytesBase(path, esiste);
    }

    //--path parziale
    //--esiste
    void getBytesBase(String sorgente, boolean esiste) {
        System.out.println(VUOTA);
        bytes = service.getBytes(sorgente);
        if (esiste) {
            assertNotNull(bytes);
            System.out.println(String.format("Il file di risorse %s nella cartella META_INF esiste e non è vuoto", sorgente));
        }
        else {
            assertNull(bytes);
            System.out.println(String.format("Nella cartella META_INF non esiste il file di risorse %s", sorgente));
        }
    }

    @ParameterizedTest
    @Order(4)
    @MethodSource(value = "META_INF")
    @DisplayName("4 - Legge le risorse")
        //--path parziale
        //--esiste
    void getSrc(String path, boolean esiste) {
        System.out.println("4 - Legge le risorse");
        getSrcBase(path, esiste);
    }

    //--path parziale
    //--esiste
    void getSrcBase(String sorgente, boolean esiste) {
        System.out.println(VUOTA);

        ottenuto = service.getSrc(sorgente);
        if (esiste) {
            assertTrue(textService.isValid(ottenuto));
            System.out.println(String.format("Il file di risorse %s nella cartella META_INF esiste e non è vuoto", sorgente));
        }
        else {
            assertTrue(textService.isEmpty(ottenuto));
            System.out.println(String.format("Nella cartella META_INF non esiste il file di risorse %s", sorgente));
        }
    }

    @ParameterizedTest
    @Order(5)
    @MethodSource(value = "CONFIG")
    @DisplayName("5 - Legge un file nella directory 'config'")
        //--path parziale
        //--esiste
    void leggeFileConfig(String path, boolean esiste) {
        System.out.println("5 - Legge un file nella directory 'config'");
        leggeFileConfigBase(path, esiste);
    }

    //--path parziale
    //--esiste
    void leggeFileConfigBase(String sorgente, boolean esiste) {
        System.out.println(VUOTA);

        ottenuto = service.leggeConfig(sorgente);
        if (esiste) {
            assertTrue(textService.isValid(ottenuto));
            System.out.println(String.format("%s%s%s", sorgente, FORWARD, "esiste nella cartella config"));
        }
        else {
            assertTrue(textService.isEmpty(ottenuto));
            System.out.println(String.format("%s%s%s", sorgente, FORWARD, "non esiste nella cartella config"));
        }
    }

    @ParameterizedTest
    @Order(6)
    @MethodSource(value = "CONFIG")
    @DisplayName("6 - Legge una lista dalla directory 'config'")
        //--path parziale
        //--esiste
    void leggeListaConfig(String path, boolean esiste) {
        System.out.println(String.format("6 - Legge dalla directory 'config' una lista per il file CSV '%s'", path));
        leggeListaConfigBase(path, esiste);
    }

    //--path parziale
    //--esiste
    void leggeListaConfigBase(String sorgente, boolean esiste) {
        System.out.println(VUOTA);

        listaStr = service.leggeListaConfig(sorgente);
        if (esiste) {
            assertTrue(listaStr != null && listaStr.size() > 0);
            System.out.println(String.format("%s%s%s", sorgente, FORWARD, "esiste nella cartella config"));
            listaStr = service.leggeListaConfig(sorgente, true);
            printVuota(listaStr, "compresi i titoli");

            System.out.println(VUOTA);
            listaStr = service.leggeListaConfig(sorgente, false);
            assertNotNull(listaStr);
            printVuota(listaStr, "esclusi i titoli");
        }
        else {
            assertTrue(listaStr == null);
            System.out.println(String.format("%s%s%s", sorgente, FORWARD, "non esiste nella cartella config"));
        }
    }

    //    @Test
    void leggeListaConfigxx() {
        sorgente = "continenti";
        System.out.println(String.format("6 - Legge dalla directory 'config' una lista per il file CSV '%s'", sorgente));

        ottenuto = service.leggeConfig(sorgente);
        assertTrue(textService.isValid(ottenuto));

        listaStr = service.leggeListaConfig(sorgente, true);
        assertNotNull(listaStr);
        printVuota(listaStr, "compresi i titoli");

        listaStr = service.leggeListaConfig(sorgente, false);
        assertNotNull(listaStr);
        System.out.println(VUOTA);
        printVuota(listaStr, "esclusi i titoli");

        listaStr = service.leggeListaConfig(sorgente);
        assertNotNull(listaStr);
        System.out.println(VUOTA);
        printVuota(listaStr, "coi titoli di default");

        sorgente = "mesi";
        listaStr = service.leggeListaConfig(sorgente);
        assertNotNull(listaStr);
        System.out.println(VUOTA);
        printVuota(listaStr, "coi titoli di default");
    }

    @Test
    @Order(7)
    @DisplayName("7 - Legge una mappa dalla directory 'config'")
    void leggeMappaConfig() {
        sorgente = "continenti";
        System.out.println(String.format("7 - Legge dalla directory 'config' una mappa per il file CSV '%s'", sorgente));
        ottenuto = service.leggeConfig(sorgente);
        assertTrue(textService.isValid(ottenuto));

        mappa = service.leggeMappaConfig(sorgente, true);
        assertNotNull(mappa);
        printMappa(mappa, "compresi i titoli");

        mappa = service.leggeMappaConfig(sorgente, false);
        assertNotNull(mappa);
        System.out.println(VUOTA);
        printMappa(mappa, "esclusi i titoli");

        mappa = service.leggeMappaConfig(sorgente);
        assertNotNull(mappa);
        System.out.println(VUOTA);
        printMappa(mappa, "coi titoli di default");

        sorgente = "mesi";
        mappa = service.leggeMappaConfig(sorgente, true);
        assertNotNull(mappa);
        printMappa(mappa, "compresi i titoli");

        mappa = service.leggeMappaConfig(sorgente, false);
        assertNotNull(mappa);
        System.out.println(VUOTA);
        printMappa(mappa, "esclusi i titoli");

        mappa = service.leggeMappaConfig(sorgente);
        assertNotNull(mappa);
        System.out.println(VUOTA);
        printMappa(mappa, "coi titoli di default");
    }

    @Test
    @Order(8)
    @DisplayName("8 - Legge un file dal server 'algos'")
    void leggeServer() {
        sorgente = "continenti";
        System.out.println(String.format("8 - Legge dal server 'algos' un file CSV '%s'", sorgente));

        ottenuto = service.leggeServer(sorgente);
        assertTrue(textService.isValid(ottenuto));
        System.out.println(ottenuto.substring(0, 200));

        sorgente = "mesi";
        ottenuto = service.leggeServer(sorgente);
        assertTrue(textService.isValid(ottenuto));
        System.out.println(ottenuto.substring(0, 40));
    }

    @Test
    @Order(9)
    @DisplayName("9 - Legge una lista dal server 'algos'")
    void leggeListaServer() {
        sorgente = "continenti";
        System.out.println(String.format("9 - Legge dal server 'algos' una lista '%s'", sorgente));

        listaStr = service.leggeListaServer(sorgente, true);
        assertNotNull(listaStr);
        printVuota(listaStr, "letta compresi i titoli");

        listaStr = service.leggeListaServer(sorgente, false);
        assertNotNull(listaStr);
        printVuota(listaStr, "letta esclusi i titoli");

        listaStr = service.leggeListaServer(sorgente);
        assertNotNull(listaStr);
        printVuota(listaStr, "letta coi titoli di default");

        sorgente = "mesi";
        listaStr = service.leggeListaServer(sorgente);
        assertNotNull(listaStr);
        printVuota(listaStr, "letta coi titoli di default");
    }

    @Test
    @Order(10)
    @DisplayName("10 - Legge una mappa dal server 'algos'")
    void leggeMappaServer() {
        sorgente = "continenti";
        System.out.println(String.format("10 - Legge dalla server 'algos' una mappa per il file CSV '%s'", sorgente));

        mappa = service.leggeMappaServer(sorgente, true);
        assertNotNull(mappa);
        printMappa(mappa, "compresi i titoli");

        mappa = service.leggeMappaServer(sorgente, false);
        assertNotNull(mappa);
        System.out.println(VUOTA);
        printMappa(mappa, "esclusi i titoli");

        mappa = service.leggeMappaServer(sorgente);
        assertNotNull(mappa);
        System.out.println(VUOTA);
        printMappa(mappa, "coi titoli di default");

        sorgente = "mesi";
        mappa = service.leggeMappaServer(sorgente);
        assertNotNull(mappa);
        System.out.println(VUOTA);
        printMappa(mappa, "coi titoli di default");
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
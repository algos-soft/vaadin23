package it.algos.unit;

import it.algos.*;
import it.algos.test.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.service.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 07-mar-2022
 * Time: 19:46
 * <p>
 * Unit test di una classe di servizio <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {SimpleApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("testAllIntegration")
@DisplayName("Text service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TextServiceTest extends ATest {


    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    private TextService service;


    //--testoIn
    //--primaMaiuscola
    //--primaMinuscola
    protected static Stream<Arguments> NOMI() {
        return Stream.of(
                Arguments.of(null, VUOTA, VUOTA),
                Arguments.of(VUOTA, VUOTA, VUOTA),
                Arguments.of("MARIO", "MARIO", "mARIO"),
                Arguments.of("mario", "Mario", "mario"),
                Arguments.of("Mario", "Mario", "mario"),
                Arguments.of("maRio", "MaRio", "maRio"),
                Arguments.of("MaRio", "MaRio", "maRio"),
                Arguments.of(" mario", "Mario", "mario"),
                Arguments.of("mario ", "Mario", "mario"),
                Arguments.of(" mario ", "Mario", "mario"),
                Arguments.of(" Mario", "Mario", "mario"),
                Arguments.of("Mario ", "Mario", "mario"),
                Arguments.of(" Mario ", "Mario", "mario")
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
        service = textService;
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


    @Test
    @Order(1)
    @DisplayName("1 - Controlla testo vuoto")
    void testIsEmpty() {
        ottenutoBooleano = service.isEmpty(null);
        assertTrue(ottenutoBooleano);

        ottenutoBooleano = service.isEmpty(VUOTA);
        assertTrue(ottenutoBooleano);

        ottenutoBooleano = service.isEmpty(PIENA);
        assertFalse(ottenutoBooleano);

        System.out.println("1 - Controlla testo vuoto");
        System.out.println("Fatto");
    }


    @Test
    @Order(2)
    @DisplayName("2 - Controlla validità")
    void testIsValid() {
        ottenutoBooleano = service.isValid(null);
        assertFalse(ottenutoBooleano);

        ottenutoBooleano = service.isValid(VUOTA);
        assertFalse(ottenutoBooleano);

        ottenutoBooleano = service.isValid(PIENA);
        assertTrue(ottenutoBooleano);

        System.out.println("2 - Controlla validità di una stringa");
        System.out.println("Fatto");

        ottenutoBooleano = service.isValid((List) null);
        assertFalse(ottenutoBooleano);

        ottenutoBooleano = service.isValid((new ArrayList()));
        assertFalse(ottenutoBooleano);

        System.out.println("2 - Controlla validità di un oggetto generico");
        System.out.println("Fatto");
    }

    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(3)
    @DisplayName("3 - Prima maiuscola")
        //--testoIn
        //--primaMaiuscola
        //--primaMinuscola
    void primaMaiuscola(final String sorgente, final String previsto, final String nonUsata) {
        System.out.println("3 - Prima maiuscola");
        System.out.println(VUOTA);
        ottenuto = service.primaMaiuscola(sorgente);
        assertEquals(previsto, ottenuto);
        System.out.println(String.format("%s%s%s", sorgente, FORWARD, ottenuto));
        System.out.println(VUOTA);
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(4)
    @DisplayName("4 - Prima minuscola")
    void primaMinuscola(final String sorgente, final String nonUsata, final String previsto) {
        System.out.println("4 - Prima minuscola");
        System.out.println(VUOTA);
        ottenuto = service.primaMinuscola(sorgente);
        assertEquals(previsto, ottenuto);
        System.out.println(String.format("%s%s%s", sorgente, FORWARD, ottenuto));
    }


    @Test
    @Order(5)
    @DisplayName("5 - Restituisce un array di stringhe")
    void getArray() {
        System.out.println("5 - Restituisce un array da una stringa di valori multipli separati da virgole");
        ottenutoArray = service.getArray(null);
        assertNull(ottenutoArray);

        sorgente = VUOTA;
        ottenutoArray = service.getArray(sorgente);
        assertNull(ottenutoArray);

        sorgente = "codedescrizioneordine";
        String[] stringArray2 = {"codedescrizioneordine"};
        previstoArray = new ArrayList(Arrays.asList(stringArray2));
        ottenutoArray = service.getArray(sorgente);
        assertNotNull(ottenutoArray);
        assertEquals(ottenutoArray, previstoArray);

        sorgente = "code,descrizione,ordine";
        String[] stringArray3 = {"code", "descrizione", "ordine"};
        previstoArray = new ArrayList(Arrays.asList(stringArray3));
        ottenutoArray = service.getArray(sorgente);
        assertNotNull(ottenutoArray);
        assertEquals(ottenutoArray, previstoArray);

        sorgente = "code, descrizione , ordine ";
        String[] stringArray4 = {"code", "descrizione", "ordine"};
        previstoArray = new ArrayList(Arrays.asList(stringArray4));
        ottenutoArray = service.getArray(sorgente);
        assertNotNull(ottenutoArray);
        assertEquals(ottenutoArray, previstoArray);
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
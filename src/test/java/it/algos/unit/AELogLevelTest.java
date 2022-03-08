package it.algos.unit;

import it.algos.*;
import it.algos.test.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
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
 * Time: 16:50
 * Unit test di una classe di servizio <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {SimpleApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("testAllValido")
@DisplayName("Enumeration AELogLevel")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AELogLevelTest extends ATest {

    private AELogLevel type;

    private List<AELogLevel> listaType;

    private List<String> listaTag;

    private AELogLevel[] matrice;


    //--log level
    protected static Stream<Arguments> LIVELLI() {
        return Stream.of(
                Arguments.of(AELogLevel.debug),
                Arguments.of(AELogLevel.info),
                Arguments.of(AELogLevel.warn),
                Arguments.of(AELogLevel.error)
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }

    @Test
    @Order(1)
    @DisplayName("matrice dei valori")
    void matrice() {
        matrice = AELogLevel.values();
        assertNotNull(matrice);

        System.out.println("Tutti i valori della enumeration come matrice []");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", matrice.length));
        System.out.println(VUOTA);
        for (AELogLevel valore : matrice) {
            System.out.println(valore);
        }
    }

    @Test
    @Order(2)
    @DisplayName("lista dei valori")
    void lista() {
        listaType = AELogLevel.getAll();
        assertNotNull(listaType);

        System.out.println("Tutti i valori della enumeration come ArrayList()");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", listaType.size()));
        System.out.println(VUOTA);
        listaType.forEach(System.out::println);
        System.out.println(VUOTA);
    }

    @ParameterizedTest
    @MethodSource(value = "LIVELLI")
    @Order(3)
    @DisplayName("getColor")
        //--log level
    void getColor(final AELogLevel type) {
        assertNotNull(type);

        System.out.println(VUOTA);
        System.out.println(String.format("%s%s%s", type, FORWARD, type.getColor()));
    }


    @ParameterizedTest
    @MethodSource(value = "LIVELLI")
    @Order(4)
    @DisplayName("getPref")
        //--log level
    void getPref(final AELogLevel type) {
        assertNotNull(type);

        System.out.println("Stringa di valori (text) da usare per memorizzare la preferenza");
        System.out.println("La stringa è composta da tutti i valori separati da virgola");
        System.out.println("Poi, separato da punto e virgola, viene il valore corrente");
        System.out.println(VUOTA);
        System.out.println(String.format("%s%s%s", type, FORWARD, type.getPref()));
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
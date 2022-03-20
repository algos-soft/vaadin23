package it.algos.unit;

import it.algos.test.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 07-mar-2022
 * Time: 16:50
 * Unit test di una enumeration <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("testAllValido")
@DisplayName("Enumeration AELogLevel")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AELogLevelTest extends ATest {

    private AELevelLog type;

    private List<AELevelLog> listaLivel;

    private List<String> listaTag;

    private AELevelLog[] matrice;


    //--log level
    protected static Stream<Arguments> LIVELLI() {
        return Stream.of(
                Arguments.of(AELevelLog.debug),
                Arguments.of(AELevelLog.info),
                Arguments.of(AELevelLog.warn),
                Arguments.of(AELevelLog.error)
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

        type = null;
        listaLivel = null;
        listaTag = null;
        matrice = null;
    }

    @Test
    @Order(1)
    @DisplayName("matrice dei valori")
    void matrice() {
        matrice = AELevelLog.values();
        assertNotNull(matrice);

        System.out.println("Tutti i valori della enumeration come matrice []");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", matrice.length));
        System.out.println(VUOTA);
        for (AELevelLog valore : matrice) {
            System.out.println(valore);
        }
    }

    @Test
    @Order(2)
    @DisplayName("lista dei valori")
    void lista() {
        listaLivel = AELevelLog.getAll();
        assertNotNull(listaLivel);

        System.out.println("Tutti i valori della enumeration come ArrayList()");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", listaLivel.size()));
        System.out.println(VUOTA);
        listaLivel.forEach(System.out::println);
        System.out.println(VUOTA);
    }

    @ParameterizedTest
    @MethodSource(value = "LIVELLI")
    @Order(3)
    @DisplayName("getColor")
        //--log level
    void getColor(final AELevelLog type) {
        assertNotNull(type);

        System.out.println(VUOTA);
        System.out.println(String.format("%s%s%s", type, FORWARD, type.getColor()));
    }


    @ParameterizedTest
    @MethodSource(value = "LIVELLI")
    @Order(4)
    @DisplayName("getPref")
        //--log level
    void getPref(final AELevelLog type) {
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
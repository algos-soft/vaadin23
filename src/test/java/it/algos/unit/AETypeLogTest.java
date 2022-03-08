package it.algos.unit;

import it.algos.test.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 07-mar-2022
 * Time: 15:20
 * Unit test di una enumeration <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("testAllValido")
@DisplayName("Enumeration AETypeLog")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AETypeLogTest extends ATest {

    private AETypeLog type;

    private List<AETypeLog> listaType;

    private List<String> listaTag;

    private AETypeLog[] matrice;


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
        listaType = null;
        listaTag = null;
        matrice = null;
    }

    @Test
    @Order(1)
    @DisplayName("1 - matrice dei valori")
    public void matrice() {
        matrice = AETypeLog.values();
        assertNotNull(matrice);

        System.out.println("Tutti i valori della enumeration come matrice []");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", matrice.length));
        System.out.println(VUOTA);
        for (AETypeLog valore : matrice) {
            System.out.println(valore);
        }
    }

    @Test
    @Order(2)
    @DisplayName("2 - lista dei valori")
    void lista() {
        listaType = AETypeLog.getAll();
        assertNotNull(listaType);

        System.out.println("Tutti i valori della enumeration come ArrayList()");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", listaType.size()));
        System.out.println(VUOTA);
        listaType.forEach(System.out::println);
        System.out.println(VUOTA);
    }

    @Test
    @Order(3)
    @DisplayName("3 - getAllTag")
    void getAllTag() {
        listaTag = AETypeLog.getAllTag();
        assertNotNull(listaTag);
        System.out.println(VUOTA);

        System.out.println(String.format("Ci sono %d elementi nella Enumeration", listaTag.size()));
        System.out.println(VUOTA);
        listaTag.forEach(System.out::println);
        System.out.println(VUOTA);
    }

    @ParameterizedTest
    @MethodSource(value = "TYPES")
    @Order(4)
    @DisplayName("4 - getSingleType")
        //--tag
        //--esiste nella enumeration
    void getSingleType(final String tag, final boolean esiste) {
        type = AETypeLog.getType(tag);
        assertTrue(esiste ? type != null : type == null);

        System.out.println(VUOTA);
        System.out.println(String.format("%s%s%s", tag, FORWARD, type != null ? type.toString() : "non esiste"));
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
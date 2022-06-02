package it.algos.simple;

import it.algos.base.*;
import it.algos.simple.backend.enumeration.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: sab, 07-mag-2022
 * Time: 17:35
 * Unit test di una classe di servizio <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("quickly")
@Tag("enums")
@DisplayName("Enumeration SPref")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SPrefTest extends AlgosTest {


    private SPref type;

    private List<SPref> listaEnum;

    private List<String> listaTag;

    private SPref[] matrice;


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
     * Qui passa prima di ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        type = null;
        listaEnum = null;
        listaTag = null;
        matrice = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - matrice dei valori")
    void matrice() {
        matrice = SPref.values();
        assertNotNull(matrice);

        System.out.println("Tutti i valori della enumeration come matrice []");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", matrice.length));
        System.out.println(VUOTA);
        for (SPref valore : matrice) {
            System.out.println(valore);
        }
    }

    @Test
    @Order(2)
    @DisplayName("2 - lista dei valori")
    void lista() {
        listaEnum = SPref.getAllEnums();
        assertNotNull(listaEnum);

        System.out.println("Tutte le occorrenze della enumeration come ArrayList()");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", listaEnum.size()));
        System.out.println(VUOTA);
        listaEnum.forEach(System.out::println);
        System.out.println(VUOTA);
    }

    @Test
    @Order(3)
    @DisplayName("3 - lista come string")
    void listaStringTag() {
        listaEnum = SPref.getAllEnums();
        assertNotNull(listaEnum);

        System.out.println(String.format("KeyCode della enumeration (%s valori)", listaEnum.size()));
        System.out.println(VUOTA);
        listaEnum.forEach(enumPref -> System.out.println(enumPref.toString()));
    }


    @Test
    @Order(4)
    @DisplayName("4 - descrizione preferenza")
    void descrizione() {
        listaEnum = SPref.getAllEnums();
        assertNotNull(listaEnum);

        System.out.println(String.format("KeyCode e descrizione della enumeration (%s valori)", listaEnum.size()));
        System.out.println(VUOTA);
        listaEnum.forEach(pref -> System.out.println(String.format("%s%s%s", pref.getKeyCode(), FORWARD, pref.getDescrizione())));
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
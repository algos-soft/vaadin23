package it.algos.service;

import it.algos.*;
import it.algos.base.*;
import it.algos.vaad23.backend.boot.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.logic.*;
import it.algos.vaad23.backend.packages.utility.nota.*;
import it.algos.vaad23.backend.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: sab, 12-mar-2022
 * Time: 18:06
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
@DisplayName("Class service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClassServiceTest extends AlgosTest {


    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    private ClassService service;

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
        service = classService;
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
    @DisplayName("1 - Test 'a freddo' (senza service)")
    void first() {
        Class clazz = VaadCost.class;
        String canonicalName = clazz.getCanonicalName();
        assertTrue(textService.isValid(canonicalName));
        System.out.println(canonicalName);
        Class clazz2 = null;

        try {
            clazz2 = Class.forName(canonicalName);
        } catch (Exception unErrore) {
            System.out.println(String.format(unErrore.getMessage()));
        }
        assertNotNull(clazz2);
        System.out.println(clazz2.getSimpleName());
        System.out.println(clazz2.getName());
        System.out.println(clazz2.getCanonicalName());
    }

    @Test
    @Order(2)
    @DisplayName("2 - Cerca backend from clazz")
    void backend() {
        System.out.println("2 - Cerca backend from clazz");
        clazz = Nota.class;
        CrudBackend backend = service.getBackendFromEntityClazz(clazz);
        assertNotNull(backend);
        System.out.println(backend.getClass().getSimpleName());
    }

    @ParameterizedTest
    @MethodSource(value = "SIMPLE_CLAZZ_NAME")
    @Order(3)
    @DisplayName("3 - clazz and canonicalName from simpleName")
        //--clazz
        //--esiste nel package
    void getClazzFromSimpleName(final String simpleName, final boolean esistePackage) {
        clazz = null;
        sorgente = simpleName;
        VaadVar.projectNameModulo = "vaad23";
        clazz = service.getClazzFromSimpleName(sorgente);
        assertFalse(esistePackage && clazz == null);
        printClazz(sorgente, clazz);
    }

    @ParameterizedTest
    @MethodSource(value = "ENTITY_NAME")
    @Order(4)
    @DisplayName("4 - isEntity clazz name")
        //--entity clazz name
        //--esiste nel package
    void isEntity(final String entityName, final boolean esistePackage) {
        System.out.println("4 - isEntity clazz name");
        System.out.println(VUOTA);
        clazz = null;
        sorgente = entityName;
        ottenutoBooleano = service.isEntity(sorgente);
        assertEquals(esistePackage, ottenutoBooleano);
        if (esistePackage) {
            System.out.println(String.format("La entity '%s' esiste", entityName));
        }
        else {
            System.out.println(String.format("La entity '%s' NON esiste", entityName));
        }
    }

    @ParameterizedTest
    @MethodSource(value = "ENTITY_CLAZZ")
    @Order(5)
    @DisplayName("5 - isEntity clazz")
        //--entity clazz
        //--esiste nel package
    void isEntity2(final Class entityClazz, final boolean esistePackage) {
        System.out.println("5 - isEntity clazz");
        System.out.println(VUOTA);
        clazz = entityClazz;
        String name = clazz != null ? clazz.getSimpleName() : "(null)";
        ottenutoBooleano = service.isEntity(clazz);
        assertEquals(esistePackage, ottenutoBooleano);
        if (esistePackage) {
            System.out.println(String.format("La entity clazz '%s' esiste", name));
        }
        else {
            System.out.println(String.format("La entity clazz '%s' NON esiste", name));
        }
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


    void printClazz(final String sorgente, final Class clazz) {
        if (clazz != null) {
            System.out.println("Classe trovata");
            System.out.println(String.format("%s%s%s", "Sorgente", FORWARD, sorgente));
            System.out.println(String.format("%s%s%s", "Name", FORWARD, clazz.getName()));
            System.out.println(String.format("%s%s%s", "SimpleName", FORWARD, clazz.getSimpleName()));
            System.out.println(String.format("%s%s%s", "CanonicalName", FORWARD, clazz.getCanonicalName()));
        }
        else {
            System.out.println(String.format("%s%s'%s'", "Non esiste la classe", FORWARD, sorgente));
        }
        System.out.println(VUOTA);
    }

}
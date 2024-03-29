package it.algos.service;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: sab, 12-mar-2022
 * Time: 18:10
 * <p>
 * Unit test di una classe di servizio (di norma) <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {SimpleApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("quickly")
@DisplayName("Reflection service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReflectionServiceTest extends AlgosTest {

    /**
     * Istanza di una interfaccia <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext appContext;

    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    private ReflectionService service;

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
        service = reflectionService;
        service.textService = textService;
        service.logger = logService;
        service.appContext = appContext;
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
    @DisplayName("1 - getClasse")
    void getClasse() {
    }

    @Test
    @Order(2)
    @DisplayName("2 - isEsisteMetodo")
    void isEsisteMetodo() {
        sorgente = "it/algos/vaad23/backend/packages/crono/anno/AnnoBackend";

        previstoBooleano = false;
        ottenutoBooleano = service.isEsisteMetodo(null, sorgente2);
        assertEquals(previstoBooleano, ottenutoBooleano);
        System.out.println(VUOTA);
        System.out.println(String.format("Nella classe %s non esiste il metodo '%s'", sorgente, sorgente2));

        previstoBooleano = false;
        ottenutoBooleano = service.isEsisteMetodo(VUOTA, sorgente2);
        assertEquals(previstoBooleano, ottenutoBooleano);
        System.out.println(VUOTA);
        System.out.println(String.format("Nella classe %s non esiste il metodo '%s'", sorgente, sorgente2));

        sorgente2 = "pippoz";
        previstoBooleano = false;
        ottenutoBooleano = service.isEsisteMetodo(sorgente, sorgente2);
        assertEquals(previstoBooleano, ottenutoBooleano);
        System.out.println(VUOTA);
        System.out.println(String.format("Nella classe %s non esiste il metodo '%s'", sorgente, sorgente2));

        sorgente2 = "reset";
        previstoBooleano = true;
        ottenutoBooleano = service.isEsisteMetodo(sorgente, sorgente2);
        assertEquals(previstoBooleano, ottenutoBooleano);
        System.out.println(VUOTA);
        System.out.println(String.format("Nella classe %s esiste il metodo '%s'", sorgente, sorgente2));

        sorgente2 = "Reset";
        previstoBooleano = true;
        ottenutoBooleano = service.isEsisteMetodo(sorgente, sorgente2);
        assertEquals(previstoBooleano, ottenutoBooleano);
        System.out.println(VUOTA);
        System.out.println(String.format("Nella classe %s esiste il metodo '%s'", sorgente, sorgente2));
    }


    @Test
    @Order(3)
    @DisplayName("3 - esegueMetodo")
    void esegueMetodo() {
        sorgente = "it/algos/vaad23/backend/packages/crono/anno/AnnoBackend";

        previstoBooleano = false;
        ottenutoBooleano = service.esegueMetodo(null, sorgente2);
        assertEquals(previstoBooleano, ottenutoBooleano);
        System.out.println(VUOTA);
        System.out.println(String.format("Nella classe %s non esiste il metodo '%s'", sorgente, sorgente2));

        previstoBooleano = false;
        ottenutoBooleano = service.esegueMetodo(VUOTA, sorgente2);
        assertEquals(previstoBooleano, ottenutoBooleano);
        System.out.println(VUOTA);
        System.out.println(String.format("Nella classe %s non esiste il metodo '%s'", sorgente, sorgente2));

        sorgente2 = "pippoz";
        previstoBooleano = false;
        ottenutoBooleano = service.esegueMetodo(sorgente, sorgente2);
        assertEquals(previstoBooleano, ottenutoBooleano);
        System.out.println(VUOTA);
        System.out.println(String.format("Nella classe %s non esiste il metodo '%s'", sorgente, sorgente2));

        sorgente2 = "reset";
        previstoBooleano = true;
        ottenutoBooleano = service.esegueMetodo(sorgente, sorgente2);
        assertEquals(previstoBooleano, ottenutoBooleano);
        System.out.println(VUOTA);
        System.out.println(String.format("Eseguito il metodo '%s' della classe '%s'", sorgente2, sorgente));

        sorgente2 = "Reset";
        previstoBooleano = true;
        ottenutoBooleano = service.esegueMetodo(sorgente, sorgente2);
        assertEquals(previstoBooleano, ottenutoBooleano);
        System.out.println(VUOTA);
        System.out.println(String.format("Eseguito il metodo '%s' della classe '%s'", sorgente2, sorgente));
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
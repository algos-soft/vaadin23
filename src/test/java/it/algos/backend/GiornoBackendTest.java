package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.packages.crono.giorno.*;
import it.algos.vaad23.backend.packages.crono.mese.*;
import it.algos.vaad23.backend.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: sab, 07-mag-2022
 * Time: 14:50
 */
@SpringBootTest(classes = {SimpleApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@Tag("backend")
@DisplayName("Giorno Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GiornoBackendTest extends AlgosTest {

    /**
     * The Service.
     */
    @InjectMocks
    private GiornoBackend backend;

    @Autowired
    private GiornoRepository repository;

    @Autowired
    private MeseRepository meseRepository;

    private List<Giorno> listaBeans;

    @Autowired
    private MeseBackend meseBackend;

    @Autowired
    protected MongoService mongoService;

    private Giorno giorno;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();

        Assertions.assertNotNull(backend);

        backend.repository = repository;
        backend.crudRepository = repository;
        backend.arrayService = arrayService;
        backend.reflectionService = reflectionService;
        backend.meseBackend = meseBackend;
        meseBackend.repository = meseRepository;
        backend.mongoService = mongoService;
    }


    /**
     * Inizializzazione dei service <br>
     * Devono essere tutti 'mockati' prima di iniettare i riferimenti incrociati <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void initMocks() {
        super.initMocks();
    }


    /**
     * Regola tutti riferimenti incrociati <br>
     * Deve essere fatto dopo aver costruito tutte le referenze 'mockate' <br>
     * Nelle sottoclassi devono essere regolati i riferimenti dei service specifici <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixRiferimentiIncrociati() {
        super.fixRiferimentiIncrociati();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
        giorno = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - count")
    void count() {
        System.out.println("1 - count");
        String message;

        ottenutoIntero = backend.count();
        assertTrue(ottenutoIntero > 0);
        message = String.format("Ci sono in totale %s entities nel database mongoDB", textService.format(ottenutoIntero));
        System.out.println(message);
    }

    @Test
    @Order(2)
    @DisplayName("2 - findAll (entity)")
    void findAll() {
        System.out.println("2 - findAll (entity)");
        String message;

        listaBeans = backend.findAll();
        assertNotNull(listaBeans);
        message = String.format("Ci sono in totale %s entities di %s", textService.format(listaBeans.size()), "Giorno");
        System.out.println(message);
        printGiorni(listaBeans);
    }

    @Test
    @Order(3)
    @DisplayName("3 - findNomi (nome)")
    void findNomi() {
        System.out.println("3 - findNomi (nome)");
        String message;

        listaStr = backend.findNomi();
        assertNotNull(listaStr);
        message = String.format("Ci sono in totale %s giorni", textService.format(listaStr.size()));
        System.out.println(message);
        printNomiGiorni(listaStr);
    }

    @Test
    @Order(4)
    @DisplayName("4 - findAllByMese (entity)")
    void findAllByMese() {
        System.out.println("4 - findAllByMese (entity)");

        for (Mese sorgente : meseBackend.findAll()) {
            listaBeans = backend.findAllByMese(sorgente);
            assertNotNull(listaBeans);
            message = String.format("Nel mese di %s ci sono %s giorni", sorgente, textService.format(listaBeans.size()));
            System.out.println(VUOTA);
            System.out.println(message);
            printGiorni(listaBeans);
        }
    }

    @Test
    @Order(5)
    @DisplayName("5 - findNomiByMese (nome)")
    void findNomiByMese() {
        System.out.println("5 - findNomiByMese (nome)");

        for (String sorgente : meseBackend.findNomi()) {
            listaStr = backend.findNomiByMese(sorgente);
            assertNotNull(listaStr);
            message = String.format("Nel mese di %s ci sono %s giorni", sorgente, textService.format(listaStr.size()));
            System.out.println(VUOTA);
            System.out.println(message);
            printNomiGiorni(listaStr);
        }
    }

    @Test
    @Order(6)
    @DisplayName("6 - findByOrdine")
    void findByOrdine() {
        System.out.println("6 - findByOrdine");

        sorgenteIntero = 14;
        giorno = backend.findByOrdine(sorgenteIntero);
        assertNotNull(giorno);
        message = String.format("Progressivo %d corrisponde al %s", sorgenteIntero, giorno.nome);
        System.out.println(message);

        sorgenteIntero = 131;
        giorno = backend.findByOrdine(sorgenteIntero);
        assertNotNull(giorno);
        message = String.format("Progressivo %d corrisponde al %s", sorgenteIntero, giorno.nome);
        System.out.println(message);

        sorgenteIntero = 218;
        giorno = backend.findByOrdine(sorgenteIntero);
        assertNotNull(giorno);
        message = String.format("Progressivo %d corrisponde al %s", sorgenteIntero, giorno.nome);
        System.out.println(message);

        sorgenteIntero = 425;
        giorno = backend.findByOrdine(sorgenteIntero);
        assertNull(giorno);
        message = String.format("Progressivo %d non corrisponde a nessun giorno", sorgenteIntero);
        System.out.println(message);
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

    void printGiorni(List<Giorno> listaGiorni) {
        int k = 0;

        for (Giorno giorno : listaGiorni) {
            System.out.print(++k);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);
            System.out.print(giorno.nome);
            System.out.print(SPAZIO);
            System.out.print(giorno.trascorsi);
            System.out.print(SPAZIO);
            System.out.println(giorno.mancanti);
        }
    }

    void printNomiGiorni(List<String> listaGiorni) {
        int k = 0;

        for (String giorno : listaGiorni) {
            System.out.print(++k);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);
            System.out.println(giorno);
        }
    }

}

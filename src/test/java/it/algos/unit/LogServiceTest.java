package it.algos.unit;

import it.algos.*;
import it.algos.test.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.service.*;
import it.algos.vaad23.backend.wrapper.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.provider.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.*;

import java.util.stream.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 07-mar-2022
 * Time: 22:50
 * <p>
 * Unit test di una classe di servizio <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {SimpleApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("testAllValido")
@DisplayName("Log service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LogServiceTest extends ATest {

    @Autowired
    protected ApplicationContext appContext;

    private WrapLogCompany wrap;

    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    private LogService service;

    //--tag
    //--esiste nella enumeration
    protected static Stream<Arguments> TYPES() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(VUOTA, false),
                Arguments.of("system", true),
                Arguments.of("setup", true),
                Arguments.of("login", true),
                Arguments.of("startup", true),
                Arguments.of("checkMenu", true),
                Arguments.of("checkData", true),
                Arguments.of("preferenze", true),
                Arguments.of("newEntity", true),
                Arguments.of("edit", true),
                Arguments.of("newEntity", true),
                Arguments.of("modifica", true),
                Arguments.of("delete", true),
                Arguments.of("deleteAll", true),
                Arguments.of("mongoDB", true),
                Arguments.of("debug", true),
                Arguments.of("info", true),
                Arguments.of("warn", true),
                Arguments.of("error", true),
                Arguments.of("info", true),
                Arguments.of("wizard", true),
                Arguments.of("wizarddoc", false),
                Arguments.of("wizardDoc", true),
                Arguments.of("info", true),
                Arguments.of("import", true),
                Arguments.of("export", true),
                Arguments.of("download", true),
                Arguments.of("update", true),
                Arguments.of("Update", false),
                Arguments.of("info", true),
                Arguments.of("elabora", true),
                Arguments.of("reset", true),
                Arguments.of("utente", true),
                Arguments.of("password", true),
                Arguments.of("cicloBio", true)
        );
    }


    //--companySigla
    //--userName
    //--addressIP
    //--type
    //--messaggio
    protected static Stream<Arguments> COMPANY() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, VUOTA, (AETypeLog) null, VUOTA),
                Arguments.of("crpt", "ugonottiMario", "2001:B07:AD4:2177:9B56:DB51:33E0:A151", AETypeLog.login, "Messaggio con una company"),
                Arguments.of("gaps", "Angelini P.", "5.168.221.253", AETypeLog.login, "Messaggio con una company"),
                Arguments.of("algos", "INFERMIERE", "2001:B07:A56:933F:F17B:8C00:76DE:1E27", AETypeLog.login, "Messaggio con una company"),
                Arguments.of(VUOTA, "Mario Beretta", "23.5678.987", AETypeLog.delete, "Messaggio con una company"),
                Arguments.of("algos", VUOTA, "127.0.0.1", AETypeLog.setup, "VUOTA"),
                Arguments.of("algos", VUOTA, "80.79.58.124", AETypeLog.modifica, "VUOTA"),
                Arguments.of("crpt", "m. Rossi", VUOTA, AETypeLog.upload, "VUOTA"),
                Arguments.of("pap", "Franca Baldini", "51.179.101.238", AETypeLog.checkMenu, "VUOTA")
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
        service = logService;
        service.slf4jLogger = LoggerFactory.getLogger("vaad23.admin");
        service.textService = textService;
    }

    /**
     * Regola tutti riferimenti incrociati <br>
     * Deve essere fatto dopo aver costruito tutte le referenze 'mockate' <br>
     * Nelle sottoclassi devono essere regolati i riferimenti dei service specifici <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    //    @Override
    protected void fixRiferimentiIncrociati2() {
        super.fixRiferimentiIncrociati();
    }

    /**
     * Qui passa prima di ogni test <br>
     * Invocare PRIMA il metodo setUpEach() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        wrap = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - Debug generico")
    void debug() {
        sorgente = "Messaggio di debug";
        service.debug(sorgente);
    }

    @Test
    @Order(2)
    @DisplayName("2 - Info generico")
    void info() {
        sorgente = "Messaggio di info";
        service.info(sorgente);
    }

    @Test
    @Order(3)
    @DisplayName("3 - Warn generico")
    void warn() {
        sorgente = "Messaggio di warning";
        service.warn(sorgente);
    }

    @Test
    @Order(4)
    @DisplayName("4 - Error generico")
    void error() {
        sorgente = "Messaggio di errore";
        service.error(sorgente);
    }

    @Test
    @Order(5)
    @DisplayName("5 - Info vuota")
    void info2() {
        service.info(VUOTA);
    }

    @Test
    @Order(6)
    @DisplayName("6 - Info tipizzata")
    void infoType() {
        sorgente = "Info tipizzata";
        service.info(AETypeLog.setup, sorgente);
    }

    @Test
    @Order(7)
    @DisplayName("7 - Info con company")
    void infoCompany() {
        sorgente = "Info con company";
        wrap = appContext.getBean(WrapLogCompany.class, "crpt", "ugonottiMario", "23.5678.987");
        service.info(AETypeLog.setup, wrap, sorgente);
    }

    @Test
    @Order(8)
    @DisplayName("8 - Info con incolonnamento")
        //--companySigla
        //--userName
        //--addressIP
        //--type
        //--messaggio
    void infoCompanyIncolonnate() {
        System.out.println("8 - Info con incolonnamento");
        System.out.println(VUOTA);
        COMPANY().forEach(this::printWrap);
    }

    @Test
    @Order(9)
    @DisplayName("9 - Invio di una mail")
    void infoMail() {
        System.out.println("9 - Invio di una mail");
        sorgente = "L'utente Rossi Carlo si è loggato con una password errata";
        wrap = appContext.getBean(WrapLogCompany.class, "crpt", "Rossi C.", "2001:B07:AD4:2177:9B56:DB51:33E0:A151");
        ottenutoBooleano = service.mail(AETypeLog.login, wrap, sorgente);
        assertTrue(ottenutoBooleano);
    }

    void printWrap(Arguments arg) {
        Object[] mat = arg.get();
        wrap = appContext.getBean(WrapLogCompany.class, mat[0], mat[1], mat[2]);
        service.info((AETypeLog) mat[3], wrap, (String) mat[4]);
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
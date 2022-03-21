package it.algos.unit;

import it.algos.*;
import it.algos.test.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.boot.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.exception.*;
import it.algos.vaad23.backend.service.*;
import it.algos.vaad23.backend.wrapper.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import org.slf4j.*;
import org.springframework.boot.test.context.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 07-mar-2022
 * Time: 22:50
 * <p>
 * Unit test di una classe di servizio (di norma) <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {SimpleApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("testAllIntegration")
@DisplayName("Log service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LogServiceTest extends ATest {


    private WrapLogCompany wrap;

    private AlgosException exception;

    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    private LogService service;


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
    }

    /**
     * Regola tutti riferimenti incrociati <br>
     * Deve essere fatto dopo aver costruito tutte le referenze 'mockate' <br>
     * Nelle sottoclassi devono essere regolati i riferimenti dei service specifici <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixRiferimentiIncrociati() {
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
        service.mailService = mailService;
        VaadVar.usaCompany = false;
        exception = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - Messaggi di solo testo su slf4jLogger")
    void log1() {
        System.out.println("1 - Messaggi di solo testo su slf4jLogger");
        System.out.println(VUOTA);

        sorgente2 = AELevelLog.info.toString();
        sorgente = String.format("Messaggio semplice di %s proveniente dal test", sorgente2);
        ottenuto = service.info(sorgente);
        printMessaggio(ottenuto);

        sorgente2 = AELevelLog.warn.toString();
        sorgente = String.format("Messaggio semplice di %s proveniente dal test", sorgente2);
        ottenuto = service.warn(sorgente);
        printMessaggio(ottenuto);

        sorgente2 = AELevelLog.error.toString();
        sorgente = String.format("Messaggio semplice di %s proveniente dal test", sorgente2);
        ottenuto = service.error(sorgente);
        printMessaggio(ottenuto);

        sorgente2 = AELevelLog.debug.toString();
        sorgente = String.format("Messaggio semplice di %s proveniente dal test", sorgente2);
        ottenuto = service.debug(sorgente);
        printMessaggio(ottenuto);
    }

    @Test
    @Order(2)
    @DisplayName("2 - Messaggi con typo su slf4jLogger")
    void log2() {
        System.out.println("2 - Messaggi con typo su slf4jLogger");
        System.out.println(VUOTA);

        sorgente2 = AELevelLog.info.toString();
        sorgente = String.format("Messaggio typo di %s proveniente dal test", sorgente2);
        ottenuto = service.info(AETypeLog.checkData, sorgente);
        printMessaggio(ottenuto);

        sorgente2 = AELevelLog.info.toString();
        sorgente = String.format("Messaggio typo di %s proveniente dal test", sorgente2);
        ottenuto = service.info(AETypeLog.download, sorgente);
        printMessaggio(ottenuto);

        sorgente2 = AELevelLog.info.toString();
        sorgente = String.format("Messaggio typo di %s proveniente dal test", sorgente2);
        ottenuto = service.info(AETypeLog.preferenze, sorgente);
        printMessaggio(ottenuto);

        sorgente2 = AELevelLog.warn.toString();
        sorgente = String.format("Messaggio typo di %s proveniente dal test", sorgente2);
        ottenuto = service.warn(AETypeLog.modifica, sorgente);
        printMessaggio(ottenuto);

        sorgente2 = AELevelLog.error.toString();
        sorgente = String.format("Messaggio typo di %s proveniente dal test", sorgente2);
        ottenuto = service.error(AETypeLog.startup, sorgente);
        printMessaggio(ottenuto);

        sorgente2 = AELevelLog.debug.toString();
        sorgente = String.format("Messaggio typo di %s proveniente dal test", sorgente2);
        ottenuto = service.debug(sorgente);
        printMessaggio(ottenuto);
    }


    @Test
    @Order(3)
    @DisplayName("3 - Messaggi con company and user")
    void log3() {
        VaadVar.usaCompany = true;
        System.out.println("3 - Messaggi con company and user");
        System.out.println(VUOTA);

        sorgente = String.format("Messaggio di %s senza company (wrap=null)", AELevelLog.info);
        ottenuto = service.info(AETypeLog.checkData, sorgente, wrap);
        printMessaggio(ottenuto);

        sorgente = String.format("Messaggio di %s con company (wrap!=null)", AELevelLog.info);
        wrap = WrapLogCompany.crea("crpt", "Domenichini", VUOTA);
        wrap.textService = textService; // serve solo per il test
        ottenuto = service.info(AETypeLog.checkData, sorgente, wrap);
        printMessaggio(ottenuto);

        sorgente = String.format("Messaggio di %s con company (wrap!=null)", AELevelLog.warn);
        wrap = WrapLogCompany.crea("pap", "Mario C.", "345.989.0.0");
        wrap.textService = textService; // serve solo per il test
        ottenuto = service.warn(AETypeLog.checkData, sorgente, wrap);
        printMessaggio(ottenuto);
    }

    @Test
    @Order(4)
    @DisplayName("4 - Messaggi registrati su mongoDB")
    void log4() {
        sorgente2 = AELevelLog.info.toString();
        sorgente = String.format("Messaggio su mongoDB di %s proveniente dal test", sorgente2);
        service.infoDb(AETypeLog.checkData, sorgente);

        sorgente2 = AELevelLog.warn.toString();
        sorgente = String.format("Messaggio su mongoDB di %s proveniente dal test", sorgente2);
        service.warnDb(AETypeLog.export, sorgente);

        sorgente2 = AELevelLog.error.toString();
        sorgente = String.format("Messaggio su mongoDB di %s proveniente dal test", sorgente2);
        service.errorDb(AETypeLog.delete, sorgente);
    }


    @Test
    @Order(5)
    @DisplayName("5 - Messaggi comprensivi di StackTrace")
    void stackTrace() {
        System.out.println(sorgente);
        System.out.println("L'errore può essere di sistema oppure un AlgosException generato nel codice");

        service.warn(AETypeLog.export, new AlgosException("service.warn(AETypeLog.export, new AlgosException(\"testo\"));"));
        service.error(AETypeLog.delete, new AlgosException("service.error(AETypeLog.delete, new AlgosException(\"testo\"));"));

        service.warn(new AlgosException("service.warn(new AlgosException(\"testo\"));"));
        service.error(new AlgosException("service.error(new AlgosException(\"testo\"));"));

        service.warn(new AlgosException(VUOTA));
        service.error(new AlgosException(VUOTA));

        service.warn(new AlgosException());
        service.error(new AlgosException());
    }


    @Test
    @Order(6)
    @DisplayName("6 - Messaggi con StackTrace registrati su mongoDB")
    void stackTraceConDb() {
        System.out.println(sorgente);
        System.out.println("L'errore può essere di sistema oppure un AlgosException generato nel codice");

        service.warnDb(AETypeLog.export, new AlgosException("service.warn(AETypeLog.export, new AlgosException(\"testo\"));"));
        service.errorDb(AETypeLog.delete, new AlgosException("service.error(AETypeLog.delete, new AlgosException(\"testo\"));"));

        // giusto
        service.warnDb(new AlgosException("service.warn(new AlgosException(\"testo\"));"));
        service.errorDb(new AlgosException("service.error(new AlgosException(\"testo\"));"));

        service.warnDb(new AlgosException(VUOTA));
        service.errorDb(new AlgosException(VUOTA));

        service.warnDb(new AlgosException());
        service.errorDb(new AlgosException());

    }

    //    @Test
    @Order(4)
    @DisplayName("4 - Error generico")
    void error() {
        sorgente = "Messaggio di errore";
        service.error(sorgente);
    }

    //    @Test
    @Order(5)
    @DisplayName("5 - Info vuota")
    void info2() {
        service.info(VUOTA);
    }

    //    @Test
    @Order(6)
    @DisplayName("6 - Info tipizzata")
    void infoType() {
        sorgente = "Info tipizzata";
        service.info(AETypeLog.setup, sorgente);
    }

    //    @Test
    //    @Order(7)
    //    @DisplayName("7 - Info con company")
    //    void infoCompany() {
    //        sorgente = "Info con company";
    //        wrap = appContext.getBean(WrapLogCompany.class, "crpt", "ugonottiMario", "23.5678.987");
    //        service.info(AETypeLog.setup, wrap, sorgente);
    //    }

    //    @Test
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

    //    @Test
    @Order(9)
    @DisplayName("9 - Invio di una mail")
    void infoMail() {
        System.out.println("9 - Invio di una mail");
        sorgente = "L'utente Rossi Carlo si è loggato con una password errata";
        wrap = appContext.getBean(WrapLogCompany.class, "crpt", "Rossi C.", "2001:B07:AD4:2177:9B56:DB51:33E0:A151");
        //        service.mail(AETypeLog.login, wrap, sorgente);
    }

    //    @Test
    @Order(10)
    @DisplayName("10 - Prova")
    void prova() {
        arrayService.isEmpty(new ArrayList());
        //        service.mail(AETypeLog.login, wrap, sorgente);
    }

    void printMessaggio(String messaggio) {
        System.out.println(String.format("Messaggio: %s", messaggio));
        System.out.println(VUOTA);
    }

    void printWrap(Arguments arg) {
        Object[] mat = arg.get();
        wrap = appContext.getBean(WrapLogCompany.class, mat[0], mat[1], mat[2]);
        service.info((AETypeLog) mat[3], (String) mat[4], wrap);
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
package it.algos.test;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.exception.*;
import it.algos.vaad23.backend.service.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 07-mar-2022
 * Time: 19:47
 * Classe astratta per i test <br>
 *
 * @see https://www.baeldung.com/parameterized-tests-junit-5
 */
public abstract class ATest {

    protected boolean previstoBooleano;

    protected boolean ottenutoBooleano;

    protected String sorgente;

    protected String sorgente2;

    protected String sorgente3;

    protected String previsto;

    protected String previsto2;

    protected String previsto3;

    protected String ottenuto;

    protected String ottenuto2;

    protected Class sorgenteClasse;

    protected Field sorgenteField;

    protected Field ottenutoField;

    protected Class previstoClasse;

    protected Class ottenutoClasse;

    protected List<String> sorgenteArray;

    protected List<String> previstoArray;

    protected List<String> ottenutoArray;


    @Autowired
    protected ApplicationContext appContext;

    @InjectMocks
    protected TextService textService;

    @InjectMocks
    protected DateService dateService;

    @InjectMocks
    protected LogService logService;

    @InjectMocks
    protected MailService mailService;


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

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    protected void setUpAll() {
        MockitoAnnotations.openMocks(this);
        initMocks();
        fixRiferimentiIncrociati();
    }

    /**
     * Inizializzazione dei service <br>
     * Devono essere tutti 'mockati' prima di iniettare i riferimenti incrociati <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void initMocks() {
        assertNotNull(textService);
        assertNotNull(logService);
        assertNotNull(mailService);
        assertNotNull(dateService);
    }


    /**
     * Regola tutti riferimenti incrociati <br>
     * Deve essere fatto dopo aver costruito tutte le referenze 'mockate' <br>
     * Nelle sottoclassi devono essere regolati i riferimenti dei service specifici <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixRiferimentiIncrociati() {
        mailService.textService = textService;
        dateService.textService = textService;
    }

    /**
     * Qui passa prima di ogni test, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpEach() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    protected void setUpEach() {
    }


    protected void printError(final AlgosException unErrore) {
        System.out.println(VUOTA);
        System.out.println("Errore");
        if (unErrore.getCause() != null) {
            System.out.println(String.format("Cause %s %s", FORWARD,
                    unErrore.getCause().getClass().getSimpleName()
            ));
        }
        System.out.println(String.format("Message %s %s", FORWARD, unErrore.getMessage()));
        if (unErrore.getEntityBean() != null) {
            System.out.println(String.format("EntityBean %s %s", FORWARD, unErrore.getEntityBean().toString()));
        }
        if (unErrore.getClazz() != null) {
            System.out.println(String.format("Clazz %s %s", FORWARD, unErrore.getClazz().getSimpleName()));
        }
        //        if (textService.isValid(unErrore.getMethod())) {
        //            System.out.println(String.format("Method %s %s()", FORWARD, unErrore.getMethod()));
        //        }
    }

    protected void printError(final Exception unErrore) {
        System.out.println(VUOTA);
        System.out.println("Errore");
        if (unErrore == null) {
            return;
        }

        if (unErrore instanceof AlgosException erroreAlgos) {
            System.out.println(String.format("Class %s %s", FORWARD, erroreAlgos.getClazz().getSimpleName()));
            System.out.println(String.format("Method %s %s", FORWARD, erroreAlgos.getMethod()));
            System.out.println(String.format("Message %s %s", FORWARD, erroreAlgos.getMessage()));
        }
        else {
            System.out.println(String.format("Class %s %s", FORWARD, unErrore.getCause() != null ? unErrore.getCause().getClass().getSimpleName() : VUOTA));
            System.out.println(String.format("Message %s %s", FORWARD, unErrore.getMessage()));
            System.out.println(String.format("Cause %s %s", FORWARD, unErrore.getCause()));
        }
    }

}
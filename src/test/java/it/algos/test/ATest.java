package it.algos.test;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.exception.*;
import it.algos.vaad23.backend.service.*;
import org.mockito.*;

import java.lang.reflect.*;
import java.util.*;

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


    @InjectMocks
    protected TextService textService;

    @InjectMocks
    protected LogService logService;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    protected void setUpAll() {
        initMocks();
        fixRiferimentiIncrociati();
    }

    /**
     * Inizializzazione dei service <br>
     * Devono essere tutti 'mockati' prima di iniettare i riferimenti incrociati <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void initMocks() {
        //        MockitoAnnotations.initMocks(textService);
        //        assertNotNull(textService);
        //        assertNotNull(logService);
    }


    /**
     * Regola tutti riferimenti incrociati <br>
     * Deve essere fatto dopo aver costruito tutte le referenze 'mockate' <br>
     * Nelle sottoclassi devono essere regolati i riferimenti dei service specifici <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixRiferimentiIncrociati() {
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
        if (textService.isValid(unErrore.getMethod())) {
            System.out.println(String.format("Method %s %s()", FORWARD, unErrore.getMethod()));
        }
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

package it.algos.vaad23.backend.service;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.interfaces.*;
import it.algos.vaad23.backend.wrapper.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;


/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 07-mar-2022
 * Time: 10:35
 * <p>
 * <p>
 * Classe di servizio per i log. <br>
 * <p>
 * Diverse località di 'uscita' dei logs, regolate da flag: <br>
 * A) nella cartella di log (sempre) <br>
 * B) nella finestra del terminale - sempre in debug - mai in produzione - regolato da flag <br>
 * C) nella collection del database (facoltativo per alcuni programmi) <br>
 * D) in una mail (facoltativo e di norma solo per 'error') <br>
 * <p>
 * Diversi 'livelli' dei logs: debug, info, warn, error <br>
 * <p>
 * Diverse modalità di 'presentazione' (formattazione e incolonnamento) dei logs, regolate da flag: <br>
 * Nel log, incolonnare la data, alcuni campi fissi (di larghezza) e poi la descrizione libera <br>
 * A) Se è una multi-company con security, i campi fissi sono: company, utente, IP e type <br>
 * B) Se l' applicazione non è multi-company e non ha security, l' unico campo fisso è il type <br>
 * Nella mail, invece d'incolonnare i campi fissi, si va a capo <br>
 * <p>
 * I type di log vengono presi dalla enum AETypeLog <br>
 * Il progetto specifico può aggiungere dei type presi da una propria enum <br>
 * Le enum implementano un'interfaccia comune AITypeLog, per poter essere intercambiabili <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class LogService extends AbstractService {

    public static final int PAD_COMPANY = 5;

    public static final int PAD_UTENTE = 15;

    public static final int PAD_ADDRESS_IP = 37;

    public static final int PAD_TYPE = 18;

    public static final int PAD_CLASS = 20;

    public static final int PAD_METHOD = 20;

    public static final int PAD_LINE = 3;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public MailService mailService;

    /**
     * Riferimento al logger usato <br>
     * È nella directory 'config', il file 'logback-spring.xml' <br>
     * Deve essere creato subito dalla factory class LoggerFactory <br>
     * Va selezionato un appender da usare e che sia presente nel file di configurazione <br>
     */
    public Logger slf4jLogger;

    /**
     * Controlla che la classe abbia usaBoot=true <br>
     */
    protected Predicate<StackTraceElement> checkStartAlgos = stack -> stack.getClassName().startsWith(PATH_ALGOS);

    /**
     * Performing the initialization in a constructor is not suggested as the state of the UI is not properly set up when the constructor is invoked. <br>
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, ma l'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     * Se viene implementata una sottoclasse, passa di qui per ogni sottoclasse oltre che per questa istanza <br>
     * Se esistono delle sottoclassi, passa di qui per ognuna di esse (oltre a questa classe madre) <br>
     */
    @PostConstruct
    private void postConstruct() {
        slf4jLogger = LoggerFactory.getLogger("vaad23.admin");
    }

    /**
     * Gestisce un log di warning <br>
     *
     * @param message    da registrare
     * @param clazz      di provenienza della richiesta
     * @param methodName di provenienza della richiesta
     */
    public void warn(String message, Class clazz, String methodName) {
        esegue(AETypeLog.warn, message, clazz, methodName);
    }

    /**
     * Gestisce un log di debug <br>
     *
     * @param message testo del log
     */
    public void debug(final String message) {
        this.logBase(AELogLevel.debug, message);
    }

    /**
     * Gestisce un log di info <br>
     *
     * @param message testo del log
     */
    public void info(final String message) {
        this.info(null, message);
    }

    /**
     * Gestisce un log di info <br>
     *
     * @param message testo del log
     */
    public void info(final AETypeLog type, final String message) {
        this.info(type, null, message);
    }

    /**
     * Gestisce un log di info <br>
     *
     * @param message testo del log
     */
    public void info(final AETypeLog type, final WrapLogCompany wrap, final String message) {
        this.logBase(AELogLevel.info, type, wrap, message);
    }

    /**
     * Gestisce un log di warning <br>
     *
     * @param message testo del log
     */
    public void warn(final String message) {
        this.logBase(AELogLevel.warn, message);
    }

    /**
     * Gestisce un log di errore <br>
     *
     * @param message testo del log
     */
    public void error(final Exception unErrore) {
        boolean usaTagIniziale = true;
        String tagClasse = "class=";
        String tagMetodo = "method=";
        String tagRiga = "line=";
        String message;
        StackTraceElement stack = null;
        String classe = VUOTA;
        String metodo = VUOTA;
        int line = 0;
        String riga = VUOTA;
        int padClass = PAD_CLASS;
        int padMethod = PAD_METHOD;
        int padLine = PAD_LINE;

        StackTraceElement[] matrice = unErrore.getStackTrace();
        Optional stackPossibile = Arrays.stream(matrice)
                .filter(algos -> algos.getClassName().startsWith(PATH_ALGOS))
                .findFirst();

        if (stackPossibile != null) {
            stack = (StackTraceElement) stackPossibile.get();
            classe = stack.getClassName();
            classe = fileService.estraeClasseFinale(classe);
            metodo = stack.getMethodName();
            line = stack.getLineNumber();
            riga = line + VUOTA;
        }

        if (usaTagIniziale) {
            classe = tagClasse + classe;
            metodo = tagMetodo + metodo;
            riga = tagRiga + riga;
            padClass += tagClasse.length();
            padMethod += tagMetodo.length();
            padLine += tagRiga.length();
        }

        classe = textService.fixSizeQuadre(classe, padClass);
        metodo = textService.fixSizeQuadre(metodo, padMethod);
        riga = textService.fixSizeQuadre(riga + VUOTA, padLine);

        message = String.format("%s%s%s%s%s%s%s", classe, DOPPIO_SPAZIO, metodo, DOPPIO_SPAZIO, riga, SEP, unErrore.getCause().getMessage());

        this.logBase(AELogLevel.error, message);
    }

    /**
     * Gestisce un log di errore <br>
     *
     * @param message testo del log
     */
    public void error(final String message) {
        this.logBase(AELogLevel.error, message);
    }

    /**
     * Gestisce una mail <br>
     *
     * @param type      merceologico di specificazione
     * @param wrap      di informazioni su company, userName e address
     * @param messageIn testo del log
     */
    public boolean mail(final AETypeLog type, final WrapLogCompany wrap, final String messageIn) {
        String message = fixMessageMail(type, wrap, messageIn);

        mailService.send("gac@algos.it", "Mercoledi", message);
        return false;
    }

    /**
     * Gestisce un log generico <br>
     *
     * @param level     di log: debug/info/warning/error
     * @param messageIn testo del log
     */
    public void logBase(final AELogLevel level, final String messageIn) {
        String message = messageIn.trim();

        switch (level) {
            case debug -> slf4jLogger.debug(message);
            case info -> slf4jLogger.info(message);
            case warn -> slf4jLogger.warn(message);
            case error -> slf4jLogger.error(message);
        }
    }

    /**
     * Gestisce un log generico <br>
     *
     * @param level     di log: debug/info/warning/error
     * @param type      merceologico di specificazione
     * @param wrap      di informazioni su company, userName e address
     * @param messageIn testo del log
     */
    public void logBase(final AELogLevel level, final AETypeLog type, final WrapLogCompany wrap, final String messageIn) {
        String message = fixMessageLog(type, wrap, messageIn);

        switch (level) {
            case debug -> slf4jLogger.debug(message);
            case info -> slf4jLogger.info(message);
            case warn -> slf4jLogger.warn(message);
            case error -> slf4jLogger.error(message);
        }
    }


    /**
     * Gestisce un log di warning <br>
     *
     * @param type       livello di log
     * @param message    da registrare
     * @param clazz      di provenienza della richiesta
     * @param methodName di provenienza della richiesta
     */
    private void esegue(AETypeLog type, String message, Class clazz, String methodName) {
        String clazzTxt;
        String sep = " --- ";
        String end = "()";
        String typeTxt = type.getTag();
        typeTxt = textService.fixSizeQuadre(typeTxt, 10);
        message = typeTxt + DOPPIO_SPAZIO + message;

        if (slf4jLogger == null) {
            return;
        }

        if (clazz != null) {
            clazzTxt = clazz.getSimpleName();
            message += sep + clazzTxt;
        }

        if (textService.isValid(methodName)) {
            message += PUNTO + methodName + end;
        }

        if (type != null) {
            switch (type) {
                case info:
                case modifica:
                    slf4jLogger.info(message.trim());
                    break;
                case warn:
                    slf4jLogger.warn(message.trim());
                    break;
                case error:
                    slf4jLogger.error(message.trim());
                    break;
                default:
                    this.warn("Switch - caso non definito", this.getClass(), "esegue");
                    break;
            }
        }
    }

    public String fixMessageLog(final AETypeLog type, final WrapLogCompany wrap, final String messageIn) {
        String message = messageIn;

        if (wrap != null) {
            message = wrap.getLog() + SEP + message;
        }

        return message.trim();
    }

    public String fixMessageMail(final AETypeLog type, final WrapLogCompany wrap, final String messageIn) {
        String message = messageIn;

        if (wrap != null) {
            message = wrap.getMail(message);

        }

        return message.trim();
    }

    //    public void info(AILogType type, String message) {
    //        String typeTxt;
    //
    //        typeTxt = type != null ? type.getTag() : AETypeLog.system.getTag();
    //        typeTxt = text.fixSizeQuadre(typeTxt, 10);
    //
    //        message = typeTxt + DOPPIO_SPAZIO + message;
    //        adminLogger.info(message.trim());
    //    }

    /**
     * Gestisce un log di error <br>
     *
     * @param unErrore   da gestire
     * @param clazz      di provenienza della richiesta
     * @param methodName di provenienza della richiesta
     */
    public void error(Exception unErrore, Class clazz, String methodName) {
        logBase(AELogLevel.error, AETypeLog.error, null, unErrore.toString());
    }

    /**
     * Gestisce un log di info <br>
     *
     * @param message    della informazione da gestire
     * @param clazz      di provenienza della richiesta
     * @param methodName di provenienza della richiesta
     */
    public void info(String message, Class clazz, String methodName) {
        //@todo Funzionalità ancora da implementare
        //        sendTerminale(AELogLivello.info, descrizione, clazz, methodName);
        //@todo Funzionalità ancora da implementare
        esegue(AETypeLog.info, message, clazz, methodName);
    }

    /**
     * Gestisce un log di error <br>
     *
     * @param message    da registrare
     * @param clazz      di provenienza della richiesta
     * @param methodName di provenienza della richiesta
     */
    public void error(String message, Class clazz, String methodName) {
        //        esegue(AETypeLog.error, message, clazz, methodName);@todo sistemare
    }

    /**
     * Gestisce un log di error <br>
     *
     * @param unErrore   da gestire
     * @param clazz      di provenienza della richiesta
     * @param methodName di provenienza della richiesta
     */
    public void warn(Exception unErrore, Class clazz, String methodName) {
        //        warn(unErrore.toString(), clazz, methodName); @todo sistemare
    }

    /**
     * Gestisce un log generico <br>
     * Di default usa il livello 'info' <br>
     *
     * @param message da registrare
     */
    public void log(AITypeLog type, String message) {
        //        info(type, message); @todo sistemare
    }

}
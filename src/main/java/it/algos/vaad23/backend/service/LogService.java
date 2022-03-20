package it.algos.vaad23.backend.service;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.boot.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.exception.*;
import it.algos.vaad23.backend.packages.utility.log.*;
import it.algos.vaad23.backend.wrapper.*;
import org.slf4j.Logger;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import javax.annotation.*;
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
 * Diverse modalità di 'uscita' dei logs, regolate da flag: <br>
 * A) nella cartella di log (sempre) <br>
 * B) nella finestra del terminale - sempre in debug - mai in produzione - regolato da flag <br>
 * C) nella collection del database (facoltativo per alcuni programmi) <br>
 * D) in una mail (facoltativo e di norma solo per 'error') <br>
 * <p>
 * Diversi 'livelli' dei logs: debug, info, warn, error <br>
 * <p>
 * Diverse modalità di 'presentazione' (formattazione e incolonnamento) dei logs, regolate da flag: <br>
 * A) Nel log, incolonnare la data, alcuni campi fissi (di larghezza) e poi la descrizione libera <br>
 * ...Se è una multi-company con security, i campi fissi sono: company, utente, IP e type <br>
 * ...Se l' applicazione non è multi-company e non ha security, l' unico campo fisso è il type <br>
 * C) Nella view della collection i campi sono (per i livelli info e warn):
 * ...Multi-company -> livello, type, data, descrizione, company, user
 * ...Single-company -> livello, type, data, descrizione,
 * C) Per i livelli error e debug si aggiungono -> classe, metodo, linea
 * D) Nella mail, invece d'incolonnare i campi fissi, si va a capo <br>
 * <p>
 * I type di log vengono presi dalla enum AETypeLog <br>
 * Il progetto specifico può aggiungere dei type presi da una propria enum <br>
 * Le enum implementano un'interfaccia comune AITypeLog, per poter essere intercambiabili <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class LogService extends AbstractService {


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public LoggerBackend loggerBackend;

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


    public String fixMessageLog(final WrapLogCompany wrap, final String messageIn) {
        String message = messageIn;

        if (wrap != null) {
            message = wrap.getLog() + DOPPIO_SPAZIO + message;
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


    /**
     * Gestisce un log di info <br>
     *
     * @param message di descrizione dell'evento
     */
    public void info(final String message) {
        logBase(AELevelLog.info, AETypeLog.system, false, false, message, null, null);
    }

    /**
     * Gestisce un log di info <br>
     *
     * @param stack info su messaggio e StackTrace
     */
    public void info(final Exception stack) {
        logBase(AELevelLog.info, AETypeLog.system, false, false, VUOTA, stack, null);
    }

    /**
     * Gestisce un log di info <br>
     *
     * @param type    merceologico di specificazione
     * @param message di descrizione dell'evento
     */
    public void info(final AETypeLog type, final String message) {
        logBase(AELevelLog.info, type, false, false, message, null, null);
    }

    /**
     * Gestisce un log di info <br>
     * Facoltativo (flag) su mongoDB <br>
     *
     * @param type    merceologico di specificazione
     * @param message di descrizione dell'evento
     */
    public void infoDb(final AETypeLog type, final String message) {
        logBase(AELevelLog.info, type, true, false, message, null, null);
    }

    /**
     * Gestisce un log di info <br>
     *
     * @param type    merceologico di specificazione
     * @param message di descrizione dell'evento
     * @param wrap    di informazioni su company, userName e address
     */
    public void info(final AETypeLog type, final String message, final WrapLogCompany wrap) {
        logBase(AELevelLog.info, type, true, false, message, null, wrap);
    }

    /**
     * Gestisce un log di info <br>
     *
     * @param type  merceologico di specificazione
     * @param stack info su messaggio e StackTrace
     */
    public void info(final AETypeLog type, final Exception stack) {
        logBase(AELevelLog.info, type, false, false, VUOTA, stack, null);
    }

    /**
     * Gestisce un log di warning <br>
     *
     * @param stack info su messaggio e StackTrace
     */
    public void warn(final Exception stack) {
        logBase(AELevelLog.warn, AETypeLog.system, false, false, VUOTA, stack, null);
    }

    /**
     * Gestisce un log di warning <br>
     * Facoltativo (flag) su mongoDB <br>
     *
     * @param stack info su messaggio e StackTrace
     */
    public void warnDb(final Exception stack) {
        logBase(AELevelLog.warn, AETypeLog.system, true, false, VUOTA, stack, null);
    }

    /**
     * Gestisce un log di warning <br>
     *
     * @param message di descrizione dell'evento
     */
    public void warn(final String message) {
        logBase(AELevelLog.warn, AETypeLog.system, false, false, message, null, null);
    }

    /**
     * Gestisce un log di warning <br>
     *
     * @param type    merceologico di specificazione
     * @param message di descrizione dell'evento
     */
    public void warn(final AETypeLog type, final String message) {
        logBase(AELevelLog.warn, type, false, false, message, null, null);
    }

    /**
     * Gestisce un log di warning <br>
     * Facoltativo (flag) su mongoDB <br>
     *
     * @param type    merceologico di specificazione
     * @param message di descrizione dell'evento
     */
    public void warnDb(final AETypeLog type, final String message) {
        logBase(AELevelLog.warn, type, true, false, message, null, null);
    }


    /**
     * Gestisce un log di warning <br>
     *
     * @param type  merceologico di specificazione
     * @param stack info su messaggio e StackTrace
     */
    public void warn(final AETypeLog type, final Exception stack) {
        logBase(AELevelLog.warn, type, false, false, VUOTA, stack, null);
    }

    /**
     * Gestisce un log di warning <br>
     * Facoltativo (flag) su mongoDB <br>
     *
     * @param type  merceologico di specificazione
     * @param stack info su messaggio e StackTrace
     */
    public void warnDb(final AETypeLog type, final Exception stack) {
        logBase(AELevelLog.warn, type, true, false, VUOTA, stack, null);
    }

    /**
     * Gestisce un log di errore <br>
     *
     * @param stack info su messaggio e StackTrace
     */
    public void error(final Exception stack) {
        logBase(AELevelLog.error, AETypeLog.system, false, false, VUOTA, stack, null);
    }

    /**
     * Gestisce un log di errore <br>
     * Facoltativo (flag) su mongoDB <br>
     *
     * @param stack info su messaggio e StackTrace
     */
    public void errorDb(final Exception stack) {
        logBase(AELevelLog.error, AETypeLog.system, true, false, VUOTA, stack, null);
    }

    /**
     * Gestisce un log di errore <br>
     *
     * @param message di descrizione dell'evento
     */
    public void error(final String message) {
        logBase(AELevelLog.error, AETypeLog.system, false, false, message, null, null);
    }

    /**
     * Gestisce un log di errore <br>
     *
     * @param type    merceologico di specificazione
     * @param message di descrizione dell'evento
     */
    public void error(final AETypeLog type, final String message) {
        logBase(AELevelLog.error, type, false, false, message, null, null);
    }


    /**
     * Gestisce un log di errore <br>
     * Facoltativo (flag) su mongoDB <br>
     *
     * @param type    merceologico di specificazione
     * @param message di descrizione dell'evento
     */
    public void errorDb(final AETypeLog type, final String message) {
        logBase(AELevelLog.error, type, true, false, message, null, null);
    }

    /**
     * Gestisce un log di errore <br>
     *
     * @param type  merceologico di specificazione
     * @param stack info su messaggio e StackTrace
     */
    public void error(final AETypeLog type, final Exception stack) {
        logBase(AELevelLog.error, type, false, false, VUOTA, stack, null);
    }

    /**
     * Gestisce un log di errore <br>
     * Facoltativo (flag) su mongoDB <br>
     *
     * @param type  merceologico di specificazione
     * @param stack info su messaggio e StackTrace
     */
    public void errorDb(final AETypeLog type, final Exception stack) {
        logBase(AELevelLog.error, type, true, false, VUOTA, stack, null);
    }

    /**
     * Gestisce un log di debug <br>
     *
     * @param message di descrizione dell'evento
     */
    public void debug(final String message) {
        logBase(AELevelLog.debug, AETypeLog.system, false, false, message, null, null);
    }


    /**
     * Gestisce tutti i log <br>
     * <p>
     * Sempre su slf4jLogger <br>
     * Facoltativo (flag) su mongoDB <br>
     * Facoltativo (flag) via mail <br>
     * <p>
     * Data evento sempre (automatica)
     * Type e descrizione sempre
     * In caso di errore runTime anche classe, metodo e riga del file che ha generato l'errore
     * In caso di multiCompany anche company, user e IP
     * <p>
     * Formattazioni diverse:
     * ...slf4jLogger (parentesi quadre di larghezza fissa)
     * ...mongoDb (singole colonne per le property)
     * ...mail (a capo ogni property)
     *
     * @param level       tra info,warn,error,debug
     * @param type        merceologico di specificazione
     * @param flagUsaDB   per memorizzare anche su mongoDB e visualizzare in LoggerView
     * @param flagUsaMail per spedire una mail
     * @param descrizione dell'evento in alternativa/aggiunta a quella dell'eccezione
     * @param eccezione   da gestire
     * @param wrap        di informazioni su company, userName e address
     */
    private void logBase(final AELevelLog level,
                         final AETypeLog type,
                         final boolean flagUsaDB,
                         final boolean flagUsaMail,
                         final String descrizione,
                         final Exception eccezione,
                         final WrapLogCompany wrap) {
        String typeText;
        String message = descrizione;
        String company = VUOTA;
        String user = VUOTA;
        String classe = VUOTA;
        String metodo = VUOTA;
        int linea = 0;

        typeText = textService.fixSizeQuadre(type.getTag(), 10);
        message = String.format("%s%s%s", typeText, SPAZIO, message);

        // StackTrace dell'errore
        if (eccezione != null) {
            message = eccezione.getMessage();
            if (eccezione instanceof AlgosException algosException) {
                classe = algosException.getClazz();
                classe = fileService.estraeClasseFinale(classe);
                metodo = algosException.getMethod();
                linea = algosException.getLineNum();
                message = utilityService.getStackTrace(algosException);
            }
            message = String.format("%s%s%s", typeText, SPAZIO, message);
        }

        // mongoDB
        if (flagUsaDB) {
            loggerBackend.crea(level, type, descrizione, company, user, classe, metodo, linea);
        }

        // mail
        if (flagUsaMail) {
        }

        // usaCompany
        if (VaadVar.usaCompany) {
            if (wrap != null) {
                message = fixMessageLog(wrap, message);
            }
        }

        // styling finale
        message = String.format("%s%s", DUE_PUNTI_SPAZIO, message);

        // logback-spring.xml
        switch (level) {
            case info -> slf4jLogger.info(message);
            case warn -> slf4jLogger.warn(message);
            case error -> slf4jLogger.error(message);
            case debug -> slf4jLogger.debug(message);
            default -> slf4jLogger.info(message);
        }
    }

}
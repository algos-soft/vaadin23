package it.algos.vaad23.backend.service;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.wrapper.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import javax.annotation.*;


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


    public static final int PAD_TYPE = 18;

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

}
package it.algos.vaad23.backend.boot;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.exception.*;
import it.algos.vaad23.backend.interfaces.*;
import it.algos.vaad23.backend.packages.utility.log.*;
import it.algos.vaad23.backend.packages.utility.nota.*;
import it.algos.vaad23.backend.packages.utility.preferenza.*;
import it.algos.vaad23.backend.packages.utility.versione.*;
import it.algos.vaad23.backend.service.*;
import it.algos.vaad23.backend.wrapper.*;
import it.algos.vaad23.wizard.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.*;
import org.springframework.core.env.*;

import javax.servlet.*;
import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 06-mar-2022
 * Time: 08:03
 * <p>
 * Running logic after the Spring context has been initialized <br>
 * Executed on container startup, before any browse command <br>
 * Any class that use the @EventListener annotation, will be executed before the application is up and its
 * onContextRefreshEvent method will be called
 * <p>
 * Questa classe astratta riceve un @EventListener dalla sottoclasse concreta alla partenza del programma <br>
 * Deve essere creata una sottoclasse (obbligatoria) per l' applicazione specifica che: <br>
 * 1) regola alcuni parametri standard del database MongoDB <br>
 * 2) regola le variabili generali dell'applicazione <br>
 * 3) crea i dati di alcune collections sul DB mongo <br>
 * 4) crea le preferenze standard e specifiche dell'applicazione <br>
 * 5) aggiunge le @Route (view) standard e specifiche <br>
 * 6) lancia gli schedulers in background <br>
 * 7) costruisce una versione demo <br>
 * 8) controlla l' esistenza di utenti abilitati all' accesso <br>
 */
public abstract class VaadBoot implements ServletContextListener {

    protected boolean allDebugSetup;

    /**
     * Istanza di una interfaccia SpringBoot <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext appContext;

    /**
     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    public AIVers versInstance;

    //    /**
    //     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) <br>
    //     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
    //     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
    //     */
    //    public AIData dataInstance;


    /**
     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    public AIEnumPref prefInstance;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public Environment environment;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected LogService logger;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected MongoService mongoService;


    /**
     * Constructor with @Autowired on setter. Usato quando ci sono sottoclassi. <br>
     * Per evitare di avere nel costruttore tutte le property che devono essere iniettate e per poterle aumentare <br>
     * senza dover modificare i costruttori delle sottoclassi, l'iniezione tramite @Autowired <br>
     * viene delegata ad alcuni metodi setter() che vengono qui invocati con valore (ancora) nullo. <br>
     * Al termine del ciclo init() del costruttore il framework SpringBoot/Vaadin, inietterà la relativa istanza <br>
     */
    public VaadBoot() {
        //        this.setMongo(mongo);
        //        this.setLogger(logger)
        //        this.setEnvironment(environment);
        this.setVersInstance(versInstance);
        //        this.setDataInstance(dataInstance);
        this.setPrefInstance(prefInstance);
    }// end of constructor with @Autowired on setter

    /**
     * The ContextRefreshedEvent happens after both Vaadin and Spring are fully initialized. At the time of this
     * event, the application is ready to service Vaadin requests <br>
     */
    @EventListener(ContextRefreshedEvent.class)
    public void onContextRefreshEvent() {
        this.inizia();
    }

    /**
     * Primo ingresso nel programma <br>
     * <p>
     * 1) regola alcuni parametri standard del database MongoDB <br>
     * 2) regola le variabili generali dell'applicazione <br>
     * 3) crea le preferenze standard e specifiche dell'applicazione <br>
     * 4) crea i dati di alcune collections sul DB mongo <br>
     * 5) aggiunge al menu le @Route (view) standard e specifiche <br>
     * 6) lancia gli schedulers in background <br>
     * 7) costruisce una versione demo <br>
     * 8) controllare l' esistenza di utenti abilitati all' accesso <br>
     * <p>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void inizia() {
        logger.setUpIni();

        this.fixEnvironment();
        this.fixDBMongo();
        this.fixVariabili();
        this.fixVariabiliRiferimentoIstanzeGenerali();
        this.fixPreferenze();
        this.fixMenuRoutes();
        this.fixData();
        this.fixVersioni();
        this.fixLogin();

        logger.setUpEnd();
    }


    /**
     * Controllo di alcune regolazioni
     */
    public void fixEnvironment() {
        String message;
        String databaseName;
        String autoIndexCreation;
        String allDebugSetupTxt;

        if (environment == null) {
            logger.error(new WrapLog().exception(new AlgosException("Manca la property 'environment'")).usaDb());
            return;
        }

        allDebugSetupTxt = environment.getProperty("algos.vaadin23.all.debug.setup");
        if (allDebugSetupTxt != null && allDebugSetupTxt.length() > 0 && allDebugSetupTxt.equals(VERO)) {
            allDebugSetup = true;
        }

        databaseName = environment.getProperty("spring.data.mongodb.database");
        message = String.format("Database mongo in uso: %s", databaseName);
        if (allDebugSetup) {
            logger.info(new WrapLog().message(message).type(AETypeLog.setup));
        }

        autoIndexCreation = environment.getProperty("spring.data.mongodb.auto-index-creation");
        message = String.format("Auto creazione degli indici (per la classi @Document): %s", autoIndexCreation);
        if (allDebugSetup) {
            logger.info(new WrapLog().message(message).type(AETypeLog.setup));
        }
    }

    /**
     * Regola alcuni parametri standard del database MongoDB <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixDBMongo() {
        if (allDebugSetup) {
            mongoService.getMaxBlockingSortBytes();//@todo da controllare
            mongoService.fixMaxBytes();//@todo da controllare
        }
    }

    /**
     * Costruisce alcune istanze generali dell'applicazione e ne mantiene i riferimenti nelle apposite variabili <br>
     * Le istanze (prototype) sono uniche per tutta l' applicazione <br>
     * Vengono create SOLO in questa classe o in una sua sottoclasse <br>
     * La selezione su quale istanza creare tocca a questa sottoclasse xxxBoot <br>
     * Se la sottoclasse non ha creato l'istanza, ci pensa la superclasse <br>
     * Può essere sovrascritto, invocando DOPO il metodo della superclasse <br>
     */
    protected void fixVariabiliRiferimentoIstanzeGenerali() {
        /**
         * Istanza da usare per lo startup del programma <br>
         * Di default VaadData oppure possibile sottoclasse del progetto xxxData <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabiliRiferimentoIstanzeGenerali() del progetto corrente <br>
         */
        VaadVar.istanzaData = VaadVar.istanzaData == null ? appContext.getBean(VaadData.class) : VaadVar.istanzaData;

        /**
         * Classe da usare per gestire le versioni <br>
         * Di default FlowVers oppure possibile sottoclasse del progetto <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        VaadVar.versionClazz = VaadVers.class;
    }

    /**
     * Regola le variabili generali dell' applicazione con il loro valore iniziale di default <br>
     * Le variabili (static) sono uniche per tutta l' applicazione <br>
     * Il loro valore può essere modificato SOLO in questa classe o in una sua sottoclasse <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixVariabili() {

        /**
         * Nome identificativo minuscolo del progetto base vaadin23 <br>
         * Deve essere regolato in backend.boot.VaadBoot.fixVariabili() del progetto base <br>
         */
        VaadVar.projectVaadin23 = PROJECT_VAADIN23;

        /**
         * Nome identificativo minuscolo del modulo base vaad23 <br>
         * Deve essere regolato in backend.boot.VaadBoot.fixVariabili() del progetto base <br>
         */
        VaadVar.moduloVaadin23 = MODULO_VAADIN23;

        /**
         * Nome identificativo minuscolo del modulo dell' applicazione <br>
         * Usato come parte del path delle varie directory <br>
         * Spesso coincide (non obbligatoriamente) con projectNameIdea <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        VaadVar.projectNameModulo = MODULO_VAADIN23;

        /**
         * Lista dei moduli di menu da inserire nel Drawer del MainLayout per le gestione delle @Routes. <br>
         * Regolata dall'applicazione durante l'esecuzione del 'container startup' (non-UI logic) <br>
         * Usata da ALayoutService per conto di MainLayout allo start della UI-logic <br>
         */
        VaadVar.menuRouteList = new ArrayList<>();

        //        /**
        //         * Classe da usare per lo startup del programma <br>
        //         * Di default FlowData oppure possibile sottoclasse del progetto <br>
        //         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() <br>
        //         */
        //        VaadVar.dataClazz = VaadData.class;
        //
        //        /**
        //         * Classe da usare per gestire le versioni <br>
        //         * Di default FlowVers oppure possibile sottoclasse del progetto <br>
        //         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
        //         */
        //        VaadVar.versionClazz = VaadVers.class;

        /**
         * Versione dell' applicazione base vaadflow14 <br>
         * Usato solo internamente <br>
         * Deve essere regolato in backend.boot.VaadBoot.fixVariabili() del progetto corrente <br>
         */
        VaadVar.vaadin23Version = Double.parseDouble(Objects.requireNonNull(environment.getProperty("algos.vaadin23.version")));

        /**
         * Controlla se l' applicazione è multi-company oppure no <br>
         * Di default uguale a false <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         * Se usaCompany=true anche usaSecurity deve essere true <br>
         */
        VaadVar.usaCompany = false;

        /**
         * Controlla se l' applicazione usa il login oppure no <br>
         * Se si usa il login, occorre la classe SecurityConfiguration <br>
         * Se non si usa il login, occorre disabilitare l'Annotation @EnableWebSecurity di SecurityConfiguration <br>
         * Di default uguale a false <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         * Se usaCompany=true anche usaSecurity deve essere true <br>
         * Può essere true anche se usaCompany=false <br>
         */
        VaadVar.usaSecurity = false;
    }

    /**
     * Aggiunge al menu le @Route (view) standard e specifiche <br>
     * Questa classe viene invocata PRIMA della chiamata del browser <br>
     * <p>
     * Nella sottoclasse che invoca questo metodo, aggiunge le @Route (view) specifiche dell' applicazione <br>
     * Le @Route vengono aggiunte ad una Lista statica mantenuta in VaadVar <br>
     * Verranno lette da MainLayout la prima volta che il browser 'chiama' una view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixMenuRoutes() {
        //        VaadVar.menuRouteList.add(HelloWorldView.class);
        //        VaadVar.menuRouteList.add(AboutView.class);
        //        VaadVar.menuRouteList.add(AddressFormView.class);
        //        VaadVar.menuRouteList.add(CarrelloFormView.class);
        //        VaadVar.menuRouteList.add(ContinenteView.class);
        VaadVar.menuRouteList.add(WizardView.class);
//        VaadVar.menuRouteList.add(UtilityView.class);
        VaadVar.menuRouteList.add(NotaView.class);
        VaadVar.menuRouteList.add(VersioneView.class);
        VaadVar.menuRouteList.add(LoggerView.class);
        VaadVar.menuRouteList.add(PreferenzaView.class);
    }


    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    @Qualifier(QUALIFIER_VERSION_VAAD)
    public void setVersInstance(final AIVers versInstance) {
        this.versInstance = versInstance;
    }

    //    /**
    //     * Set con @Autowired di una property chiamata dal costruttore <br>
    //     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) <br>
    //     * Chiamata dal costruttore di questa classe con valore nullo <br>
    //     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
    //     */
    //    @Autowired
    //    @Qualifier(QUALIFIER_DATA_VAAD)
    //    public void setDataInstance(final AIData dataInstance) {
    //        this.dataInstance = dataInstance;
    //    }


    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    @Qualifier(QUALIFIER_PREFERENCES_VAAD)
    public void setPrefInstance(final AIEnumPref prefInstance) {
        this.prefInstance = prefInstance;
    }


    /**
     * Inizializzazione dei database di vaadinFlow <br>
     * Inizializzazione dei database del programma specifico <br>
     */
    protected void fixData() {
        //        this.dataInstance.inizia();
    }

    /**
     * Inizializzazione delle versioni standard di vaadinFlow <br>
     * Inizializzazione delle versioni del programma specifico <br>
     */
    protected void fixVersioni() {
        this.versInstance.inizia();
    }

    /**
     * Inizializzazione delle versioni standard di vaadinFlow <br>
     * Inizializzazione delle versioni del programma specifico <br>
     */
    public void fixPreferenze() {
        this.prefInstance.inizia();
    }

    /**
     * Eventuale collegamento <br>
     * Sviluppato nelle sottoclassi <br>
     */
    public void fixLogin() {
    }


}

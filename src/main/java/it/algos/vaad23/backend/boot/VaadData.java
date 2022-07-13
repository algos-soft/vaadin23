package it.algos.vaad23.backend.boot;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.exception.*;
import it.algos.vaad23.backend.logic.*;
import it.algos.vaad23.backend.service.*;
import it.algos.vaad23.backend.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.*;

import javax.annotation.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 20-ott-2018
 * Time: 08:53
 * <p>
 * Poiché siamo in fase di boot, la sessione non esiste ancora <br>
 * Questo vuol dire che eventuali classi @VaadinSessionScope
 * NON possono essere iniettate automaticamente da Spring <br>
 * Vengono costruite con la BeanFactory <br>
 * <p>
 * Superclasse astratta per la costruzione iniziale delle Collections <br>
 * Viene invocata PRIMA della chiamata del browser, tramite il <br>
 * metodo FlowBoot.onContextRefreshEvent() <br>
 * Crea i dati di alcune collections sul DB mongo <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) <br>
 *
 * @since java 8
 */
@SpringComponent
@Primary
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class VaadData extends AbstractService {

    /**
     * Messaggio di errore <br>
     *
     * @since java 8
     */
    public Runnable mancaPrefLogic = () -> System.out.println("Non ho trovato la classe PreferenzaLogic");

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    //    @Autowired
    //    public AIMongoService mongo;

    //    /**
    //     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
    //     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
    //     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
    //     */
    //    @Autowired
    //    public ArrayService arrayService;
    //
    //    /**
    //     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
    //     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
    //     * al termine del ciclo init() del costruttore di questa classe <br>
    //     */
    //    protected FileService fileService;
    //
    //    /**
    //     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
    //     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
    //     * al termine del ciclo init() del costruttore di questa classe <br>
    //     */
    //    protected TextService textService;
    //
    //    /**
    //     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
    //     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
    //     * al termine del ciclo init() del costruttore di questa classe <br>
    //     */
    //    protected ClassService classService;
    //
    //    /**
    //     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
    //     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
    //     * al termine del ciclo init() del costruttore di questa classe <br>
    //     */
    //    protected LogService logger;
    //
    //    /**
    //     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
    //     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
    //     * al termine del ciclo init() del costruttore di questa classe <br>
    //     */
    //    protected AnnotationService annotationService;

    /**
     * Controlla che la classe sia una Entity <br>
     */
    protected Predicate<String> checkEntity = canonicalName -> classService.isEntity(canonicalName);

    /**
     * Controlla che la Entity estenda AREntity <br>
     */
    //    protected Predicate<Object> checkResetEntity = clazzName -> classService.isResetEntity(clazzName.toString());÷÷@todo rimettere

    /**
     * Controlla che la classe abbia usaBoot=true <br>
     */
    //    protected Predicate<Object> checkUsaBoot = clazzName -> annotationService.usaBoot(clazzName.toString());÷÷@todo rimettere

    /**
     * Controlla che il service abbia il metodo reset() oppure download() <br>
     */
    protected Predicate<Method> esisteMetodo = clazzName -> clazzName.getName().contains("reset") || clazzName.getName().contains("download");


    /**
     * Controlla che il service xxxBackend abbia il metodo resetStartUp() <br>
     * Altrimenti i dati non possono essere ri-creati <br>
     */
    protected Predicate<Object> checkUsaResetStartUp = clazzName -> {
        boolean esiste = false;
        final String tag = "resetStartUp";
        Class clazz = null;
        String nomeMetodo;

        try {
            clazz = Class.forName(clazzName.toString());
        } catch (Exception unErrore) {
            logger.error(new WrapLog().exception(new AlgosException(unErrore)).usaDb());
        }
        if (clazz != null) {
            final Method[] methods = clazz.getDeclaredMethods();

            for (Method metodo : methods) {
                nomeMetodo = metodo.getName();
                if (nomeMetodo.equals(tag)) {
                    esiste = true;
                }
            }
        }

        return esiste;

        //        return Arrays.stream(methods)
        //                .filter(method -> !method.getName().equals(tag))
        //                .count() == 0;
    };

    /**
     * Costruttore senza parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * L'istanza DEVE essere creata con appContext.getBean(VaadData.class); <br>
     * Non utilizzato e non necessario <br>
     * Per evitare il bug (solo visivo), aggiungo un costruttore senza parametri <br>
     */
    public VaadData() {
    }// end of constructor not @Autowired


    /**
     * Performing the initialization in a constructor is not suggested as the state of the UI is not properly set up when the constructor is invoked. <br>
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti <br>
     * L'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     * Se viene implementata una istanza di sottoclasse, passa di qui per ogni istanza <br>
     */
    @PostConstruct
    private void postConstruct() {
        this.resetData();
    }


    /**
     * Check iniziale. A ogni avvio del programma spazzola tutte le collections <br>
     * Ognuna viene ricreata (mantenendo le entities che hanno reset=false) se:
     * - xxx->@AIEntity usaBoot=true,
     * - esiste xxxService.reset(),
     * - la collezione non contiene nessuna entity che abbia la property reset=true
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * L' ordine con cui vengono create le collections è significativo <br>
     *
     * @since java 8
     */
    protected void resetData() {
        resetData(VaadVar.moduloVaadin23);
        resetData(VaadVar.projectCurrent);
    }


    /**
     * Check iniziale. A ogni avvio del programma spazzola tutte le collections <br>
     * Per ogni classe service di tipo xxxBackend esegue (se esistono) i metodi resetStartUp(), download() e reset(),<br>
     * resetStartUp() viene eseguito sempre se e solo se la collection è vuota <br>
     * download() viene eseguito sempre se e solo se la collection è vuota <br>
     * reset() viene eseguito sempre se e solo se la collection non contiene nessuna entity che abbia la property reset=true <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * L' ordine con cui vengono create le collections è significativo <br>
     *
     * @param moduleName da controllare
     */
    protected void resetData(final String moduleName) {
        List<String> allModulePackagesClasses = null;
        List<Object> allBackendClasses = null;
        List<Object> allBackendClassesResetStartUp = null;
        List<Object> allEntityClassesRicreabiliResetDownload = null;
        String message;
        Object[] matrice = null;
        String tagFinale = "/backend/packages";

        //--spazzola tutta la directory package del modulo in esame e recupera
        //--tutte le classi contenute nella directory e nelle sue sottoclassi
        allModulePackagesClasses = fileService.getAllSubFilesJava(PATH_PREFIX + moduleName + tagFinale);

        //--seleziono solo le classi CrudBackend
        allBackendClasses = allModulePackagesClasses
                .stream()
                .filter(n -> n.endsWith(SUFFIX_BACKEND))
                .collect(Collectors.toList());

        //--seleziono solo le classi xxxBackend che implementano il metodo resetStartUp
        allBackendClassesResetStartUp = allBackendClasses
                .stream()
                .filter(checkUsaResetStartUp)
                .collect(Collectors.toList());
        if (allBackendClassesResetStartUp != null && allBackendClassesResetStartUp.size() > 0) {
            message = String.format("In %s sono stati trovati %d packages con classi di tipo xxxBackend", moduleName, allBackendClassesResetStartUp.size());
        }
        else {
            message = String.format("In %s non è stato trovato nessun package con classi di tipo xxxBackend", moduleName);
        }
        logger.info(new WrapLog().message(message).type(AETypeLog.checkData));

        //--esegue il metodo xxxBackend.resetStartUp per tutte le classi che lo implementano
        if (allBackendClassesResetStartUp != null) {
            allBackendClassesResetStartUp
                    .stream()
                    .forEach(bootReset);
            message = String.format("Controllati i dati iniziali di %s", moduleName);
            logger.info(new WrapLog().message(message).type(AETypeLog.checkData));
        }

        //        //--seleziona le Entity classes che hanno @AIEntity usaBoot=true
        //        if (allEntityClasses != null) {
        //            //            allUsaBootEntityClasses = Arrays.asList(allEntityClasses.stream().filter(checkUsaBoot).sorted().toArray());
        //        }
        //        if (arrayService.isAllValid(allUsaBootEntityClasses)) {
        //            message = String.format("In %s sono stati trovati %d packages con classi di tipo AEntity che hanno usaBoot=true", moduleName, allUsaBootEntityClasses.size());
        //        }
        //        else {
        //            message = String.format("In %s non è stato trovato nessun package con classi di tipo AEntity che hanno usaBoot=true", moduleName);
        //        }
        //        logger.info(new WrapLog().message(message).type(AETypeLog.checkData));

        //        //--seleziona le xxxService classes che hanno il metodo reset() oppure download()
        //        if (allUsaBootEntityClasses != null) {
        //            //            allEntityClassesRicreabiliResetDownload = Arrays.asList(allUsaBootEntityClasses.stream().filter(esisteMetodoService).sorted().toArray());
        //        }
        //        if (arrayService.isAllValid(allEntityClassesRicreabiliResetDownload)) {
        //            message = String.format("In %s sono stati trovati %d packages con classi di tipo xxxService che hanno reset() oppure download()", moduleName, allEntityClassesRicreabiliResetDownload.size());
        //        }
        //        else {
        //            message = String.format("In %s non è stato trovato nessun package con classi di tipo xxxService che hanno reset() oppure download()", moduleName);
        //        }
        //        logger.info(new WrapLog().message(message).type(AETypeLog.checkData));

        //        //--elabora le entity classes che hanno il metodo reset() oppure download() e quindi sono ricreabili
        //        //--eseguendo xxxService.bootReset (forEach=elaborazione)
        //        if (allEntityClassesRicreabiliResetDownload != null) {
        //            //            allEntityClassesRicreabiliResetDownload.stream().forEach(bootReset);
        //            message = String.format("Controllati i dati iniziali di %s", moduleName);
        //            logger.info(new WrapLog().message(message).type(AETypeLog.checkData));
        //        }

    }

    /**
     * Controlla che il backend (service) abbia il metodo reset() oppure download() nella sottoclasse specifica xxxBackend <br>
     * Altrimenti i dati non possono essere ri-creati <br>
     */
    protected Predicate<Object> esisteMetodoBackend = clazzName -> {
        final Method[] methods;
        final CrudBackend crudBackend = classService.getBackendFromEntityClazz(clazzName.toString());

        try {
            methods = crudBackend.getClass().getDeclaredMethods();
        } catch (Exception unErrore) {
            return false;
        }

        if (Arrays.stream(methods).filter(esisteMetodo).count() == 0) {
            return false;
        }
        else {
            return true;
        }
    };

    /**
     * Controllo e ricreo (se serve) la singola collezione <br>
     * <p>
     * Costruisco un' istanza della classe xxxBackend corrispondente alla entityClazz <br>
     * Controllo se l' istanza xxxBackend è creabile <br>
     * Un package standard contiene sempre xxxBackend <br>
     */
    protected Consumer<Object> bootReset = canonicalBackend -> {
        final String canonicaBackendName = (String) canonicalBackend;
        int numRec;
        final String tag = "resetStartUp";

        //--controlla se la collection è vuota o piena
        numRec = mongoService.count(canonicaBackendName);
        if (numRec > 0) {
            return;
        }

        try {
            final Class clazz = classService.getClazzFromCanonicalName(canonicaBackendName);
            final Method metodo = clazz.getMethod(tag);
            final Object istanza = appContext.getBean(clazz);
            metodo.invoke(istanza);
        } catch (Exception unErrore) {
            logger.error(new WrapLog().exception(new AlgosException(unErrore)).usaDb());
        }
    };


    /**
     * Check iniziale. Ad ogni avvio del programma spazzola tutte le collections <br>
     * Ognuna viene ricreata (mantenendo le entities che hanno reset=false) se:
     * - xxx->@AIEntity usaBoot=true,
     * - esiste xxxService.reset(),
     * - la collezione non contiene nessuna entity che abbia la property reset=true
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * L' ordine con cui vengono create le collections è significativo <br>
     *
     * @param moduleName da controllare
     *
     * @since java 8
     */
    protected void resetDataOld(final String moduleName) {
        List<String> allModulePackagesClasses = null;
        List<Object> allEntityClasses = null;
        List<Object> allUsaBootEntityClasses = null;
        List<Object> allEntityClassesRicreabiliResetDownload = null;
        String message;
        Object[] matrice = null;

        //--spazzola tutta la directory package del modulo in esame e recupera
        //--tutte le classi contenute nella directory e nelle sue sottoclassi
        try {
            allModulePackagesClasses = fileService.getModuleSubFilesEntity(moduleName);
        } catch (Exception unErrore) {
            logger.error(AETypeLog.file, unErrore);
        }

        //--seleziona le classes che estendono AEntity
        logger.info(new WrapLog().type(AETypeLog.checkData));
        try {
            //            allEntityClasses = Arrays.asList(allModulePackagesClasses.stream().filter(checkEntity).sorted().toArray());÷÷@todo rimettere
        } catch (Exception unErrore) {
            logger.error(AETypeLog.file, unErrore);
        }
        if (arrayService.isAllValid(allEntityClasses)) {
            message = String.format("In %s sono stati trovati %d packages con classi di tipo AEntity", moduleName, allEntityClasses.size());
        }
        else {
            message = String.format("In %s non è stato trovato nessun package con classi di tipo AEntity", moduleName);
        }
        logger.info(new WrapLog().type(AETypeLog.checkData).message(message));

        //        //--seleziona le Entity classes che estendono AREntity
        //        allResetEntityClasses = Arrays.asList(allEntityClasses.stream().filter(checkResetEntity).sorted().toArray());
        //        message = String.format("In %s sono stati trovati %d packages con classi di tipo AREntity da controllare", moduleName, allResetEntityClasses.size() + 1);
        //        logger.log(AETypeLog.checkData, message);

        //--seleziona le Entity classes che hanno @AIEntity usaBoot=true
        if (allEntityClasses != null) {
            //            allUsaBootEntityClasses = Arrays.asList(allEntityClasses.stream().filter(checkUsaBoot).sorted().toArray());÷÷@todo rimettere
        }
        if (arrayService.isAllValid(allUsaBootEntityClasses)) {
            message = String.format("In %s sono stati trovati %d packages con classi di tipo AEntity che hanno usaBoot=true", moduleName, allUsaBootEntityClasses.size());
        }
        else {
            message = String.format("In %s non è stato trovato nessun package con classi di tipo AEntity che hanno usaBoot=true", moduleName);
        }
        logger.info(new WrapLog().type(AETypeLog.checkData).message(message));

        //--seleziona le xxxService classes che hanno il metodo reset() oppure download()
        if (allUsaBootEntityClasses != null) {
            //            allEntityClassesRicreabiliResetDownload = Arrays.asList(allUsaBootEntityClasses.stream().filter(esisteMetodoService).sorted().toArray());÷÷@todo rimettere
        }
        if (arrayService.isAllValid(allEntityClassesRicreabiliResetDownload)) {
            message = String.format("In %s sono stati trovati %d packages con classi di tipo xxxService che hanno reset() oppure download()", moduleName, allEntityClassesRicreabiliResetDownload.size());
        }
        else {
            message = String.format("In %s non è stato trovato nessun package con classi di tipo xxxService che hanno reset() oppure download()", moduleName);
        }
        logger.info(new WrapLog().type(AETypeLog.checkData).message(message));

        //--elabora le entity classes che hanno il metodo reset() oppure download() e quindi sono ricreabili
        //--eseguendo xxxService.bootReset (forEach=elaborazione)
        if (allEntityClassesRicreabiliResetDownload != null) {
            //            allEntityClassesRicreabiliResetDownload.stream().forEach(bootReset); @todo rimettere
            message = String.format("Controllati i dati iniziali di %s", moduleName);
            logger.info(new WrapLog().type(AETypeLog.checkData).message(message));
        }

    }

    //    /**
    //     * Set con @Autowired di una property chiamata dal costruttore <br>
    //     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
    //     * Chiamata dal costruttore di questa classe con valore nullo <br>
    //     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
    //     */
    //    @Autowired
    //    void setFile(final FileService fileService) {
    //        this.fileService = fileService;
    //    }
    //
    //
    //    /**
    //     * Set con @Autowired di una property chiamata dal costruttore <br>
    //     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
    //     * Chiamata dal costruttore di questa classe con valore nullo <br>
    //     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
    //     */
    //    @Autowired
    //    void setText(final TextService textService) {
    //        this.textService = textService;
    //    }
    //
    //    /**
    //     * Set con @Autowired di una property chiamata dal costruttore <br>
    //     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
    //     * Chiamata dal costruttore di questa classe con valore nullo <br>
    //     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
    //     */
    //    @Autowired
    //    void setClassService(final ClassService classService) {
    //        this.classService = classService;
    //    }
    //
    //    /**
    //     * Set con @Autowired di una property chiamata dal costruttore <br>
    //     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
    //     * Chiamata dal costruttore di questa classe con valore nullo <br>
    //     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
    //     */
    //    @Autowired
    //    void setLogger(final LogService logger) {
    //        this.logger = logger;
    //    }
    //
    //    /**
    //     * Set con @Autowired di una property chiamata dal costruttore <br>
    //     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
    //     * Chiamata dal costruttore di questa classe con valore nullo <br>
    //     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
    //     */
    //    @Autowired
    //    void setAnnotation(final AnnotationService annotationService) {
    //        this.annotationService = annotationService;
    //    }

}
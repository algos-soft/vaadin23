package it.algos.vaad23.backend.logic;

import it.algos.vaad23.backend.entity.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: gio, 10-mar-2022
 * Time: 21:02
 * Layer di collegamento del backend con mongoDB <br>
 * Classe astratta di servizio per la Entity di un package <br>
 * Le sottoclassi concrete sono SCOPE_SINGLETON e non mantengono dati <br>
 * L'unico dato mantenuto nelle sottoclassi concrete: la property final entityClazz <br>
 * Se la sottoclasse xxxService non esiste (non è indispensabile), usa la classe generica GenericService; i metodi esistono ma occorre un
 * cast in uscita <br>
 */
public abstract class EntityBackend extends AbstractService {

    /**
     * The Entity Class  (obbligatoria sempre e final)
     */
    protected final Class<? extends AEntity> entityClazz;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ResourceService resourceService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public TextService textService;

    protected MongoRepository crudRepository;

    /**
     * Constructor @Autowired. <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * L' @Autowired (esplicito o implicito) funziona SOLO per UN costruttore <br>
     * Se ci sono DUE o più costruttori, va in errore <br>
     * Se ci sono DUE costruttori, di cui uno senza parametri, inietta quello senza parametri <br>
     */
    public EntityBackend(final MongoRepository crudRepository, final Class<? extends AEntity> entityClazz) {
        this.crudRepository = crudRepository;
        this.entityClazz = entityClazz;
    }// end of constructor with @Autowired


    /**
     * Creazione in memoria di una nuova entityBean che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * Senza properties per compatibilità con la superclasse <br>
     *
     * @return la nuova entityBean appena creata (non salvata)
     */
    public AEntity newEntity() {
        AEntity newEntityBean = null;

        try {
            newEntityBean = entityClazz.getDeclaredConstructor().newInstance();
        } catch (Exception unErrore) {
            logger.warn(AETypeLog.nuovo, unErrore);
        }

        return newEntityBean;
    }

    public List findAll() {
        return crudRepository.findAll();
    }

    public AEntity add(Object objEntity) {
        AEntity entity = (AEntity) objEntity;

        return (AEntity) crudRepository.insert(entity);
    }

    public AEntity update(Object entity) {
        return (AEntity) crudRepository.save(entity);
    }

    public void delete(Object entity) {
        try {
            crudRepository.delete(entity);
        } catch (Exception unErrore) {
            logger.error(unErrore);
        }
    }


    public List findByDescrizioneAndLivelloAndType(final String value, final AENotaLevel level, final AETypeLog type) {
        return null;
    }

    public void reset() {
    }

}

package it.algos.vaad23.backend.packages.utility.note;

import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.logic.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 18-mar-2022
 * Time: 06:55
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
@Qualifier("Nota") //@todo Qualifier da sostituire (eventualmente) con costante da registrare su VaadCost 
//@AIScript(sovraScrivibile = true)
public class NotaBackend extends EntityBackend {

    private NotaRepository repository;

    /**
     * Costruttore @Autowired (facoltativo) @Qualifier (obbligatorio) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Si usa un @Qualifier(), per specificare la classe che incrementa l'interfaccia repository <br>
     * Si usa una costante statica, per essere sicuri di scriverla uguale a quella di xxxRepository <br>
     * Regola la classe di persistenza dei dati specifica e la passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questo service <br>
     *
     * @param crudRepository per la persistenza dei dati
     */
    //@todo registrare eventualmente come costante in VaadCost il valore del Qualifier
    public NotaBackend(@Autowired @Qualifier("Nota") final MongoRepository crudRepository) {
        super(crudRepository, Nota.class);
        this.repository = (NotaRepository) crudRepository;
    }

    @Override
    public Nota add(Object objEntity) {
        if (objEntity instanceof Nota notaEntity) {
            notaEntity.inizio = LocalDate.now();
            notaEntity.livello = notaEntity.livello != null ? notaEntity.livello : AELevelNota.normale;
            notaEntity.type = notaEntity.type != null ? notaEntity.type : AETypeLog.system;

            return (Nota) crudRepository.insert(notaEntity);
        }
        else {
            return null;
        }
    }

    @Override
    public Nota update(Object objEntity) {
        if (objEntity instanceof Nota notaEntity) {
            notaEntity.fine = notaEntity.fine == null && notaEntity.fatto ? LocalDate.now() : null;

            return (Nota) crudRepository.save(notaEntity);
        }
        else {
            return null;
        }

    }

    public int countAll() {
        return repository.findAll().size();
    }

    public List<Nota> findByDescrizione(final String value) {
        return repository.findByDescrizioneContainingIgnoreCase(value);
    }

    public List<Nota> findByLevel(final AELevelNota level) {
        return repository.findByLivello(level);
    }

    public List<Nota> findByType(final AETypeLog type) {
        return repository.findByType(type);
    }

    public List<Nota> findByLivelloAndType(final AELevelNota level, final AETypeLog type) {
        if (level == null) {
            return repository.findByType(type);
        }
        if (type == null) {
            return repository.findByLivello(level);
        }
        return repository.findByLivelloAndType(level, type);
    }

    @Override
    public List<Nota> findByDescrizioneAndLivelloAndType(final String value, final AELevelNota level, final AETypeLog type) {
        if (level != null && type != null) {
            return repository.findByDescrizioneContainingIgnoreCaseAndLivelloAndType(value, level, type);
        }
        if (level != null) {
            return repository.findByDescrizioneContainingIgnoreCaseAndLivello(value, level);
        }
        if (type != null) {
            return repository.findByDescrizioneContainingIgnoreCaseAndType(value, type);
        }
        return repository.findByDescrizioneContainingIgnoreCase(value);
    }

}// end of crud backend class

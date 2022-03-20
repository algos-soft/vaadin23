package it.algos.vaad23.backend.packages.utility.log;

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
 * Date: mer, 16-mar-2022
 * Time: 19:47
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
@Qualifier("Logger") //@todo Qualifier da sostituire (eventualmente) con costante da registrare su VaadCost 
//@AIScript(sovraScrivibile = true)
public class LoggerBackend extends EntityBackend {

    private LoggerRepository repository;

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
    public LoggerBackend(@Autowired @Qualifier("Logger") final MongoRepository crudRepository) {
        super(crudRepository, Logger.class);
        this.repository = (LoggerRepository) crudRepository;
    }

    /**
     * Ordine messo in automatico (progressivo) <br>
     */
    public void crea(final AELevelLog livello, final AETypeLog type, final String descrizione, final String company, final String user,
                     final String classe, final String metodo, final int linea) {
        Logger entity = new Logger();

        entity.livello = livello;
        entity.type = type;
        entity.evento = LocalDateTime.now();
        entity.descrizione = textService.isValid(descrizione) ? descrizione : null;
        entity.company = textService.isValid(company) ? company : null;
        entity.user = textService.isValid(user) ? user : null;
        entity.classe = textService.isValid(classe) ? classe : null;
        entity.metodo = textService.isValid(metodo) ? metodo : null;
        entity.linea = linea;

        try {
            this.add(entity);
        } catch (Exception unErrore) {
            //            logger.error(unErrore);
            System.out.println("errore");
        }
    }

    public List<Logger> findByDescrizioneAndLivelloAndType(final String value, final AELevelLog level, final AETypeLog type) {
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

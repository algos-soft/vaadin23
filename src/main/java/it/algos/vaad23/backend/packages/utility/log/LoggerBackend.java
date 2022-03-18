package it.algos.vaad23.backend.packages.utility.log;

import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.logic.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.time.*;

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
    public void crea(final AELogLevel livello, final AETypeLog type, final String descrizione, final String company, final String user,
                     final String classe, final String metodo, final int linea) {
        Logger entity = new Logger();

        entity.livello = livello;
        entity.type = type;
        entity.evento = LocalDateTime.now();
        entity.descrizione = descrizione;
        entity.company = company;
        entity.user = user;
        entity.classe = classe;
        entity.metodo = metodo;
        entity.linea = linea;

        try {
            this.add(entity);
        } catch (Exception unErrore) {
            //            logger.error(unErrore);
            System.out.println("erore");
        }
    }

}// end of crud backend class

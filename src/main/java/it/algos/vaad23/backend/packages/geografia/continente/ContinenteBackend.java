package it.algos.vaad23.backend.packages.geografia.continente;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.logic.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 13-mar-2022
 * Time: 20:20
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
@Qualifier(TAG_CONTINENTE)
//@AIScript(sovraScrivibile = true)
public class ContinenteBackend extends EntityBackend {

    private ContinenteRepository repository;

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
    public ContinenteBackend(@Autowired @Qualifier(TAG_CONTINENTE) final MongoRepository crudRepository) {
        super(crudRepository, Continente.class);
        this.repository = (ContinenteRepository) crudRepository;
    }

    public void reset() {
        repository.deleteAll();

        //  si può scegliere
        if (false) {
            resetEnumeration();
        }
        else {
            resetCSV();
        }
    }

    public void resetEnumeration() {
        Continente entity;

        for (AEContinente cont : AEContinente.values()) {
            entity = new Continente(cont.getNome().toLowerCase(), cont.getOrd(), cont.getNome(), cont.isAbitato());
            repository.save(entity);
        }
    }

    public void resetCSV() {
        Continente entity;
        Map<String, List<String>> mappa = resourceService.leggeMappaConfigSenzaTitoli("continenti");
        List<String> value;

        for (String key : mappa.keySet()) {
            value = mappa.get(key);
            entity = new Continente(key, Integer.decode(value.get(0)), value.get(1), Boolean.valueOf(value.get(2)));
            repository.save(entity);
        }
    }

}// end of Backend class

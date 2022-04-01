package it.algos.vaad23.backend.packages.crono.mese;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.logic.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: gio, 31-mar-2022
 * Time: 18:41
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
@Qualifier("Mese") //@todo Qualifier da sostituire (eventualmente) con costante da registrare su VaadCost 
//@AIScript(sovraScrivibile = true)
public class MeseBackend extends EntityBackend {

    private MeseRepository repository;

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
    public MeseBackend(@Autowired @Qualifier("Mese") final MongoRepository crudRepository) {
        super(crudRepository, Mese.class);
        this.repository = (MeseRepository) crudRepository;
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Mese newEntity() {
        return newEntity(0, VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param ordine      di presentazione nel popup/combobox (obbligatorio, unico)
     * @param code        (obbligatorio, unico)
     * @param descrizione (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public Mese newEntity(final int ordine, final String code, final String descrizione) {
        return Mese.builder()
                .ordine(ordine)
                .code(textService.isValid(code) ? code : null)
                .descrizione(textService.isValid(descrizione) ? descrizione : null)
                .build();
    }

}// end of crud backend class

package it.algos.vaad23.backend.packages.utility.versione;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.boot.*;
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
 * Date: mer, 09-mar-2022
 * Time: 21:31
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
@Qualifier(TAG_VERSIONE)
public class VersioneBackend extends CrudBackend {

    private VersioneRepository repository;

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
    public VersioneBackend(@Autowired @Qualifier(TAG_VERSIONE) final MongoRepository crudRepository) {
        super(crudRepository, Versione.class);
        this.repository = (VersioneRepository) crudRepository;
    }


    /**
     * Ordine messo in automatico (progressivo) <br>
     */
    public void crea(final String key, final AETypeVers type, final String descrizione, final String company, final boolean riferitoVaadin23) {
        Versione versione = new Versione();
        String tag = " del ";

        versione.id = key;
        versione.ordine = this.nextOrdine();
        versione.type = type;
        versione.release = riferitoVaadin23 ? VaadVar.vaadin23Version : VaadVar.projectVersion;
        versione.titolo = String.format("%s%s%s", versione.release, tag, dateService.get());
        versione.giorno = LocalDate.now();
        versione.descrizione = textService.isValid(descrizione) ? descrizione : null;
        ;
        versione.company = textService.isValid(company) ? company : null;
        versione.vaadin23 = riferitoVaadin23;

        try {
            this.add(versione);
        } catch (Exception unErrore) {
            logger.error(unErrore);
        }
    }

    public int nextOrdine() {
        int nextOrdine = 1;
        List<Versione> listaDiUnSoloElemento = null;
        Versione versione = null;

        try {
            listaDiUnSoloElemento = repository.findFirstVersioneByTitoloIsNotNullOrderByOrdineAsc();
        } catch (Exception unErrore) {
            logger.error(unErrore);
            return nextOrdine;
        }

        if (listaDiUnSoloElemento != null && listaDiUnSoloElemento.size() == 1) {
            versione = listaDiUnSoloElemento.get(0);
        }

        if (versione != null) {
            nextOrdine = versione.getOrdine();
            nextOrdine = nextOrdine + 1;
        }

        return nextOrdine;
    }

    public boolean isEsiste(final String titolo, final String descrizione) {
        Versione versione = null;

        try {
            versione = repository.findFirstByTitoloAndDescrizioneOrderByOrdineAsc(titolo, descrizione);
        } catch (Exception unErrore) {
            logger.error(unErrore);
        }

        return versione != null;
    }

    /**
     * Recupera una istanza della Entity usando la query della property specifica (obbligatoria e unica) <br>
     *
     * @param sigla     del progetto interessato (transient, obbligatorio, un solo carattere) <br>
     * @param newOrdine progressivo della versione (transient, obbligatorio) <br>
     *
     * @return true se trovata
     */
    public boolean isMancaByKeyUnica(final String sigla, final int newOrdine) {
        return findByKeyUnica(getIdKey(sigla, newOrdine)) == null;
    }

    /**
     * Recupera una istanza della Entity usando la query della property specifica (obbligatoria e unica) <br>
     *
     * @param keyCode (obbligatorio, unico)
     *
     * @return istanza della Entity, null se non trovata
     */
    public Versione findByKeyUnica(final String keyCode) {
        Versione entity = null;
        Object optional = repository.findById(keyCode);

        if (((Optional) optional).isPresent()) {
            entity = (Versione) ((Optional) optional).get();
        }

        return entity;
    }

    /**
     * Ordine di presentazione (obbligatorio, unico per ogni project), <br>
     * Viene calcolato in automatico alla creazione della entity <br>
     * Recupera dal DB il valore massimo pre-esistente della property per lo specifico progetto <br>
     * Incrementa di uno il risultato <br>
     *
     * @param sigla     del progetto interessato (transient, obbligatorio, un solo carattere) <br>
     * @param newOrdine progressivo della versione (transient, obbligatorio) <br>
     */
    public String getIdKey(final String sigla, int newOrdine) {
        List<Versione> lista;
        String idKey = "0";

        if (newOrdine == 0) {
            lista = repository.findByIdRegexOrderByOrdineAsc(sigla);
            if (lista != null && lista.size() > 0) {
                idKey = lista.get(0).getId();
                idKey = idKey.substring(1);
                idKey = idKey.startsWith(PUNTO) ? textService.levaTesta(idKey, PUNTO) : idKey;
                idKey = idKey.startsWith(PUNTO) ? textService.levaTesta(idKey, PUNTO) : idKey;//doppio per numeri sopra i 10 e fino a 100
            }

            try {
                newOrdine = Integer.decode(idKey);
                newOrdine++;
            } catch (Exception unErrore) {
                logger.error(unErrore);
            }
        }

        return sigla + newOrdine;
    }

    public List<Versione> findByDescrizioneContainingIgnoreCase(final String value) {
        return repository.findByDescrizioneContainingIgnoreCaseOrderByOrdineAsc(value);
    }

    public List<Versione> findByType(final AETypeVers type) {
        return repository.findByTypeOrderByOrdineAsc(type);
    }

    public List<Versione> findByDescrizioneAndType(final String value, final AETypeVers type) {
        if (type != null) {
            return repository.findByDescrizioneContainingIgnoreCaseAndTypeOrderByOrdineAsc(value, type);
        }
        return repository.findByDescrizioneContainingIgnoreCaseOrderByOrdineAsc(value);
    }


}// end of crud backend class

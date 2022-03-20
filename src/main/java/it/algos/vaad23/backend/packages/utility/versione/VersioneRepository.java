package it.algos.vaad23.backend.packages.utility.versione;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadflow <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 20-set-2019 21.19.40 <br>
 * <br>
 * Estende la l'interfaccia MongoRepository col casting alla Entity relativa di questa repository <br>
 * <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Repository) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Repository) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di collegarsi con la classe xxxBackend <br>
 */
@Repository
@Qualifier(TAG_VERSIONE)
//@AIScript(sovraScrivibile = true)
public interface VersioneRepository extends MongoRepository<Versione, String> {

    @Override
    List<Versione> findAll();

    @Override
    Versione insert(Versione entity);

    @Override
    Versione save(Versione entity);

    @Override
    void delete(Versione entity);

    Versione findFirstByTitoloAndDescrizione(String titolo, String descrizione);

    List<Versione> findFirstVersioneByTitoloIsNotNullOrderByOrdineDesc();

    List<Versione> findByIdRegexOrderByOrdineDesc(String idKey);

    List<Versione> findByDescrizioneContainingIgnoreCase(String descrizione);

    List<Versione> findByType(AETypeVers type);

    List<Versione> findByDescrizioneContainingIgnoreCaseAndType(String descrizione, AETypeVers type);

}// end of crud repository class
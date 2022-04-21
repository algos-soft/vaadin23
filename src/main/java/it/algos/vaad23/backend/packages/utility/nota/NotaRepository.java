package it.algos.vaad23.backend.packages.utility.nota;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import javax.validation.constraints.*;
import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 18-mar-2022
 * Time: 06:55
 * <p>
 * Estende l'interfaccia MongoRepository col casting alla Entity relativa di questa repository <br>
 * <p>
 * Annotated with @Repository (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Eventualmente usare una costante di VaadCost come @Qualifier sia qui che nella corrispondente classe xxxBackend <br>
 */
@Repository
@Qualifier(TAG_NOTA)
public interface NotaRepository extends MongoRepository<Nota, String> {


    @Override
    List<Nota> findAll();

    @Override
    Nota insert(Nota entity);

    @Override
    Nota save(@NotNull Nota entity);

    @Override
    void delete(Nota entity);


    List<Nota> findByDescrizioneContainingIgnoreCaseOrderByInizioDesc(String descrizione);

    List<Nota> findByLivelloOrderByInizioDesc(AENotaLevel level);

    List<Nota> findByTypeOrderByInizioDesc(AETypeLog type);

    List<Nota> findByLivelloAndTypeOrderByInizioDesc(AENotaLevel level, AETypeLog type);

    List<Nota> findByDescrizioneContainingIgnoreCaseAndLivelloOrderByInizioDesc(String descrizione, AENotaLevel level);

    List<Nota> findByDescrizioneContainingIgnoreCaseAndTypeOrderByInizioDesc(String descrizione, AETypeLog type);

    List<Nota> findByDescrizioneContainingIgnoreCaseAndLivelloAndTypeOrderByInizioDesc(String descrizione, AENotaLevel level, AETypeLog type);

}// end of crud repository class
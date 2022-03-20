package it.algos.vaad23.backend.packages.utility.note;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad23.backend.entity.*;
import it.algos.vaad23.backend.enumeration.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 18-mar-2022
 * Time: 06:55
 * <p>
 * Estende la l'interfaccia MongoRepository col casting alla Entity relativa di questa repository <br>
 * <br>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier("Nota")
@Repository
public interface NotaRepository extends MongoRepository<Nota, String> {

    List<Nota> findAll();

    <Nota extends AEntity> Nota insert(Nota entity);

    <Nota extends AEntity> Nota save(Nota entity);

    void delete(Nota entity);


    List<Nota> findByDescrizioneContainingIgnoreCase(String descrizione);

    List<Nota> findByLivello(AELevelNota level);

    List<Nota> findByType(AETypeLog type);

    List<Nota> findByLivelloAndType(AELevelNota level, AETypeLog type);

    List<Nota> findByDescrizioneContainingIgnoreCaseAndLivello(String descrizione, AELevelNota level);

    List<Nota> findByDescrizioneContainingIgnoreCaseAndType(String descrizione, AETypeLog type);

    List<Nota> findByDescrizioneContainingIgnoreCaseAndLivelloAndType(String descrizione, AELevelNota level, AETypeLog type);

}// end of crud repository class
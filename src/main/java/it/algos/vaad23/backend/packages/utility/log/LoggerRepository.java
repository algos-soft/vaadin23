package it.algos.vaad23.backend.packages.utility.log;

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
 * Date: mer, 16-mar-2022
 * Time: 19:47
 * <p>
 * Estende la l'interfaccia MongoRepository col casting alla Entity relativa di questa repository <br>
 * <br>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier("Logger")
@Repository
public interface LoggerRepository extends MongoRepository<Logger, String> {

    List<Logger> findAll();

    <Logger extends AEntity> Logger insert(Logger entity);

    <Logger extends AEntity> Logger save(Logger entity);

    void delete(Logger entity);

    List<Logger> findByDescrizioneContainingIgnoreCaseAndLivello(String descrizione, AELevelLog level);

    List<Logger> findByDescrizioneContainingIgnoreCaseAndType(String descrizione, AETypeLog type);

    List<Logger> findByDescrizioneContainingIgnoreCaseAndLivelloAndType(String descrizione, AELevelLog level, AETypeLog type);

    List<Logger> findByDescrizioneContainingIgnoreCase(String descrizione);

}// end of crud repository class
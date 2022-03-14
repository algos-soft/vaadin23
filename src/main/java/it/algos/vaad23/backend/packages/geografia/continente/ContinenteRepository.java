package it.algos.vaad23.backend.packages.geografia.continente;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 13-mar-2022
 * Time: 10:27
 * <p>
 * Estende la l'interfaccia MongoRepository col casting alla Entity relativa di questa repository <br>
 * <br>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 */
@Repository
@Qualifier(TAG_CONTINENTE)
public interface ContinenteRepository extends MongoRepository<Continente, String> {

    List<Continente> findAll();

    <Continente extends AEntity> Continente insert(Continente entity);

    <Continente extends AEntity> Continente save(Continente entity);

    void delete(Continente entity);

}// end of class
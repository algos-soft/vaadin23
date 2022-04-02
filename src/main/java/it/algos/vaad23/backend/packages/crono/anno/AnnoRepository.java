package it.algos.vaad23.backend.packages.crono.anno;

import it.algos.vaad23.backend.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: sab, 02-apr-2022
 * Time: 11:28
 * <p>
 * Estende l'interfaccia MongoRepository col casting alla Entity relativa di questa repository <br>
 * <p>
 * Annotated with @Repository (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Eventualmente usare una costante di VaadCost come @Qualifier sia qui che nella corrispondente classe xxxBackend <br>
 */
@Repository
@Qualifier("Anno")
public interface AnnoRepository extends MongoRepository<Anno, String> {

    @Override
    List<Anno> findAll();

    <Anno extends AEntity> Anno insert(Anno entity);

    <Anno extends AEntity> Anno save(Anno entity);

    @Override
    void delete(Anno entity);

}// end of crud repository class
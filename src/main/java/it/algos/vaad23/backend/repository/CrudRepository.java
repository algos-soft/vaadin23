package it.algos.vaad23.backend.repository;

import org.springframework.context.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 03-apr-2022
 * Time: 08:39
 * <p>
 * Estende l'interfaccia MongoRepository col casting alla Entity relativa di questa repository <br>
 * <p>
 * Annotated with @Repository (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Eventualmente usare una costante di VaadCost come @Qualifier sia qui che nella corrispondente classe xxxBackend <br>
 */
@Repository
@Primary
public interface CrudRepository<T, ID> extends MongoRepository<T, ID> {

    @Override
    List<T> findAll();

    List<T> findAll(Sort sort);

    <S extends T> S insert(S entity);

    <S extends T> S save(S entity);

    @Override
    void delete(T entity);

}// end of crud repository class
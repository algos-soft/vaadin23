package it.algos.vaad23.backend.packages.crono.mese;

import it.algos.vaad23.backend.entity.*;
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
 * Date: gio, 31-mar-2022
 * Time: 18:41
 * <p>
 * Estende l'interfaccia MongoRepository col casting alla Entity relativa di questa repository <br>
 * <br>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 */
@Repository
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier("Mese")
public interface MeseRepository extends MongoRepository<Mese, String> {

    @Override
    List<Mese> findAll();

    <Mese extends AEntity> Mese insert(Mese entity);

    <Mese extends AEntity> Mese save(Mese entity);

    @Override
    void delete(Mese entity);

}// end of crud repository class
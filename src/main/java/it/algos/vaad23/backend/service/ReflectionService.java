package it.algos.vaad23.backend.service;

import it.algos.vaad23.backend.exception.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import java.lang.reflect.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mer, 09-mar-2022
 * Time: 21:13
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(ReflectionService.class); <br>
 * 3) @Autowired public ReflectionService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ReflectionService extends AbstractService {

    /**
     * Field statico di una classe generica. <br>
     *
     * @param genericClazz    da cui estrarre il field statico
     * @param publicFieldName property statica e pubblica
     *
     * @return the field
     */
    public Field getField(final Class<?> genericClazz, final String publicFieldName) throws AlgosException {
        return null;
    }

}
package it.algos.vaad23.backend.service;

import org.springframework.beans.factory.annotation.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 06-mar-2022
 * Time: 18:31
 * <p>
 * Superclasse astratta delle librerie xxxService. <br>
 * Serve per 'dichiarare' in un posto solo i riferimenti ad altre classi ed usarli nelle sottoclassi concrete <br>
 * I riferimenti sono 'public' (e non protected) per poterli usare con TestUnit <br>
 * <p>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AxxxService.class); <br>
 * 3) @Autowired public AxxxService annotation; <br>
 * <p>
 * La sottoclasse è annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * La sottoclasse NON è annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * La sottoclasse è annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
public abstract class AbstractService {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public TextService textService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AnnotationService annotationService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ReflectionService reflectionService;

}
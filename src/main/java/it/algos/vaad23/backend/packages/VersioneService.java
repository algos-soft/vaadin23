package it.algos.vaad23.backend.packages;

import it.algos.vaad23.backend.logic.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mer, 09-mar-2022
 * Time: 21:31
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 */
@Service
@Qualifier("VersioneService")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
//@AIScript(sovraScrivibile = true)
public class VersioneService extends EntityService {


    /**
     * Versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


    /**
     * Costruttore senza parametri <br>
     * Regola la entityClazz (final) associata a questo service <br>
     */
    public VersioneService() {
        super(Versione.class);
    }


}// end of Singleton class

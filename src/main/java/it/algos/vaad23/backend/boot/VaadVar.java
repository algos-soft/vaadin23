package it.algos.vaad23.backend.boot;

import com.vaadin.flow.component.*;
import com.vaadin.flow.spring.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 06-mar-2022
 * Time: 07:19
 * <p>
 * Classe statica (astratta) per le variabili generali dell'applicazione <br>
 * Le variabili (static) sono uniche per tutta l'applicazione <br>
 * Il valore delle variabili è unico per tutta l'applicazione, ma può essere modificato <br>
 * The compiler automatically initializes class fields to their default values before setting them with any
 * initialization values, so there is no need to explicitly set a field to its default value. <br>
 * Further, under the logic that cleaner code is better code, it's considered poor style to do so. <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class VaadVar {

    /**
     * Lista dei moduli di menu da inserire nel Drawer del MainLayout per le gestione delle @Routes. <br>
     * Regolata dall' applicazione durante l' esecuzione del 'container startup' (non-UI logic) <br>
     * Usata da ALayoutService per conto di MainLayout allo start della UI-logic <br>
     */
    public static List<Class<? extends Component>> menuRouteList;


    /**
     * Nome identificativo minuscolo dell' applicazione nella directory dei projects Idea <br>
     * Usato come base per costruire i path delle varie directory <br>
     * Spesso coincide (non obbligatoriamente) con projectNameModulo <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
     */
    public static String projectNameDirectoryIdea;

    /**
     * Nome identificativo minuscolo del modulo dell' applicazione <br>
     * Usato come parte del path delle varie directory <br>
     * Spesso coincide (non obbligatoriamente) con projectNameIdea <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
     */
    public static String projectNameModulo;

    /**
     * Nome identificativo maiuscolo dell' applicazione <br>
     * Usato (eventualmente) nella barra di menu in testa pagina <br>
     * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
     */
    public static String projectNameUpper;


    /**
     * Classe da usare per lo startup del programma <br>
     * Di default FlowData oppure possibile sottoclasse del progetto <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
     */
    public static Class dataClazz;


    /**
     * Classe da usare per gestire le versioni <br>
     * Di default FlowVers oppure possibile sottoclasse del progetto <br>
     * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
     */
    public static Class versionClazz;

}

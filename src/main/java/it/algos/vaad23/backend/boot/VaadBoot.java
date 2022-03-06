package it.algos.vaad23.backend.boot;

import com.vaadin.flow.spring.annotation.*;
import it.algos.application.views.addressform.*;
import it.algos.application.views.helloworld.*;
import it.algos.simple.ui.views.about.*;
import it.algos.simple.ui.views.carrelloform.*;
import it.algos.vaad23.ui.service.*;
import it.algos.vaad23.ui.views.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.*;

import javax.servlet.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 06-mar-2022
 * Time: 08:03
 * <p>
 * Running logic after the Spring context has been initialized <br>
 * Executed on container startup, before any browse command <br>
 * Any class that use the @EventListener annotation, will be executed before the application is up and its
 * onContextRefreshEvent method will be called
 * <p>
 * Questa classe riceve un @EventListener alla partenza del programma <br>
 * Deve essere creata una sottoclasse per l' applicazione specifica che: <br>
 * 1) regola alcuni parametri standard del database MongoDB <br>
 * 2) regola le variabili generali dell'applicazione <br>
 * 3) crea i dati di alcune collections sul DB mongo <br>
 * 4) crea le preferenze standard e specifiche dell'applicazione <br>
 * 5) aggiunge le @Route (view) standard e specifiche <br>
 * 6) lancia gli schedulers in background <br>
 * 7) costruisce una versione demo <br>
 * 8) controlla l' esistenza di utenti abilitati all' accesso <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class VaadBoot implements ServletContextListener {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    private LayoutService layoutService;

    /**
     * The ContextRefreshedEvent happens after both Vaadin and Spring are fully initialized. At the time of this
     * event, the application is ready to service Vaadin requests <br>
     */
    @EventListener(ContextRefreshedEvent.class)
    public void onContextRefreshEvent() {
        this.inizia();
    }

    /**
     * Primo ingresso nel programma <br>
     * <p>
     * 1) regola alcuni parametri standard del database MongoDB <br>
     * 2) regola le variabili generali dell'applicazione <br>
     * 3) crea le preferenze standard e specifiche dell'applicazione <br>
     * 4) crea i dati di alcune collections sul DB mongo <br>
     * 5) aggiunge al menu le @Route (view) standard e specifiche <br>
     * 6) lancia gli schedulers in background <br>
     * 7) costruisce una versione demo <br>
     * 8) controllare l' esistenza di utenti abilitati all' accesso <br>
     * <p>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void inizia() {
        //        this.createMenuItems();
    }

    /**
     * Aggiunge al menu le @Route (view) standard e specifiche <br>
     * <p>
     * Questa classe viene invocata PRIMA della chiamata del browser <br>
     * Se NON usa la security, le @Route vengono create solo qui <br>
     * Se USA la security, le @Route vengono sovrascritte all' apertura del browser nella classe AUserDetailsService <br>
     * <p>
     * Nella sottoclasse che invoca questo metodo, aggiunge le @Route (view) specifiche dell' applicazione <br>
     * Le @Route vengono aggiunte ad una Lista statica mantenuta in VaadVar <br>
     * Verranno lette da MainLayout la prima volta che il browser 'chiama' una view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected MenuItemInfo2[] createMenuItems() {
        return new MenuItemInfo2[]{ //
                new MenuItemInfo2("Hello World", "la la-globe", HelloWorldView.class), //

                new MenuItemInfo2("About", "la la-file", AboutView.class), //

                new MenuItemInfo2("Address Form", "la la-map-marker", AddressFormView.class), //

                new MenuItemInfo2("Carrello Form", "la la-credit-card", CarrelloFormView.class), //

        };

    }

}

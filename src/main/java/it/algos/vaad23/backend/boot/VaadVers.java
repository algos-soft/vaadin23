package it.algos.vaad23.backend.boot;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.interfaces.*;
import it.algos.vaad23.backend.packages.versione.*;
import it.algos.vaad23.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mar, 08-feb-2022
 * Time: 16:45
 * Log delle versioni, modifiche e patch installate <br>
 * <p>
 * Executed on container startup <br>
 * Setup non-UI logic here <br>
 * Classe eseguita solo quando l'applicazione viene caricata/parte nel server (Tomcat o altri) <br>
 * Eseguita quindi a ogni avvio/riavvio del server e NON a ogni sessione <br>
 */
@SpringComponent
@Qualifier(TAG_FLOW_VERSION)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class VaadVers implements AIVers {


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected LogService logger;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected VersioneBackend backend;

    /**
     * This method is called prior to the servlet context being initialized (when the Web application is deployed). <br>
     * You can initialize servlet context related data here. <br>
     * <p>
     * Tutte le aggiunte, modifiche e patch vengono inserite con una versione <br>
     * L'ordine di inserimento è FONDAMENTALE <br>
     */
    public void inizia() {
        //--prima installazione del progetto base VaadFlow14
        //--non fa nulla, solo informativo
        this.flow(AETypeVers.setup, "Setup", "Installazione iniziale di VaadFlow");
    }

    /**
     * Inserimento di una versione del progetto base VaadFlow14 <br>
     */
    protected void flow(final AETypeVers type, final String code, final String descrizione) {
        this.crea(type, code, descrizione, true, false);
    }


    /**
     * Inserimento di una versione del progetto specifico in esecuzione <br>
     */
    protected void specifico(final AETypeVers type, final String code, final String descrizione) {
        this.crea(type, code, descrizione, false, false);
    }


    /**
     * Inserimento di una versione del progetto base VaadFlow14 <br>
     * Controlla che la entity non esista già <br>
     */
    protected void crea(final AETypeVers type, final String titolo, final String descrizione, final boolean usaBase, final boolean usaCompany) {
        Versione versione = new Versione();
        versione.id = "pippoz";
        versione.type = type;
        versione.titolo = titolo;
        versione.descrizione = descrizione;
        versione.usaBase = usaBase;
        versione.usaCompany = usaCompany;
        try {
            backend.add(versione);
        } catch (Exception unErrore) {
            logger.error(unErrore);
        }

    }

}

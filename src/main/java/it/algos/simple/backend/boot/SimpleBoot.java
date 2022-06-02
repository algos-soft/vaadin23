package it.algos.simple.backend.boot;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.simple.backend.boot.SimpleCost.*;
import it.algos.vaad23.backend.boot.*;
import it.algos.vaad23.backend.interfaces.*;
import it.algos.vaad23.backend.packages.anagrafica.*;
import it.algos.vaad23.backend.packages.crono.anno.*;
import it.algos.vaad23.backend.packages.crono.giorno.*;
import it.algos.vaad23.backend.packages.crono.mese.*;
import it.algos.vaad23.backend.packages.crono.secolo.*;
import it.algos.vaad23.backend.packages.geografia.continente.*;
import it.algos.vaad23.backend.service.*;
import it.algos.vaad23.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mar, 15-mar-2022
 * Time: 09:36
 * <p>
 * Questa classe astratta riceve un @EventListener implementato nella superclasse <br>
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
public class SimpleBoot extends VaadBoot {

    private String property;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public LogService logger;

    /**
     * Regola le variabili generali dell' applicazione con il loro valore iniziale di default <br>
     * Le variabili (static) sono uniche per tutta l' applicazione <br>
     * Il loro valore può essere modificato SOLO in questa classe o in una sua sottoclasse <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixVariabili() {
        super.fixVariabili();

        /**
         * Nome identificativo minuscolo del progetto corrente <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        VaadVar.projectCurrent = "simple";

        /**
         * Nome identificativo maiuscolo dell' applicazione <br>
         * Usato (eventualmente) nella barra di menu in testa pagina <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        VaadVar.projectNameUpper = "Simple";

        /**
         * Classe da usare per gestire le versioni <br>
         * Di default FlowVers oppure possibile sottoclasse del progetto <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        VaadVar.versionClazz = SimpleVers.class;

        /**
         * Versione dell' applicazione <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        try {
            property = "algos.simple.version";
            VaadVar.projectVersion = Double.parseDouble(Objects.requireNonNull(environment.getProperty(property)));
        } catch (Exception unErrore) {
            String message = String.format("Non ho trovato la property %s nelle risorse", property);
            logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
        }

        /**
         * Data di rilascio della versione <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        try {
            property = "algos.simple.version.date";
            VaadVar.projectDate = Objects.requireNonNull(environment.getProperty(property));
        } catch (Exception unErrore) {
            String message = String.format("Non ho trovato la property %s nelle risorse", property);
            logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
        }
    }

    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    @Qualifier(TAG_SIMPLE_VERSION)
    public void setVersInstance(final AIVers versInstance) {
        this.versInstance = versInstance;
    }

    @Autowired
    @Qualifier(TAG_SIMPLE_PREFERENCES)
    public void setPrefInstance(final AIEnumPref prefInstance) {
        this.prefInstance = prefInstance;
    }

    @Override
    protected void fixMenuRoutes() {
        super.fixMenuRoutes();

        VaadVar.menuRouteList.add(ContinenteView.class);
        VaadVar.menuRouteList.add(MeseView.class);
        VaadVar.menuRouteList.add(SecoloView.class);
        VaadVar.menuRouteList.add(GiornoView.class);
        VaadVar.menuRouteList.add(AnnoView.class);
        VaadVar.menuRouteList.add(ViaView.class);
    }

}

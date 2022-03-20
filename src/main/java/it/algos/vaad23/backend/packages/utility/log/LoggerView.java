package it.algos.vaad23.backend.packages.utility.log;

import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.data.provider.*;
import com.vaadin.flow.router.*;
import it.algos.vaad23.backend.boot.*;
import it.algos.vaad23.ui.views.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mer, 16-mar-2022
 * Time: 19:47
 * <p>
 */
@PageTitle("Logger")
@Route(value = "logger", layout = MainLayout.class)
public class LoggerView extends CrudView {


    //--per eventuali metodi specifici
    private LoggerBackend backend;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Si usa un @Qualifier(), per specificare la classe che incrementa l'interfaccia repository <br>
     * Si usa una costante statica, per essere sicuri di scriverla uguale a quella di xxxRepository <br>
     * Regola il service specifico di persistenza dei dati e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public LoggerView(@Autowired final LoggerBackend crudBackend) {
        super(crudBackend, Logger.class, false);
        this.backend = crudBackend;
    }

    /**
     * Preferenze usate da questa view <br>
     * Primo metodo chiamato dopo AfterNavigationEvent <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixPreferenze() {
        super.fixPreferenze();

        this.usaBottoneFilter = true;
        this.usaComboLivello = true;
        this.usaComboType = true;
    }

    /**
     * Regola la visibilità delle colonne della grid <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixVisibilitaColumns() {
        gridCrud.getGrid().setColumns("livello", "type", "evento", "descrizione", "company", "user", "classe", "metodo", "linea");

        String larLevel = "5em";
        String larType = "9em";
        String larEvento = "15em";
        String larDesc = "30em";
        String larCompany = "8em";
        String larClasse = "12em";
        String larMetodo = "12em";
        String larLinea = "5em";

        gridCrud.getGrid().getColumnByKey("livello").setWidth(larLevel).setFlexGrow(0).setHeader("#");
        gridCrud.getGrid().getColumnByKey("type").setWidth(larType).setFlexGrow(0);
        gridCrud.getGrid().getColumnByKey("evento").setWidth(larEvento).setFlexGrow(0);
        gridCrud.getGrid().getColumnByKey("descrizione").setWidth(larDesc).setFlexGrow(1);
        gridCrud.getGrid().getColumnByKey("company").setWidth(larCompany).setFlexGrow(0);
        gridCrud.getGrid().getColumnByKey("user").setWidth(larCompany).setFlexGrow(0);
        gridCrud.getGrid().getColumnByKey("classe").setWidth(larClasse).setFlexGrow(0);
        gridCrud.getGrid().getColumnByKey("metodo").setWidth(larMetodo).setFlexGrow(0);
        gridCrud.getGrid().getColumnByKey("linea").setHeader("Riga").setWidth(larLinea).setFlexGrow(0);

        if (!VaadVar.usaCompany) {
            gridCrud.getGrid().getColumnByKey("company").setVisible(false);
            gridCrud.getGrid().getColumnByKey("user").setVisible(false);
        }
    }

    /**
     * Regola l'ordinamento della <grid <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixOrdinamento() {
        super.fixOrdinamento();
        Grid.Column column = grid.getColumnByKey("evento");
        List<GridSortOrder> lista = new ArrayList<>();
        lista.add(new GridSortOrder(column, SortDirection.DESCENDING));
        gridCrud.getGrid().sort(lista);
    }

}// end of crud @Route view class
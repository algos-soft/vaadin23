package it.algos.vaad23.backend.packages.utility.log;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.data.provider.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.boot.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.ui.views.*;
import org.springframework.beans.factory.annotation.*;
import org.vaadin.crudui.crud.*;

import java.time.format.*;
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

    private ComboBox comboLivello;

    private ComboBox comboTypeLog;

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

        this.usaBottoneDeleteAll = true;
        this.usaBottoneFilter = true;
    }

    /**
     * Qui va tutta la logica iniziale della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void initView() {
        super.initView();

        gridCrud.setAddOperationVisible(false);
        gridCrud.setUpdateOperationVisible(false);
        gridCrud.setDeleteOperationVisible(false);

    }

    /**
     * Qui vanno i collegamenti con la logica del backend <br>
     * logic configuration <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void backendLogic() {
        super.backendLogic();

        gridCrud.setAddOperationVisible(false);
        gridCrud.setUpdateOperationVisible(true);
        gridCrud.setDeleteOperationVisible(false);
    }

    /**
     * Regola la visibilità delle colonne della grid <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixVisibilitaColumns() {
        super.fixVisibilitaColumns();

        grid.setColumns("livello", "type", "descrizione", "company", "user", "classe", "metodo", "linea");

        String larLevel = "5em";
        String larType = "9em";
        String larEvento = "15em";
        String larDesc = "30em";
        String larCompany = "8em";
        String larClasse = "12em";
        String larMetodo = "12em";
        String larLinea = "5em";

        grid.getColumnByKey("livello").setWidth(larLevel).setFlexGrow(0).setHeader("#");
        grid.getColumnByKey("type").setWidth(larType).setFlexGrow(0);
        grid.getColumnByKey("descrizione").setWidth(larDesc).setFlexGrow(1);
        grid.getColumnByKey("company").setWidth(larCompany).setFlexGrow(0).setVisible(VaadVar.usaCompany);
        grid.getColumnByKey("user").setWidth(larCompany).setFlexGrow(0).setVisible(VaadVar.usaCompany);
        ;
        grid.getColumnByKey("classe").setWidth(larClasse).setFlexGrow(0);
        grid.getColumnByKey("metodo").setWidth(larMetodo).setFlexGrow(0);
        grid.getColumnByKey("linea").setHeader("Riga").setWidth(larLinea).setFlexGrow(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yy HH:mm:ss");
        grid.addColumn(bean -> formatter.format(((Logger) bean).getEvento())).setKey("evento").setHeader("Evento").setWidth(larEvento).setFlexGrow(0);
    }

    /**
     * Regola la visibilità dei fields del Form<br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixVisibilitaFields() {
        super.fixVisibilitaFields();

        if (VaadVar.usaCompany) {
            crudForm.setVisibleProperties(CrudOperation.READ, "livello", "type", "descrizione", "company", "user", "classe", "metodo", "linea", "evento");
            crudForm.setVisibleProperties(CrudOperation.UPDATE, "livello", "type", "descrizione", "company", "user", "classe", "metodo", "linea", "evento");
        }
        else {
            crudForm.setVisibleProperties(CrudOperation.READ, "livello", "type", "descrizione", "classe", "metodo", "linea", "evento");
            crudForm.setVisibleProperties(CrudOperation.UPDATE, "livello", "type", "descrizione", "classe", "metodo", "linea", "evento");
        }

        crudForm.setFieldType("descrizione", TextArea.class);

        //        crudForm.setFieldProvider("evento", logger -> {
        //            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yy HH:mm:ss");
        //            TextField field = new TextField("Evento");
        //            field.setValue(((Logger) logger).evento.format(formatter));
        //            return field;
        //        });
    }

    /**
     * Regola l'ordinamento della <grid <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    @Override
    public void fixOrdinamento() {
        Grid.Column column = grid.getColumnByKey("evento");
        List<GridSortOrder> lista = new ArrayList<>();
        lista.add(new GridSortOrder(column, SortDirection.DESCENDING));
        gridCrud.getGrid().sort(lista);
    }

    /**
     * Componenti aggiuntivi oltre quelli base <br>
     * Tipicamente bottoni di selezione/filtro <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixAdditionalComponents() {
        super.fixAdditionalComponents();

        comboLivello = new ComboBox();
        comboLivello.setPlaceholder("Livello");
        comboLivello.setClearButtonVisible(true);
        List items = AELevelLog.getAll();
        comboLivello.setItems(items);
        gridCrud.getCrudLayout().addFilterComponent(comboLivello);
        comboLivello.addValueChangeListener(event -> sincroFiltri());

        comboTypeLog = new ComboBox();
        comboTypeLog.setPlaceholder("Type");
        comboTypeLog.setClearButtonVisible(true);
        List items2 = AETypeLog.getAll();
        comboTypeLog.setItems(items2);
        gridCrud.getCrudLayout().addFilterComponent(comboTypeLog);
        comboTypeLog.addValueChangeListener(event -> sincroFiltri());
    }


    /**
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected List sincroFiltri() {
        List items = null;
        String textSearch = VUOTA;
        AELevelLog level = null;
        AETypeLog type = null;

        if (usaBottoneFilter && filter != null) {
            textSearch = filter != null ? filter.getValue() : VUOTA;
            items = backend.findByDescrizione(textSearch);
        }

        if (comboLivello != null) {
            level = (AELevelLog) comboLivello.getValue();
        }

        if (comboTypeLog != null) {
            type = (AETypeLog) comboTypeLog.getValue();
        }

        if (usaBottoneFilter) {
            items = backend.findByDescrizioneAndLivelloAndType(textSearch, level, type);
        }

        if (items != null) {
            gridCrud.getGrid().setItems(items);
        }

        return items;
    }

}// end of crud @Route view class
package it.algos.vaad23.ui.views;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.logic.*;
import org.vaadin.crudui.crud.impl.*;
import org.vaadin.crudui.form.*;
import org.vaadin.crudui.layout.impl.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 20-mar-2022
 * Time: 11:05
 */
public abstract class CrudView extends VerticalLayout implements AfterNavigationObserver {

    protected EntityBackend crudBackend;

    protected GridCrud gridCrud;

    protected TextField filter;

    protected ComboBox comboLivello;

    protected ComboBox comboType;

    protected boolean usaBottoneFilter;

    protected boolean usaComboLivello;

    protected boolean usaComboType;

    protected Grid grid;

    protected CrudFormFactory crudForm;

    public CrudView(EntityBackend crudBackend, Class entityClazz, boolean splitLayout) {
        this.crudBackend = crudBackend;

        // crud instance
        if (splitLayout) {
            gridCrud = new GridCrud<>(entityClazz, new HorizontalSplitCrudLayout());
        }
        else {
            gridCrud = new GridCrud<>(entityClazz);
        }

        // grid configuration
        gridCrud.getCrudFormFactory().setUseBeanValidation(true);

        // logic configuration
        gridCrud.setFindAllOperation(() -> crudBackend.findAll());
        gridCrud.setAddOperation(crudBackend::add);
        gridCrud.setUpdateOperation(crudBackend::update);
        gridCrud.setDeleteOperation(crudBackend::delete);

        grid = gridCrud.getGrid();
        crudForm = gridCrud.getCrudFormFactory();

        // layout configuration
        setSizeFull();
        this.add(gridCrud);
    }

    /**
     * Qui va tutta la logica della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void afterNavigation(AfterNavigationEvent beforeEnterEvent) {
        this.fixPreferenze();
        this.initView();
        this.fixVisibilitaColumns();
        this.fixVisibilitaFields();
        this.fixOrdinamento();
        this.backendLogic();
        this.fixAdditionalComponents();
    }


    /**
     * Preferenze usate da questa view <br>
     * Primo metodo chiamato dopo AfterNavigationEvent <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        this.usaBottoneFilter = false;
        this.usaComboLivello = false;
        this.usaComboType = false;
    }

    /**
     * Qui va tutta la logica iniziale della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void initView() {
    }

    /**
     * Regola la visibilità delle colonne della grid <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixVisibilitaColumns() {
    }

    /**
     * Regola la visibilità dei fields del Form<br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixVisibilitaFields() {
    }

    /**
     * Regola l'ordinamento della <grid <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixOrdinamento() {
    }

    /**
     * Qui vanno i collegamenti con la logica del backend <br>
     * logic configuration <br>
     */
    protected void backendLogic() {
        gridCrud.setOperations(
                () -> sincroFiltri(),
                user -> crudBackend.add(user),
                user -> crudBackend.update(user),
                user -> crudBackend.delete(user)
        );
    }

    /**
     * Componenti aggiuntivi oltre quelli base <br>
     * Tipicamente bottoni di selezione/filtro <br>
     */
    protected void fixAdditionalComponents() {
        if (usaBottoneFilter) {
            filter = new TextField();
            filter.setPlaceholder("Filter by descrizione");
            filter.setClearButtonVisible(true);
            gridCrud.getCrudLayout().addFilterComponent(filter);
            filter.addValueChangeListener(event -> gridCrud.refreshGrid());
        }

        if (usaComboLivello) {
            comboLivello = new ComboBox();
            comboLivello.setPlaceholder("Livello");
            comboLivello.setClearButtonVisible(true);
            List items = AENotaLevel.getAll();
            comboLivello.setItems(items);
            gridCrud.getCrudLayout().addFilterComponent(comboLivello);
            comboLivello.addValueChangeListener(event -> sincroFiltri());
        }

        if (usaComboType) {
            comboType = new ComboBox();
            comboType.setPlaceholder("Type");
            comboType.setClearButtonVisible(true);
            List items2 = AETypeLog.getAll();
            comboType.setItems(items2);
            gridCrud.getCrudLayout().addFilterComponent(comboType);
            comboType.addValueChangeListener(event -> sincroFiltri());
        }
    }

    /**
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected List sincroFiltri() {
        List items = null;
        String textSearch = VUOTA;
        AENotaLevel level = null;
        AETypeLog type = null;

        if (usaBottoneFilter && filter != null) {
            textSearch = filter != null ? filter.getValue() : VUOTA;
        }

        if (usaComboLivello && comboLivello != null) {
            level = (AENotaLevel) comboLivello.getValue();
        }

        if (usaComboType && comboType != null) {
            type = (AETypeLog) comboType.getValue();
        }

        if (usaBottoneFilter && usaComboLivello && usaComboType) {
            items = crudBackend.findByDescrizioneAndLivelloAndType(textSearch, level, type);
        }

        if (items != null) {
            gridCrud.getGrid().setItems(items);
        }

        return items;
    }

}

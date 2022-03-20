package it.algos.vaad23.ui.views;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.logic.*;
import org.vaadin.crudui.crud.impl.*;
import org.vaadin.crudui.layout.impl.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 20-mar-2022
 * Time: 11:05
 */
public abstract class AlgosView extends VerticalLayout implements AfterNavigationObserver {

    protected EntityBackend crudBackend;

    protected GridCrud crud;

    protected TextField filter;

    protected ComboBox comboLivello;

    protected ComboBox comboType;

    protected boolean usaBottoneFilter;

    protected boolean usaComboLivello;

    protected boolean usaComboType;


    public AlgosView(EntityBackend crudBackend, Class entityClazz, boolean splitLayout) {
        //        super(crudBackend, entityClazz, splitLayout);
        this.crudBackend = crudBackend;
        // crud instance
        if (splitLayout) {
            crud = new GridCrud<>(entityClazz, new HorizontalSplitCrudLayout());
        }
        else {
            crud = new GridCrud<>(entityClazz);
        }

        // grid configuration
        crud.getCrudFormFactory().setUseBeanValidation(true);

        // logic configuration
        crud.setFindAllOperation(() -> crudBackend.findAll());
        crud.setAddOperation(crudBackend::add);
        crud.setUpdateOperation(crudBackend::update);
        crud.setDeleteOperation(crudBackend::delete);

        // layout configuration
        setSizeFull();
        this.add(crud);
    }

    /**
     * Qui va tutta la logica della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void afterNavigation(AfterNavigationEvent beforeEnterEvent) {
        this.fixPreferenze();
        this.initView();
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
     * Qui vanno i collegamenti con la logica del backend <br>
     * logic configuration <br>
     */
    protected void backendLogic() {
        crud.setOperations(
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
            crud.getCrudLayout().addFilterComponent(filter);
            filter.addValueChangeListener(event -> crud.refreshGrid());
        }

        if (usaComboLivello) {
            comboLivello = new ComboBox();
            comboLivello.setPlaceholder("Livello");
            comboLivello.setClearButtonVisible(true);
            List items = AENotaLevel.getAll();
            comboLivello.setItems(items);
            crud.getCrudLayout().addFilterComponent(comboLivello);
            comboLivello.addValueChangeListener(event -> sincroFiltri());
        }

        if (usaComboType) {
            comboType = new ComboBox();
            comboType.setPlaceholder("Type");
            comboType.setClearButtonVisible(true);
            List items2 = AETypeLog.getAll();
            comboType.setItems(items2);
            crud.getCrudLayout().addFilterComponent(comboType);
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
            crud.getGrid().setItems(items);
        }

        return items;
    }

}

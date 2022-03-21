package it.algos.vaad23.ui.views;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.notification.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.logic.*;
import it.algos.vaad23.ui.dialog.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
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

    /**
     * Istanza di una interfaccia SpringBoot <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext appContext;

    protected EntityBackend crudBackend;

    protected GridCrud gridCrud;

    protected Button buttonDelete;

    protected TextField filter;

    protected boolean usaBottoneDelete;

    protected boolean usaBottoneFilter;

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
        this.backendLogic();
        this.fixVisibilitaColumns();
        this.fixVisibilitaFields();
        this.fixOrdinamento();
        this.fixAdditionalComponents();
        this.addListeners();
    }


    /**
     * Preferenze usate da questa view <br>
     * Primo metodo chiamato dopo AfterNavigationEvent <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        this.usaBottoneDelete = false;
        this.usaBottoneFilter = false;
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
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
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
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    public void fixOrdinamento() {
    }

    /**
     * Aggiunge tutti i listeners ai bottoni di 'topPlaceholder' che sono stati creati SENZA listeners <br>
     * <p>
     * Chiamato da afterNavigation() <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void addListeners() {
    }

    /**
     * Componenti aggiuntivi oltre quelli base <br>
     * Tipicamente bottoni di selezione/filtro <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected void fixAdditionalComponents() {
        if (usaBottoneDelete) {
            buttonDelete = new Button();
            buttonDelete.setIcon(VaadinIcon.TRASH.create());
            buttonDelete.setText("Delete All");
            buttonDelete.getElement().setAttribute("theme", "error");
            gridCrud.getCrudLayout().addFilterComponent(buttonDelete);
            buttonDelete.addClickListener(event -> openConfirmDeleteAll());
        }

        if (usaBottoneFilter) {
            filter = new TextField();
            filter.setPlaceholder("Filter by descrizione");
            filter.setClearButtonVisible(true);
            gridCrud.getCrudLayout().addFilterComponent(filter);
            filter.addValueChangeListener(event -> gridCrud.refreshGrid());
        }
    }

    /**
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected List sincroFiltri() {
        List items = null;
        String textSearch;

        if (usaBottoneFilter && filter != null) {
            textSearch = filter != null ? filter.getValue() : VUOTA;
            items = crudBackend.findByDescrizione(textSearch);
        }

        if (items != null) {
            gridCrud.getGrid().setItems(items);
        }

        return items;
    }


    /**
     * Opens the confirmation dialog before deleting all items. <br>
     * <p>
     * The dialog will display the given title and message(s), then call <br>
     * Può essere sovrascritto dalla classe specifica se servono avvisi diversi <br>
     */
    protected final void openConfirmDeleteAll() {
        appContext.getBean(DialogDelete.class, "tutta la collection").open(this::deleteAll);
    }

    /**
     * Cancellazione effettiva (dopo dialogo di conferma) di tutte le entities della collezione. <br>
     * Rimanda al service specifico <br>
     * Azzera gli items <br>
     * Ridisegna la GUI <br>
     */
    public void deleteAll() {
        crudBackend.deleteAll();
        gridCrud.refreshGrid();
        Notification.show("Cancellata tutta la collection");
    }

    /**
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected void delete() {
    }

}

package it.algos.vaad23.backend.packages.utility.versione;

import com.vaadin.flow.component.*;
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

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: sab, 12-mar-2022
 * Time: 18:24
 */
@PageTitle(TAG_VERSIONE)
@Route(value = TAG_VERSIONE, layout = MainLayout.class)
public class VersioneView extends CrudView {

    private TextField filter;

    private VersioneBackend backend;

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
    public VersioneView(@Autowired final VersioneBackend crudBackend) {
        super(crudBackend, Versione.class, true);
        this.backend = crudBackend;

        crud.getGrid().setColumns("id", "type", "titolo", "descrizione", "company", "vaadin23", "ordine");

        String larId = "3em";
        String larType = "9em";
        String larTitolo = "11em";
        String larDesc = "30em";
        String larCompany = "8em";

        crud.getGrid().getColumnByKey("id").setWidth(larId).setFlexGrow(0);
        crud.getGrid().getColumnByKey("type").setWidth(larType).setFlexGrow(0);
        crud.getGrid().getColumnByKey("titolo").setWidth(larTitolo).setFlexGrow(0);
        crud.getGrid().getColumnByKey("descrizione").setWidth(larDesc).setFlexGrow(1);
        crud.getGrid().getColumnByKey("company").setWidth(larCompany).setFlexGrow(0);

        // colonne visibili
        if (!VaadVar.usaCompany) {
            crud.getGrid().getColumnByKey("company").setVisible(false);
        }

        // additional components
        filter = new TextField();
        filter.setPlaceholder("Filter by descrizione");
        filter.setClearButtonVisible(true);
        crud.getCrudLayout().addFilterComponent(filter);
        filter.addValueChangeListener(event -> crud.refreshGrid());

        ComboBox combo = new ComboBox();
        combo.setPlaceholder("Type");
        combo.setClearButtonVisible(true);
        List items = AETypeVers.getAll();
        combo.setItems(items);
        crud.getCrudLayout().addFilterComponent(combo);
        combo.addValueChangeListener(event -> filtroCombo(event));

        // logic configuration
        crud.setOperations(
                () -> backend.findByDescrizioneContainingIgnoreCase(filter.getValue()),
                user -> backend.add(user),
                user -> backend.update(user),
                user -> backend.delete(user)
        );

        Grid.Column columnVaad = crud.getGrid().getColumnByKey("vaadin23");
        columnVaad.setVisible(false);
        Grid.Column columnOrd = crud.getGrid().getColumnByKey("ordine");
        columnOrd.setVisible(false);
        List<GridSortOrder> lista = new ArrayList<>();
        lista.add(new GridSortOrder(columnVaad, SortDirection.DESCENDING));
        lista.add(new GridSortOrder(columnOrd, SortDirection.ASCENDING));
        crud.getGrid().sort(lista);

        crud.setAddOperationVisible(false);
        crud.setUpdateOperationVisible(false);
        crud.setDeleteOperationVisible(false);

        // fields visibili
        if (VaadVar.usaCompany) {
            crud.getCrudFormFactory().setVisibleProperties(CrudOperation.READ, "id", "type", "titolo", "descrizione", "company", "ordine", "vaadin23");
            crud.getCrudFormFactory().setVisibleProperties(CrudOperation.UPDATE, "id", "type", "titolo", "descrizione", "company", "vaadin23");
        }
        else {
            crud.getCrudFormFactory().setVisibleProperties(CrudOperation.READ, "id", "type", "titolo", "descrizione", "ordine", "vaadin23");
            crud.getCrudFormFactory().setVisibleProperties(CrudOperation.UPDATE, "id", "type", "titolo", "descrizione", "vaadin23");
        }

    }

    public void filtroCombo(HasValue.ValueChangeEvent event) {
        List items;

        if (event.getValue() instanceof AETypeVers type) {
            filter.setValue(VUOTA);
            items = backend.findByType(type);
            crud.getGrid().setItems(items);
            return;
        }

        if (event.getValue() == null) {
            crud.refreshGrid();
            return;
        }
    }


}// end of crud @Route view class
package it.algos.vaad23.backend.packages.utility.versione;

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
@PageTitle("Versioni")
@Route(value = TAG_VERSIONE, layout = MainLayout.class)
public class VersioneView extends CrudView {

    private ComboBox comboTypeVers;

    //--per eventuali metodi specifici
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
        super(crudBackend, Versione.class);
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

        this.splitLayout = true;
        this.usaBottoneFilter = true;
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();
        span("Sviluppo, patch e update del programma. Sigla V iniziale per il programma base Vaadin23");
    }

    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixCrud() {
        super.fixCrud();

        gridCrud.setAddOperationVisible(false);
        gridCrud.setUpdateOperationVisible(false);
        gridCrud.setDeleteOperationVisible(false);

        if (VaadVar.usaCompany) {
            crudForm.setVisibleProperties(CrudOperation.READ, "id", "type", "titolo", "descrizione", "company", "vaadin23");
            crudForm.setVisibleProperties(CrudOperation.UPDATE, "id", "type", "titolo", "descrizione", "company", "vaadin23");
        }
        else {
            crudForm.setVisibleProperties(CrudOperation.READ, "id", "type", "titolo", "descrizione", "vaadin23");
            crudForm.setVisibleProperties(CrudOperation.UPDATE, "id", "type", "titolo", "descrizione", "vaadin23");
        }
    }


    /**
     * Regola la visibilità delle colonne della grid <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixColumns() {
        super.fixColumns();

        grid.setColumns("id", "type", "titolo", "descrizione", "company", "vaadin23", "ordine");

        String larId = "3em";
        String larType = "8em";
        String larTitolo = "11em";
        String larDesc = "30em";
        String larCompany = "8em";

        grid.getColumnByKey("id").setWidth(larId).setFlexGrow(0);
        grid.getColumnByKey("type").setWidth(larType).setFlexGrow(0);
        grid.getColumnByKey("titolo").setWidth(larTitolo).setFlexGrow(0);
        grid.getColumnByKey("descrizione").setWidth(larDesc).setFlexGrow(1);
        grid.getColumnByKey("company").setWidth(larCompany).setFlexGrow(0);
        grid.getColumnByKey("vaadin23").setVisible(false);
        grid.getColumnByKey("ordine").setVisible(false);

        if (!VaadVar.usaCompany) {
            grid.getColumnByKey("company").setVisible(false);
        }
    }

    /**
     * Regola la visibilità dei fields del Form<br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixFields() {
        super.fixFields();

        crudForm.setFieldType("descrizione", TextArea.class);
    }

    /**
     * Regola l'ordinamento della <grid <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    @Override
    public void fixOrder() {
        Grid.Column columnVaad = grid.getColumnByKey("vaadin23");
        Grid.Column columnOrd = grid.getColumnByKey("ordine");
        List<GridSortOrder> lista = new ArrayList<>();
        lista.add(new GridSortOrder(columnVaad, SortDirection.DESCENDING));
        lista.add(new GridSortOrder(columnOrd, SortDirection.ASCENDING));
        gridCrud.getGrid().sort(lista);
    }


    /**
     * Componenti aggiuntivi oltre quelli base <br>
     * Tipicamente bottoni di selezione/filtro <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixAdditionalComponents() {
        super.fixAdditionalComponents();

        comboTypeVers = new ComboBox();
        comboTypeVers.setPlaceholder("Type");
        comboTypeVers.setClearButtonVisible(true);
        List items2 = AETypeVers.getAllEnums();
        comboTypeVers.setItems(items2);
        gridCrud.getCrudLayout().addFilterComponent(comboTypeVers);
        comboTypeVers.addValueChangeListener(event -> sincroFiltri());
    }

    /**
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected List sincroFiltri() {
        List items = null;
        String textSearch = VUOTA;
        AETypeVers type = null;

        if (usaBottoneFilter && filter != null) {
            textSearch = filter != null ? filter.getValue() : VUOTA;
            items = backend.findByDescrizione(textSearch);
        }

        if (comboTypeVers != null) {
            type = (AETypeVers) comboTypeVers.getValue();
        }

        if (usaBottoneFilter) {
            items = backend.findByDescrizioneAndType(textSearch, type);
        }

        if (items != null) {
            gridCrud.getGrid().setItems(items);
        }

        return items;
    }


}// end of crud @Route view class
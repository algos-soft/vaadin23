package it.algos.vaad23.backend.packages.utility.note;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.ui.views.*;
import org.springframework.beans.factory.annotation.*;
import org.vaadin.crudui.crud.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 18-mar-2022
 * Time: 06:55
 * <p>
 */
@PageTitle("Nota")
@Route(value = "nota", layout = MainLayout.class)
public class NotaView extends CrudView {

    private TextField filter;

    private ComboBox comboLivello;

    private ComboBox comboType;

    private NotaBackend backend;

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
    public NotaView(@Autowired final NotaBackend crudBackend) {
        super(crudBackend, Nota.class, false);
        this.backend = crudBackend;

        crud.getGrid().setColumns("livello", "type", "inizio", "descrizione", "fatto", "fine");

        String larLevel = "9em";
        String larType = "9em";
        String larData = "9em";
        String larDesc = "30em";
        String larBool = "8em";

        crud.getGrid().getColumnByKey("livello").setWidth(larLevel).setFlexGrow(0);
        crud.getGrid().getColumnByKey("type").setWidth(larType).setFlexGrow(0);
        crud.getGrid().getColumnByKey("inizio").setWidth(larData).setFlexGrow(0);
        crud.getGrid().getColumnByKey("descrizione").setWidth(larDesc).setFlexGrow(1);
        crud.getGrid().getColumnByKey("fatto").setWidth(larBool).setFlexGrow(0);
        crud.getGrid().getColumnByKey("fine").setWidth(larData).setFlexGrow(0);

        // additional components
        filter = new TextField();
        filter.setPlaceholder("Filter by descrizione");
        filter.setClearButtonVisible(true);
        crud.getCrudLayout().addFilterComponent(filter);
        filter.addValueChangeListener(event -> crud.refreshGrid());

        comboLivello = new ComboBox();
        comboLivello.setPlaceholder("Livello");
        comboLivello.setClearButtonVisible(true);
        List items = AENotaLevel.getAll();
        comboLivello.setItems(items);
        crud.getCrudLayout().addFilterComponent(comboLivello);
        comboLivello.addValueChangeListener(event -> sincroFiltri());

        comboType = new ComboBox();
        comboType.setPlaceholder("Type");
        comboType.setClearButtonVisible(true);
        List items2 = AETypeLog.getAll();
        comboType.setItems(items2);
        crud.getCrudLayout().addFilterComponent(comboType);
        comboType.addValueChangeListener(event -> sincroFiltri());

        // logic configuration
        crud.setOperations(
                () -> sincroFiltri(),
                user -> backend.add(user),
                user -> backend.update(user),
                user -> backend.delete(user)
        );

        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD, "livello", "type", "descrizione");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.UPDATE, "livello", "type", "descrizione", "fatto");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.DELETE, "livello", "type", "descrizione", "fatto", "inizio", "fine");
    }


    public List sincroFiltri() {
        List items;
        String textSearch = filter != null ? filter.getValue() : VUOTA;
        AENotaLevel level = (AENotaLevel) comboLivello.getValue();
        AETypeLog type = (AETypeLog) comboType.getValue();

        items = backend.findByDescrizioneAndLivelloAndType(textSearch, level, type);
        crud.getGrid().setItems(items);
        return items;
    }


}// end of crud @Route view class
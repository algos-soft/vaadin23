package it.algos.vaad23.backend.packages.geografia.continente;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.ui.views.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 13-mar-2022
 * Time: 20:30
 * <p>
 */
@PageTitle(TAG_CONTINENTE)
@Route(value = TAG_CONTINENTE, layout = MainLayout.class)
public class ContinenteView extends CrudView {

    private ContinenteBackend backend;

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
    public ContinenteView(@Autowired final ContinenteBackend crudBackend) {
        super(crudBackend, Continente.class, true);
        this.backend = crudBackend;

        gridCrud.getGrid().setColumns("ordine", "nome", "abitato");

        // additional components
        Button reset = new Button("Reset");
        gridCrud.getCrudLayout().addFilterComponent(reset);
        reset.addClickListener(e -> reset());
    }

    public void reset() {
        backend.reset();
        gridCrud.refreshGrid();
    }

}// end of crud @Route view class
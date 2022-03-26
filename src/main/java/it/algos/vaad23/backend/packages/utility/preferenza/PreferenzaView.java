package it.algos.vaad23.backend.packages.utility.preferenza;

import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.router.*;
import it.algos.vaad23.backend.boot.*;
import it.algos.vaad23.ui.views.*;
import org.springframework.beans.factory.annotation.*;
import org.vaadin.crudui.crud.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: sab, 26-mar-2022
 * Time: 14:02
 * <p>
 */
@PageTitle("Preferenze")
@Route(value = "preferenza", layout = MainLayout.class)
public class PreferenzaView extends CrudView {


    private PreferenzaBackend backend;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Si usa un @Qualifier(), per specificare la classe che incrementa l'interfaccia repository <br>
     * Si usa una costante statica, per essere sicuri di scriverla uguale a quella di xxxRepository <br>
     * Regola il service specifico di persistenza dei dati e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view <br>
     *
     * @param entityBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public PreferenzaView(@Autowired final PreferenzaBackend entityBackend) {
        super(entityBackend, Preferenza.class);
        this.backend = entityBackend;
    }

    /**
     * Logic configuration <br>
     * Qui vanno i collegamenti con la logica del backend <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixCrud() {
        super.fixCrud();

        if (VaadVar.usaCompany) {
            crudForm.setVisibleProperties(CrudOperation.ADD, "id", "code", "descrizione", "vaadFlow", "needRiavvio", "usaCompany", "visibileAdmin");
            crudForm.setVisibleProperties(CrudOperation.READ, "id", "code", "descrizione", "vaadFlow", "needRiavvio", "usaCompany", "visibileAdmin");
            crudForm.setVisibleProperties(CrudOperation.UPDATE, "id", "code", "descrizione", "vaadFlow", "needRiavvio", "usaCompany", "visibileAdmin");
        }
        else {
            crudForm.setVisibleProperties(CrudOperation.ADD, "code", "descrizione", "vaadFlow", "needRiavvio", "descrizioneEstesa");
            crudForm.setVisibleProperties(CrudOperation.READ, "code", "descrizione", "vaadFlow", "needRiavvio", "descrizioneEstesa");
            crudForm.setVisibleProperties(CrudOperation.UPDATE, "code", "descrizione", "vaadFlow", "needRiavvio", "descrizioneEstesa");
        }
    }

    /**
     * Regola la visibilità delle colonne della grid <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixColumns() {
        super.fixColumns();

        grid.setColumns("code", "descrizione", "vaadFlow", "needRiavvio");

        String larCode = "12em";
        String larDesc = "30em";
        String larBool = "8em";

        grid.getColumnByKey("code").setWidth(larCode).setFlexGrow(0);
        grid.getColumnByKey("descrizione").setWidth(larDesc).setFlexGrow(1);
        grid.getColumnByKey("vaadFlow").setWidth(larBool).setFlexGrow(0).setHeader("Flow");
        grid.getColumnByKey("needRiavvio").setWidth(larBool).setFlexGrow(0).setHeader("Riavvio");
    }

    /**
     * Regola la visibilità dei fields del Form<br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixFields() {
        super.fixFields();

        crudForm.setFieldType("descrizioneEstesa", TextArea.class);
    }

}// end of crud @Route view class
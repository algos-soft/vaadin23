package it.algos.vaad23.backend.packages.utility.note;

import com.vaadin.flow.router.*;
import it.algos.vaad23.ui.views.*;
import org.springframework.beans.factory.annotation.*;
import org.vaadin.crudui.crud.*;

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
public class NotaView extends AlgosView {


    //--per eventuali metodi specifici
    private NotaBackend backend;

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
    public NotaView(@Autowired final NotaBackend entityBackend) {
        super(entityBackend, Nota.class, true);
        this.backend = entityBackend;
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
     * Qui va tutta la logica iniziale della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void initView() {
        super.initView();

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

        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD, "livello", "type", "descrizione");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.READ, "livello", "type", "inizio", "fine", "descrizione", "fatto");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.UPDATE, "livello", "type", "descrizione", "fatto");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.DELETE, "livello", "type", "inizio", "descrizione", "fatto", "fine");
    }


}// end of crud @Route view class
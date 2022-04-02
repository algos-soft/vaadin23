package it.algos.vaad23.backend.packages.crono.mese;

import com.vaadin.flow.router.*;
import it.algos.vaad23.ui.views.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: gio, 31-mar-2022
 * Time: 18:41
 * <p>
 */
@PageTitle("Mesi")
@Route(value = "mese", layout = MainLayout.class)
public class MeseView extends CrudView {


    //--per eventuali metodi specifici
    private MeseBackend meseBackend;

    //--per eventuali metodi specifici
    private MeseDialog meseDialog;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public MeseView(@Autowired final MeseBackend crudBackend) {
        super(crudBackend, Mese.class);
        this.meseBackend = crudBackend;
    }

    /**
     * Preferenze usate da questa 'view' <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.riordinaColonne = true;
        super.colonne = Arrays.asList("ordine", "code", "descrizione");
        super.fields = Arrays.asList("code", "descrizione");
        super.usaBottoneRefresh = true;
        super.usaBottoneReset = true;
        super.usaBottoneDeleteAll = true;
        super.usaBottoneNew = true;
        super.usaBottoneEdit = true;
        super.usaBottoneDelete = true;

        super.dialogClazz = MeseDialog.class;
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();

        addSpanVerde("Prova di colori");
    }

}// end of crud @Route view class
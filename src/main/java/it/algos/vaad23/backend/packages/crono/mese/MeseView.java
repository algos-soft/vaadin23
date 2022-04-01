package it.algos.vaad23.backend.packages.crono.mese;

import com.vaadin.flow.component.notification.*;
import com.vaadin.flow.router.*;
import it.algos.vaad23.backend.entity.*;
import it.algos.vaad23.ui.dialog.*;
import it.algos.vaad23.ui.views.*;
import org.springframework.beans.factory.annotation.*;
import org.vaadin.crudui.crud.*;

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


    private MeseBackend backend;

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
    public MeseView(@Autowired final MeseBackend entityBackend) {
        super(entityBackend, Mese.class);
        this.backend = entityBackend;
    }

    /**
     * Preferenze usate da questa 'logica' <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.riordinaColonne = true;
        super.listaNomiColonne = Arrays.asList("ordine", "code", "descrizione");
        super.usaBottoneRefresh = true;
        super.usaBottoneReset = true;
        super.usaBottoneDeleteAll = true;
        super.usaBottoneNew = true;
        super.usaBottoneEdit = true;
        super.usaBottoneDelete = true;
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


    /**
     * Apre un dialogo di creazione <br>
     * Proveniente da un click sul bottone New della Top Bar <br>
     * Sempre attivo <br>
     * Passa al dialogo gli handler per annullare e creare <br>
     */
    @Override
    public void newItem() {
        MeseDialog dialog = appContext.getBean(MeseDialog.class, backend.newEntity(), CrudOperation.ADD);
        dialog.open(this::saveHandler, this::annullaHandler);
    }


    /**
     * Apre un dialogo di editing <br>
     * Proveniente da un doppio click su una riga della Grid <br>
     * Passa al dialogo gli handler per annullare e modificare <br>
     *
     * @param entityBeanDaRegistrare (nuova o esistente)
     */
    @Override
    public void updateItem(AEntity entityBeanDaRegistrare) {
        MeseDialog dialog = appContext.getBean(MeseDialog.class, entityBeanDaRegistrare, CrudOperation.UPDATE);
        dialog.open(this::saveHandler, this::annullaHandler);
    }

    /**
     * Apre un dialogo di cancellazione<br>
     * Proveniente da un click sul bottone Delete della Top Bar <br>
     * Attivo solo se è selezionata una e una sola riga <br>
     * Passa al dialogo gli handler per annullare e cancellare <br>
     */
    @Override
    public void deleteItem() {
        Optional entityBean = grid.getSelectedItems().stream().findFirst();
        if (entityBean.isPresent()) {
            MeseDialog dialog = appContext.getBean(MeseDialog.class, entityBean.get(), CrudOperation.DELETE);
            dialog.open(this::saveHandler, this::deleteHandler, this::annullaHandler);
        }
    }

    /**
     * Primo ingresso dopo il click sul bottone del dialogo <br>
     */
    protected void saveHandler(final Mese entityBean, final CrudOperation operation) {
        grid.setItems(backend.findAll());
    }


    public void deleteHandler(final Mese entityBean) {
        backend.delete(entityBean);
        grid.setItems(backend.findAll());
        Avviso.show(String.format("%s successfully deleted", entityBean.code)).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    public void annullaHandler(final Mese entityBean) {
        //        Notification.show(entityBean + " successfully deleted.", 3000, Notification.Position.BOTTOM_START);
    }

}// end of crud @Route view class
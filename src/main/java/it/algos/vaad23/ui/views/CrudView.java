package it.algos.vaad23.ui.views;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.data.selection.*;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.entity.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.logic.*;
import it.algos.vaad23.backend.service.*;
import it.algos.vaad23.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.*;
import org.springframework.context.annotation.Scope;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 01-apr-2022
 * Time: 06:41
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public abstract class CrudView extends VerticalLayout implements AfterNavigationObserver {

    /**
     * Istanza di una interfaccia SpringBoot <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext appContext;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public HtmlService htmlService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public LogService logger;

    protected EntityBackend crudBackend;

    protected Class entityClazz;

    protected HorizontalLayout topPlaceHolder;

    protected int width;

    /**
     * Flag di preferenza per la cancellazione della colonna ID. Di default true. <br>
     */
    protected boolean cancellaColonnaKeyId;

    /**
     * Flag di preferenza per avere un ordine prestabilito per le colonne. Di default false. <br>
     */
    protected boolean riordinaColonne;

    protected List<String> listaNomiColonne;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneRefresh;

    protected Button buttonRefresh;


    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneReset;

    protected Button buttonReset;


    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneDeleteAll;

    protected Button buttonDeleteAll;


    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneNew;

    protected Button buttonNew;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneEdit;

    protected Button buttonEdit;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneDelete;

    protected Button buttonDelete;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneExport;

    protected Button buttonExport;

    protected Grid<AEntity> grid;

    private Function<String, Grid.Column<AEntity>> getColonna = name -> grid.getColumnByKey(name);


    public CrudView(final EntityBackend crudBackend, final Class entityClazz) {
        this.crudBackend = crudBackend;
        this.entityClazz = entityClazz;
    }

    /**
     * Qui va tutta la logica della view <br>
     * Invocato da SpringBoot dopo il metodo init() del costruttore <br>
     * Sono disponibili tutte le istanze @Autowired <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void afterNavigation(AfterNavigationEvent beforeEnterEvent) {
        //--Preferenze usate da questa 'logica'
        this.fixPreferenze();

        //--Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
        this.fixAlert();

        //--Costruisce un layout (obbligatorio per la List) per i bottoni di comando della view al Top <br>
        //--Eventualmente i bottoni potrebbero andare su due righe <br>
        this.fixTopLayout();

        //--Corpo principale della Grid/Form (obbligatorio) <br>
        this.fixBodyLayout();

        //--Aggiunge i listener ai vari oggetti <br>
        this.fixListener();
    }

    /**
     * Preferenze usate da questa 'logica' <br>
     * Metodo chiamato da CrudView.afterNavigation() <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        //--Larghezza del browser utilizzato in questa sessione <br>
        UI.getCurrent().getPage().retrieveExtendedClientDetails(details -> width = details.getBodyClientWidth());

        riordinaColonne = false;
        listaNomiColonne = new ArrayList<>();
        cancellaColonnaKeyId = true;
        usaBottoneRefresh = false;
        usaBottoneReset = false;
        usaBottoneDeleteAll = false;
        usaBottoneNew = false;
        usaBottoneEdit = false;
        usaBottoneDelete = false;
        usaBottoneExport = false;
    }

    /**
     * Metodo chiamato da CrudView.afterNavigation() <br>
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixAlert() {
    }

    /**
     * Costruisce un layout per i componenti al Top della view <br>
     * I componenti possono essere (nell'ordine):
     * Bottoni standard (solo icone) Reset, New, Edit, Delete, ecc.. <br>
     * SearchField per il filtro testuale di ricerca <br>
     * ComboBox e CheckBox di filtro <br>
     * Bottoni specifici non standard <br>
     * <p>
     * Metodo chiamato da CrudView.afterNavigation() <br>
     * Costruisce tutti i componenti in metodi che possono essere sovrascritti <br>
     * Inserisce la istanza in topPlaceHolder della view <br>
     * Aggiunge tutti i listeners dei bottoni, searchField, comboBox, checkBox <br>
     * <p>
     * Non può essere sovrascritto <br>
     */
    private void fixTopLayout() {
        this.topPlaceHolder = new HorizontalLayout();
        topPlaceHolder.setClassName("buttons");
        topPlaceHolder.setPadding(false);
        topPlaceHolder.setSpacing(true);
        topPlaceHolder.setMargin(false);
        topPlaceHolder.setClassName("confirm-dialog-buttons");

        this.fixBottoniTopStandard();
        this.fixFiltri();
        this.fixBottoniTopSpecifici();
        this.add(topPlaceHolder);
    }

    /**
     * Bottoni standard (solo icone) Reset, New, Edit, Delete, ecc.. <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixBottoniTopStandard() {
        if (usaBottoneRefresh) {
            buttonRefresh = new Button();
            buttonRefresh.getElement().setAttribute("theme", "secondary");
            buttonRefresh.getElement().setProperty("title", "Refresh: ricarica dal database i valori della finestra");
            buttonRefresh.setIcon(new Icon(VaadinIcon.REFRESH));
            topPlaceHolder.add(buttonRefresh);
        }

        //--ha senso solo per le entity che estendono AREntity con la property 'reset'
        if (usaBottoneReset && AREntity.class.isAssignableFrom(entityClazz)) {
            buttonReset = new Button();
            buttonReset.getElement().setAttribute("theme", "error");
            buttonReset.getElement().setProperty("title", "Reset: ripristina nel database i valori di default annullando le eventuali modifiche apportate successivamente");
            buttonReset.setIcon(new Icon(VaadinIcon.REFRESH));
            topPlaceHolder.add(buttonReset);
        }

        if (usaBottoneDeleteAll) {
            buttonDeleteAll = new Button();
            buttonDeleteAll.getElement().setAttribute("theme", "error");
            buttonDeleteAll.getElement().setProperty("title", "Delete: cancella completamente tutta la collezione");
            buttonDeleteAll.setIcon(new Icon(VaadinIcon.REFRESH));
            topPlaceHolder.add(buttonDeleteAll);
            //            buttonDeleteAll.addClickListener(event -> openConfirmDeleteAll());
        }

        if (usaBottoneNew) {
            buttonNew = new Button();
            buttonNew.getElement().setAttribute("theme", "secondary");
            buttonNew.getElement().setProperty("title", "Add: aggiunge un elemento alla collezione");
            buttonNew.setIcon(new Icon(VaadinIcon.PLUS));
            buttonNew.setEnabled(true);
            topPlaceHolder.add(buttonNew);
            buttonNew.addClickListener(event -> newItem());
        }

        if (usaBottoneEdit) {
            buttonEdit = new Button();
            buttonEdit.getElement().setAttribute("theme", "secondary");
            buttonEdit.getElement().setProperty("title", "Update: modifica l'elemento selezionato");
            buttonEdit.setIcon(new Icon(VaadinIcon.PENCIL));
            buttonEdit.setEnabled(false);
            buttonEdit.addClickListener(e -> updateItem());
            topPlaceHolder.add(buttonEdit);
        }

        if (usaBottoneDelete) {
            buttonDelete = new Button();
            buttonDelete.getElement().setAttribute("theme", "error");
            buttonDelete.getElement().setProperty("title", "Delete: cancella l'elemento selezionato");
            buttonDelete.setIcon(new Icon(VaadinIcon.TRASH));
            buttonDelete.setEnabled(false);
            buttonDelete.addClickListener(e -> deleteItem());
            topPlaceHolder.add(buttonDelete);
        }

        if (usaBottoneExport) {
        }
    }

    protected void fixFiltri() {
    }

    protected void fixBottoniTopSpecifici() {
    }

    /**
     * Costruisce il corpo principale (obbligatorio) della Grid <br>
     * <p>
     * Metodo chiamato da CrudView.afterNavigation() <br>
     * Costruisce un' istanza dedicata con la Grid <br>
     */
    protected void fixBodyLayout() {
        // Create a listing component for a bean type
        grid = new Grid(entityClazz, true);

        // Regola numero e ordine delle colonne
        this.fixColumns();

        // Pass all objects to a grid from a Spring Data repository object
        grid.setItems(crudBackend.findAll());

        // The row-stripes theme produces a background color for every other row.
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        // switch to single select mode
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        // sincronizzazione dei bottoni Edit e Delete
        grid.addSelectionListener(event -> sincroSelection(event));

        // layout configuration
        setSizeFull();
        this.add(grid);
    }

    /**
     * Elimina la colonna 'keyID' -> 'id' costruita in automatico <br>
     * Le colonne sono state costruite in automatico senza ordine garantito <br>
     * Riordina le colonne secondo una lista prestabilita <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixColumns() {
        if (cancellaColonnaKeyId) {
            try {
                grid.removeColumnByKey(FIELD_NAME_ID_SENZA);
            } catch (Exception unErrore) {
                logger.error(new WrapLog().exception(unErrore).usaDb().message("Non ho indicato correttamente la colonna 'id' "));
                return;
            }
        }

        //--cambia solo l'ordine di presentazione delle colonne. Ha senso solo se sono state costruite in automatico.
        //--tutte le caratteristiche delle colonne create in automatico rimangono immutate
        //--se servono caratteristiche particolari per una colonna o va creata manualmente o va recuperata e modificata
        if (riordinaColonne && listaNomiColonne.size() > 0) {
            try {
                grid.setColumnOrder(listaNomiColonne.stream().map(getColonna).collect(Collectors.toList()));
            } catch (Exception unErrore) {
                logger.error(new WrapLog().exception(unErrore).usaDb());
            }
        }
    }

    /**
     * Aggiunge alcuni listeners alla Grid <br>
     * Aggiunge alcuni listeners eventualmente non aggiunti ai bottoni, comboBox <br>
     * <p>
     * Metodo chiamato da CrudView.afterNavigation() <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixListener() {
        // pass the row/item that the user double-clicked to method updateItem
        if (Pref.doubleClick.is()) {
            grid.addItemDoubleClickListener(listener -> updateItem(listener.getItem()));
        }
    }

    protected void sincroSelection(SelectionEvent event) {
        boolean singoloSelezionato = event.getAllSelectedItems().size() == 1;
        buttonEdit.setEnabled(singoloSelezionato);
        buttonDelete.setEnabled(singoloSelezionato);
    }

    /**
     * Apre un dialogo di creazione <br>
     * Proveniente da un click sul bottone New della Top Bar <br>
     * Sempre attivo <br>
     * Passa al dialogo gli handler per annullare e creare <br>
     */
    public void newItem() {
    }

    /**
     * Apre un dialogo di editing <br>
     * Proveniente da un click sul bottone Edit della Top Bar <br>
     * Attivo solo se è selezionata una e una sola riga <br>
     * Passa al dialogo gli handler per annullare e modificare <br>
     */
    public void updateItem() {
        Optional entityBean = grid.getSelectedItems().stream().findFirst();
        if (entityBean.isPresent()) {
            updateItem((AEntity) entityBean.get());
        }
    }

    /**
     * Apre un dialogo di editing <br>
     * Proveniente da un doppio click su una riga della Grid <br>
     * Passa al dialogo gli handler per annullare e modificare <br>
     */
    public void updateItem(AEntity entityBean) {
    }

    /**
     * Apre un dialogo di cancellazione<br>
     * Proveniente da un click sul bottone Delete della Top Bar <br>
     * Attivo solo se è selezionata una e una sola riga <br>
     * Passa al dialogo gli handler per annullare e cancellare <br>
     */
    public void deleteItem() {
    }

    public Span getSpan(final String avviso) {
        return htmlService.getSpanVerde(avviso);
    }

    public void addSpanVerde(final String message) {
        addSpan(new WrapSpan(message).color(AETypeColor.verde));
    }

    public void addSpanBlue(final String message) {
        addSpan(new WrapSpan(message).color(AETypeColor.blu));
    }

    public void addSpanRosso(final String message) {
        addSpan(new WrapSpan(message).color(AETypeColor.rosso));
    }

    public void addSpan(final String message) {
        addSpan(new WrapSpan(message));
    }

    public void addSpan(WrapSpan wrap) {
        Span span;

        if (wrap.getColor() == null) {
            wrap.color(AETypeColor.verde);
        }
        if (wrap.getWeight() == null) {
            wrap.weight(AEFontWeight.bold);
        }
        if (wrap.getFontHeight() == null) {
            if (width == 0 || width > 500) {
                wrap.fontHeight(AEFontHeight.em9);
            }
            else {
                wrap.fontHeight(AEFontHeight.em7);
            }
        }
        if (wrap.getLineHeight() == null) {
            if (width == 0 || width > 500) {
                wrap.lineHeight(AELineHeight.em3);
            }
            else {
                wrap.lineHeight(AELineHeight.em12);
            }
        }

        span = htmlService.getSpan(wrap);
        if (span != null) {
            this.add(span);
        }
    }

}

package it.algos.vaad23.backend.packages.utility.preferenza;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.page.*;
import com.vaadin.flow.data.renderer.*;
import com.vaadin.flow.router.*;
import it.algos.vaad23.backend.annotation.*;
import it.algos.vaad23.backend.boot.*;
import it.algos.vaad23.backend.entity.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.service.*;
import it.algos.vaad23.backend.wrapper.*;
import it.algos.vaad23.ui.views.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.vaadin.crudui.crud.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 27-mar-2022
 * Time: 10:29
 */
@PageTitle("Preferenze")
@Route(value = "preferenza", layout = MainLayout.class)
@AIView(lineawesomeClassnames = "wrench")
public class PreferenzaView extends VerticalLayout implements AfterNavigationObserver {

    @Autowired
    protected ApplicationContext appContext;

    protected Grid<Preferenza> grid;

    protected List<Preferenza> items;

    @Autowired
    protected PreferenzaBackend backend;

    @Autowired
    protected HtmlService htmlService;

    protected Button refreshButton;

    protected Button addButton;

    protected Button editButton;

    protected Button deleteButton;

    protected int width;

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        UI.getCurrent().getPage().retrieveExtendedClientDetails(details -> fixBrowser(details));
        this.fixAlert();
        this.fixTop();
        this.fixCrud();
        this.fixColumns();
        this.fixFields();
        this.fixOrder();
        this.fixAdditionalComponents();
        this.addListeners();
    }

    /**
     *
     */
    public void fixBrowser(ExtendedClientDetails details) {
        width = details.getBodyClientWidth();
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixAlert() {
        spanBlue("Preferenze registrate nel database mongoDB");
        spanRosso("Mostra solo le properties di un programma non multiCompany");
        span(String.format("VaadFlow=true per le preferenze del programma base '%s'", VaadVar.projectVaadFlow));
        span(String.format("VaadFlow=false per le preferenze del programma corrente '%s'", VaadVar.projectCurrent));
        span("NeedRiavvio=true se la preferenza ha effetto solo dopo un riavvio del programma");
    }

    /**
     * Costruisce un (eventuale) layout per bottoni di comando in testa alla grid <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixTop() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setClassName("buttons");
        layout.setPadding(false);
        layout.setSpacing(true);
        layout.setMargin(false);
        layout.setClassName("confirm-dialog-buttons");

        refreshButton = new Button();
        refreshButton.getElement().setAttribute("theme", "secondary");
        refreshButton.setIcon(new Icon(VaadinIcon.REFRESH));
        refreshButton.addClickListener(e -> refresh());
        layout.add(refreshButton);

        addButton = new Button();
        addButton.getElement().setAttribute("theme", "secondary");
        addButton.setIcon(new Icon(VaadinIcon.PLUS));
        addButton.addClickListener(e -> newItem());
        layout.add(addButton);

        editButton = new Button();
        editButton.getElement().setAttribute("theme", "secondary");
        editButton.setIcon(new Icon(VaadinIcon.PENCIL));
        layout.add(editButton);

        deleteButton = new Button();
        deleteButton.getElement().setAttribute("theme", "secondary");
        deleteButton.setIcon(new Icon(VaadinIcon.TRASH));
        layout.add(deleteButton);

        //        annullaButton.setText(textAnnullaButton);
        //        annullaButton.getElement().setAttribute("theme", "secondary");
        //        annullaButton.addClickListener(e -> annullaHandler());
        //        annullaButton.setIcon(new Icon(VaadinIcon.ARROW_LEFT));
        //        layout.add(annullaButton);

        this.add(layout);
    }

    /**
     * Logic configuration <br>
     * Qui vanno i collegamenti con la logica del backend <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixCrud() {
        // Create a listing component for a bean type
        grid = new Grid<>(Preferenza.class, false);

        // Pass all Preferenza objects to a grid from a Spring Data repository object
        grid.setItems(backend.findAll());

        // layout configuration
        setSizeFull();
        this.add(grid);
    }

    /**
     * Regola la visibilità delle colonne della grid <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixColumns() {
        grid.addColumns("code", "type");

        grid.addColumn(new ComponentRenderer<>(pref -> {

            return switch (pref.getType()) {
                case string -> new Label(pref.toString());
                default -> new Label("");
            };

        })).setHeader("Value").setKey("value");

        grid.addColumns("descrizione", "vaadFlow", "needRiavvio");
    }

    /**
     * Regola la visibilità dei fields del Form<br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixFields() {
    }

    /**
     * Regola l'ordinamento della <grid <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    public void fixOrder() {
    }


    /**
     * Componenti aggiuntivi oltre quelli base <br>
     * Tipicamente bottoni di selezione/filtro <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixAdditionalComponents() {
    }

    /**
     * Aggiunge tutti i listeners ai bottoni di 'topPlaceholder' che sono stati creati SENZA listeners <br>
     * <p>
     * Chiamato da afterNavigation() <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void addListeners() {
        // pass the row/item that the user double-clicked to method openDialog
        grid.addItemClickListener(listener -> {
            if (listener.getClickCount() == 2)
                openDialog(listener.getItem());
        });
    }

    public void newItem() {
        Preferenza entityBean = new Preferenza();
        entityBean.setType(AETypePref.string);
        PreferenzaDialog dialog = appContext.getBean(PreferenzaDialog.class, entityBean, CrudOperation.ADD);
        dialog.open(this::saveHandler, this::deleteHandler, this::annullaHandler);
    }

    public void openDialog(Preferenza entityBean) {
        PreferenzaDialog dialog = appContext.getBean(PreferenzaDialog.class, entityBean, CrudOperation.UPDATE);
        dialog.open(this::saveHandler, this::deleteHandler, this::annullaHandler);
    }

    protected void refresh() {
        grid.setItems(backend.findAll());
    }

    /**
     * Primo ingresso dopo il click sul bottone <br>
     */
    protected void saveHandler(final Preferenza entityBean, final CrudOperation operation) {
        grid.setItems(backend.findAll());
    }

    public AEntity deleteHandler(final Preferenza entityBean) {
        //        Notification.show(entityBean + " successfully deleted.", 3000, Notification.Position.BOTTOM_START);
        return null;
    }

    public AEntity annullaHandler(final Preferenza entityBean) {
        //        Notification.show(entityBean + " successfully deleted.", 3000, Notification.Position.BOTTOM_START);
        return null;
    }

    public Span getSpan(final String avviso) {
        return htmlService.getSpanVerde(avviso);
    }

    public void spanBlue(final String message) {
        span(new WrapSpan(message).color(AETypeColor.blu));
    }

    public void spanRosso(final String message) {
        span(new WrapSpan(message).color(AETypeColor.rosso));
    }

    public void span(final String message) {
        span(new WrapSpan(message));
    }

    public void span(WrapSpan wrap) {
        Span span;

        if (wrap.getColor() == null) {
            wrap.color(AETypeColor.verde);
        }
        if (wrap.getWeight() == null) {
            wrap.weight(AEFontWeight.bold);
        }
        if (wrap.getFontHeight() == null) {
            wrap.fontHeight(AEFontHeight.px14);
        }

        if (wrap.getLineHeight() == null) {
            if (width == 0 || width > 500) {
                wrap.lineHeight(AELineHeight.px2);
            }
            else {
                wrap.lineHeight(AELineHeight.normal);
            }
        }

        span = htmlService.getSpan(wrap);
        if (span != null) {
            this.add(span);
        }
    }

}

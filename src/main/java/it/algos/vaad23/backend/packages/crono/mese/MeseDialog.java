package it.algos.vaad23.backend.packages.crono.mese;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.dialog.*;
import com.vaadin.flow.component.formlayout.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.notification.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad23.backend.exception.*;
import it.algos.vaad23.backend.service.*;
import it.algos.vaad23.backend.wrapper.*;
import it.algos.vaad23.ui.dialog.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.vaadin.crudui.crud.*;

import javax.annotation.*;
import java.util.function.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 01-apr-2022
 * Time: 15:08
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MeseDialog extends Dialog {

    protected final H2 titleField = new H2();

    /**
     * Corpo centrale del Form <br>
     * Placeholder (eventuale, presente di default) <br>
     */
    protected final FormLayout formLayout = new FormLayout();

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public TextService textService;

    @Autowired
    public LogService logger;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public MeseBackend preferenzaBackend;

    protected String textAnnullaButton = "Annulla";

    protected String textSaveButton = "Registra";

    protected String textDeleteButton = "Cancella";

    protected Button annullaButton = new Button(textAnnullaButton);

    protected Button saveButton = new Button(textSaveButton);

    protected Button deleteButton = new Button(textDeleteButton);


    //--collegamento tra i fields e la entityBean
    protected BeanValidationBinder<Mese> binder;

    IntegerField ordine;

    TextField code;

    TextField descrizione;


    private Mese currentItem;

    private BiConsumer<Mese, CrudOperation> saveHandler;

    private Consumer<Mese> deleteHandler;

    private Consumer<Mese> annullaHandler;

    private CrudOperation operation;

    /**
     * Constructor not @Autowired. <br>
     * Non utilizzato e non necessario <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Se c'è un SOLO costruttore questo diventa automaticamente @Autowired e IntelliJ Idea 'segna' in rosso i
     * parametri <br>
     * Per evitare il bug (solo visivo), aggiungo un costruttore senza parametri <br>
     */
    public MeseDialog() {
    }// end of second constructor not @Autowired


    /**
     * Costruttore con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * L'istanza DEVE essere creata con appContext.getBean(PreferenzaDialog.class, operation, itemSaver, itemDeleter, itemAnnulla); <br>
     *
     * @param entityBean The item to edit; it may be an existing or a newly created instance
     * @param operation  The operation being performed on the item (addNew, edit, editNoDelete, editDaLink, showOnly)
     */
    public MeseDialog(Mese entityBean, CrudOperation operation) {
        this.currentItem = entityBean;
        this.operation = operation;
    }// end of constructor not @Autowired

    /**
     * Performing the initialization in a constructor is not suggested as the state of the UI is not properly set up when the constructor is invoked. <br>
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, ma l'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     * Se viene implementata una sottoclasse, passa di qui per ogni sottoclasse oltre che per questa istanza <br>
     * Se esistono delle sottoclassi, passa di qui per ognuna di esse (oltre a questa classe madre) <br>
     */
    @PostConstruct
    private void postConstruct() {
        //--Titolo placeholder del dialogo
        this.add(fixHeader());

        //--Form placeholder standard per i campi
        this.add(getFormLayout());
        //--Corpo centrale del Dialog
        fixBody();

        //--spazio per distanziare i bottoni sottostanti
        this.add(new H3());

        //--Barra placeholder dei bottoni, creati e regolati
        this.add(fixBottom());

        //--Listener varii
        this.fixListener();
    }

    /**
     * Opens the given item for editing in the dialog. <br>
     * Crea i fields e visualizza il dialogo <br>
     * Gli handler vengono aggiunti qui perché non passano come parametri di appContext.getBean(PreferenzaDialog.class) <br>
     * La view è già pronta, i listener anche e rimane solo da lanciare il metodo open() nella superclasse <br>
     *
     * @param saveHandler    funzione associata al bottone 'accetta' ('registra', 'conferma')
     * @param annullaHandler funzione associata al bottone 'annulla' (bottone obbligatorio, azione facoltativa)
     */
    public void open(final BiConsumer<Mese, CrudOperation> saveHandler, final Consumer<Mese> annullaHandler) {
        this.open(saveHandler, null, annullaHandler);
    }

    /**
     * Opens the given item for editing in the dialog. <br>
     * Crea i fields e visualizza il dialogo <br>
     * Gli handler vengono aggiunti qui perché non passano come parametri di appContext.getBean(PreferenzaDialog.class) <br>
     * La view è già pronta, i listener anche e rimane solo da lanciare il metodo open() nella superclasse <br>
     *
     * @param saveHandler    funzione associata al bottone 'accetta' ('registra', 'conferma')
     * @param deleteHandler  funzione associata al bottone 'delete' (eventuale)
     * @param annullaHandler funzione associata al bottone 'annulla' (bottone obbligatorio, azione facoltativa)
     */
    public void open(final BiConsumer<Mese, CrudOperation> saveHandler, final Consumer<Mese> deleteHandler, final Consumer<Mese> annullaHandler) {
        this.saveHandler = saveHandler;
        this.deleteHandler = deleteHandler;
        this.annullaHandler = annullaHandler;

        super.open();
    }

    /**
     * Titolo del dialogo <br>
     * Placeholder (eventuale, presente di default) <br>
     */
    protected Component fixHeader() {
        String tag = switch (operation) {
            case READ -> "Mostra";
            case ADD -> "Nuova";
            case UPDATE -> "Modifica";
            case DELETE -> "Cancella";
            default -> "Edit";
        };

        return new H2(String.format("%s %s", tag, currentItem.getClass().getSimpleName().toLowerCase()));
    }


    /**
     * Body placeholder per i campi <br>
     * Normalmente colonna doppia <br>
     */
    protected Div getFormLayout() {
        Div div;

        formLayout.setResponsiveSteps(
                // Use one column by default
                new FormLayout.ResponsiveStep("0", 1),
                // Use two columns, if layout's width exceeds 500px
                new FormLayout.ResponsiveStep("500px", 2)
        );

        formLayout.addClassName("no-padding");
        div = new Div(formLayout);
        div.addClassName("has-padding");

        return div;
    }


    /**
     * Crea i fields
     * <p>
     * Crea un nuovo binder (vuoto) per questo Dialog e questa Entity
     * <p>
     * Inizializza le properties grafiche (caption, visible, editable, width, ecc)
     * Aggiunge i fields al binder
     * <p>
     * Aggiunge eventuali fields specifici direttamente al layout grafico (senza binder e senza fieldMap)
     * Legge la entityBean ed inserisce nella UI i valori di eventuali fields NON associati al binder
     */
    protected void fixBody() {
        //--Crea un nuovo binder (vuoto) per questo Dialog e questa entityBean (currentItem)
        binder = new BeanValidationBinder(currentItem.getClass());

        code = new TextField("Code");
        code.setReadOnly(operation == CrudOperation.DELETE);

        descrizione = new TextField("Descrizione");
        descrizione.setReadOnly(operation == CrudOperation.DELETE);

        binder.bindInstanceFields(this);

        // Updates the value in each bound field component
        binder.readBean(currentItem);

        formLayout.add(code, descrizione);
        formLayout.setColspan(descrizione, 2);
    }


    /**
     * Barra dei bottoni <br>
     * Placeholder (eventuale, presente di default) <br>
     */
    protected Component fixBottom() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setClassName("buttons");
        layout.setPadding(false);
        layout.setSpacing(true);
        layout.setMargin(false);
        layout.setClassName("confirm-dialog-buttons");

        Label spazioVuotoEspandibile = new Label("");

        annullaButton.setText(textAnnullaButton);
        annullaButton.getElement().setAttribute("theme", operation == CrudOperation.ADD ? "secondary" : "primary");
        annullaButton.addClickListener(e -> annullaHandler());
        annullaButton.setIcon(new Icon(VaadinIcon.ARROW_LEFT));
        annullaButton.addClickShortcut(Key.ARROW_LEFT, KeyModifier.CONTROL); //@todo non funziona
        layout.add(annullaButton);

        //        layout.add(spazioVuotoEspandibile);

        saveButton.setText(textSaveButton);
        saveButton.getElement().setAttribute("theme", operation == CrudOperation.ADD ? "primary" : "secondary");
        saveButton.addClickListener(e -> saveHandler());
        saveButton.setIcon(new Icon(VaadinIcon.CHECK));
        saveButton.addClickShortcut(Key.ENTER);
        layout.add(saveButton);

        if (operation == CrudOperation.DELETE) {
            deleteButton.setText(textDeleteButton);
            deleteButton.getElement().setAttribute("theme", "error");
            deleteButton.addClickListener(e -> deleteHandler());
            deleteButton.setIcon(new Icon(VaadinIcon.TRASH));
            layout.add(deleteButton);
        }

        layout.setFlexGrow(1, spazioVuotoEspandibile);

        //--Controlla la visibilità dei bottoni
        saveButton.setVisible(operation == CrudOperation.ADD || operation == CrudOperation.UPDATE);

        return layout;
    }

    public void fixListener() {
        //        type.addValueChangeListener(e -> sincroValueToPresentation());
    }


    public void saveHandler() {
        try {
            if (binder.writeBeanIfValid(currentItem)) {
                binder.writeBean(currentItem);
            }
            else {
                logger.info(new WrapLog().exception(new AlgosException("binder non valido")));
                return;
            }
        } catch (ValidationException error) {
            logger.error(error);
            return;
        }
        preferenzaBackend.update(currentItem);
        switch (operation) {
            case ADD -> Avviso.show("Aggiunto un elemento").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            case UPDATE -> Avviso.show("Registrata la modifica").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            default -> Notification.show("Caso non previsto").addThemeVariants(NotificationVariant.LUMO_ERROR);
        }

        if (saveHandler != null) {
            saveHandler.accept(currentItem, operation);
        }
        close();
    }

    public void deleteHandler() {
        Mese item = currentItem;
        if (deleteHandler != null) {
            deleteHandler.accept(item);
        }
        close();
    }

    public void annullaHandler() {
        switch (operation) {
            case ADD -> Avviso.show("Mese non registrato").addThemeVariants(NotificationVariant.LUMO_PRIMARY);
            case READ -> Avviso.show("Mese letto").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            case UPDATE -> Avviso.show("Mese non modificato").addThemeVariants(NotificationVariant.LUMO_PRIMARY);
            case DELETE -> Avviso.show("Mese non cancellato").addThemeVariants(NotificationVariant.LUMO_PRIMARY);
            default -> Notification.show("Caso non previsto").addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
        if (annullaHandler != null) {
            annullaHandler.accept(currentItem);
        }
        close();
    }

}

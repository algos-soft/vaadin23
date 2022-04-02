package it.algos.vaad23.backend.packages.crono.mese;

import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad23.backend.logic.*;
import it.algos.vaad23.ui.views.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.vaadin.crudui.crud.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 01-apr-2022
 * Time: 15:08
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MeseDialog extends CrudDialog {

    //--I fields devono essere class variable e non local variable
    private TextField code;

    //--I fields devono essere class variable e non local variable
    private TextField descrizione;


    /**
     * Costruttore con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * L'istanza DEVE essere creata con appContext.getBean(PreferenzaDialog.class, operation, itemSaver, itemDeleter, itemAnnulla); <br>
     *
     * @param entityBean  The item to edit; it may be an existing or a newly created instance
     * @param operation   The operation being performed on the item (addNew, edit, editNoDelete, editDaLink, showOnly)
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public MeseDialog(final Mese entityBean, final CrudOperation operation, final CrudBackend crudBackend) {
        super(entityBean, operation, crudBackend);
    }// end of constructor not @Autowired


    /**
     * Crea i fields
     * Inizializza le properties grafiche (caption, visible, editable, width, ecc)
     * Aggiunge i fields al binder
     * Aggiunge eventuali fields specifici direttamente al layout grafico (senza binder e senza fieldMap)
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixBody() {
        code = new TextField("Code");
        code.setReadOnly(operation == CrudOperation.DELETE);

        descrizione = new TextField("Descrizione");
        descrizione.setReadOnly(operation == CrudOperation.DELETE);

        formLayout.add(code, descrizione);
    }

}// end of crud Dialog class
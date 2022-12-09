package it.algos.vaad23.backend.utility;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.dependency.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad23.backend.boot.*;
import it.algos.vaad23.ui.views.*;

import javax.annotation.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: Thu, 08-Dec-2022
 * Time: 13:46
 * Utilizzato per operazioni varie <br>
 */
@SpringComponent
@Route(value = VaadCost.TAG_WIZ, layout = MainLayout.class)
@CssImport("./styles/shared-styles.css")
public class UtilityView extends VerticalLayout {

    /**
     * Questa classe viene costruita partendo da @Route e NON dalla catena @Autowired di SpringBoot <br>
     */
    public UtilityView() {
        super();
    }// end of Vaadin/@Route constructor


    @PostConstruct
    protected void postConstruct() {
        initView();
    }

    /**
     * Qui va tutta la logica iniziale della view principale <br>
     */
    protected void initView() {
        this.setMargin(true);
        this.setPadding(false);
        this.setSpacing(false);

        this.titolo();
    }

    /**
     * Chiama i reset di alcune classi in un determinato ordine per i riferimenti incrociati <br>
     */
    public void paragrafoResetOrdinati() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setPadding(false);
        layout.setSpacing(false);
        H3 paragrafo = new H3("Reset ordinati");
        paragrafo.getElement().getStyle().set("color", "blue");

        layout.add(new Label("Esegui i reset di alcune collection in un ordine fisso prestabilito"));

        Button bottone = new Button("Reset");
        bottone.getElement().setAttribute("theme", "primary");
        bottone.addClickListener(event -> esegueReset());

        this.add(paragrafo);
        layout.add(bottone);
        this.add(layout);
    }

    public void titolo() {
        H1 titolo = new H1("Gestione Utility");
        titolo.getElement().getStyle().set("color", "green");
        this.add(titolo);
    }

    public void esegueReset() {
        return;
    }

}

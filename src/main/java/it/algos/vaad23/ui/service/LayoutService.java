package it.algos.vaad23.ui.service;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.*;
import com.vaadin.flow.spring.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 06-mar-2022
 * Time: 12:48
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class LayoutService {


    /**
     * Metodo chiamato quando esiste una sessione <br>
     */
    public ListItem getItem(String menuTitle, String iconClass, Class<? extends Component> view) {
        ListItem item = new ListItem();
        RouterLink link = new RouterLink();

        // Controlla che esista una sessione
        if (VaadinService.getCurrent() == null) {
            System.out.println(" ");
            System.out.println("ERRORE");
            System.out.println(" ");
            return null;
        }

        // Use Lumo classnames for various styling
        link.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary");
        link.setRoute(view);

        Span text = new Span(menuTitle);
        // Use Lumo classnames for various styling
        text.addClassNames("font-medium", "text-s");

        link.add(new LineAwesomeIcon(iconClass), text);
        item.add(link);

        return item;
    }


    /**
     * Simple wrapper to create icons using LineAwesome iconset. See
     * https://icons8.com/line-awesome
     */
    @NpmPackage(value = "line-awesome", version = "1.3.0")
    public static class LineAwesomeIcon extends Span {

        public LineAwesomeIcon(String lineawesomeClassnames) {
            // Use Lumo classnames for suitable font size and margin
            addClassNames("me-s", "text-l");
            if (!lineawesomeClassnames.isEmpty()) {
                addClassNames(lineawesomeClassnames);
            }
        }

    }

}

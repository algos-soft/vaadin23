package it.algos.vaad23.ui.views;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 06-mar-2022
 * Time: 11:49
 * A simple navigation item component, based on ListItem element.
 */
public class MenuItemInfo2 extends ListItem {

    private final Class<? extends Component> view;

    public MenuItemInfo2(String menuTitle, String iconClass, Class<? extends Component> view) {
        this.view = view;
        RouterLink link = new RouterLink();

        // Use Lumo classnames for various styling
        link.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary");
        link.setRoute(view);

        Span text = new Span(menuTitle);
        // Use Lumo classnames for various styling
        text.addClassNames("font-medium", "text-s");

        link.add(new LineAwesomeIcon(iconClass), text);
        add(link);
    }

    public Class<?> getView() {
        return view;
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

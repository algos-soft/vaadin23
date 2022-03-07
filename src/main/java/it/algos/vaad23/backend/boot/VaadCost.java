package it.algos.vaad23.backend.boot;

import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.spring.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 06-mar-2022
 * Time: 17:20
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class VaadCost {

    public final static String VUOTA = "";

    public final static VaadinIcon DEFAULT_ICON = VaadinIcon.ASTERISK;

    public final static String DEFAULT_ICON_NAME = "asterisk";

}

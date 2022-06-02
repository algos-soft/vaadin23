package it.algos.simple.backend.boot;

import com.vaadin.flow.spring.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mar, 15-mar-2022
 * Time: 09:22
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SimpleCost {

    public static final String TAG_SIMPLE_VERSION = "simpleversion";

    public static final String TAG_SIMPLE_PREFERENCES = "simplepreferences";

}

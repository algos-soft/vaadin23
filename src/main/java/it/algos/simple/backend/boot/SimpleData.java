package it.algos.simple.backend.boot;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.simple.backend.boot.SimpleCost.*;
import it.algos.vaad23.backend.boot.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: Wed, 15-Jun-2022
 * Time: 15:00
 */
@SpringComponent
@Qualifier(QUALIFIER_DATA_SIMPLE)
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SimpleData extends VaadData {


    public void inizia() {
        super.inizia();
    }

}

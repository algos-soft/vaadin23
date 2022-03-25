package it.algos.test;

import com.vaadin.flow.spring.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.*;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: gio, 24-mar-2022
 * Time: 11:28
 * Layer per gestire ApplicationContext
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public abstract class STest extends ATest {

    @Autowired
    protected ApplicationContext appContext;

}

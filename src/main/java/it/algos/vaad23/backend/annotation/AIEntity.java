package it.algos.vaad23.backend.annotation;

import static it.algos.vaad23.backend.boot.VaadCost.*;

import java.lang.annotation.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 13-ott-2017
 * Time: 14:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //can use in class and interface.
public @interface AIEntity {

    /**
     * (Optional) entity indispensabile per il reset
     */
    String preReset() default VUOTA;


}// end of interface annotation

package it.algos.vaad23.backend.wrapper;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad23.backend.enumeration.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 25-mar-2022
 * Time: 13:41
 * Wrapper di informazioni (Fluent API) per costruire un elemento Span <br>
 * Può contenere:
 * -weight (AETypeWeight) grassetto
 * -color (AETypeColor) colore
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WrapSpan {

    private String message;

    private AETypeWeight weight;

    private AETypeColor color;

    private AETypeHeight height;

    public WrapSpan() {
    }

    public WrapSpan message(String message) {
        this.message = message;
        return this;
    }

    public WrapSpan weight(AETypeWeight weight) {
        this.weight = weight;
        return this;
    }

    public WrapSpan color(AETypeColor color) {
        this.color = color;
        return this;
    }

    public WrapSpan height(AETypeHeight height) {
        this.height = height;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AETypeWeight getWeight() {
        return weight != null ? weight : AETypeWeight.normal;
    }

    public AETypeColor getColor() {
        return color != null ? color : AETypeColor.blu;
    }

    public AETypeHeight getHeight() {
        return height != null ? height : AETypeHeight.normal;
    }

}

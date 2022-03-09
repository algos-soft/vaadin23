package it.algos.vaad23.backend.packages;

import com.querydsl.core.annotations.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad23.backend.annotation.*;
import it.algos.vaad23.backend.entity.*;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import javax.validation.constraints.*;


/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mer, 09-mar-2022
 * Time: 18:17
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@SpringComponent
@QueryEntity
@Document(collection = "versione") //@todo Modificare con iniziale minuscola
@TypeAlias("versione") //@todo Modificare con iniziale minuscola
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderVersione")
@EqualsAndHashCode(callSuper = false)
//@AIEntity(recordName = "Versione", keyPropertyName = "code", usaCompany = false) //@todo Modificare il field
@AIView(menuName = "Versione", menuIcon = VaadinIcon.COG, sortProperty = "ordine")
//@AIScript(sovraScrivibile = false)
//@AIList(fields = "code,descrizione", usaRowIndex = true)
//@AIForm(fields = "code,descrizione", usaSpostamentoTraSchede = true)
public class Versione extends AEntity {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;

    //@TODO
    // Le properties riportate sono INDICATIVE e possono/debbono essere sostituite
    //@TODO

    /**
     * ordinamento (obbligatorio, unico) <br>
     */
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    //    @AIField(type = AETypeField.integer, caption = "ordine", typeNum = AETypeNum.positiviOnly)
    //    @AIColumn(header = "#", widthEM = 3)
    public int ordine;

    /**
     * codice di riferimento (obbligatorio, unico)
     */
    @NotBlank()
    @Size(min = 3)
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    //    @AIField(type = AETypeField.text, required = true, focus = true, caption = "Codice")
    //    @AIColumn(header = "Code")
    public String code;


    /**
     * descrizione (facoltativa)
     */
    //    @AIField(type = AETypeField.text, caption = "Descrizione completa")
    //    @AIColumn(header = "Descrizione", flexGrow = true)
    public String descrizione;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getCode();
    }

}// end of Bean
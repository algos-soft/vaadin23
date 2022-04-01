package it.algos.vaad23.backend.packages.crono.mese;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.entity.*;
import lombok.*;
import org.springframework.data.mongodb.core.index.*;

import javax.validation.constraints.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: gio, 31-mar-2022
 * Time: 18:41
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
//Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder()
@EqualsAndHashCode(callSuper = false)
public class Mese extends AEntity {

    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    public int ordine;

    @NotBlank()
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    public String code;

    public String descrizione;

    @Override
    public String toString() {
        return VUOTA;
    }

    public int getOrdine() {
        return ordine;
    }

    public void setOrdine(int ordine) {
        this.ordine = ordine;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

}// end of crud entity class
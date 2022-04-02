package it.algos.vaad23.backend.packages.crono.mese;

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
        return code;
    }

}// end of crud entity class
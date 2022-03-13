package it.algos.vaad23.backend.packages.versione;

import it.algos.vaad23.backend.entity.*;
import org.springframework.data.mongodb.core.index.*;

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
public class Versione extends AEntity {

    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    public int ordine;

    @NotBlank()
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    public String code;

    public String descrizione;

    @Override
    public String toString() {
        return "";
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

}// end of Bean
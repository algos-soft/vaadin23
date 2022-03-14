package it.algos.vaad23.backend.packages.versione;

import it.algos.vaad23.backend.entity.*;
import it.algos.vaad23.backend.enumeration.*;

import java.time.*;


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

    //    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    //    public int ordine;

    //    @NotBlank()
    //    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    //    public String code;

    //    @AIField(type = AETypeField.enumeration, enumClazz = AETypeVers.class, usaComboBox = true)
    public AETypeVers type;

    public double release;

    public String titolo;

    public LocalDate giorno;

    public String descrizione;

    public boolean usaBase;

    public boolean usaCompany;


    @Override
    public String toString() {
        return "";
    }

    public AETypeVers getType() {
        return type;
    }

    public void setType(AETypeVers type) {
        this.type = type;
    }

    public double getRelease() {
        return release;
    }

    public void setRelease(double release) {
        this.release = release;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public LocalDate getGiorno() {
        return giorno;
    }

    public void setGiorno(LocalDate giorno) {
        this.giorno = giorno;
    }

    public boolean isUsaBase() {
        return usaBase;
    }

    public void setUsaBase(boolean usaBase) {
        this.usaBase = usaBase;
    }

    public boolean isUsaCompany() {
        return usaCompany;
    }

    public void setUsaCompany(boolean usaCompany) {
        this.usaCompany = usaCompany;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

}// end of crud entity class
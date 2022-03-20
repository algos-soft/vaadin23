package it.algos.vaad23.backend.packages.utility.versione;

import it.algos.vaad23.backend.entity.*;
import it.algos.vaad23.backend.enumeration.*;
import org.springframework.data.mongodb.core.index.*;

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

    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    public int ordine;

    public AETypeVers type;

    public double release;

    public String titolo;

    public LocalDate giorno;

    public String descrizione;

    public String company;

    public boolean vaadin23;


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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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


    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public boolean isVaadin23() {
        return vaadin23;
    }

    public void setVaadin23(boolean vaadin23) {
        this.vaadin23 = vaadin23;
    }

}// end of crud entity class
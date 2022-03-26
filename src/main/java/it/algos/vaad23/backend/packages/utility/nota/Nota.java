package it.algos.vaad23.backend.packages.utility.nota;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.entity.*;
import it.algos.vaad23.backend.enumeration.*;

import java.time.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 18-mar-2022
 * Time: 06:55
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
public class Nota extends AEntity {

    public AENotaLevel livello;

    public AETypeLog type;

    public LocalDate inizio;

    public String descrizione;

    public boolean fatto;

    public LocalDate fine;

    @Override
    public String toString() {
        return VUOTA;
    }

    public AENotaLevel getLivello() {
        return livello;
    }

    public void setLivello(AENotaLevel livello) {
        this.livello = livello;
    }

    public AETypeLog getType() {
        return type;
    }

    public void setType(AETypeLog type) {
        this.type = type;
    }

    public LocalDate getInizio() {
        return inizio;
    }

    public void setInizio(LocalDate inizio) {
        this.inizio = inizio;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public boolean isFatto() {
        return fatto;
    }

    public void setFatto(boolean fatto) {
        this.fatto = fatto;
    }

    public LocalDate getFine() {
        return fine;
    }

    public void setFine(LocalDate fine) {
        this.fine = fine;
    }

}// end of crud entity class
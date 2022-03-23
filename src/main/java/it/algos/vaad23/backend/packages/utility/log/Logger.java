package it.algos.vaad23.backend.packages.utility.log;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.entity.*;
import it.algos.vaad23.backend.enumeration.*;

import java.time.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mer, 16-mar-2022
 * Time: 19:47
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
public class Logger extends AEntity {


    public AELevelLog livello;

    public AETypeLog type;

    public LocalDateTime evento;

    public String descrizione;

    public String company;

    public String user;

    public String address;

    public String classe;

    public String metodo;

    public int linea;

    public AELevelLog getLivello() {
        return livello;
    }

    public void setLivello(AELevelLog livello) {
        this.livello = livello;
    }

    public AETypeLog getType() {
        return type;
    }

    public void setType(AETypeLog type) {
        this.type = type;
    }

    public LocalDateTime getEvento() {
        return evento;
    }

    public void setEvento(LocalDateTime evento) {
        this.evento = evento;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return VUOTA;
    }


}// end of crud entity class
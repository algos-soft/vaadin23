package it.algos.vaad23.backend.packages.geografia.continente;

import it.algos.vaad23.backend.entity.*;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 13-mar-2022
 * Time: 06:21
 */
public class Continente extends AEntity {

    @NotNull
    @Id
    @GeneratedValue
    private Long id;

    public int ordine;

    @NotNull
    public String nome;

    public boolean abitato;

    public int getOrdine() {
        return ordine;
    }

    public void setOrdine(int ordine) {
        this.ordine = ordine;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAbitato() {
        return abitato;
    }

    public void setAbitato(boolean abitato) {
        this.abitato = abitato;
    }

}

package it.algos.vaad23.backend.packages.geografia.continente;

import it.algos.vaad23.backend.entity.*;

import javax.validation.constraints.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 13-mar-2022
 * Time: 06:21
 */
public class Continente extends AEntity {

    //    @NotNull
    //    @Id
    //    @GeneratedValue
    //    private Long id;

    private int ordine;

    @NotNull
    private String nome;

    private boolean abitato;

    public Continente(String id, int ordine, String nome, boolean abitato) {
        this.id = id;
        this.ordine = ordine;
        this.nome = nome;
        this.abitato = abitato;
    }

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

package it.algos.vaad23.backend.enumeration;

import java.util.*;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: mer, 26-set-2018
 * Time: 07:39
 */
public enum AETypeLog implements AIType {

    system("system"),
    setup("setup"),
    login("login"),
    startup("startup"),
    checkMenu("checkMenu"),
    checkData("checkData"),
    preferenze("preferenze"),
    nuovo("newEntity"),
    edit("edit"),
    modifica("modifica"),
    delete("delete"),
    deleteAll("deleteAll"),
    mongo("mongoDB"),
    debug("debug"),
    info("info"),
    warn("warn"),
    error("error"),
    wizard("wizard"),
    wizardDoc("wizardDoc"),
    importo("import"),
    export("export"),
    download("download"),
    upload("upload"),
    update("update"),
    elabora("elabora"),
    reset("reset"),
    utente("utente"),
    password("password"),
    bio("cicloBio"),
    ;

    private String tag;


    AETypeLog(String tag) {
        this.tag = tag;
    }


    public static List<AETypeLog> getAll() {
        return Arrays.stream(values()).toList();
    }


    public static List<String> getAllTag() {
        List<String> listaTag = new ArrayList<>();

        getAll().forEach(type -> listaTag.add(type.getTag()));
        return listaTag;
    }

    public static AETypeLog getType(final String tag) {
        return getAll()
                .stream()
                .filter(type -> type.getTag().equals(tag))
                .findAny()
                .orElse(null);
    }


    @Override
    public String getTag() {
        return tag;
    }
}


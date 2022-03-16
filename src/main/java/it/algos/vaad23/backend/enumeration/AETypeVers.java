package it.algos.vaad23.backend.enumeration;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 10-feb-2022
 * Time: 13:46
 */
public enum AETypeVers {
    setup, patch, miglioramento;

    public static List<AETypeVers> getAll() {
        return Arrays.stream(values()).toList();
    }
}

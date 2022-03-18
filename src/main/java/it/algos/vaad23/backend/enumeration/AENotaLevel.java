package it.algos.vaad23.backend.enumeration;

import static com.vaadin.flow.server.frontend.FrontendUtils.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 18-mar-2022
 * Time: 07:13
 */
public enum AENotaLevel implements AIType {
    dettaglio(GREEN),
    normale(BRIGHT_BLUE),
    urgente(YELLOW),
    critico(RED),
    ;

    public String tag;


    AENotaLevel(String tag) {
        this.tag = tag;
    }

    public static List<AENotaLevel> getAll() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public String getTag() {
        return tag;
    }
}

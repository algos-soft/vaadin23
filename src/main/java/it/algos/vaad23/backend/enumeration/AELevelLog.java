package it.algos.vaad23.backend.enumeration;

import static com.vaadin.flow.server.frontend.FrontendUtils.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.interfaces.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 07-mar-2022
 * Time: 11:45
 */
public enum AELevelLog implements AIPref {
    debug(GREEN),
    info(BRIGHT_BLUE),
    warn(YELLOW),
    error(RED),
    ;

    public String color;


    AELevelLog(String color) {
        this.color = color;
    }

    public static List<AELevelLog> getAll() {
        return Arrays.stream(values()).toList();
    }

    /**
     * Stringa di valori (text) da usare per memorizzare la preferenza <br>
     * La stringa è composta da tutti i valori separati da virgola <br>
     * Poi, separato da punto e virgola, viene il valore corrente <br>
     *
     * @return stringa di valori e valore di default
     */
    @Override
    public String getPref() {
        StringBuffer buffer = new StringBuffer();

        getAll().forEach(level -> buffer.append(level.name() + VIRGOLA));

        buffer.delete(buffer.length() - 1, buffer.length());
        buffer.append(PUNTO_VIRGOLA);
        buffer.append(name());

        return buffer.toString();
    }

    public String getColor() {
        return color;
    }
    //@todo Funzionalità ancora da implementare
    //    /**
    //     * Azione memorizzata nelle preferenze <br>
    //     *
    //     * @return azione
    //     */
    //    public static EALogLivello get(PreferenzaService pref) {
    //        EALogLivello eaLogLevl = null;
    //        String livelloStr = VUOTA;
    //
    //        if (pref != null) {
    //            livelloStr = pref.getStr(EAPreferenza.logLevelCorrente);
    //        }// end of if cycle
    //
    //        if (livelloStr.length() > 0) {
    //            eaLogLevl = EALogLivello.valueOf(livelloStr);
    //        }// end of if cycle
    //
    //        return eaLogLevl;
    //    }// end of method

}

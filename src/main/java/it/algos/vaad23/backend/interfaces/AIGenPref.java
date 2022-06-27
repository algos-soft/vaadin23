package it.algos.vaad23.backend.interfaces;

import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.service.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: Sun, 26-Jun-2022
 * Time: 08:42
 */
public interface AIGenPref {

    void setPreferenceService(PreferenceService preferenceService);

    void setLogger(LogService logger);

    Object get();

    Object getValue();

    String getStr();

    boolean is();

    public int getInt();

    public String getEnumAll();

    public AITypePref getEnumCurrentObj();

    public String getEnumCurrentTxt();

    AETypePref getType();

    String getKeyCode();

    String getDescrizione();

    Object getDefaultValue();

    void setDate(DateService date);

    void setText(TextService text);

    void setValue(Object javaValue);

    void setEnumCurrent(String currentValue);

}// end of interface

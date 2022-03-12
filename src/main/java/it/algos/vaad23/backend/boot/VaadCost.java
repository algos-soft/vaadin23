package it.algos.vaad23.backend.boot;

import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.spring.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 06-mar-2022
 * Time: 17:20
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class VaadCost {

    public static final String VUOTA = "";

    public static final String PIENA = "Piena";

    public static final VaadinIcon DEFAULT_ICON = VaadinIcon.ASTERISK;

    public static final String DEFAULT_ICON_NAME = "asterisk";

    public static final String FORWARD = " -> ";

    public static final String SEP = " - ";

    public static final String PUNTO = ".";

    public static final String VIRGOLA = ",";

    public static final String PUNTO_VIRGOLA = ";";

    public static final String PUNTO_INTERROGATIVO = "?";

    public static final String DUE_PUNTI = ":";

    public static final String SPAZIO = " ";

    public static final String DOPPIO_SPAZIO = SPAZIO + SPAZIO;

    public static final String QUADRA_INI = "[";

    public static final String QUADRA_INI_REGEX = "\\[";

    public static final String DOPPIE_QUADRE_INI = QUADRA_INI + QUADRA_INI;

    public static final String QUADRA_END = "]";

    public static final String QUADRA_END_REGEX = "\\]";

    public static final String DOPPIE_QUADRE_END = QUADRA_END + QUADRA_END;

    public static final String PARENTESI_TONDA_INI = "(";

    public static final String PARENTESI_TONDA_END = ")";

    public static final String SLASH = "/";

    public static final String PIPE = "|";

    public static final String REGEX_PIPE = "\\|";

    public static final String RETURN = "\n";

    public static final String SORT_SPRING_ASC = "ASC";

    public static final String SORT_SPRING_DESC = "DESC";


    public static final String KEY_NULL = "null";

    public static final String KEY_ROUTE_TYPE = "type";

    public static final String KEY_BEAN_CLASS = "beanClass";

    public static final String KEY_FORM_TYPE = "formType";

    public static final String KEY_BEAN_ENTITY = "beanID";

    public static final String KEY_BEAN_PREV_ID = "beanPrevID";

    public static final String KEY_BEAN_NEXT_ID = "beanNextID";

    public static final String KEY_SERVICE_CLASS = "serviceClass";

    public static final String KEY_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";

    public static final String KEY_SESSION_LOGIN = "SESSION_LOGIN";

    public static final String KEY_SESSION_MOBILE = "sessionMobile";

    public static final String KEY_MAPPA_PARSE = "parse";

    public static final String KEY_MAPPA_PAGEID = "pageid";

    public static final String KEY_MAPPA_TITLE = "title";

    public static final String KEY_MAPPA_TEXT = "wikitext";

    public static final String KEY_MAPPA_DOMAIN = "domain";

    public static final String KEY_MAPPA_LAST_MODIFICA = "lastModifica";


    /**
     * The constant ROUTE_NAME.
     */
    public static final String ROUTE_NAME_LOGIN = "Login";

    public static final String ROUTE_NAME_LOGIN_ERROR = ROUTE_NAME_LOGIN + "?error";

    public static final String ROUTE_NAME_ABOUT = "About";

    public static final String ROUTE_NAME_HOME = "Home";

    public static final String ROUTE_NAME_GENERIC_VIEW = "vista";

    public static final String ROUTE_NAME_GENERIC_LIST = "list";

    public static final String ROUTE_NAME_GENERIC_FORM = "form";

}

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

    public static final String ALGOS = "algos";

    public static final String PATH_RISORSE = "src/main/resources/META-INF/resources/";

    public static final String PATH_ALGOS = "it.algos";

    public static final String VUOTA = "";

    public static final String CAPO = "\n";

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

    public static final String DUE_PUNTI_SPAZIO = DUE_PUNTI + SPAZIO;

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

    public static final String TXT_SUFFIX = ".txt";

    public static final String XML_SUFFIX = ".xml";

    public static final String JAVA_SUFFIX = ".java";

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


    public static final String SWITCH = "Switch - caso non definito";

    /**
     * tag per la singola graffa di apertura
     */
    public static final String GRAFFA_INI = "{";

    /**
     * tag per le doppie graffe di apertura
     */
    public static final String DOPPIE_GRAFFE_INI = GRAFFA_INI + GRAFFA_INI;

    /**
     * tag per la singola graffa di apertura
     */
    public static final String GRAFFA_END = "}";

    /**
     * tag per le doppie graffe di chiusura
     */
    public static final String DOPPIE_GRAFFE_END = GRAFFA_END + GRAFFA_END;


    //--chiavi mappa eventi
    public static final String JSON_SUCCESS = "Success";

    public static final String JSON_ERROR = "error";

    public static final String JSON_REASON = "reason";

    public static final String JSON_FAILED = "Failed";

    public static final String JSON_COOKIES = "cookies";

    public static final String JSON_BOT_LOGIN = "botLogin";

    public static final String JSON_NOT_QUERY_LOGIN = "notQueryLogin";

    public static final String JSON_NO_BOT = "assertbotfailed";

    public static final String JSON_CODE = "code";

    public static final String JSON_INFO = "info";

    public static final String NULL_WIKI_TITLE = "Il titolo wiki è nullo";

    public static final String ERROR_WIKI_TITLE = "Manca il titolo wiki";

    public static final String ERROR_WIKI_PAGINA = "Manca la pagina wiki";

    public static final String ERROR_WIKI_CATEGORIA = "Manca la categoria wiki";

    public static final String ERROR_FILE_WIKI = "java.io.FileNotFoundException: https://it.wikipedia.org/wiki/";

    public static final String KEY_MAPPA_EVENTO_AZIONE = "eventoAzione";

    public static final String KEY_MAPPA_SEARCH = "search";

    public static final String ERRORE = "Errore";

    public static final String UGUALE_SEMPLICE = "=";

    public static final String UGUALE_SPAZIATO = SPAZIO + UGUALE_SEMPLICE + SPAZIO;

    public static final String PARAGRAFO = UGUALE_SEMPLICE + UGUALE_SEMPLICE;

    public static final String PARAGRAFO_SUB = UGUALE_SEMPLICE + UGUALE_SEMPLICE + UGUALE_SEMPLICE;


    /**
     * tag '@Qualifier'
     */
    public static final String TAG_PRE = "preferenza";

    public static final String TAG_REGIONE = "regione";

    public static final String TAG_PROVINCIA = "provincia";

    public static final String TAG_COMUNE = "comune";

    public static final String TAG_VERSIONE = "versione";

    public static final String TAG_CONTINENTE = "continente";

    public static final String TAG_FLOW_DATA = "flowdata";

    public static final String TAG_FLOW_VERSION = "flowversion";

    public static final int PAD_TYPE = 10;

    public static final int PAD_CLASS = 20;

    public static final int PAD_SYSTEM = 10;

    public static final int PAD_METHOD = 20;

    public static final int PAD_LINE = 3;

    public static final int PAD_COMPANY = 5;

    public static final int PAD_USER = 18;

    public static final int PAD_IP = 37;

}

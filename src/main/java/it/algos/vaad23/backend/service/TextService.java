package it.algos.vaad23.backend.service;

import com.google.common.base.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import org.apache.commons.lang3.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import java.util.*;


/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 06-mar-2022
 * Time: 18:34
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(ATextService.class); <br>
 * 3) @Autowired public TextService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TextService extends AbstractService {


    /**
     * Null-safe, short-circuit evaluation. <br>
     *
     * @param stringa in ingresso da controllare
     *
     * @return vero se la stringa è vuota o nulla
     */
    public boolean isEmpty(final String stringa) {
        return Strings.isNullOrEmpty(stringa);
    }


    /**
     * Null-safe, short-circuit evaluation. <br>
     * Controlla che sia una stringa e che sia valida.
     *
     * @param obj in ingresso da controllare che non sia nullo, che sia una stringa e che non sia vuota
     *
     * @return vero se la stringa esiste e non è vuota
     */
    public boolean isValid(final Object obj) {
        if (obj == null) {
            return false;
        }

        return (obj instanceof String stringa) && !isEmpty(stringa);
    }


    /**
     * Forza il primo carattere della stringa (e solo il primo) al carattere maiuscolo <br>
     * <p>
     * Se la stringa è nulla, ritorna un nullo <br>
     * Se la stringa è vuota, ritorna una stringa vuota <br>
     * Elimina spazi vuoti iniziali e finali <br>
     *
     * @param testoIn ingresso
     *
     * @return testo formattato in uscita
     */
    public String primaMaiuscola(final String testoIn) {
        String testoOut = isValid(testoIn) ? testoIn.trim() : VUOTA;
        String primoCarattere;

        if (isValid(testoOut)) {
            primoCarattere = testoOut.substring(0, 1).toUpperCase();
            testoOut = primoCarattere + testoOut.substring(1);
        }

        return testoOut.trim();
    }


    /**
     * Forza il primo carattere della stringa (e solo il primo) al carattere minuscolo <br>
     * <p>
     * Se la stringa è nulla, ritorna un nullo
     * Se la stringa è vuota, ritorna una stringa vuota
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn ingresso
     *
     * @return testo formattato in uscita
     */
    public String primaMinuscola(final String testoIn) {
        String testoOut = isValid(testoIn) ? testoIn.trim() : VUOTA;
        String primoCarattere;

        if (isValid(testoOut)) {
            primoCarattere = testoOut.substring(0, 1).toLowerCase();
            testoOut = primoCarattere + testoOut.substring(1);
        }

        return testoOut.trim();
    }


    /**
     * Costruisce un array da una stringa di valori multipli separati da virgole. <br>
     * Se la stringa è nulla, ritorna un nullo <br>
     * Se la stringa è vuota, ritorna un nullo <br>
     * Se manca la virgola, ritorna un array di un solo valore col testo completo <br>
     * Elimina spazi vuoti iniziali e finali di ogni valore <br>
     *
     * @param stringaMultipla in ingresso
     *
     * @return lista di singole stringhe
     */
    public List<String> getArray(final String stringaMultipla) {
        List<String> lista = new ArrayList<>();
        String tag = VIRGOLA;
        String[] parti;

        if (isEmpty(stringaMultipla)) {
            return null;
        }

        if (stringaMultipla.contains(tag)) {
            parti = stringaMultipla.split(tag);
            for (String value : parti) {
                lista.add(value.trim());
            }
        }
        else {
            lista.add(stringaMultipla);
        }

        return lista;
    }

    /**
     * Sostituisce nel testo tutte le occorrenze di oldTag con newTag.
     * Esegue solo se il testo è valido
     * Esegue solo se il oldTag è valido
     * newTag può essere vuoto (per cancellare le occorrenze di oldTag)
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn ingresso da elaborare
     * @param oldTag  da sostituire
     * @param newTag  da inserire
     *
     * @return testo modificato
     */
    public String sostituisce(final String testoIn, final String oldTag, final String newTag) {
        String testoOut = testoIn;
        String prima = VUOTA;
        String rimane = testoIn;
        int pos = 0;
        String charVuoto = SPAZIO;

        if (this.isValid(testoIn) && this.isValid(oldTag)) {
            if (rimane.contains(oldTag)) {
                pos = rimane.indexOf(oldTag);

                while (pos != -1) {
                    pos = rimane.indexOf(oldTag);
                    if (pos != -1) {
                        prima += rimane.substring(0, pos);
                        prima += newTag;
                        pos += oldTag.length();
                        rimane = rimane.substring(pos);
                        if (prima.endsWith(charVuoto) && rimane.startsWith(charVuoto)) {
                            rimane = rimane.substring(1);
                        }
                    }
                }

                testoOut = prima + rimane;
            }
        }

        return testoOut.trim();
    }


    /**
     * Elimina tutti i caratteri contenuti nella stringa. <br>
     * Esegue solo se il testo è valido <br>
     *
     * @param testoIn    in ingresso
     * @param subStringa da eliminare
     *
     * @return testoOut stringa convertita
     */
    public String levaTesto(String testoIn, String subStringa) {
        String testoOut = testoIn;

        if (testoIn != null && subStringa != null) {
            testoOut = testoIn.trim();
            if (testoOut.contains(subStringa)) {
                testoOut = sostituisce(testoOut, subStringa, VUOTA);
            }
        }

        return testoOut;
    }

    /**
     * Elimina tutte le virgole contenute nella stringa. <br>
     * Esegue solo se la stringa è valida <br>
     * Se arriva un oggetto non stringa, restituisce l'oggetto <br>
     *
     * @param entrata stringa in ingresso
     *
     * @return uscita stringa convertita
     */
    public String levaVirgole(String entrata) {
        return levaTesto(entrata, VIRGOLA);
    }


    /**
     * Elimina tutti i punti contenuti nella stringa. <br>
     * Esegue solo se la stringa è valida <br>
     * Se arriva un oggetto non stringa, restituisce l'oggetto <br>
     *
     * @param entrata stringa in ingresso
     *
     * @return uscita stringa convertita
     */
    public String levaPunti(String entrata) {
        return levaTesto(entrata, PUNTO);
    }

    /**
     * Formattazione di un numero. <br>
     * <p>
     * Il numero può arrivare come stringa, intero o double <br>
     * Se la stringa contiene punti e virgole, viene pulita <br>
     * Se la stringa non è convertibile in numero, viene restituita uguale <br>
     * Inserisce il punto separatore ogni 3 cifre <br>
     * Se arriva un oggetto non previsto, restituisce null <br>
     *
     * @param numObj da formattare (stringa, intero, long o double)
     *
     * @return stringa formattata
     */
    public String format(Object numObj) {
        String formattato = VUOTA;
        String numText = VUOTA;
        String sep = PUNTO;
        int numTmp = 0;
        int len;
        String num3;
        String num6;
        String num9;
        String num12;

        if (numObj instanceof String || numObj instanceof Integer || numObj instanceof Long || numObj instanceof Double || numObj instanceof List || numObj instanceof Object[]) {
            if (numObj instanceof String) {
                numText = (String) numObj;
                numText = levaVirgole(numText);
                numText = levaPunti(numText);
                try {
                    numTmp = Integer.decode(numText);
                } catch (Exception unErrore) {
                    return (String) numObj;
                }
            }
            else {
                if (numObj instanceof Integer) {
                    numText = Integer.toString((int) numObj);
                }
                if (numObj instanceof Long) {
                    numText = Long.toString((long) numObj);
                }
                if (numObj instanceof Double) {
                    numText = Double.toString((double) numObj);
                }
                if (numObj instanceof List) {
                    numText = Integer.toString((int) ((List) numObj).size());
                }
                if (numObj instanceof Object[]) {
                    numText = Integer.toString(((Object[]) numObj).length);
                }
            }
        }
        else {
            return null;
        }

        formattato = numText;
        len = numText.length();
        if (len > 3) {
            num3 = numText.substring(0, len - 3);
            num3 += sep;
            num3 += numText.substring(len - 3);
            formattato = num3;
            if (len > 6) {
                num6 = num3.substring(0, len - 6);
                num6 += sep;
                num6 += num3.substring(len - 6);
                formattato = num6;
                if (len > 9) {
                    num9 = num6.substring(0, len - 9);
                    num9 += sep;
                    num9 += num6.substring(len - 9);
                    formattato = num9;
                    if (len > 12) {
                        num12 = num9.substring(0, len - 12);
                        num12 += sep;
                        num12 += num9.substring(len - 12);
                        formattato = num12;
                    }
                }
            }
        }

        //--valore di ritorno
        return formattato;
    }


    /**
     * Elimina dal testo il tagFinale, se esiste. <br>
     * <p>
     * Esegue solo se il testo è valido <br>
     * Se tagFinale è vuoto, restituisce il testo <br>
     * Elimina spazi vuoti iniziali e finali <br>
     *
     * @param testoIn   ingresso
     * @param tagFinale da eliminare
     *
     * @return testo ridotto in uscita
     */
    public String levaCoda(final String testoIn, final String tagFinale) {
        String testoOut = testoIn.trim();
        String tag = VUOTA;

        if (this.isValid(testoOut) && this.isValid(tagFinale)) {
            tag = tagFinale.trim();
            if (testoOut.endsWith(tag)) {
                testoOut = testoOut.substring(0, testoOut.length() - tag.length());
            }
        }

        return testoOut.trim();
    }

    /**
     * Elimina (eventuali) parentesi quadre singole in testa e coda della stringa. <br>
     * Funziona solo se le quadre sono esattamente in TESTA ed in CODA alla stringa <br>
     * Se arriva una stringa vuota, restituisce una stringa vuota <br>
     * Elimina spazi vuoti iniziali e finali <br>
     * Esegue anche se le quadre in testa ed in coda alla stringa sono presenti in numero diverso <br>
     * Esegue anche se le quadre in testa ed in coda alla stringa sono singole o doppie o triple o quadruple <br>
     *
     * @param stringaIn in ingresso
     *
     * @return stringa con quadre iniziali e finali eliminate
     */
    public String setNoQuadre(String stringaIn) {
        String stringaOut = stringaIn;

        if (isValid(stringaIn)) {
            stringaOut = stringaIn.trim();

            while (stringaOut.startsWith(QUADRA_INI) && stringaOut.endsWith(QUADRA_END)) {
                stringaOut = stringaOut.substring(1);
                stringaOut = stringaOut.substring(0, stringaOut.length() - 1);
            }
        }

        return stringaOut.trim();
    }


    /**
     * Allunga un testo alla lunghezza desiderata. <br>
     * Se è più corta, aggiunge spazi vuoti <br>
     * Se è più lungo, rimane inalterato <br>
     * La stringa in ingresso viene 'giustificata' a sinistra <br>
     * Vengono eliminati gli spazi vuoti che precedono la stringa <br>
     *
     * @param testoIn ingresso
     *
     * @return testo della 'lunghezza' richiesta
     */

    public String rightPad(final String testoIn, int size) {
        String testoOut = testoIn.trim();
        testoOut = StringUtils.rightPad(testoOut, size);
        return testoOut;
    }

    /**
     * Forza un testo alla lunghezza desiderata. <br>
     * Se è più corta, aggiunge spazi vuoti <br>
     * Se è più lungo, lo tronca <br>
     * La stringa in ingresso viene 'giustificata' a sinistra <br>
     * Vengono eliminati gli spazi vuoti che precedono la stringa <br>
     *
     * @param testoIn ingresso
     *
     * @return testo della 'lunghezza' richiesta
     */

    public String fixSize(final String testoIn, int size) {
        String testoOut = testoIn.trim();
        testoOut = rightPad(testoIn, size);

        if (testoOut.length() > size) {
            testoOut = testoOut.substring(0, size);
        }

        return testoOut;
    }

    /**
     * Forza un testo alla lunghezza desiderata e aggiunge parentesi quadre in testa e coda. <br>
     * Se arriva una stringa vuota, restituisce una stringa vuota con parentesi quadre aggiunte <br>
     *
     * @param stringaIn in ingresso
     *
     * @return stringa con lunghezza prefissata e parentesi quadre aggiunte
     */
    public String fixSizeQuadre(final String stringaIn, final int size) {
        String stringaOut = this.setNoQuadre(stringaIn);
        stringaOut = rightPad(stringaOut, size);
        stringaOut = fixSize(stringaOut, size);
        if (this.isValid(stringaOut)) {
            if (!stringaOut.startsWith(QUADRA_INI)) {
                stringaOut = QUADRA_INI + stringaOut;
            }
            if (!stringaOut.endsWith(QUADRA_END)) {
                stringaOut = stringaOut + QUADRA_END;
            }
            //            }
        }

        return stringaOut.trim();
    }

}
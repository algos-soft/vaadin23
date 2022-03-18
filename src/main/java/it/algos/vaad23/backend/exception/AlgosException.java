package it.algos.vaad23.backend.exception;


import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.entity.*;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 25-ago-2021
 * Time: 20:00
 */
public class AlgosException extends Exception {

    private AEntity entityBean;

    private Throwable cause;

    public AlgosException(final Throwable cause) {
        super(cause);
    }

    public AlgosException(final String message) {
        super(message);
    }

    public AlgosException(final Throwable cause, final String message) {
        super(message, cause);
    }

    public AlgosException(final Throwable cause, final AEntity entityBean) {
        this(cause, VUOTA, entityBean);
    }

    public AlgosException(final Throwable cause, final String message, final AEntity entityBean) {
        super(message, cause);
        this.entityBean = entityBean;
    }


    public static AlgosException crea(final Throwable cause) {
        return AlgosException.crea(cause, VUOTA);
    }

    public static AlgosException crea(final String message) {
        return AlgosException.crea(null, message);
    }


    public static AlgosException crea(final Throwable cause, final String message) {
        return new AlgosException(cause, message);
    }


    public static AlgosException crea(final Throwable cause, final AEntity entityBean) {
        return new AlgosException(cause, VUOTA, entityBean);
    }

    public static AlgosException crea(final Throwable cause, final String message, final AEntity entityBean) {
        AlgosException algosException = new AlgosException(cause, message);
        algosException.entityBean = entityBean;

        return algosException;
    }

    public AEntity getEntityBean() {
        return entityBean;
    }


    /**
     * Classe da cui proviene l'errore <br>
     *
     * @return simpleName della classe di errore
     */
    public String getClazz() {
        StackTraceElement stack = getStack();
        return stack != null ? stack.getClassName() : VUOTA;
    }

    /**
     * Metodo da cui proviene l'errore <br>
     *
     * @return simpleName della classe di errore
     */
    public String getMethod() {
        StackTraceElement stack = getStack();
        return stack != null ? stack.getMethodName() : VUOTA;
    }

    /**
     * Riga da cui proviene l'errore <br>
     *
     * @return simpleName della classe di errore
     */
    public int getLine() {
        StackTraceElement stack = getStack();
        return stack != null ? stack.getLineNumber() : 0;
    }


    /**
     * Stack dell'errore <br>
     */
    private StackTraceElement getStack() {
        StackTraceElement stack = null;

        if (cause != null) {
            StackTraceElement[] matrice = cause.getStackTrace();
            Optional stackPossibile = Arrays.stream(matrice)
                    .filter(algos -> algos.getClassName().startsWith(PATH_ALGOS))
                    .findFirst();
            if (stackPossibile != null) {
                stack = (StackTraceElement) stackPossibile.get();
            }
        }

        return stack;
    }

}

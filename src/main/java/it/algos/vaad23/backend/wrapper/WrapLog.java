package it.algos.vaad23.backend.wrapper;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.exception.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 21-mar-2022
 * Time: 14:08
 * Wrapper di informazioni per accedere al logger <br>
 * Può contenere:
 * -type (AETypeLog) merceologico per il log
 * -message (String)
 * -errore (AlgosException) con StackTrace per recuperare classe, metodo e riga di partenza
 * -wrapCompany (WrapLogCompany) per recuperare companySigla, userName e addressIP se l'applicazione è multiCompany
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WrapLog {

    private AETypeLog type;

    private String message;

    private AlgosException exception;

    private String companySigla;

    private String userName;

    private String addressIP;

    private boolean flagUsaDB;

    private boolean flagUsaMail;

    public WrapLog() {
    }


    public WrapLog message(String message) {
        this.message = message;
        return this;
    }

    public WrapLog type(AETypeLog type) {
        this.type = type;
        return this;
    }

    public WrapLog exception(AlgosException exception) {
        this.exception = exception;
        return this;
    }

    public WrapLog company(String companySigla) {
        this.companySigla = companySigla;
        return this;
    }

    public WrapLog user(String userName) {
        this.userName = userName;
        return this;
    }

    public WrapLog address(String addressIP) {
        this.addressIP = addressIP;
        return this;
    }

    public WrapLog usaDb() {
        this.flagUsaDB = true;
        return this;
    }

    public WrapLog usaMail() {
        this.flagUsaMail = true;
        return this;
    }

    public AETypeLog getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public AlgosException getException() {
        return exception;
    }

    public String getCompanySigla() {
        return companySigla;
    }

    public String getUserName() {
        return userName;
    }

    public String getAddressIP() {
        return addressIP;
    }

    public boolean isFlagUsaDB() {
        return flagUsaDB;
    }

    public boolean isFlagUsaMail() {
        return flagUsaMail;
    }

}

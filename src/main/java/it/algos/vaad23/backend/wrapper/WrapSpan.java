package it.algos.vaad23.backend.wrapper;

import com.vaadin.flow.spring.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 25-mar-2022
 * Time: 13:41
 * Wrapper di informazioni per costruire un elemento Span <br>
 * Può contenere:
 * -weight (font-weight) merceologico per il log
 * -message (String)
 * -errore (AlgosException) con StackTrace per recuperare classe, metodo e riga di partenza
 * -wrapCompany (WrapLogCompany) per recuperare companySigla, userName e addressIP se l'applicazione è multiCompany
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class WrapSpan {

   private String weight;

}

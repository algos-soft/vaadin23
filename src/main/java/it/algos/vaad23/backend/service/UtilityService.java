package it.algos.vaad23.backend.service;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.exception.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 20-mar-2022
 * Time: 06:26
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(UtilityService.class); <br>
 * 3) @Autowired public UtilityService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UtilityService extends AbstractService {

    public static final int PAD_CLASS = 20;

    public static final int PAD_METHOD = 20;

    public static final int PAD_LINE = 3;

    public String getStackTrace(AlgosException exception) {
        boolean usaTagIniziale = true;
        String tagClasse = "class=";
        String tagMetodo = "method=";
        String tagRiga = "line=";
        String message;
        String classe;
        String metodo;
        String riga;
        int padClass = PAD_CLASS;
        int padMethod = PAD_METHOD;
        int padLine = PAD_LINE;

        message = exception.getCause() != null ? exception.getCause().getMessage() : exception.getMessage();

        classe = exception.getClazz();
        classe = fileService.estraeClasseFinale(classe);
        metodo = exception.getMethod();
        riga = exception.getLine();

        if (usaTagIniziale) {
            classe = tagClasse + classe;
            metodo = tagMetodo + metodo;
            riga = tagRiga + riga;
            padClass += tagClasse.length();
            padMethod += tagMetodo.length();
            padLine += tagRiga.length();
        }

        classe = textService.fixSizeQuadre(classe, padClass);
        metodo = textService.fixSizeQuadre(metodo, padMethod);
        riga = textService.fixSizeQuadre(riga + VUOTA, padLine);

        return String.format("%s%s%s%s%s%s%s", classe, SPAZIO, metodo, SPAZIO, riga, SPAZIO, message);
    }


}
package it.algos.vaad23.backend.service;

import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.data.renderer.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.entity.*;
import it.algos.vaad23.backend.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import java.lang.reflect.*;
import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 04-apr-2022
 * Time: 18:09
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(hService.class); <br>
 * 3) @Autowired public hService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ColumnService extends AbstractService {


    /**
     * Aggiunge in automatico le colonne previste in gridPropertyNamesList <br>
     *
     * @param grid                  a cui aggiungere la colonna
     * @param entityClazz           modello-dati specifico
     * @param gridPropertyNamesList lista di property da creare
     */
    public void addColumnsOneByOne(final Grid grid, Class<? extends AEntity> entityClazz, final List<String> gridPropertyNamesList) {
        if (grid != null && gridPropertyNamesList != null) {
            for (String propertyName : gridPropertyNamesList) {
                this.crea(grid, entityClazz, propertyName);
            }
        }
    }

    /**
     * Aggiunge in automatico le colonne previste in gridPropertyNamesList <br>
     *
     * @param grid         a cui aggiungere la colonna
     * @param entityClazz  modello-dati specifico
     * @param propertyName della property
     */
    public void crea(final Grid grid, Class<? extends AEntity> entityClazz, final String propertyName) {
        Grid.Column<AEntity> colonna = null;
        String width = annotationService.getWidth(entityClazz, propertyName);

        colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
            Field field = null;
            String testo = VUOTA;
            try {
                field = reflectionService.getField(entityClazz, propertyName);
            } catch (Exception unErrore) {
                logger.error(new WrapLog().exception(unErrore).usaDb());
            }

            try {
                if (field.get(entity) instanceof String) {
                    testo = (String) field.get(entity);
                }
                if (field.get(entity) instanceof Integer) {
                    testo = field.get(entity) + "";
                }
                if (field.get(entity) instanceof Boolean) {
                    Icon icona = (boolean) field.get(entity) ? VaadinIcon.CHECK.create() : VaadinIcon.CLOSE.create();
                    icona.setColor((boolean) field.get(entity) ? COLOR_VERO : COLOR_FALSO);
                    return icona;
                }
            } catch (Exception unErrore) {
                logger.error(new WrapLog().exception(unErrore).usaDb());
            }

            return new Label(testo);
        })).setWidth(width).setFlexGrow(0).setSortable(true).setHeader(textService.primaMaiuscola(propertyName));
        //end of lambda expressions and anonymous inner class
    }


}
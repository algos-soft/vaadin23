package it.algos.vaad23.ui.views;

import com.vaadin.flow.component.orderedlayout.*;
import it.algos.vaad23.backend.logic.*;
import org.vaadin.crudui.crud.impl.*;
import org.vaadin.crudui.layout.impl.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 13-mar-2022
 * Time: 11:33
 */
public abstract class CrudView extends VerticalLayout {

    protected EntityBackend crudBackend;

    protected GridCrud crud;

    //    public CrudView() {
    //    }

    public CrudView(final EntityBackend crudBackend, final Class entityClazz, boolean splitLayout) {
        this.crudBackend = crudBackend;
        // crud instance
        if (splitLayout) {
            crud = new GridCrud<>(entityClazz, new HorizontalSplitCrudLayout());
        }
        else {
            crud = new GridCrud<>(entityClazz);
        }

        // grid configuration
        crud.getCrudFormFactory().setUseBeanValidation(true);

        // logic configuration
        crud.setFindAllOperation(() -> crudBackend.findAll());
        crud.setAddOperation(crudBackend::add);
        crud.setUpdateOperation(crudBackend::update);
        crud.setDeleteOperation(crudBackend::delete);

        // layout configuration
        setSizeFull();
        this.add(crud);
        //        crud.setFindAllOperationVisible(false);
    }

}

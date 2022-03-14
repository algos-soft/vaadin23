package it.algos.vaad23.ui.views;

import com.vaadin.flow.component.orderedlayout.*;
import it.algos.vaad23.backend.logic.*;
import org.vaadin.crudui.crud.impl.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 13-mar-2022
 * Time: 11:33
 */
public abstract class CrudView extends VerticalLayout {

    protected EntityBackend backend;

    protected GridCrud crud;

    //    public CrudView() {
    //    }

    public CrudView(final EntityBackend backend, final Class entityClazz) {
        this.backend = backend;
        // crud instance
        crud = new GridCrud<>(entityClazz);
        //         crud = new GridCrud<>(entityClazz, new HorizontalSplitCrudLayout());

        // grid configuration
        crud.getCrudFormFactory().setUseBeanValidation(true);

        // logic configuration
        crud.setFindAllOperation(() -> backend.findAll());
        crud.setAddOperation(backend::add);
        crud.setUpdateOperation(backend::update);
        crud.setDeleteOperation(backend::delete);

        // layout configuration
        setSizeFull();
        this.add(crud);
        //        crud.setFindAllOperationVisible(false);
    }

}

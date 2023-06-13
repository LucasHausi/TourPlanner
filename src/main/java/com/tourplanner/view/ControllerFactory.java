package com.tourplanner.view;

import com.tourplanner.viewmodel.NewTourViewModel;
import org.springframework.context.ConfigurableApplicationContext;

public class ControllerFactory {
    private final NewTourViewModel newTourViewModel;

    public ControllerFactory(ConfigurableApplicationContext applicationContext){
        newTourViewModel = new NewTourViewModel();
    }
    public Object create(Class<?> controllerClass){
        if(controllerClass == NewTourController.class){
            return new NewTourController(newTourViewModel);
        }
        throw new IllegalArgumentException("Unknown controller class: " + controllerClass);
    }
    private static ControllerFactory instance;
    public static ControllerFactory getInstance(ConfigurableApplicationContext applicationContext) {
        if (instance == null) {
            instance = new ControllerFactory(applicationContext);
        }
        return instance;
    }
}

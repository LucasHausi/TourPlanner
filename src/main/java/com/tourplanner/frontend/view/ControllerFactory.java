package com.tourplanner.frontend.view;


import com.tourplanner.backend.bl.TourLogServiceImpl;
import com.tourplanner.backend.bl.TourServiceImpl;
import com.tourplanner.frontend.viewmodel.MainWindowViewModel;
import com.tourplanner.frontend.viewmodel.NewTourLogViewModel;
import com.tourplanner.frontend.viewmodel.NewTourViewModel;
import org.springframework.context.ConfigurableApplicationContext;


public class ControllerFactory {
    private final NewTourViewModel newTourViewModel;
    private final NewTourLogViewModel newTourLogViewModel;
    private final MainWindowViewModel mainWindowViewModel;

    public ControllerFactory(ConfigurableApplicationContext applicationContext){
        newTourViewModel = new NewTourViewModel(new TourServiceImpl());
        newTourLogViewModel = new NewTourLogViewModel(new TourLogServiceImpl());
        mainWindowViewModel = new MainWindowViewModel();
    }
    public Object create(Class<?> controllerClass){
        if(controllerClass == MainWindowController.class)
        {
            return new MainWindowController(mainWindowViewModel);
        }
        if(controllerClass == NewTourController.class){
            return new NewTourController(newTourViewModel);
        }
        if(controllerClass == NewTourLogController.class){
            return new NewTourLogController(newTourLogViewModel);
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

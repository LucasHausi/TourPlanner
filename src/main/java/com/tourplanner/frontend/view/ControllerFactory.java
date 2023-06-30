package com.tourplanner.frontend.view;


import com.tourplanner.frontend.bl.Publisher;
import com.tourplanner.frontend.bl.TourLogServiceImpl;
import com.tourplanner.frontend.bl.TourServiceImpl;
import com.tourplanner.frontend.viewmodel.MainWindowViewModel;
import com.tourplanner.frontend.viewmodel.NewTourLogViewModel;
import com.tourplanner.frontend.viewmodel.NewTourViewModel;
import com.tourplanner.frontend.viewmodel.ReusableCompViewModel;
import org.springframework.context.ConfigurableApplicationContext;


public class ControllerFactory {
    private final NewTourViewModel newTourViewModel;
    private final NewTourLogViewModel newTourLogViewModel;
    private final MainWindowViewModel mainWindowViewModel;
    private final Publisher publisher;
    private final ReusableCompViewModel reusableCompViewModel;

    public ControllerFactory(ConfigurableApplicationContext applicationContext){
        newTourViewModel = new NewTourViewModel(new TourServiceImpl());
        newTourLogViewModel = new NewTourLogViewModel(new TourLogServiceImpl());
        mainWindowViewModel = new MainWindowViewModel();
        reusableCompViewModel = new ReusableCompViewModel();
        publisher = new Publisher();
    }
    public Object create(Class<?> controllerClass){
        if(controllerClass == MainWindowController.class)
        {
            MainWindowController mainWindowController = new MainWindowController(mainWindowViewModel);
            publisher.subscribe(mainWindowController);
            return mainWindowController;
        }
        if(controllerClass == NewTourController.class){
            return new NewTourController(newTourViewModel);
        }
        if(controllerClass == NewTourLogController.class){
            return new NewTourLogController(newTourLogViewModel);
        }
        if(controllerClass == ReusableCompController.class){
            return new ReusableCompController(reusableCompViewModel);
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

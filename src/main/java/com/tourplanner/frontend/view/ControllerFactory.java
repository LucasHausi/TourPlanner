package com.tourplanner.frontend.view;


import com.tourplanner.frontend.bl.MapServiceImp;
import com.tourplanner.frontend.bl.Publisher;
import com.tourplanner.frontend.bl.TourLogServiceImpl;
import com.tourplanner.frontend.bl.TourServiceImpl;
import com.tourplanner.frontend.viewmodel.*;
import org.springframework.context.ConfigurableApplicationContext;


public class ControllerFactory {
    private final NewTourViewModel newTourViewModel;
    private final NewTourLogViewModel newTourLogViewModel;
    private final MainWindowViewModel mainWindowViewModel;
    private final Publisher publisher;
    private final ReusableCompViewModel reusableCompViewModel;
    private final PDFGenerationViewModel pdfGenerationViewModel;

    public ControllerFactory(ConfigurableApplicationContext applicationContext){
        newTourViewModel = new NewTourViewModel(new TourServiceImpl(), new MapServiceImp());
        newTourLogViewModel = new NewTourLogViewModel(new TourLogServiceImpl());
        mainWindowViewModel = new MainWindowViewModel();
        reusableCompViewModel = new ReusableCompViewModel();
        pdfGenerationViewModel = new PDFGenerationViewModel(new TourServiceImpl());
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
        if(controllerClass == PDFGenerationController.class){
            return new PDFGenerationController(pdfGenerationViewModel);
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

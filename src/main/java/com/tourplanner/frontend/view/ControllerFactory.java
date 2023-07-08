package com.tourplanner.frontend.view;


import com.tourplanner.frontend.bl.service.MapServiceImpl;
import com.tourplanner.frontend.bl.Publisher;
import com.tourplanner.frontend.bl.service.TourLogServiceImpl;
import com.tourplanner.frontend.bl.service.TourServiceImpl;
import com.tourplanner.frontend.viewmodel.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;


public class ControllerFactory {
    private final NewTourViewModel newTourViewModel;
    private final NewTourLogViewModel newTourLogViewModel;
    private final MainWindowViewModel mainWindowViewModel;
    private final Publisher publisher;
    private final ReusableCompViewModel reusableCompViewModel;
    private final PDFGenerationViewModel pdfGenerationViewModel;
    private final TourLogCommentWindowViewModel tourLogCommentWindowViewModel;

    private static final Logger logger;
    static {
        try {
            // you need to do something like below instaed of Logger.getLogger(....);
            logger = LogManager.getLogger(TourServiceImpl.class);
        } catch (Throwable th) {
            throw new IllegalArgumentException("Cannot load the log property file", th);
        }
    }

    public ControllerFactory(ConfigurableApplicationContext applicationContext){
        newTourViewModel = new NewTourViewModel(new TourServiceImpl(), new MapServiceImpl());
        newTourLogViewModel = new NewTourLogViewModel(new TourLogServiceImpl());
        mainWindowViewModel = new MainWindowViewModel();
        reusableCompViewModel = new ReusableCompViewModel();
        pdfGenerationViewModel = new PDFGenerationViewModel(new TourServiceImpl());
        tourLogCommentWindowViewModel = new TourLogCommentWindowViewModel();
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
        if(controllerClass == TourLogCommentWindowController.class){
            return new TourLogCommentWindowController(tourLogCommentWindowViewModel);
        }
        logger.error("unknown controller class: " + controllerClass);
        throw new IllegalArgumentException("Unknown controller class: " + controllerClass);
    }
    private static ControllerFactory instance;
    public static ControllerFactory getInstance(ConfigurableApplicationContext applicationContext) {
        if (instance == null) {
            logger.info("creating new controller factory instance");
            instance = new ControllerFactory(applicationContext);
        }
        return instance;
    }
}

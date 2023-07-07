package com.tourplanner.frontend.viewmodel;

import com.tourplanner.frontend.bl.service.TourService;
import com.tourplanner.frontend.bl.service.TourServiceImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Getter
public class PDFGenerationViewModel {
    private final TourService tourService;
    private final StringProperty pdfName = new SimpleStringProperty();

    public PDFGenerationViewModel(TourServiceImpl tourService){
        this.tourService = tourService;
    }

    public void printTourPdf(UUID id) throws IOException {
        tourService.printTourPdf(id, pdfName.get());
    }

    public void printSummaryPdf() throws IOException {
        tourService.printSummaryPdf();
    }
}

package com.tourplanner.frontend.view;

import com.tourplanner.frontend.viewmodel.PDFGenerationViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.UUID;

@Service
public class PDFGenerationController  implements Initializable {
    @FXML
    public TextField pdfNameTextField;
    @FXML
    public Button createButton;
    @FXML
    public Button closeButton;

    private final PDFGenerationViewModel pdfGenerationViewModel;

    @Setter
    private Stage pdfGenerationDialogStage;

    @Setter
    private UUID tourToPrint;

    public PDFGenerationController(PDFGenerationViewModel pdfGenerationViewModel) {
        this.pdfGenerationViewModel = pdfGenerationViewModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pdfNameTextField.textProperty().bindBidirectional(pdfGenerationViewModel.getPdfName());
    }

    public void printPdf() throws IOException {
        if(Objects.nonNull(pdfNameTextField.getText()) && !pdfNameTextField.getText().isEmpty()){
            printTourPdf();
        }
        else {
            printSummaryPdf();
        }
        closeDialog();
    }

    public void printTourPdf() throws IOException {
        pdfGenerationViewModel.printTourPdf(tourToPrint);
    }

    public void printSummaryPdf() throws IOException {
        pdfGenerationViewModel.printSummaryPdf();
    }

    public void closeDialog() {
        pdfNameTextField.clear();
        pdfGenerationDialogStage.close();
    }
}

package com.tourplanner.frontend.bl;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.property.UnitValue;
import com.tourplanner.frontend.dal.TourApi;
import com.tourplanner.shared.model.Tour;
import com.tourplanner.shared.model.TourLog;
import lombok.Getter;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Component
public class TourServiceImpl implements TourService {
    @Getter
    private final TourApi tourApi;
    public TourServiceImpl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:30019")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        tourApi = retrofit.create(TourApi.class);
    }

    @Override
    public void createOrUpdate(Tour tour) throws IOException {
        tourApi.createOrUpdateTour(tour).execute();
    }

    @Override
    public void deleteTour(UUID id) throws IOException {
        tourApi.deleteTour(id).execute();
    }

    @Override
    public List<Tour> getAllTours() throws IOException {
        return tourApi.getAllTours().execute().body();
    }

    @Override
    public void printTourPdf(UUID id, String pdfName) throws IOException {
        Tour tour = tourApi.getTour(id).execute().body();
        Files.createDirectories(Paths.get("reports/"));
        PdfWriter writer = new PdfWriter("reports/"+(pdfName.endsWith(".pdf") ? pdfName : pdfName + ".pdf"));
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        document.add(new Paragraph("Tour Report for \""+tour.getName()+"\"").setFontSize(24).setBold());

        document.add(new Paragraph("\n"));

        document.add(new Paragraph("Tour Data").setFontSize(14).setBold());
        float [] pointColumnWidths = {200F, 200F};
        Table tourValueTable = new Table(pointColumnWidths);
        tourValueTable.addCell("From");
        tourValueTable.addCell(tour.getStartingPoint());
        tourValueTable.addCell("To");
        tourValueTable.addCell(tour.getDestination());
        tourValueTable.addCell("Estimated time");
        tourValueTable.addCell(tour.getEstimatedTime());
        tourValueTable.addCell("Distance");
        tourValueTable.addCell(String.valueOf(tour.getDistance()));
        tourValueTable.addCell("Transport Type");
        tourValueTable.addCell(tour.getTransportType().toString());
        document.add(tourValueTable);

        document.add(new Paragraph("\n").setFontSize(2));
        document.add(new Paragraph("Description").setFontSize(14).setBold());
        document.add(new Paragraph(tour.getDescription()).setFontSize(12));
        document.add(new Paragraph("Route Information").setFontSize(14).setBold());
        document.add(new Paragraph(tour.getRouteInformation()).setFontSize(12));

        if(Objects.nonNull(tour.getTourLogList()) && !tour.getTourLogList().isEmpty()) {
            document.add(new Paragraph("Tour Logs").setFontSize(14).setBold());
            // Create tourLog table
            Table tourLogTable = new Table(UnitValue.createPercentArray(3));

            tourLogTable.addHeaderCell("Date");
            tourLogTable.addHeaderCell("Duration");
            tourLogTable.addHeaderCell("Distance");

            for (TourLog tourLog : tour.getTourLogList()) {
                tourLogTable.addCell("gibts nu ned");//tourLog.getDateTime().toLocalDate().toString());
                tourLogTable.addCell(tourLog.getTotalTime());
                tourLogTable.addCell(tourLog.getRating() + "");
            }
            document.add(tourLogTable);
        }
        document.add(new Paragraph("\n").setFontSize(2));
        document.add(new Paragraph("Route Image").setFontSize(14).setBold());
        document.add(new Image(ImageDataFactory.create("images/Route"+id+".png")).setAutoScale(true));
        document.close();
    }
}

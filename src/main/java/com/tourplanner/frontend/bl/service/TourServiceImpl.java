package com.tourplanner.frontend.bl.service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.property.UnitValue;
import com.tourplanner.frontend.dal.TourApi;
import com.tourplanner.frontend.bl.mapper.TourMapper;
import com.tourplanner.frontend.bl.model.Tour;
import com.tourplanner.frontend.bl.model.TourLog;
import com.tourplanner.shared.model.TourDTO;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
public class TourServiceImpl implements TourService {
    @Getter
    private final TourApi tourApi;
    private final TourMapper tourMapper = Mappers.getMapper(TourMapper.class);
    private static final Logger logger;
    static {
        try {
            // you need to do something like below instaed of Logger.getLogger(....);
            logger = LogManager.getLogger(TourServiceImpl.class);
        } catch (Throwable th) {
            throw new IllegalArgumentException("Cannot load the log property file", th);
        }
    }
    public TourServiceImpl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:30019")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        tourApi = retrofit.create(TourApi.class);
    }

    @Override
    public UUID createOrUpdate(Tour tour) throws IOException {
        Response<TourDTO> response = tourApi.createOrUpdateTour(tourMapper.toDTO(tour)).execute();
        return Objects.nonNull(response.body()) ? response.body().getId() : tour.getId();
    }

    @Override
    public void deleteTour(UUID id) throws IOException {
        tourApi.deleteTour(id).execute();
    }

    @Override
    public List<Tour> getAllTours() throws IOException {
        return Objects.requireNonNull(tourApi.getAllTours().execute().body()).stream()
                .map(tourMapper::fromDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void printTourPdf(UUID id, String pdfName) throws IOException {
        logger.info("printing TourReport pdf for Tour: " + id);
        if(!Files.exists(Paths.get("reports/"))){
            logger.warn("Path for report saving not found!");
            Files.createDirectories(Paths.get("reports/"));
            logger.info("Created path to save reports");
        }

        Tour tour = tourMapper.fromDTO(tourApi.getTour(id).execute().body());

        Files.createDirectories(Paths.get("reports/"));
        String filename;
        if(Objects.nonNull(pdfName) && !pdfName.isEmpty()){
            filename = "reports/"+(pdfName.endsWith(".pdf") ? pdfName : pdfName + ".pdf");
        }
        else {
            filename = "reports/"+tour.getName()+"_report_"+Instant.now().getNano()+".pdf";

        }

        PdfWriter writer = new PdfWriter(filename);
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
        int timeInSeconds = Integer.parseInt(tour.getEstimatedTime());
        Duration duration = Duration.ofSeconds(timeInSeconds);
        long HH = duration.toHours();
        long MM = duration.toMinutesPart();
        long SS = duration.toSecondsPart();
        String timeInHHMMSS = String.format("%02d:%02d:%02d", HH, MM, SS);
        tourValueTable.addCell(timeInHHMMSS);
        tourValueTable.addCell("Distance");
        tourValueTable.addCell(tour.getDistance() + " km");
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
            Table tourLogTable = new Table(UnitValue.createPercentArray(4));

            tourLogTable.addHeaderCell("Date");
            tourLogTable.addHeaderCell("Duration");
            tourLogTable.addHeaderCell("Rating");
            tourLogTable.addHeaderCell("Difficulty");

            for (TourLog tourLog : tour.getTourLogList()) {
                tourLogTable.addCell(tourLog.getDate().toString());
                tourLogTable.addCell(tourLog.getTotalTime() + " h");
                tourLogTable.addCell(tourLog.getRating() + "");
                tourLogTable.addCell(tourLog.getDifficulty().toString());
            }
            document.add(tourLogTable);
        }
        document.add(new Paragraph("\n").setFontSize(2));
        document.add(new Paragraph("Route Image").setFontSize(14).setBold());
        document.add(new Image(ImageDataFactory.create("images/Route"+id+".png")).setAutoScale(true));
        document.close();
    }

    @Override
    public void printSummaryPdf(String pdfName) throws IOException {
        logger.info("Printing summary report of all Tours!");
        if(!Files.exists(Paths.get("reports/"))){
            logger.warn("Path for report saving not found!");
            Files.createDirectories(Paths.get("reports/"));
            logger.info("Created path to save reports");
        }

        PdfWriter writer;
        if(Objects.nonNull(pdfName) && !pdfName.isEmpty()){
            writer = new PdfWriter("reports/"+(pdfName.endsWith(".pdf") ? pdfName : pdfName + ".pdf"));
        }
        else {
            writer = new PdfWriter("reports/summary_"+ Instant.now().getNano()+".pdf");
        }

        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        document.add(new Paragraph("Summary of all Tours from " + LocalDate.now()).setFontSize(18).setBold());
        document.add(new Paragraph("\n"));

        float [] pointColumnWidths = {200F, 200F, 200F, 200F};
        List<Tour> tours = getAllTours();
        Table tourValueTable = new Table(pointColumnWidths);

        tourValueTable.addHeaderCell("Tour");
        tourValueTable.addHeaderCell("Average time");
        tourValueTable.addHeaderCell("Distance");
        tourValueTable.addHeaderCell("Average rating");

        for(Tour tour : tours){
            tourValueTable.addCell(tour.getName());
            Duration duration = Duration.ofSeconds(tour.getAverageTime());
            long HH = duration.toHours();
            long MM = duration.toMinutesPart();
            long SS = duration.toSecondsPart();
            String timeInHHMMSS = String.format("%02d:%02d:%02d", HH, MM, SS);
            tourValueTable.addCell(timeInHHMMSS);
            tourValueTable.addCell(tour.getDistance()+" km");
            tourValueTable.addCell(tour.getAverageRating().toString());
        }
        document.add(tourValueTable);
        document.close();
    }
}

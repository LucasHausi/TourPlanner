package com.tourplanner.frontend.bl;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.property.UnitValue;
import com.tourplanner.frontend.dal.TourApi;
import com.tourplanner.frontend.mapper.TourMapper;
import com.tourplanner.frontend.mapper.TourMapperImpl;
import com.tourplanner.frontend.model.Tour;
import com.tourplanner.frontend.model.TourLog;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private TourMapper tourMapper = new TourMapperImpl();
    private static Logger logger;
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
    public void createOrUpdate(Tour tour) throws IOException {
        tourApi.createOrUpdateTour(tourMapper.toDTO(tour)).execute();
    }

    @Override
    public void deleteTour(UUID id) throws IOException {
        tourApi.deleteTour(id).execute();
    }

    @Override
    public List<Tour> getAllTours() throws IOException {
        return tourApi.getAllTours().execute().body().stream()
                .map(tourLogDTO -> tourMapper.fromDTO(tourLogDTO))
                .collect(Collectors.toList());
    }

    @Override
    public void printTourPdf(UUID id, String pdfName) throws IOException {
        logger.info("printing TourReport pdf for Tour: " + id);
        Tour tour = tourMapper.fromDTO(tourApi.getTour(id).execute().body());
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

    @Override
    public void printSummaryPdf() throws IOException {
        Files.createDirectories(Paths.get("reports/"));
        PdfWriter writer = new PdfWriter("reports/Summary"+ Instant.now().getNano()+".pdf");
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
            List<Integer> ratings =  tour.getTourLogList().stream().map(TourLog::getRating).toList();
            Double avgRating = 0.0;
            for(Integer rating : ratings){
                avgRating += rating;
            }
            avgRating /= ratings.size();

            List<String> times =  tour.getTourLogList().stream().map(TourLog::getTotalTime).toList();
            Integer avgTime = 0;
            for(String time : times){
                var splitTime = time.split(":");
                avgTime += Integer.valueOf(splitTime[0])*60 + Integer.valueOf(splitTime[1]);
            }
            avgTime/=times.size();

            tourValueTable.addCell(tour.getName());
            tourValueTable.addCell(avgTime.toString());
            tourValueTable.addCell(tour.getDistance()+"");
            tourValueTable.addCell(avgRating+"");
        }
        document.add(tourValueTable);
        document.close();
    }
}

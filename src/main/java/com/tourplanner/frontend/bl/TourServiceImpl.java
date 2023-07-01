package com.tourplanner.frontend.bl;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Image;
import com.tourplanner.backend.dal.entity.TourEntity;
import com.tourplanner.frontend.dal.TourApi;
import lombok.Getter;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;
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
    public void createOrUpdate(TourEntity tourEntity) throws IOException {
        tourApi.createOrUpdateTour(tourEntity).execute();
    }

    @Override
    public void deleteTour(UUID id) throws IOException {
        tourApi.deleteTour(id).execute();
    }

    @Override
    public List<TourEntity> getAllTours() throws IOException {
        return tourApi.getAllTours().execute().body();
    }

    @Override
    public void printTourPdf(UUID id) throws IOException {
        TourEntity tour = tourApi.getTour(id).execute().body();
        PdfWriter writer = new PdfWriter("RoutePdf_"+id+".pdf");
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        document.add(new Paragraph(tour.getName()).setFontSize(24).setBold());
        document.add(new Image(ImageDataFactory.create("images/Route"+id+".png")));
        document.add(new Paragraph("Description: " + tour.getDescription()).setFontSize(18));
        document.add(new Paragraph("From: " + tour.getStartingPoint()).setFontSize(18));
        document.add(new Paragraph("To: " + tour.getDestination()).setFontSize(18));
        document.add(new Paragraph("Estimated time: " + tour.getEstimatedTime()).setFontSize(18));
        document.add(new Paragraph("Distance: " + tour.getDistance()).setFontSize(18));
        document.add(new Paragraph("Transport Type: " + tour.getTransportType()).setFontSize(18));
        document.add(new Paragraph("Route Information: " + tour.getRouteInformation()).setFontSize(18));

        document.close();
    }
}

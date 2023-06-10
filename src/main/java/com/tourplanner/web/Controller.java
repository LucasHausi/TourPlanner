package com.tourplanner.web;
import com.tourplanner.model.Tour;
import com.tourplanner.repository.TourRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    public final TourRepository tourRepository;

    public Controller(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    @PostMapping(path = "/newTour")
    public ResponseEntity<Tour> newTour(@RequestBody Tour tour) {
        Tour savedTour = tourRepository.save(tour);
        //String path = "/newCar";
        //URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath(path).build(savedTour);
        return ResponseEntity.ok().body(savedTour);
    }
}

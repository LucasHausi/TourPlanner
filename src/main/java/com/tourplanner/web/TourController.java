package com.tourplanner.web;
import com.tourplanner.model.Tour;
import com.tourplanner.dal.repository.TourRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
public class TourController {
    public final TourRepository tourRepository;

    public TourController(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    @PostMapping(path = "/newTour")
    public ResponseEntity<Tour> newTour(@RequestBody Tour tour) {
        Tour savedTour = tourRepository.save(tour);
        String path = "/tour/"+savedTour.getId();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath(path).build(savedTour);
        return ResponseEntity.created(uri).body(savedTour);
    }

    @GetMapping(path = "/tour/{id}")
    public Tour getTour(@PathVariable("id") UUID id){
        return tourRepository.findById(id).orElseThrow();
   }

    @GetMapping(path = "/allTours")
    public List<Tour> getAllTours(){
        return tourRepository.findAll();
    }
}

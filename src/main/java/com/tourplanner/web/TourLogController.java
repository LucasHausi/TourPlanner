package com.tourplanner.web;
import com.tourplanner.model.TourLog;
import com.tourplanner.dal.repository.TourLogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
public class TourLogController {
    public final TourLogRepository tourLogRepository;

    public TourLogController(TourLogRepository tourLogRepository) {
        this.tourLogRepository = tourLogRepository;
    }

    @PostMapping(path = "/tourLog/add")
    public ResponseEntity<TourLog> addTourLog(@RequestBody TourLog tourLog) {
        TourLog savedTour = tourLogRepository.save(tourLog);
        String path = "/tour/"+savedTour.getId();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath(path).build(savedTour);
        return ResponseEntity.created(uri).body(savedTour);
    }

    @GetMapping(path = "/tourLog/{id}")
    public TourLog getTourLog(@PathVariable("id") UUID id){
        return tourLogRepository.findById(id).orElseThrow();
   }

    @GetMapping(path = "/tour/{tourId}/tourLogs")
    public List<TourLog> getTourLogs(@PathVariable("tourId") UUID tourId){
        return tourLogRepository.getTourLogByTourId(tourId);
    }

    @GetMapping(path = "/tourLogs")
    public List<TourLog> getAllTourLogs(){
        return tourLogRepository.findAll();
    }
}

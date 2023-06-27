package com.tourplanner.backend.web;
import com.tourplanner.backend.dal.entity.TourLogEntity;
import com.tourplanner.backend.dal.repository.TourLogRepository;
import com.tourplanner.backend.dal.repository.TourRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
public class TourLogController {
    public final TourLogRepository tourLogRepository;
    public final TourRepository tourRepository;

    public TourLogController(TourLogRepository tourLogRepository, TourRepository tourRepository) {
        this.tourLogRepository = tourLogRepository;
        this.tourRepository = tourRepository;
    }

    @PostMapping(path = "/tourLog/save")
    public ResponseEntity<TourLogEntity> createOrUpdateTourLog(@RequestBody TourLogEntity tourLogEntity) {
        TourLogEntity savedTour = tourLogRepository.save(tourLogEntity);
        String path = "/tour/"+savedTour.getId();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath(path).build(savedTour);
        return ResponseEntity.created(uri).body(savedTour);
    }

    @GetMapping(path = "/tourLog/{id}")
    public TourLogEntity getTourLog(@PathVariable("id") UUID id){
        return tourLogRepository.findById(id).orElseThrow();
   }

    @GetMapping(path = "/tour/{tourId}/tourLogs")
    public List<TourLogEntity> getTourLogs(@PathVariable("tourId") UUID tourId){
        return tourLogRepository.getTourLogEntityByTourEntity(tourRepository.findById(tourId).get());
    }

    @GetMapping(path = "/tourLogs")
    public List<TourLogEntity> getAllTourLogs(){
        return tourLogRepository.findAll();
    }
}

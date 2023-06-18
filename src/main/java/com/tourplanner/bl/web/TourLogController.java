package com.tourplanner.bl.web;
import com.tourplanner.dal.entity.TourLogEntity;
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
    public ResponseEntity<TourLogEntity> addTourLog(@RequestBody TourLogEntity tourLogEntity) {
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
        return tourLogRepository.getTourLogById(tourId);
    }

    @GetMapping(path = "/tourLogs")
    public List<TourLogEntity> getAllTourLogs(){
        return tourLogRepository.findAll();
    }
}

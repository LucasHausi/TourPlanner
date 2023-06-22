package com.tourplanner.backend.web;
import com.tourplanner.backend.dal.entity.TourEntity;
import com.tourplanner.backend.dal.repository.TourRepository;
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

    @PostMapping(path = "/tour/save")
    public ResponseEntity<TourEntity> createOrUpdateTour(@RequestBody TourEntity tourEntity) {
        TourEntity savedTourEntity = tourRepository.save(tourEntity);
        String path = "/tour/"+ savedTourEntity.getId();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath(path).build(savedTourEntity);
        return ResponseEntity.created(uri).body(savedTourEntity);
    }
    @GetMapping(path = "/tour/{id}")
    public TourEntity getTour(@PathVariable("id") UUID id){
        return tourRepository.findById(id).orElseThrow();
   }

    @GetMapping(path = "/allTours")
    public List<TourEntity> getAllTours(){
        return tourRepository.findAll();
    }
}

package com.tourplanner.backend.web;
import com.tourplanner.backend.dal.entity.TourEntity;
import com.tourplanner.backend.dal.repository.TourRepository;
import com.tourplanner.backend.mapper.TourMapper;
import com.tourplanner.shared.model.Tour;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class TourController {
    private final TourRepository tourRepository;
    private final TourMapper tourMapper;

    public TourController(TourRepository tourRepository, TourMapper tourMapper) {
        this.tourRepository = tourRepository;
        this.tourMapper = tourMapper;
    }

    @PostMapping(path = "/tour/save")
    public ResponseEntity<Tour> createOrUpdateTour(@RequestBody Tour tour) {
        TourEntity savedTourEntity = tourRepository.save(tourMapper.toEntity(tour));
        String path = "/tour/"+ savedTourEntity.getId();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath(path).build(savedTourEntity);
        return ResponseEntity.created(uri).body(tourMapper.fromEntity(savedTourEntity));
    }
    @DeleteMapping(value = "/tour/{tourId}/delete")
    public ResponseEntity<UUID> deleteTour(@PathVariable UUID tourId) {
        if (!tourRepository.existsById(tourId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        tourRepository.deleteById(tourId);
        return new ResponseEntity<>(tourId, HttpStatus.OK);
    }

    @GetMapping(path = "/tour/{id}")
    public Tour getTour(@PathVariable("id") UUID id){
        return tourMapper.fromEntity(tourRepository.findById(id).orElseThrow());
   }

    @GetMapping(path = "/allTours")
    public List<Tour> getAllTours(){
        return tourRepository.findAll().stream().map(tourMapper::fromEntity)
                .collect(Collectors.toList());
    }
}

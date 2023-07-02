package com.tourplanner.backend.web;
import com.tourplanner.backend.dal.entity.TourLogEntity;
import com.tourplanner.backend.dal.repository.TourLogRepository;
import com.tourplanner.backend.dal.repository.TourRepository;
import com.tourplanner.backend.mapper.TourLogMapper;
import com.tourplanner.shared.model.TourLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class TourLogController {
    public final TourLogRepository tourLogRepository;
    public final TourRepository tourRepository;

    public final TourLogMapper tourLogMapper;

    public TourLogController(TourLogRepository tourLogRepository, TourRepository tourRepository, TourLogMapper tourLogMapper) {
        this.tourLogRepository = tourLogRepository;
        this.tourRepository = tourRepository;
        this.tourLogMapper = tourLogMapper;
    }

    @PostMapping(path = "/tourLog/save")
    public ResponseEntity<TourLog> createOrUpdateTourLog(@RequestBody TourLog tourLog) {
        TourLogEntity savedTour = tourLogRepository.save(tourLogMapper.toEntity(tourLog));
        String path = "/tour/"+savedTour.getId();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath(path).build(savedTour);
        return ResponseEntity.created(uri).body(tourLogMapper.fromEntity(savedTour));
    }

    @GetMapping(path = "/tourLog/{id}")
    public TourLog getTourLog(@PathVariable("id") UUID id){
        return tourLogMapper.fromEntity(tourLogRepository.findById(id).orElseThrow());
   }

    @DeleteMapping(value = "/tourLog/{tourLogId}/delete")
    public ResponseEntity<UUID> deleteTourLog(@PathVariable UUID tourLogId) {
        if (!tourLogRepository.existsById(tourLogId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        tourLogRepository.deleteById(tourLogId);
        return new ResponseEntity<>(tourLogId, HttpStatus.OK);
    }

    @GetMapping(path = "/tour/{tourId}/tourLogs")
    public List<TourLog> getTourLogs(@PathVariable("tourId") UUID tourId){
        return tourLogRepository.getTourLogEntityByTourEntity(tourRepository.findById(tourId).get())
                .stream().map(tourLogMapper::fromEntity).collect(Collectors.toList());
    }

    @GetMapping(path = "/tourLogs")
    public List<TourLog> getAllTourLogs(){
        return tourLogRepository.findAll().stream().map(tourLogMapper::fromEntity)
                .collect(Collectors.toList());
    }
}

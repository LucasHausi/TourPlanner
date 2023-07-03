package com.tourplanner.backend.web;
import com.tourplanner.backend.dal.entity.TourLogEntity;
import com.tourplanner.backend.dal.repository.TourLogRepository;
import com.tourplanner.backend.dal.repository.TourRepository;
import com.tourplanner.backend.mapper.TourLogMapper;
import com.tourplanner.shared.model.TourLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static Logger logger;
    static {
        try {
            // you need to do something like below instaed of Logger.getLogger(....);
            logger = LogManager.getLogger(TourLogController.class);
        } catch (Throwable th) {
            throw new IllegalArgumentException("Cannot load the log property file", th);
        }
    }

    public TourLogController(TourLogRepository tourLogRepository, TourRepository tourRepository, TourLogMapper tourLogMapper) {
        this.tourLogRepository = tourLogRepository;
        this.tourRepository = tourRepository;
        this.tourLogMapper = tourLogMapper;
    }

    @PostMapping(path = "/tourLog/save")
    public ResponseEntity<TourLog> createOrUpdateTourLog(@RequestBody TourLog tourLog) {
        TourLogEntity savedTourLogEntity = tourLogRepository.save(tourLogMapper.toEntity(tourLog));
        String path = "/tour/"+savedTourLogEntity.getId();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath(path).build(savedTourLogEntity);
        logger.info("Sucessfully created or updated tourLog: " + savedTourLogEntity.getId());
        return ResponseEntity.created(uri).body(tourLogMapper.fromEntity(savedTourLogEntity));
    }

    @GetMapping(path = "/tourLog/{id}")
    public TourLog getTourLog(@PathVariable("id") UUID id){
        logger.info("Fetching TourLog: " + id);
        return tourLogMapper.fromEntity(tourLogRepository.findById(id).orElseThrow());
   }

    @DeleteMapping(value = "/tourLog/{tourLogId}/delete")
    public ResponseEntity<UUID> deleteTourLog(@PathVariable UUID tourLogId) {
        if (!tourLogRepository.existsById(tourLogId)) {
            logger.warn("Deletion not performed, TourLog " + tourLogId + " could not be found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Deleting TourLog: " + tourLogId);
        tourLogRepository.deleteById(tourLogId);
        return new ResponseEntity<>(tourLogId, HttpStatus.OK);
    }

    @GetMapping(path = "/tour/{tourId}/tourLogs")
    public List<TourLog> getTourLogs(@PathVariable("tourId") UUID tourId){
        logger.info("Fetching all TourLogs of Tour: " + tourId);
        return tourLogRepository.getTourLogEntityByTourEntity(tourRepository.findById(tourId).get())
                .stream().map(tourLogMapper::fromEntity).collect(Collectors.toList());
    }

    @GetMapping(path = "/tourLogs")
    public List<TourLog> getAllTourLogs(){
        logger.info("Fetching all existing TourLogs");
        return tourLogRepository.findAll().stream().map(tourLogMapper::fromEntity)
                .collect(Collectors.toList());
    }
}

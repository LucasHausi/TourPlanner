package com.tourplanner.backend.bl;
import com.tourplanner.backend.dal.entity.TourEntity;
import com.tourplanner.backend.dal.entity.TourLogEntity;
import com.tourplanner.backend.dal.repository.TourLogRepository;
import com.tourplanner.backend.dal.repository.TourRepository;
import com.tourplanner.backend.bl.mapper.TourMapper;
import com.tourplanner.shared.model.TourDTO;
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
public class TourController {
    private final TourRepository tourRepository;
    private final TourLogRepository tourLogRepository;
    private final TourMapper tourMapper;

    private static final Logger logger;
    static {
        try {
            // you need to do something like below instaed of Logger.getLogger(....);
            logger = LogManager.getLogger(TourController.class);
        } catch (Throwable th) {
            throw new IllegalArgumentException("Cannot load the log property file", th);
        }
    }

    public TourController(TourRepository tourRepository, TourLogRepository tourLogRepository, TourMapper tourMapper) {
        this.tourRepository = tourRepository;
        this.tourLogRepository = tourLogRepository;
        this.tourMapper = tourMapper;
    }

    @PostMapping(path = "/tour/save")
    public ResponseEntity<TourDTO> createOrUpdateTour(@RequestBody TourDTO tour) {
        TourEntity savedTourEntity = tourRepository.save(tourMapper.fromDTO(tour));
        String path = "/tour/"+ savedTourEntity.getId();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath(path).build(savedTourEntity);
        logger.info("Sucessfully created or updated tour: " + savedTourEntity.getId());
        return ResponseEntity.created(uri).body(tourMapper.toDTO(savedTourEntity));
    }
    @DeleteMapping(value = "/tour/{tourId}/delete")
    public ResponseEntity<UUID> deleteTour(@PathVariable UUID tourId) {
        if (!tourRepository.existsById(tourId)) {
            logger.warn("Deletion not performed, Tour " + tourId + " could not be found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        for(TourLogEntity tourLog : tourLogRepository.getTourLogEntityByTourEntity(tourRepository.findById(tourId).orElseThrow())){
            logger.info("Deleting TourLog: " + tourLog.getId() + " of Tour: " + tourId);
            tourLogRepository.deleteById(tourLog.getId());
        }
        logger.info("Deleting Tour: " + tourId);
        tourRepository.deleteById(tourId);
        return new ResponseEntity<>(tourId, HttpStatus.OK);
    }

    @GetMapping(path = "/tour/{id}")
    public TourDTO getTour(@PathVariable("id") UUID id){
        logger.info("Fetching Tour: " + id);
        return tourMapper.toDTO(tourRepository.findById(id).orElseThrow());
   }

    @GetMapping(path = "/allTours")
    public List<TourDTO> getAllTours(){
        logger.info("Fetching all existing Tours");
        return tourRepository.findAll().stream().map(tourMapper::toDTO)
                .collect(Collectors.toList());
    }
}

package com.tourplanner.shared.model;

import com.tourplanner.frontend.model.Tour;
import com.tourplanner.shared.enums.Difficulty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class TourLogDTO {
    private UUID id;
    private LocalDateTime dateTime;
    private String comment;
    private Difficulty difficulty;

    private String totalTime;

    private int rating;

    private Tour tour;
}

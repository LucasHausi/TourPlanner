package com.tourplanner.shared.model;

import com.tourplanner.shared.enums.Difficulty;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class TourLog {

    private UUID id;
    private LocalDateTime dateTime;
    private String comment;
    private Difficulty difficulty;

    private LocalTime totalTime;

    private int rating;

    private Tour tourEntity;
}

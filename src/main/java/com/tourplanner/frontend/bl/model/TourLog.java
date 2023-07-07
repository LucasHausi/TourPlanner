package com.tourplanner.frontend.bl.model;

import com.tourplanner.shared.enums.Difficulty;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class TourLog {

    private UUID id;
    private LocalDate date;
    private String comment;
    private Difficulty difficulty;
    private String totalTime;
    private int rating;

    private Tour tour;
}

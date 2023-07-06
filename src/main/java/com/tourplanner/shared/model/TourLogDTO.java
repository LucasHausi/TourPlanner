package com.tourplanner.shared.model;

import com.tourplanner.shared.enums.Difficulty;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class TourLogDTO {
    private UUID id;
    private String date;
    private String comment;
    private Difficulty difficulty;

    private String totalTime;

    private int rating;

    private TourDTO tour;
}

package com.tourplanner.frontend.bl.model;

import com.tourplanner.shared.enums.Difficulty;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;
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

    public boolean fitsFulltextSearchCriteria(String searchString) {
        return  (Objects.nonNull(this.date) && this.date.toString().contains(searchString)) ||
                (Objects.nonNull(this.comment) && this.comment.contains(searchString)) ||
                (Objects.nonNull(this.difficulty) && this.difficulty.toString().contains(searchString)) ||
                (Objects.nonNull(this.totalTime) && this.totalTime.contains(searchString)) ||
                String.valueOf(this.rating).contains(searchString);
    }
}

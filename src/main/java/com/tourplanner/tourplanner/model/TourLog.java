package com.tourplanner.tourplanner.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name="tourLog")
public class TourLog {
    @Id
    @GeneratedValue
    @Column(name="id")
    private UUID id;

    @Column(name="dateTime")
    private LocalDateTime dateTime;

    @Column(name="comment")
    private String comment;

    @Column(name="difficulty")
    private Difficulty difficulty;

    @Column(name="totalTime")
    private LocalTime totalTime;

    @Column(name="rating")
    private int rating;

}

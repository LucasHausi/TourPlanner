package com.tourplanner.dal.entity;

import com.tourplanner.bl.model.Difficulty;
import jakarta.persistence.*;
import lombok.*;

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
public class TourLogEntity {
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

    @ManyToOne
    @JoinColumn(name="tour_id", nullable=false)
    private TourEntity tourEntity;
}

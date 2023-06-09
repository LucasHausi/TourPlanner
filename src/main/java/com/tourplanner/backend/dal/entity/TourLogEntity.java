package com.tourplanner.backend.dal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tourplanner.shared.enums.Difficulty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    @Column(name="date")
    private LocalDate date;

    @Column(name="comment")
    private String comment;

    @Column(name="difficulty")
    private Difficulty difficulty;

    @Column(name="totalTime")
    private String totalTime;

    @Column(name="rating")
    private int rating;

    @ManyToOne
    @JoinColumn(name="tour_id", nullable=false)
    @JsonBackReference
    private TourEntity tourEntity;
}

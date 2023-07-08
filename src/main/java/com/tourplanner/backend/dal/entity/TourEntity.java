package com.tourplanner.backend.dal.entity;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tourplanner.shared.enums.TransportType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name="tour")
public class TourEntity {
    @Id
    @GeneratedValue
    @Column(name="id")
    private UUID id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="startingPoint")
    private String startingPoint;

    @Column(name="destination")
    private String destination;

    @Column(name="transportType")
    private TransportType transportType;

    @Column(name="distance")
    private double distance;

    @Column(name="estimatedTime")
    private String estimatedTime;

    @OneToMany(mappedBy="tourEntity")
    @JsonManagedReference
    private List<TourLogEntity> tourLogEntityList;

    @Column(name="routeInformation")
    private String routeInformation;
}

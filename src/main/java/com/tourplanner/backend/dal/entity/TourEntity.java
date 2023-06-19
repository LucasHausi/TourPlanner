package com.tourplanner.backend.dal.entity;

import java.util.UUID;

import com.tourplanner.shared.model.TransportType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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

    private String routeInformation; //ToDO soll irgendwie eine Map oder ein Bild einer Map oder so sein
}

package com.tourplanner.tourplanner.model;

import java.util.UUID;
import lombok.*;
import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name="tour")
public class Tour {
    @Id
    @GeneratedValue
    @Column(name="id")
    private UUID id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="from")
    private String from;

    @Column(name="to")
    private String to;

    @Column(name="transportType")
    private TransportType transportType;

    @Column(name="distance")
    private double distance;

    @Column(name="estimatedTime")
    private String estimatedTime;

    private String routeInformation; //ToDO soll irgendwie eine Map oder ein Bild einer Map oder so sein

}

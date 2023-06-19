package com.tourplanner.shared.model;

import com.tourplanner.shared.enums.TransportType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Tour {
    private UUID id;
    private String name;
    private String description;
    private String startingPoint;
    private String destination;
    private TransportType transportType;
    private double distance;
    private String estimatedTime;
    private String routeInformation; //ToDO soll irgendwie eine Map oder ein Bild einer Map oder so sein
}

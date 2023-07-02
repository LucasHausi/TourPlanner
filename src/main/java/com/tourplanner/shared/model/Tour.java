package com.tourplanner.shared.model;

import com.tourplanner.shared.enums.TransportType;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private List<TourLog> tourLogList;
    private String routeInformation; //ToDO soll irgendwie eine Map oder ein Bild einer Map oder so sein

    @Override
    public String toString() {
        return name;
    }

    public boolean fitsFulltextSearchCriteria(String searchString) {
        return  this.description.contains(searchString) ||
                this.destination.contains(searchString) ||
                this.startingPoint.contains(searchString) ||
                this.name.contains(searchString) ||
                this.estimatedTime.contains(searchString) ||
                this.routeInformation.contains(searchString) ||
                this.transportType.toString().contains(searchString);
    }
}

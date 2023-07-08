package com.tourplanner.frontend.bl.model;

import com.tourplanner.shared.enums.ChildFriendliness;
import com.tourplanner.shared.enums.Difficulty;
import com.tourplanner.shared.enums.Popularity;
import com.tourplanner.shared.enums.TransportType;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Tour implements Serializable {
    private UUID id;
    private String name;
    private String description;
    private String startingPoint;
    private String destination;
    private TransportType transportType;
    private double distance;
    private String estimatedTime;
    private List<TourLog> tourLogList;
    private String routeInformation;//ToDO soll irgendwie eine Map oder ein Bild einer Map oder so sein

    public Popularity getPopularity(){
       if(tourLogList.size()<3){
           return Popularity.UNPOPULAR;
       }
       else if(tourLogList.size()<5){
           return Popularity.LIKED;
       }
       else if(tourLogList.size()<7){
           return Popularity.WELL_LIKED;
       }
       else {
           return Popularity.FAVOURITE;
       }
    }

    public ChildFriendliness getChildFriendliness(){

        Integer avgTime = getAverageTime();
        var numberOfUnfriendlyLogs = tourLogList.stream().map(TourLog::getDifficulty)
                .filter(difficulty -> difficulty.equals(Difficulty.HARD) || difficulty.equals(Difficulty.MASTER))
                .count();
        var numberOfFriendlyLogs= tourLogList.stream().map(TourLog::getDifficulty)
                .filter(difficulty -> difficulty.equals(Difficulty.MEDIUM) || difficulty.equals(Difficulty.EASY))
                .count();


        if(numberOfUnfriendlyLogs >= numberOfFriendlyLogs || avgTime > 180 || distance > 30){
            return ChildFriendliness.NOT_FRIENDLY;
        }
        else if(avgTime > 120 || distance > 20){
            return ChildFriendliness.FEASIBLE;
        }
        else if(numberOfFriendlyLogs > numberOfUnfriendlyLogs*2 || avgTime <= 60 || distance <= 10){
            return ChildFriendliness.VERY_FRIENDLY;
        }
        else {
            return ChildFriendliness.FRIENDLY;
        }
    }
    public Integer getAverageTime(){
        List<String> times =  tourLogList.stream().map(TourLog::getTotalTime).toList();
        int avgTime = 0;
        for(String time : times){
            var splitTime = time.split(":");
            avgTime += Integer.parseInt(splitTime[0])*60 + Integer.parseInt(splitTime[1]);
        }
        if(times.size() > 0) {
            avgTime /= times.size();
        }
        return avgTime;
    }
    @Override
    public String toString() {
        return name;
    }

    public boolean fulltextSearch(String searchString) {
        for(String searchKeyword : searchString.split(" ")){
            if(this.fitsFulltextSearchCriteria(searchKeyword)){
                return true;
            }

            if(tourLogList.stream().anyMatch(tourLog -> tourLog.fitsFulltextSearchCriteria(searchKeyword))){
                return true;
            }
        }
        return false;
    }
    public boolean fitsFulltextSearchCriteria(String searchString) {
        return (Objects.nonNull(this.description) && this.description.contains(searchString)) ||
                (Objects.nonNull(this.destination) && this.destination.contains(searchString)) ||
                (Objects.nonNull(this.startingPoint) && this.startingPoint.contains(searchString)) ||
                (Objects.nonNull(this.name) && this.name.contains(searchString)) ||
                (Objects.nonNull(this.estimatedTime) && this.estimatedTime.contains(searchString)) ||
                (Objects.nonNull(this.routeInformation) && this.routeInformation.contains(searchString)) ||
                (Objects.nonNull(this.transportType) && this.transportType.toString().contains(searchString)) ||
                this.getPopularity().label.contains(searchString) ||
                this.getPopularity().toString().contains(searchString) ||
                this.getChildFriendliness().label.contains(searchString) ||
                this.getChildFriendliness().toString().contains(searchString);
    }
}

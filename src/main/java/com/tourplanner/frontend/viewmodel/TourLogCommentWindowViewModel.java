package com.tourplanner.frontend.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TourLogCommentWindowViewModel {
    private final StringProperty tourLogComment = new SimpleStringProperty();

    public void setTourLogComment(String tourLogComment){
        this.tourLogComment.set(tourLogComment);
    }
}

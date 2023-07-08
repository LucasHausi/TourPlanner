package com.tourplanner.frontend.bl.model;

import com.tourplanner.shared.enums.Popularity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TourTest {


    @Test
    void testGetPopularityUnpopular() {
        Tour tour = new Tour();
        tour.setTourLogList(List.of(new TourLog(), new TourLog()));
        assertThat(tour.getPopularity()).isEqualTo(Popularity.UNPOPULAR);
    }

    @Test
    void testGetPopularityLiked() {
        Tour tour = new Tour();
        tour.setTourLogList(List.of(new TourLog(), new TourLog(), new TourLog()));
        assertThat(tour.getPopularity()).isEqualTo(Popularity.LIKED);
    }

    @Test
    void testGetPopularityWellLiked() {
        Tour tour = new Tour();
        tour.setTourLogList(List.of(new TourLog(), new TourLog(), new TourLog(),new TourLog(), new TourLog()));
        assertThat(tour.getPopularity()).isEqualTo(Popularity.WELL_LIKED);
    }

    @Test
    void testGetPopularityFavourite() {
        Tour tour = new Tour();
        tour.setTourLogList(List.of(new TourLog(), new TourLog(), new TourLog(),new TourLog(), new TourLog(),new TourLog(), new TourLog(), new TourLog(),new TourLog(), new TourLog()));
        assertThat(tour.getPopularity()).isEqualTo(Popularity.FAVOURITE);
    }

    @Test
    void getChildFriendliness() {
    }

    @Test
    void getAverageTime() {
    }
}
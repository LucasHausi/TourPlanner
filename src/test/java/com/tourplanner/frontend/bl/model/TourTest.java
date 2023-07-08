package com.tourplanner.frontend.bl.model;

import com.tourplanner.shared.enums.ChildFriendliness;
import com.tourplanner.shared.enums.Difficulty;
import com.tourplanner.shared.enums.Popularity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TourTest {
    @Test
    void testGetPopularityUnpopular() {
        Tour tour = getEmptyTourWithTourLogs(2);
        assertThat(tour.getPopularity()).isEqualTo(Popularity.UNPOPULAR);
    }

    @Test
    void testGetPopularityLiked() {
        Tour tour = getEmptyTourWithTourLogs(3);
        assertThat(tour.getPopularity()).isEqualTo(Popularity.LIKED);
    }

    @Test
    void testGetPopularityWellLiked() {
        Tour tour = getEmptyTourWithTourLogs(5);
        assertThat(tour.getPopularity()).isEqualTo(Popularity.WELL_LIKED);
    }

    @Test
    void testGetPopularityFavourite() {
        Tour tour = getEmptyTourWithTourLogs(10);
        assertThat(tour.getPopularity()).isEqualTo(Popularity.FAVOURITE);
    }

    @Test
    void tesGetChildFriendlinessNotFriendly_distance() {
        Tour tour = getEmptyTourWithTourLog();
        TourLog tourLog = tour.getTourLogList().get(0);

        tour.setDistance(31);
        tourLog.setTotalTime("00:10");
        tourLog.setDifficulty(Difficulty.EASY);
        assertThat(tour.getChildFriendliness()).isEqualTo(ChildFriendliness.NOT_FRIENDLY);
    }
    @Test
    void tesGetChildFriendlinessNotFriendly_unfriendlyLogs() {
        Tour tour = getEmptyTourWithTourLog();
        TourLog tourLog = tour.getTourLogList().get(0);

        tour.setDistance(29);
        tourLog.setTotalTime("00:10");
        tourLog.setDifficulty(Difficulty.HARD);
        assertThat(tour.getChildFriendliness()).isEqualTo(ChildFriendliness.NOT_FRIENDLY);
    }
    @Test
    void tesGetChildFriendlinessNotFriendly_time() {
        Tour tour = getEmptyTourWithTourLog();
        TourLog tourLog = tour.getTourLogList().get(0);

        tour.setDistance(29);
        tourLog.setTotalTime("08:00");
        tourLog.setDifficulty(Difficulty.EASY);
        assertThat(tour.getChildFriendliness()).isEqualTo(ChildFriendliness.NOT_FRIENDLY);
    }
    @Test
    void tesGetChildFriendlinessFeasible_distance() {
        Tour tour = getEmptyTourWithTourLog();
        TourLog tourLog = tour.getTourLogList().get(0);

        tour.setDistance(21);
        tourLog.setTotalTime("00:10");
        tourLog.setDifficulty(Difficulty.EASY);
        assertThat(tour.getChildFriendliness()).isEqualTo(ChildFriendliness.FEASIBLE);
    }

    @Test
    void tesGetChildFriendlinessFeasible_time() {
        Tour tour = getEmptyTourWithTourLog();
        TourLog tourLog = tour.getTourLogList().get(0);

        tour.setDistance(19);
        tourLog.setTotalTime("02:30");
        tourLog.setDifficulty(Difficulty.EASY);
        assertThat(tour.getChildFriendliness()).isEqualTo(ChildFriendliness.FEASIBLE);
    }
    @Test
    void tesGetChildFriendlinessFriendly() {
        Tour tour = getEmptyTourWithTourLog();
        TourLog tourLog = tour.getTourLogList().get(0);

        tour.setDistance(15);
        tourLog.setTotalTime("01:30");
        tourLog.setDifficulty(Difficulty.EASY);
        assertThat(tour.getChildFriendliness()).isEqualTo(ChildFriendliness.FRIENDLY);
    }
    @Test
    void tesGetChildFriendlinessVeryFriendly_time() {
        Tour tour = getEmptyTourWithTourLog();
        TourLog tourLog = tour.getTourLogList().get(0);

        tour.setDistance(11);
        tourLog.setTotalTime("00:30");
        tourLog.setDifficulty(Difficulty.EASY);
        assertThat(tour.getChildFriendliness()).isEqualTo(ChildFriendliness.VERY_FRIENDLY);
    }
    @Test
    void tesGetChildFriendlinessVeryFriendly_distance() {
        Tour tour = getEmptyTourWithTourLog();
        TourLog tourLog = tour.getTourLogList().get(0);

        tour.setDistance(5);
        tourLog.setTotalTime("02:00");
        tourLog.setDifficulty(Difficulty.EASY);
        assertThat(tour.getChildFriendliness()).isEqualTo(ChildFriendliness.VERY_FRIENDLY);
    }

    @Test
    void testGetAverageTime_WithTourLogs() {
        Tour tour = getEmptyTourWithTourLogs(2);
        tour.getTourLogList().get(0).setTotalTime("02:00");
        tour.getTourLogList().get(1).setTotalTime("01:00");
        assertThat(tour.getAverageTime()).isEqualTo(90);
    }

    @Test
    void testGetAverageTime_WithoutTourLogs() {
        Tour tour = getEmptyTourWithTourLogs(0);
        assertThat(tour.getAverageTime()).isEqualTo(0);
    }

    @Test
    void testGetAverageRating_WithTourLogs() {
        Tour tour = getEmptyTourWithTourLogs(2);
        tour.getTourLogList().get(0).setRating(1);
        tour.getTourLogList().get(1).setRating(2);
        assertThat(tour.getAverageRating()).isEqualTo(1.5);
    }

    @Test
    void testGetAverageRating_WithoutTourLogs() {
        Tour tour = getEmptyTourWithTourLogs(0);
        assertThat(tour.getAverageRating()).isEqualTo(0);
    }

    private Tour getEmptyTourWithTourLogs(Integer numberOfTourLogs){
        Tour tour = new Tour();
        List<TourLog> tourLogList = new ArrayList<>();
        for (int i=0; i<numberOfTourLogs; i++){
            tourLogList.add(new TourLog());
        }
        tour.setTourLogList(tourLogList);
        return tour;
    }

    private Tour getEmptyTourWithTourLog(){
        return getEmptyTourWithTourLogs(1);
    }
}
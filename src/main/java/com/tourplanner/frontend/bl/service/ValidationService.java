package com.tourplanner.frontend.bl.service;

import com.tourplanner.shared.enums.Difficulty;
import com.tourplanner.shared.enums.TransportType;

import java.time.LocalDate;
import java.util.Objects;

public class ValidationService {
    private static final String NAME_REGEX = "^[A-Za-zß\\s0-9]+$";
    private static final String DESTINATION_REGEX = "^[A-Za-z\\s0-9ßüöä\\/]+$";
    private static final String TIME_REGEX = "^[0-9]*:[0-59]{2}$";

    public static boolean isValidName(String name) {
        return !name.isEmpty() && name.matches(NAME_REGEX);
    }

    public static boolean isValidDate(LocalDate date) {
        return Objects.nonNull(date) &&!date.isAfter(LocalDate.now());
    }

    public static boolean isValidDestination(String destination) {
        return Objects.nonNull(destination) && !destination.isEmpty() && destination.matches(DESTINATION_REGEX);
    }
    public static boolean isValidTransType(TransportType transportType){
        for(TransportType t : TransportType.values()){
            if(transportType == t){
                return true;
            }
        }
        return false;
    }
    public static boolean isValidTime(String time) {
        return Objects.nonNull(time) && !time.isEmpty() && time.matches(TIME_REGEX);
    }

    public static boolean isValidDifficulty(Difficulty difficulty) {
        for(Difficulty d : Difficulty.values()){
            if(difficulty == d){
                return true;
            }
        }
        return false;
    }
}

package com.tourplanner.frontend.bl;

import com.tourplanner.shared.enums.TransportType;

public class ValidationService {
    private static final String NAME_REGEX = "^[A-Za-zß\\s0-9]+$";
    private static final String DESTINATION_REGEX = "^[A-Za-z\\s0-9ß]+$";
    private static final String TIME_REGEX = "^[0-9]*:[0-59]{2}$";

    public static boolean isValidName(String name) {
        return !name.isEmpty() && name.matches(NAME_REGEX);
    }

    public static boolean isValidDestination(String destination) {
        return !destination.isEmpty() && destination.matches(DESTINATION_REGEX);
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
        return !time.isEmpty() && time.matches(TIME_REGEX);
    }
}

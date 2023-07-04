package com.tourplanner.shared.enums;

public enum Popularity {
    FAVOURITE("favourite pick"),
    WELL_LIKED("well liked"),
    LIKED("liked"),
    UNPOPULAR("unpopular");

    public final String label;

    Popularity(String label) {
        this.label = label;
    }
}

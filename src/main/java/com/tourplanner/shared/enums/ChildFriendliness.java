package com.tourplanner.shared.enums;

public enum ChildFriendliness {
    VERY_FRIENDLY("very child-friendly"),
    FRIENDLY("child-friendly"),
    FEASIBLE("doable with an active/older child"),
    NOT_FRIENDLY("not child-friendly");

    public final String label;

    ChildFriendliness(String label) {
        this.label = label;
    }

}

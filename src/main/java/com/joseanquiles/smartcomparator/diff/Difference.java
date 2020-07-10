package com.joseanquiles.smartcomparator.diff;

public class Difference {

    public DiffType diffType;
    public String description;

    @Override
    public String toString() {
        if (diffType == DiffType.LEFT) {
            return "LEFT: " + description;
        } else if (diffType == DiffType.RIGHT) {
            return "RIGHT: " + description;
        } else {
            return "MIXED";
        }
    }

}

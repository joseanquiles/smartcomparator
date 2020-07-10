package com.joseanquiles.smartcomparator.diff;

import java.util.ArrayList;
import java.util.List;

public class Difference {

    public DiffType diffType;
    public String description;
    public List<String> linesBefore = new ArrayList<String>();
    public List<String> linesAfter = new ArrayList<String>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < linesBefore.size(); i++) {
            sb.append("        ").append(linesBefore.get(i)).append("\n");
        }
        if (diffType == DiffType.LEFT) {
            sb.append("LEFT  : ").append(description);
        } else if (diffType == DiffType.RIGHT) {
            sb.append("RIGHT : ").append(description);
        } else {
            sb.append("MIXED");
        }
        sb.append("\n");
        for (int i = 0; i < linesAfter.size(); i++) {
            sb.append("        ").append(linesAfter.get(i)).append("\n");
        }
        return sb.toString();
    }

}

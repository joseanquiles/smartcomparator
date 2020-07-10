package com.joseanquiles.smartcomparator.plugin;

import java.util.List;

import com.joseanquiles.smartcomparator.diff.Difference;

public interface SmartComparatorPlugin {

    public List<Difference> compare(String leftFilename, String rightFilename) throws Exception;

}

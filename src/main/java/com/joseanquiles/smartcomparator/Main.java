package com.joseanquiles.smartcomparator;

import java.util.List;
import java.util.Map;

import com.joseanquiles.smartcomparator.diff.Difference;
import com.joseanquiles.smartcomparator.plugin.SmartComparatorPlugin;
import com.joseanquiles.smartcomparator.plugin.SmartComparatorPluginFactory;
import com.joseanquiles.smartcomparator.util.ArgsUtil;
import com.joseanquiles.smartcomparator.util.FileUtil;

public class Main {

    private static String syntax() {
        StringBuilder sb = new StringBuilder();
        sb.append("java ");
        sb.append(Main.class.getName());
        sb.append(" ");
        sb.append("-left leftFilename -right rightFilename");
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {

        // program arguments
        Map<String, String> argsMap = ArgsUtil.parseArgs(args);
        String leftFilename = argsMap.get("left");
        if (leftFilename == null) {
            System.out.println("-left argument is mandatory");
            System.out.println(syntax());
            System.exit(1);
        }
        String rightFilename = argsMap.get("right");
        if (rightFilename == null) {
            System.out.println("-right argument is mandatory");
            System.out.println(syntax());
            System.exit(2);
        }

        // file extension
        String leftFileExtension = FileUtil.getFileExtension(leftFilename);
        String rightFileExtension = FileUtil.getFileExtension(rightFilename);
        if (!leftFileExtension.equals(rightFileExtension)) {
            System.out.println("File extensions must match: " + leftFileExtension + ", " + rightFileExtension);
            System.exit(3);
        }

        // find plugin
        SmartComparatorPlugin plugin = SmartComparatorPluginFactory.getInstance().getPlugin(leftFileExtension);
        if (plugin == null) {
            System.out.println("No plugin found for " + leftFileExtension + " file extension");
            System.exit(4);
        }

        List<Difference> diffs = plugin.compare(leftFilename, rightFilename);

        for (int i = 0; i < diffs.size(); i++) {
            System.out.println(diffs.get(i).toString());
        }
    }

}

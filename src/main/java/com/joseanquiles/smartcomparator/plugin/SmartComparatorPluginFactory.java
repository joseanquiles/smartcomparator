package com.joseanquiles.smartcomparator.plugin;

import java.util.HashMap;
import java.util.Map;

public class SmartComparatorPluginFactory {

    private static SmartComparatorPluginFactory _instance = new SmartComparatorPluginFactory();

    private Map<String, SmartComparatorPlugin> plugins;

    private SmartComparatorPlugin[] pluginList = new SmartComparatorPlugin[] { new XmlComparator(), };
    private String[] extensionList = new String[] { "xml", };

    public static SmartComparatorPluginFactory getInstance() {
        return _instance;
    }

    public SmartComparatorPlugin getPlugin(String fileExtension) {
        return this.plugins.get(fileExtension);
    }

    private void registerPlugin(SmartComparatorPlugin plugin, String fileExtension) {
        if (this.plugins.containsKey(fileExtension)) {
            throw new RuntimeException("Plugin for files " + fileExtension + " already exists");
        }
        this.plugins.put(fileExtension, plugin);
    }

    private SmartComparatorPluginFactory() {
        this.plugins = new HashMap<String, SmartComparatorPlugin>();
        for (int i = 0; i < pluginList.length; i++) {
            this.registerPlugin(pluginList[i], extensionList[i]);
        }
    }

}

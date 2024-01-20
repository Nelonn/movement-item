package com.example.plugin;

import me.nelonn.coprolite.api.PluginContainer;
import me.nelonn.coprolite.api.PluginInitializer;
import me.nelonn.coprolite.paper.CoprolitePlugin;

public class Main implements PluginInitializer {
    public static final String ID = "example-plugin";

    @Override
    public void onInitialize(PluginContainer pluginContainer) {
        CoprolitePlugin.ON_ENABLE.register(plugin -> {
            plugin.getServer().getPluginManager().registerEvents(new EventListener(), plugin);
        });
    }
}

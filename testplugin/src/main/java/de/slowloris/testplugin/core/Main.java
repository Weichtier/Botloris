package de.slowloris.testplugin.core;

import com.github.theholywaffle.teamspeak3.TS3Api;
import de.slowloris.slowbot.ts3.core.plugin.PluginManager;
import de.slowloris.slowbot.ts3.core.plugin.TeamspeakPlugin;

public class Main implements TeamspeakPlugin {

    private static TS3Api api;

    public void onEnable(PluginManager pluginManager) {
        System.out.println("Loading!");


        api = pluginManager.getApi();
        api.sendChannelMessage("Testplugin");

    }

    public void onDisable(PluginManager pluginManager) {

    }

    public static TS3Api getApi() {
        return api;
    }
}

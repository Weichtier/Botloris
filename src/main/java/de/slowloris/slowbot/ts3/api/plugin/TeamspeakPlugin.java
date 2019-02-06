package de.slowloris.slowbot.ts3.api.plugin;

import com.github.theholywaffle.teamspeak3.TS3Api;
import de.slowloris.slowbot.ts3.core.TeamspeakBot;

public abstract class TeamspeakPlugin {

    private String dataFolder;

    public abstract void onEnable();
    public abstract void onDisable();

    public PluginManager getPluginManager(){
        return TeamspeakBot.getInstance().getPluginManager();
    }

    public String getDataFolder() {
        return dataFolder;
    }

    void setDataFolder(String dataFolder) {
        this.dataFolder = dataFolder;
    }

    public TS3Api getApi(){
        return getPluginManager().getApi();
    }
}

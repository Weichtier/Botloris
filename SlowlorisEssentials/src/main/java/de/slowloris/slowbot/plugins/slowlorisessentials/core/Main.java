package de.slowloris.slowbot.plugins.slowlorisessentials.core;

import de.slowloris.slowbot.plugins.slowlorisessentials.commands.DevCommand;
import de.slowloris.slowbot.plugins.slowlorisessentials.listeners.JoinListener;
import de.slowloris.slowbot.plugins.slowlorisessentials.utils.AfkManager;
import de.slowloris.slowbot.ts3.api.configuration.YamlConfiguration;
import de.slowloris.slowbot.ts3.api.plugin.TeamspeakPlugin;

import java.io.File;
import java.util.ArrayList;

public class Main extends TeamspeakPlugin {

    private static Main instance;
    private static YamlConfiguration configuration;

    public void onEnable() {
        instance = this;

        File file = new File("plugins/SlowlorisEssentials/config.yml");

        if(!file.exists()){
            configuration = YamlConfiguration.loadConfiguration(file);
            configuration.set("Welcomemessage.Activated", true);
            configuration.set("Welcomemessage.Message", "Welcome on this Server!");
            configuration.set("Afk.ChannelId", 0);
            configuration.set("Afk.MovedMessage", "You got moved because you were 10 minutes away!");
            configuration.set("Afk.AfkTime", 600);
            ArrayList<String> blockedgroups = new ArrayList<String>();
            blockedgroups.add("6");
            configuration.set("Afk.BlockedGroups", blockedgroups);
            configuration.save();
        }

        configuration = YamlConfiguration.loadConfiguration(file);


        getPluginManager().registerListener(new JoinListener());
        getPluginManager().registerCommand("dev", new DevCommand());

        AfkManager.setAfkTime(getConfiguration().getInt("Afk.AfkTime"));
        AfkManager.startTiming();

    }

    public void onDisable() {

    }

    public static Main getInstance() {
        return instance;
    }

    public static YamlConfiguration getConfiguration() {
        return configuration;
    }
}

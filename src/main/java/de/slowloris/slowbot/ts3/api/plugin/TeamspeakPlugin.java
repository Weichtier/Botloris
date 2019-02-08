package de.slowloris.slowbot.ts3.api.plugin;

import com.github.theholywaffle.teamspeak3.TS3Api;
import de.slowloris.slowbot.ts3.api.configuration.Configuration;
import de.slowloris.slowbot.ts3.api.configuration.ConfigurationProvider;
import de.slowloris.slowbot.ts3.api.configuration.YamlConfiguration;
import de.slowloris.slowbot.ts3.core.TeamspeakBot;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;

public abstract class TeamspeakPlugin {

    private PluginManager pluginManager;
    private Logger logger;
    private String dataFolder;
    private File configFile;
    private Configuration config;

    public abstract void onEnable();
    public abstract void onDisable();

    public PluginManager getPluginManager(){
        return pluginManager;
    }

    public String getDataFolder() {
        return dataFolder;
    }

    public Logger getLogger() {
        return logger;
    }

    public Configuration getConfig() {
        return config;
    }

    public TS3Api getApi(){
        return getPluginManager().getApi();
    }

    public void init(PluginManager pluginManager, String dataFolder){
        this.pluginManager = pluginManager;
        this.logger = pluginManager.getLogger();
        this.dataFolder = dataFolder;
        this.configFile = new File(dataFolder, "config.yml");

        try {
            if(!this.configFile.exists()){
                this.configFile.getParentFile().mkdirs();
                this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load("");
            }else {
                this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
            }

        } catch (IOException e) {
            throw new RuntimeException("Cannot read configuration file", e);
        }
    }
}

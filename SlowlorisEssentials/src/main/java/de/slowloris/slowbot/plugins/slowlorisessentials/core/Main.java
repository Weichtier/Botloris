package de.slowloris.slowbot.plugins.slowlorisessentials.core;

import de.slowloris.slowbot.plugins.slowlorisessentials.commands.*;
import de.slowloris.slowbot.plugins.slowlorisessentials.listeners.JoinListener;
import de.slowloris.slowbot.plugins.slowlorisessentials.listeners.LeaveListener;
import de.slowloris.slowbot.plugins.slowlorisessentials.sql.MySQL;
import de.slowloris.slowbot.plugins.slowlorisessentials.utils.AfkManager;
import de.slowloris.slowbot.plugins.slowlorisessentials.utils.Utils;
import de.slowloris.slowbot.ts3.api.configuration.Configuration;
import de.slowloris.slowbot.ts3.api.configuration.ConfigurationProvider;
import de.slowloris.slowbot.ts3.api.configuration.YamlConfiguration;
import de.slowloris.slowbot.ts3.api.plugin.TeamspeakPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends TeamspeakPlugin {

    private static Main instance;
    private static MySQL sql;
    private static Configuration configuration;

    public void onEnable() {
        instance = this;

        File file = new File(getDataFolder(), "config.yml");

        if(!file.exists()){
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Unable to create configuration file", e);
            }
            try {
                configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            configuration.set("Mysql.Activated", false);
            configuration.set("Mysql.Host", "localhost");
            configuration.set("Mysql.Port", 3306);
            configuration.set("Mysql.Username", "root");
            configuration.set("Mysql.Password", "password");
            configuration.set("Mysql.Database", "ts3bot");
            configuration.set("Mysql.Ranksync.CopyGroupsAtRegister", false);

            configuration.set("Welcomemessage.Activated", true);
            configuration.set("Welcomemessage.Message", "Welcome on this Server!");

            configuration.set("Afk.ChannelId", 0);
            configuration.set("Afk.MovedMessage", "You got moved because you were 10 minutes away!");
            configuration.set("Afk.AfkTime", 600);

            List<String> blockedgroups = new ArrayList<>();
            blockedgroups.add("6");
            configuration.set("Afk.BlockedGroups", blockedgroups);

            configuration.set("Admin.AdminGroupId", 6);

            configuration.set("Usercounter.Enabled", false);
            configuration.set("Usercounter.ChannelId", 0);
            configuration.set("Usercounter.ChannelName", "Users Online: %USERS%");

            List<String> badnicknames = new ArrayList<>();
            badnicknames.add("ImABadUsername");
            badnicknames.add("ImTheSecondBadUsername");
            configuration.set("BadNicknames", badnicknames);

            try {
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
                onEnable();
                return;
            } catch (IOException e) {
                throw new RuntimeException("Unable to save configuration file", e);
            }
        }
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read configuration file", e);
        }


        if(configuration.getBoolean("Mysql.Activated")){
            getLogger().info("Connecting to Mysql Database");
            sql = new MySQL(
                    configuration.getString("Mysql.Host"),
                    configuration.getString("Mysql.Database"),
                    configuration.getString("Mysql.Username"),
                    configuration.getString("Mysql.Password"));
            if(sql.getConnErr().equals("")){
                Utils.checkSqlTables();
                getLogger().info("Connected to Mysql Database");
            }else {
                getLogger().warn("Error with Mysql Database: " + sql.getConnErr());
            }
        }


        getPluginManager().registerListener(new JoinListener());
        getPluginManager().registerListener(new LeaveListener());

        getPluginManager().registerCommand("channelid", new ChannelIdCommand());
        getPluginManager().registerCommand("clientdbid", new ClientDbIdCommand());
        getPluginManager().registerCommand("clientid", new ClientIdCommand());
        getPluginManager().registerCommand("clientinfo", new ClientinfoCommand());
        getPluginManager().registerCommand("clientuid", new ClientUidCommand());
        getPluginManager().registerCommand("dev", new DevCommand());
        getPluginManager().registerCommand("reload", new ReloadCommand());

        AfkManager.setAfkTime(getConfiguration().getInt("Afk.AfkTime") * 1000);
        AfkManager.startTiming();

    }

    public void onDisable() {
        try {
            sql.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static Main getInstance() {
        return instance;
    }

    public static MySQL getSql() {
        return sql;
    }

    public static Configuration getConfiguration() {
        return configuration;
    }
}

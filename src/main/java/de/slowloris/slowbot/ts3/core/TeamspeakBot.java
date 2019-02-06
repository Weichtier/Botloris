package de.slowloris.slowbot.ts3.core;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventType;
import de.slowloris.slowbot.ts3.api.plugin.PluginManager;
import de.slowloris.slowbot.ts3.core.commands.HelpCommand;

public class TeamspeakBot {

    private static TeamspeakBot instance;
    private PluginManager pluginManager;
    private TS3Api api;

    private TeamspeakBot(String host, Integer port, Integer serverid, String username, String password, String nickname) {

        instance = this;

        final TS3Config config = new TS3Config();
        config.setHost(host);
        config.setQueryPort(port);

        final TS3Query query = new TS3Query(config);
        query.connect();
        api = query.getApi();
        api.login(username, password);
        api.selectVirtualServerById(serverid);
        api.registerAllEvents();
        api.registerEvent(TS3EventType.TEXT_PRIVATE);
        api.setNickname(nickname);

        pluginManager = PluginManager.newInstance(api);
        pluginManager.loadPlugins();

        pluginManager.registerListener(new CommandReader());
        pluginManager.registerCommand("help", new HelpCommand());

        api.sendChannelMessage( api.whoAmI().getNickname() + " is online!");
    }

    public TS3Api getApi() {
        return api;
    }

    public PluginManager getPluginManager() {
        if(pluginManager == null){
            newPluginManager();
        }
        return pluginManager;
    }

    public boolean reload(){
        try {
            pluginManager.unloadPlugins();
            pluginManager.loadPlugins();
        }catch (Exception exc){
            return true;
        }
        return false;
    }

    private void newPluginManager(){
        pluginManager = new PluginManager(api);
    }

    public static TeamspeakBot getInstance() {
        return instance;
    }

    public static TeamspeakBot newInstance(String host, Integer port, Integer serverid, String username, String password, String nickname){
        if(instance == null){
            new TeamspeakBot(host, port, serverid, username, password, nickname);
            return instance;
        }else {
            return instance;
        }
    }

    public static TeamspeakBot newInstance(String host, Integer serverid, String username, String password, String nickname){
        return newInstance(host, 10011, serverid, username, password, nickname);
    }
}

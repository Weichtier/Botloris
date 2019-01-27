package de.slowloris.slowbot.ts3.core;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import de.slowloris.slowbot.ts3.core.plugin.PluginManager;

public class TeamspeakBot {

    private static TeamspeakBot instance;
    private PluginManager pluginManager;
    private TS3Api api;

    public TeamspeakBot(String host, String username, String password, String nickname) {

        final TS3Config config = new TS3Config();
        config.setHost(host);

        final TS3Query query = new TS3Query(config);
        query.connect();
        api = query.getApi();

        pluginManager = new PluginManager(api);

        api.login(username, password);
        api.selectVirtualServerById(1);
        api.setNickname(nickname);

        api.sendChannelMessage("Slowbot is online!");
    }

    public TS3Api getApi() {
        return api;
    }

    public static TeamspeakBot getInstance() {
        return instance;
    }

    public static TeamspeakBot newInstance(String host, String username, String password, String nickname){
        instance = new TeamspeakBot(host, username, password, nickname);
        return instance;
    }
}

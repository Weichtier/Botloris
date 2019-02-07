package de.slowloris.slowbot.plugins.slowlorisessentials.utils;

import com.github.theholywaffle.teamspeak3.api.wrapper.ChannelBase;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.slowloris.slowbot.plugins.slowlorisessentials.core.Main;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AfkManager {

    private static long afkTime;

    public static void startTiming(){
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(AfkManager::kickAfk, 0, afkTime, TimeUnit.MILLISECONDS);
    }

    public static void setAfkTime(long afkTime) {
        AfkManager.afkTime = afkTime;
    }

    private static void kickAfk(){

        List<String> blockedgroups = Main.getConfiguration().getStringList("Afk.BlockedGroups");
        ChannelBase channelBase = Main.getInstance().getApi().getChannelInfo(Main.getConfiguration().getInt("Afk.ChannelId"));

        for (Client client : Main.getInstance().getApi().getClients()){
            for (int group : client.getServerGroups()){
                if(blockedgroups.contains(group + "")){
                    return;
                }
            }

            if(client.getChannelId() != channelBase.getId()){
                if(client.getIdleTime() > afkTime){
                    Main.getInstance().getApi().moveClient(client, channelBase);
                    Main.getInstance().getApi().sendPrivateMessage(client.getId(), Main.getConfiguration().getString("Afk.MovedMessage"));
                }
            }
        }
    }
}

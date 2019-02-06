package de.slowloris.slowbot.plugins.slowlorisessentials.utils;

import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.slowloris.slowbot.plugins.slowlorisessentials.core.Main;
import de.slowloris.slowbot.ts3.utils.BBCode;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static String noPermissions = BBCode.BOLD_START + "You must be an Admin to use that!" + BBCode.BOLD_END;

    public static boolean isAdmin(Client client){
        int id = Main.getConfiguration().getInt("Admin.AdminGroupId");
        for (int serverGroup : client.getServerGroups()) {
            if(serverGroup == id){
                return true;
            }
        }
        return false;
    }

    public static String toString(String[] strings){
        StringBuilder builder = new StringBuilder();
        int count = 1;
        for (String string : strings){
            builder.append(string);
            if(count != strings.length){
                builder.append(" ");
            }
            count++;
        }
        return builder.toString();
    }

    public static void renameChannel(int channelid, String channelname){
        Map<ChannelProperty, String> properties = new HashMap<ChannelProperty, String>();
        properties.put(ChannelProperty.CHANNEL_NAME, channelname);
        Main.getInstance().getApi().editChannel(channelid, properties);
    }

    public static void userCounter(){
        if(Main.getConfiguration().getBoolean("Usercounter.Enabled")){

            int channelid = Main.getConfiguration().getInt("Usercounter.ChannelId");
            String channelname = Main.getConfiguration().getString("Usercounter.ChannelName").replaceAll("%USERS%", String.valueOf(Main.getInstance().getApi().getClients().size()));
            Utils.renameChannel(channelid, channelname);
        }
    }

}

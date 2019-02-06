package de.slowloris.slowbot.plugins.slowlorisessentials.commands;

import com.github.theholywaffle.teamspeak3.api.wrapper.Channel;
import com.github.theholywaffle.teamspeak3.api.wrapper.ChannelInfo;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.slowloris.slowbot.plugins.slowlorisessentials.core.Main;
import de.slowloris.slowbot.plugins.slowlorisessentials.utils.Utils;
import de.slowloris.slowbot.ts3.api.command.Command;
import de.slowloris.slowbot.ts3.utils.BBCode;

public class ChannelIdCommand implements Command {
    @Override
    public boolean execute(Client client, String s, String[] args) {
        if(args.length >= 1){

            String channelname = Utils.toString(args);

            Channel channel = Main.getInstance().getApi().getChannelByNameExact(channelname, false);
            if(channel == null){
                Main.getInstance().getApi().sendPrivateMessage(client.getId(), "Sorry, but the Channel " + BBCode.BOLD_START + channelname + BBCode.BOLD_END + " cannot be found! INFO: Upper/Lower case have to be correct!");
                return false;
            }
            Main.getInstance().getApi().sendPrivateMessage(client.getId(), "Channel Id of Channel " + BBCode.BOLD_START + channel.getName() + BBCode.BOLD_END + " is " + BBCode.BOLD_START + channel.getId() + BBCode.BOLD_END +  "");
        }else {
            ChannelInfo channel = Main.getInstance().getApi().getChannelInfo(client.getChannelId());
            Main.getInstance().getApi().sendPrivateMessage(client.getId(), "Channel Id of Channel " + BBCode.BOLD_START + channel.getName() + BBCode.BOLD_END + " is " + BBCode.BOLD_START + channel.getId() + BBCode.BOLD_END +  "");
        }
        return false;
    }
}

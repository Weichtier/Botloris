package de.slowloris.slowbot.ts3.core;

import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

public class CommandReader extends TS3EventAdapter {
    @Override
    public void onTextMessage(TextMessageEvent e) {
        TeamspeakBot bot = TeamspeakBot.getInstance();
        if(e.getMessage().startsWith("!")){
            TeamspeakBot.getInstance().getLogger().info("User " + e.getInvokerName() + " [UUID=" + e.getInvokerUniqueId() + "] executed command " + e.getMessage().split(" ")[0]);
            if(e.getTargetMode().equals(TextMessageTargetMode.CLIENT) && e.getInvokerId() != bot.getApi().whoAmI().getId()){
                bot.getPluginManager().getCommandHandler().handleCommand(TeamspeakBot.getInstance().getApi().getClientByUId(e.getInvokerUniqueId()), e.getMessage().substring(1));
            }else if((e.getTargetMode().equals(TextMessageTargetMode.CHANNEL) || e.getTargetMode().equals(TextMessageTargetMode.SERVER)) && e.getMessage().equalsIgnoreCase("!open")){
                bot.getApi().sendPrivateMessage(e.getInvokerId(), "Commands here, please!");
            }
        }
    }
}

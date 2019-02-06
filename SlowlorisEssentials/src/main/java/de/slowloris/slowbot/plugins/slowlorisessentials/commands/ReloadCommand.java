package de.slowloris.slowbot.plugins.slowlorisessentials.commands;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.slowloris.slowbot.plugins.slowlorisessentials.core.Main;
import de.slowloris.slowbot.plugins.slowlorisessentials.utils.Utils;
import de.slowloris.slowbot.ts3.api.command.Command;
import de.slowloris.slowbot.ts3.core.TeamspeakBot;

public class ReloadCommand implements Command {
    @Override
    public boolean execute(Client client, String s, String[] strings) {
        if(Utils.isAdmin(client)){
            Main.getInstance().getApi().sendPrivateMessage(client.getId(), "Now reloading the bot...");
            TeamspeakBot.getInstance().reload();
            Main.getInstance().getApi().sendPrivateMessage(client.getId(), "Reloading complete!");
        }else {
            Main.getInstance().getApi().sendPrivateMessage(client.getId(), Utils.noPermissions);
        }
        return false;
    }
}

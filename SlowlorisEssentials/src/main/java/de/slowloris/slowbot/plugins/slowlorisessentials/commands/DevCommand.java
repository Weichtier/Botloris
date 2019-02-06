package de.slowloris.slowbot.plugins.slowlorisessentials.commands;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.slowloris.slowbot.plugins.slowlorisessentials.core.Main;
import de.slowloris.slowbot.plugins.slowlorisessentials.utils.Utils;
import de.slowloris.slowbot.ts3.api.command.Command;

import java.util.ArrayList;

public class DevCommand implements Command {

    private static ArrayList<String> clients = new ArrayList<String>();

    public boolean execute(Client client, String s, String[] strings) {
        if(Utils.isAdmin(client)){
            if(clients.contains(client.getUniqueIdentifier())){
                Main.getInstance().getApi().sendPrivateMessage(client.getId(), "You're already in Devmode!");
            }else {
                clients.add(client.getUniqueIdentifier());
                Main.getInstance().getApi().sendPrivateMessage(client.getId(), "Welcome in the Devmode!");
            }
        }else {
            Main.getInstance().getApi().sendPrivateMessage(client.getId(), Utils.noPermissions);
        }
        return false;
    }
}

package de.slowloris.slowbot.plugins.slowlorisessentials.commands;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.slowloris.slowbot.plugins.slowlorisessentials.core.Main;
import de.slowloris.slowbot.plugins.slowlorisessentials.utils.Utils;
import de.slowloris.slowbot.ts3.api.command.Command;
import de.slowloris.slowbot.ts3.utils.BBCode;

public class ClientUidCommand implements Command {
    @Override
    public boolean execute(Client client, String s, String[] args) {
        if(args.length >= 1){
            if(Utils.isAdmin(client)){

                String username = Utils.toString(args);

                Client target = Main.getInstance().getApi().getClientByNameExact(username, false);
                if(target == null){
                    Main.getInstance().getApi().sendPrivateMessage(client.getId(), "Sorry, but the User " + BBCode.BOLD_START + username + BBCode.BOLD_END + " cannot be found! INFO: Upper/Lower case have to be correct!");
                    return false;
                }
                Main.getInstance().getApi().sendPrivateMessage(client.getId(), "Unique ID of User " + BBCode.BOLD_START + username + BBCode.BOLD_END + ": " + BBCode.BOLD_START + target.getUniqueIdentifier() + BBCode.BOLD_END +  "");
            }else {
                Main.getInstance().getApi().sendPrivateMessage(client.getId(), Utils.noPermissions);
            }
        }else {
            Main.getInstance().getApi().sendPrivateMessage(client.getId(), "Your Unique ID: " + BBCode.BOLD_START + client.getUniqueIdentifier() + BBCode.BOLD_END);
        }
        return false;
    }
}

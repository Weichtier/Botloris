package de.slowloris.slowbot.ts3.core.commands;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.slowloris.slowbot.ts3.api.command.Command;
import de.slowloris.slowbot.ts3.core.TeamspeakBot;

import java.util.Map;

public class HelpCommand implements Command {
    @Override
    public boolean execute(Client sender, String command, String[] args) {
        String out = "\nAvailable Commands:\n";
        for (Map.Entry<String, Command> entry : TeamspeakBot.getInstance().getPluginManager().getCommandHandler().getCommands().entrySet()){
            out += "- [b]" + entry.getKey() + "[/b]\n";
        }

        TeamspeakBot.getInstance().getApi().sendPrivateMessage(sender.getId(), out);

        return false;
    }
}

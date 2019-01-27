package de.slowloris.slowbot.ts3.api.command;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

public class CommandSender extends Client {
    public CommandSender(Client client) {
        super(client.getMap());
    }
}

package de.slowloris.slowbot.ts3.api.command;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

public interface Command {
    public boolean execute(Client sender, String command, String[] args);
}

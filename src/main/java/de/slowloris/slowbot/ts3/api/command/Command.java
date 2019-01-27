package de.slowloris.slowbot.ts3.api.command;

public interface Command {
    public boolean execute(CommandSender commandSender, String command, String[] args);
}

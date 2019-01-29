package de.slowloris.slowbot.ts3.api.command;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

import java.util.Arrays;
import java.util.HashMap;

public class CommandHandler {

    public HashMap<String, Command> commands = new HashMap<String, Command>();
    public TS3Api api;

    public CommandHandler(TS3Api api) {
        this.api = api;
    }

    public void handleCommand(Client sender, String command, String[] args){
        if(commands.containsKey(command)){
            commands.get(command).execute(sender, command, args);
        }else {
            api.sendPrivateMessage(sender.getId(), "Command not Found! Try \"[b]!help[/b]\" for help!");
        }
    }

    public void handleCommand(Client sender, String in){
        String[] list = in.split(" ");
        String command = list[0];
        String[] args = Arrays.copyOfRange(list, 1, list.length);
        handleCommand(sender, command, args);
    }

    public void registerCommand(String invoke, Command command){
        if(!commands.containsKey(invoke)){
            commands.put(invoke, command);
        }
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }
}

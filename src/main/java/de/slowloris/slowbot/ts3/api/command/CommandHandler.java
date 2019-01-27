package de.slowloris.slowbot.ts3.api.command;

import java.util.Arrays;
import java.util.HashMap;

public class CommandHandler {

    public HashMap<String, Command> commands = new HashMap<String, Command>();

    public void handleCommand(CommandSender commandSender, String command, String[] args){
        if(commands.containsKey(command)){
            commands.get(command).execute(commandSender, command, args);
        }
    }

    public void handleCommand(CommandSender commandSender, String in){
        String[] list = in.split(" ");
        String command = list[0];
        String[] args = Arrays.copyOfRange(list, 1, list.length);
        handleCommand(commandSender, command, args);
    }

    public void registerCommand(String invoke, Command command){
        if(!commands.containsKey(invoke)){
            commands.put(invoke, command);
        }
    }
}

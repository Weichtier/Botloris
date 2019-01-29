package de.slowloris.slowbot;

import de.slowloris.slowbot.ts3.core.TeamspeakBot;

public class Main {
    public static void main(String[] args){


        TeamspeakBot tsBot = TeamspeakBot.newInstance("192.168.0.55", "serveradmin", "marvinwenk1324", "Putze");


        /*
        if(args.length >= 1){
            if(args[0].equalsIgnoreCase("ts3")){
                if(args.length == 5){
                    String host = args[1];
                    String username = args[2];
                    String password = args[3];
                    String nickname = args[4];
                    TeamspeakBot tsBot = TeamspeakBot.getInstance();
                    if(tsBot == null){
                        tsBot = TeamspeakBot.newInstance(host, username, password, nickname);
                    }
                }
            }
        }
        */


    }
}

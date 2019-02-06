package de.slowloris.slowbot;

import com.google.common.io.ByteStreams;
import de.slowloris.slowbot.ts3.api.configuration.Configuration;
import de.slowloris.slowbot.ts3.api.configuration.ConfigurationProvider;
import de.slowloris.slowbot.ts3.api.configuration.YamlConfiguration;
import de.slowloris.slowbot.ts3.core.TeamspeakBot;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args){

        Configuration configuration;

        File file = new File("./config.yml");

        if(!file.exists()){
            new File(file.getParentFile().getAbsolutePath()).mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Unable to create configuration file", e);
            }


            try {
                configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            } catch (IOException e) {
                throw new RuntimeException("Unable to read configuration file", e);
            }

            configuration.set("Teamspeak.Host", "localhost");
            configuration.set("Teamspeak.Port", 10011);
            configuration.set("Teamspeak.ServerId", 1);
            configuration.set("Teamspeak.Username", "serveradmin");
            configuration.set("Teamspeak.Password", "password");
            configuration.set("Teamspeak.Nickname", "Ts3Bot by Slowloris");

            try {
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
            } catch (IOException e) {
                throw new RuntimeException("Unable to write configuration file", e);
            }

            System.out.println("Please edit Config and restart.");
            System.exit(0);
            return;
        }

        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            System.out.println("Config Error!");
            return;
        }

        TeamspeakBot tsBot = TeamspeakBot.newInstance(
                configuration.getString("Teamspeak.Host"),
                configuration.getInt("Teamspeak.Port"),
                configuration.getInt("Teamspeak.ServerId"),
                configuration.getString("Teamspeak.Username"),
                configuration.getString("Teamspeak.Password"),
                configuration.getString("Teamspeak.Nickname")
        );


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

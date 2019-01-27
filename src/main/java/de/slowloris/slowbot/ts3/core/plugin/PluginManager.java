package de.slowloris.slowbot.ts3.core.plugin;

import com.github.theholywaffle.teamspeak3.TS3Api;
import de.slowloris.slowbot.ts3.api.command.Command;
import de.slowloris.slowbot.ts3.api.command.CommandHandler;
import de.slowloris.slowbot.ts3.core.TeamspeakBot;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class PluginManager {

    private CommandHandler commandHandler;
    private TeamspeakBot teamspeakBot;
    private TS3Api api;
    private List<TeamspeakPlugin> plugins = new ArrayList<TeamspeakPlugin>();

    public PluginManager(TS3Api api) {

        teamspeakBot = TeamspeakBot.getInstance();
        this.api = api;

        commandHandler = new CommandHandler();
        File[] files = new File("plugins").listFiles();
        assert files != null;
        for(File f : files){
            try {
                loadPlugin(f);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadPlugin(File file) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        System.out.println("Loading " + file.getName());
        JarFile jar = new JarFile(file);
        Manifest manifest = jar.getManifest();
        Attributes attrib = manifest.getMainAttributes();
        String main = attrib.getValue(Attributes.Name.MAIN_CLASS);


        Class cl = new URLClassLoader(new URL[]{file.toURL()}).loadClass(main);
        Class[] interfaces = cl.getInterfaces();
        boolean isplugin = false;
        for(int y = 0; y < interfaces.length && !isplugin; y++)
            if(interfaces[y].getName().equals("de.slowloris.slowbot.core.plugin.TeamspeakPlugin"))
                isplugin = true;
        if(isplugin){
            TeamspeakPlugin plugin = (TeamspeakPlugin) cl.newInstance();
            plugins.add(plugin);
            plugin.onEnable(this);
        }

        System.out.println("Loaded " + file.getName());

    }

    public TS3Api getApi() {
        return api;
    }

    public void registerCommand(String invoke, Command command){
        commandHandler.registerCommand(invoke, command);
    }
}

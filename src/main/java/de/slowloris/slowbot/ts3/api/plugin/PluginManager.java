package de.slowloris.slowbot.ts3.api.plugin;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.TS3Listener;
import de.slowloris.slowbot.ts3.api.command.Command;
import de.slowloris.slowbot.ts3.api.command.CommandHandler;
import de.slowloris.slowbot.ts3.core.TeamspeakBot;
import org.apache.commons.io.FilenameUtils;

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

    private static PluginManager instance;
    private CommandHandler commandHandler;
    private TeamspeakBot teamspeakBot;
    private TS3Api api;
    private List<TeamspeakPlugin> plugins = new ArrayList<TeamspeakPlugin>();

    public PluginManager(TS3Api api) {

        teamspeakBot = TeamspeakBot.getInstance();
        this.api = api;

        commandHandler = new CommandHandler(api);
    }

    public void loadPlugin(File file) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        if(!FilenameUtils.isExtension(file.getName(), "jar")){
            return;
        }

        System.out.println("Loading Plugin");

        JarFile jar = new JarFile(file);
        Manifest manifest = jar.getManifest();
        Attributes attrib = manifest.getMainAttributes();
        String main = attrib.getValue(Attributes.Name.MAIN_CLASS);
        Class cl = new URLClassLoader(new URL[]{file.toURL()}).loadClass(main);

        TeamspeakPlugin plugin = (TeamspeakPlugin) cl.newInstance();
        plugins.add(plugin);
        plugin.onEnable();

        System.out.println("Loaded Plugin!");

    }

    public void loadPlugins(){
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

    public void unloadPlugin(TeamspeakPlugin plugin){
        if(plugins.contains(plugin)){
            System.out.println("Unloading Plugin!");
            plugin.onDisable();
            plugins.remove(plugin);
            System.out.println("Unloaded Plugin!");
        }
    }

    public void unloadPlugins(){
        for (TeamspeakPlugin plugin : plugins) {
            unloadPlugin(plugin);
        }
    }

    public TS3Api getApi() {
        return api;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public void registerCommand(String invoke, Command command){
        commandHandler.registerCommand(invoke, command);
    }

    public void registerListener(TS3Listener... listener){
        api.addTS3Listeners(listener);
    }

    public static PluginManager getInstance() {
        return instance;
    }

    public static PluginManager newInstance(TS3Api api){
        if(instance == null){
            instance = new PluginManager(api);
            return instance;
        }else {
            return instance;
        }
    }
}

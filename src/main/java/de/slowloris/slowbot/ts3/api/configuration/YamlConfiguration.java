package de.slowloris.slowbot.ts3.api.configuration;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YamlConfiguration {

    private File file;
    private Map<String, Object> entrys;

    public YamlConfiguration(File file) {
        this.file = file;

        if(!this.file.exists()){
            try {
                this.file.getParentFile().mkdirs();
                boolean created = this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }


        Yaml yaml = new Yaml();
        entrys = new HashMap<String, Object>();
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        entrys = yaml.load(inputStream);
    }

    public String getString(String key){
        return get(key) instanceof String ? (String) get(key) : null;
    }

    public Integer getInt(String key){
        return get(key) instanceof Integer ? (Integer) get(key) : null;
    }

    public Double getDouble(String key){
        return get(key) instanceof Double ? (Double) get(key) : null;
    }

    public Float getFloat(String key){
        return get(key) instanceof Float ? (Float) get(key) : null;
    }

    public Boolean getBoolean(String key){
        return get(key) instanceof Boolean ? (Boolean) get(key) : null;
    }

    public List<String> getStringList(String key){
        return get(key) instanceof List ? (List<String>) get(key) : null;
    }

    public Object get(String key){

        return entrys.containsKey(key) ? entrys.get(key) : null;
    }

    public YamlConfiguration set(String key, Object value){
        if(entrys == null){
            entrys = new HashMap<String, Object>();
        }
        entrys.put(key, value);
        return this;
    }

    public boolean isSet(String key){
        return entrys.containsKey(key);
    }

    public void save(){

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Yaml yaml = new Yaml(new Representer(){
            @Override
            protected Node representMapping(Tag tag, Map<?, ?> mapping, DumperOptions.FlowStyle flowStyle) {
                return super.representMapping(tag, mapping, DumperOptions.FlowStyle.BLOCK);
            }
        }, options);

        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        yaml.dump(entrys, writer);
    }

    public static YamlConfiguration loadConfiguration(File file){
        return new YamlConfiguration(file);
    }
}

package dev.bedless.carrythat.config;

import dev.bedless.carrythat.CarryThat;
import lombok.Getter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

@Getter
@SuppressWarnings("all")
public abstract class ConfigurationLIB {

    private static final ArrayList<String> registeredConfigs = new ArrayList<>();
    private final File file;
    @Getter
    private final HashMap<String, Object> defaults = new HashMap<>();
    @Getter
    private final JSONParser parser;
    @Getter
    private JSONObject json;
    private String registerName;

    /**
     * Creates, reads and writes the Configuration to given File
     *
     * @param file File to read/write the configuration to/from
     */
    public ConfigurationLIB(File file) {
        this.file = file;
        this.parser = new JSONParser();
        runFileLogic();
        try {
            registeredConfigs.add(file.getName());
            this.json = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Saves the Default Values of the Configuration
     */
    public abstract void saveDefaults();

    /**
     * Executes the saveCache() function
     */
    public void handleCreation() {
        saveCache();
    }

    /**
     * Adds give object to the default HashMap with the given Name
     * @param object Object to add to the HashMap
     * @param registerName Name to add with the Object
     */
    public void addToCache(Object object, String registerName) {
        this.registerName = registerName;
        defaults.put(registerName, object);
    }

    /**
     * Writes the File
     */
    private void runFileLogic() {
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
                PrintWriter pw = new PrintWriter(file, StandardCharsets.UTF_8);
                pw.print("{");
                pw.print("}");
                pw.flush();
                pw.close();
            }
        } catch (Exception ex) {
            CarryThat.getInstance().getServer().getConsoleSender().sendMessage("Error Creating File");
        }
    }

    /**
     * Writes previously given data to the File
     */
    public void saveCache() {
        JSONObject toSave = new JSONObject();
        for (String s : defaults.keySet()) {
            Object o = defaults.get(s);
            if (o instanceof String) toSave.put(s, getString(s));
            if (o instanceof Double) toSave.put(s, getDouble(s));
            if (o instanceof Integer) toSave.put(s, getInteger(s));
            if (o instanceof JSONObject) toSave.put(s, getObject(s));
            if (o instanceof JSONArray) toSave.put(s, getArray(s));
        }
        TreeMap<String, Object> treeMap = new TreeMap<String, Object>(toSave);
        String prettyJsonString = CarryThat.getInstance().getGson().toJson(treeMap);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(prettyJsonString);
            fw.flush();
            fw.close();
        } catch (Exception ex) {
            CarryThat.getInstance().getServer().getConsoleSender().sendMessage("Error Writing to File");
        }
    }

    /**
     * Gets data with given key as a String
     * @param key The key associated to the Data to look for
     * @return Returns data as a String
     */
    public String getRawData(String key) {
        if (!json.containsKey(key)) return "";

        return String.valueOf(defaults.get(key));
    }

    /**
     * Gets data with given key as a String
     * @param key The key associated to the Data to look for
     * @return Returns data as a String
     */
    public String getString(String key) {
        return getRawData(key);
    }

    /**
     * Gets data with given key as a Boolean
     * @param key The key associated to the Data to look for
     * @return Returns data as a Boolean
     */
    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(getRawData(key));
    }

    /**
     * Gets data with given key as a Double
     * @param key The key associated to the Data to look for
     * @return Returns data as a Double
     */
    public double getDouble(String key) {
        try {
            return Double.parseDouble(getRawData(key));
        } catch (Exception ex) {
            //
        }
        return -1;
    }

    /**
     * Gets data with given key as an Integer
     * @param key The key associated to the Data to look for
     * @return Returns data as an Integer
     */
    public double getInteger(String key) {
        try {
            return Integer.parseInt(getRawData(key));
        } catch (Exception ex) {
            //
        }
        return -1;
    }

    /**
     * Gets data with given key as an Object
     * @param key The key associated to the Data to look for
     * @return Returns data as an Object
     */
    public JSONObject getObject(String key) {
        return json.containsKey(key) ? (JSONObject) json.get(key)
                : (defaults.containsKey(key) ? (JSONObject) defaults.get(key) : new JSONObject());
    }

    /**
     * Gets data with given key as an Array
     * @param key The key associated to the Data to look for
     * @return Returns data as an Array
     */
    public JSONArray getArray(String key) {
        return json.containsKey(key) ? (JSONArray) json.get(key)
                : (defaults.containsKey(key) ? (JSONArray) defaults.get(key) : new JSONArray());
    }

    /**
     * Gets if the JSON Object contains the given Key as a Boolean
     * @param key The key associated to the Data to look for
     * @return Returns if the key exists in the JSON Object
     */
    public boolean contains(String key) {
        return json.containsKey(key);
    }

    /**
     * Removes key with associated Data
     * @param key The key associated to the Data to remove
     */
    public void remove(String key) {
        json.remove(key);
    }
}

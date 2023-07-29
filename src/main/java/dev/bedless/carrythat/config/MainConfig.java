package dev.bedless.carrythat.config;

import dev.bedless.carrythat.util.log.CLogger;
import org.json.simple.JSONObject;

import java.io.File;

public class MainConfig extends ConfigurationLIB {

    private JSONObject master;

    /**
     * Creates and Inits the Config
     *
     * @param file The file to read/write the config to
     */
    public MainConfig(File file) {
        super(file);
        saveDefaults();
        addToCache(master, "Settings");
        saveCache();
        handleCreation();
    }

    /**
     * Saves the Default Configuration
     * @Override overwrites the inherited saveDefaults() to save the values we want
     */
    @Override
    public void saveDefaults() {
        master = new JSONObject();
        for (CarryGroup carryGroup : CarryGroup.values()) {
            JSONObject g = new JSONObject();
            for (Carry carry : Carry.getGroup(carryGroup)) {
                g.put(carry.getFancyName(), carry.isEnabled());
            }
            master.put(carryGroup.getConfigName(), g);
        }
    }

    /**
     * Reloads the Configuration, gets the main object from the JSON String
     */
    public void reload() {
        master = (JSONObject) getJson().get(getRegisterName());
        if (master == null) return;
        load();
    }

    /**
     * Loads the Values from our config into our Enum
     */
    public void load() {
        if (getJson() == null || master == null) {
            CLogger.printConfigError();
            return;
        }
        for (CarryGroup carryGroup : CarryGroup.values()) {
            for (Carry carry : Carry.getGroup(carryGroup)) {
                JSONObject g = (JSONObject) master.get(carryGroup.getConfigName());
                if (g == null) {
                    CLogger.printConfigError();
                    continue;
                }
                carry.setIsEnabled((boolean) g.get(carry.getFancyName()));
            }
        }
    }
}

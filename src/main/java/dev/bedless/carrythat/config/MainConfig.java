package dev.bedless.carrythat.config;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.json.simple.JSONObject;

import java.io.File;

public class MainConfig extends ConfigurationLIB {

    private JSONObject master;

    public MainConfig(File file) {
        super(file);
        saveDefaults();
        addToCache(master, "Settings");
        saveCache();
        handleCreation();
    }

    @Override
    public void saveDefaults() {
        master = new JSONObject();
        for (CarryGroup carryGroup : CarryGroup.values()) {
            JSONObject g = new JSONObject();
            for (Carry carry : Carry.getGroup(carryGroup)) {
                g.put(carry.getFancyName(), carry.getIsEnableConfig());
            }
            master.put(carryGroup.getConfigName(), g);
        }
    }

    public void reload() {
        master = (JSONObject) getJson().get(getRegisterName());
        if (master == null) return;
        load();
    }

    public void load() {
        try {
            for (CarryGroup carryGroup : CarryGroup.values()) {
                for (Carry carry : Carry.getGroup(carryGroup)) {
                    JSONObject g = (JSONObject) master.get(carryGroup.getConfigName());
                    carry.setIsEnabled((boolean) g.get(carry.getFancyName()));
                }
            }
        } catch (NullPointerException ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[FATAL] Invalid File or Configuration, try restarting the Server, if that doesnt work, delete the config and restart!");
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[FATAL] Pulling Backup, please Restart the Plugin to Use Custom Values!");
        }
    }
}

package dev.bedless.carrythat;

import co.aikar.commands.PaperCommandManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.bedless.carrythat.command.AdminCommand;
import dev.bedless.carrythat.config.MainConfig;
import dev.bedless.carrythat.event.CarryEvent;
import dev.bedless.carrythat.util.log.CLogLevel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static dev.bedless.carrythat.util.log.CLogger.log;

@SuppressWarnings("all")
public final class CarryThat extends JavaPlugin {
    @Getter
    private static CarryThat instance;
    @Getter
    private Gson gson;
    @Getter
    private PaperCommandManager paperCommandManager;
    private float tps;


    @Override
    public void onEnable() {
        log("Starting Timing Manager", CLogLevel.STARTUP);
        log("Initializing Variables", CLogLevel.STARTUP);
        handleVar();
        handleEvents();
        handleCommands();
    }

    @Override
    public void onDisable() {
        log("Saving Data", CLogLevel.SHUTDOWN);
    }

    /**
     * Creates a GSON Builder, registers the PaperCommandManager and run the handleConfig() function
     */
    private void handleVar() {
        instance = this;
        gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        paperCommandManager = new PaperCommandManager(this);
        handleConfig();
    }

    /**
     * Registers events
     */
    private void handleEvents() {
        new CarryEvent();
    }

    /**
     * Adds commands to the PaperCommandManager
     */
    private void handleCommands() {
        new AdminCommand();
    }

    /**
     * Creates a new MainConfiguration with the name config.json and reloads it
     */
    private void handleConfig() {
        new MainConfig(new File(this.getDataFolder() + File.separator, "config.json")).reload();
    }

    /**
     * Calculates the TPS, send it to the console and returns it as a Float
     *
     * @return Returns the TPS in a float
     * @deprecated This function will be removed in the near future
     */
    public float getTPS() {
        tps = System.currentTimeMillis();
        Bukkit.getConsoleSender().sendMessage(tps + "");
        Bukkit.getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                tps = System.currentTimeMillis() - tps;
                Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[CRITICAL] Server TPS" + tps);
            }
        }, 20);
        return tps;
    }
}

package dev.bedless.carrythat.util.log;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class CLogger {

    /**
     * Sends a Message to the Console with given LogLevel
     *
     * @param message the Message to send to the console
     * @param level   the level of the Message
     */
    public static void log(String message, CLogLevel level) {
        Bukkit.getConsoleSender().sendMessage(level.getPrefix() + message);
    }

    /**
     * Send a Message to the Console with the LogLevel of INFO
     * @param message the Message to send to the console
     */
    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(CLogLevel.INFO.getPrefix() + message);
    }

    /**
     * Prints the Configuration Error to the Console
     */
    public static void printConfigError() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[FATAL] Invalid File or Configuration, try restarting the Server, if that doesnt work, delete the config and restart!");
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[FATAL] Pulling Backup, please Restart the Plugin to Use Custom Values!");
    }
}

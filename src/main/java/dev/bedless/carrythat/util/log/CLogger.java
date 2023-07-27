package dev.bedless.carrythat.util.log;

import org.bukkit.Bukkit;

public class CLogger {
    public static void log(String message, CLogLevel level) {
        Bukkit.getConsoleSender().sendMessage(level.getPrefix() + message);
    }

    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(CLogLevel.INFO.getPrefix() + message);
    }
}

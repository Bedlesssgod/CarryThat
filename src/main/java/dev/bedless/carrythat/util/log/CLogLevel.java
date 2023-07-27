package dev.bedless.carrythat.util.log;

import lombok.Getter;
import org.bukkit.ChatColor;

@Getter
public enum CLogLevel {

    PLUGIN(ChatColor.GRAY + "[CarryThat] "),
    STARTUP(PLUGIN.getPrefix() + ChatColor.GREEN + "[STARTUP] "),
    SHUTDOWN(PLUGIN.getPrefix() + ChatColor.RED + "[SHUTDOWN] "),
    INFO(PLUGIN.getPrefix() + ChatColor.WHITE + "[INFO] "),
    WARNING(PLUGIN.getPrefix() + ChatColor.YELLOW + "[WARNING] "),
    ERROR(PLUGIN.getPrefix() + ChatColor.RED + "[ERROR] ");

    private final String prefix;

    CLogLevel(String prefix) {
        this.prefix = prefix;
    }
}

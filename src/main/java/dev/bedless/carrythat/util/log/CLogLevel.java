package dev.bedless.carrythat.util.log;

import org.bukkit.ChatColor;

public enum CLogLevel {

    PLUGIN(ChatColor.GRAY + "[CarryThat] "),
    STARTUP(PLUGIN.getPrefix() + ChatColor.GREEN + "[STARTUP] "),
    SHUTDOWN(PLUGIN.getPrefix() + ChatColor.RED + "[SHUTDOWN] "),
    INFO(PLUGIN.getPrefix() + ChatColor.WHITE + "[INFO] "),
    WARNING(PLUGIN.getPrefix() + ChatColor.YELLOW + "[WARNING] "),
    ERROR(PLUGIN.getPrefix() + ChatColor.RED + "[ERROR] ");

    private final String prefix;

    /**
     * Creates a constant with the given Data
     *
     * @param prefix The prefix to return when using getPrefix()
     */
    CLogLevel(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Gets the prefix of the Constant
     *
     * @return Returns the Prefix set by the Constant
     */
    public String getPrefix() {
        return prefix;
    }
}

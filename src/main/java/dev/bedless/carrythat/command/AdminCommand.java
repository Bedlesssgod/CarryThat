package dev.bedless.carrythat.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import dev.bedless.carrythat.CarryThat;
import dev.bedless.carrythat.util.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("admin")
public class AdminCommand extends BaseCommand {

    public AdminCommand() {
        CarryThat.getInstance().getPaperCommandManager().registerCommand(this);
    }

    @Subcommand("tN")
    public void terminateNearCommand(Player player) {
        Bukkit.getServer().dispatchCommand(player, "kill @e[type=minecraft:armor_stand,distance=..4]");
    }

    @Subcommand("clearBlock")
    public void removeBlock(Player player) {
        PlayerData.getPlayerData(player.getUniqueId()).removeArmorStand();
    }

}

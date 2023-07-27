package dev.bedless.carrythat.event;

import dev.bedless.carrythat.CarryThat;
import dev.bedless.carrythat.util.data.PlayerData;
import dev.bedless.carrythat.util.player.CarryUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;

public class CarryEvent implements Listener {

    public CarryEvent() {
        CarryThat.getInstance().getServer().getPluginManager().registerEvents(this, CarryThat.getInstance());
    }

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || !event.getPlayer().isSneaking()) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        Bukkit.getConsoleSender().sendMessage("Checking if can be picked Up!");
        if (PlayerData.exist(event.getPlayer()) && PlayerData.getPlayerData(event.getPlayer()).hasFollowingArmorStand()) {
            PlayerData playerData = PlayerData.getPlayerData(event.getPlayer());
            Block block = event.getClickedBlock();
            Block block1 = block.getLocation().add(0, 1, 0).getBlock();
            if (block1.getType() == Material.AIR) {
                block1.setType(playerData.getPickedUp());
                event.getPlayer().sendMessage(ChatColor.RED + "Set block one above");
                return;
            }
            event.getPlayer().sendMessage("Not y1");
            Block block2 = block.getLocation().add(1, 0, 0).getBlock();
            if (block2.getType() == Material.AIR) {
                block2.setType(playerData.getPickedUp());
                event.getPlayer().sendMessage(ChatColor.RED + "Set block x 1");
                return;
            }
            event.getPlayer().sendMessage("Not x1");
            Block block3 = block.getLocation().add(0, 0, 1).getBlock();
            if (block3.getType() == Material.AIR) {
                block3.setType(playerData.getPickedUp());
                event.getPlayer().sendMessage(ChatColor.RED + "Set block z 1");
                return;
            }
            event.getPlayer().sendMessage("Not z1");
            event.getPlayer().sendMessage("Hot the acctual fuck");
            event.getPlayer().sendMessage(ChatColor.RED + "Couldn't Place the " + playerData.getPickedUp().name() + " you were holding!");
            event.setCancelled(true);
            return;
        }
        if (!CarryUtils.canBePickedUp(event)) return;
        Bukkit.getConsoleSender().sendMessage("could be picked up");
        Block clone = event.getClickedBlock();
        if (!PlayerData.exist(event.getPlayer())) {
            Bukkit.getConsoleSender().sendMessage("Player Didnt have Data");
            new PlayerData(event.getPlayer(), clone.getType()).createFollowingArmorStand();
            event.getClickedBlock().setType(Material.AIR);
            return;
        }
        if (PlayerData.getPlayerData(event.getPlayer()).hasFollowingArmorStand()) {
            Bukkit.getConsoleSender().sendMessage("Player already had Item Picked up");
            event.getClickedBlock().setType(Material.AIR);
            return;
        }
        PlayerData.getPlayerData(event.getPlayer().getUniqueId()).createFollowingArmorStand();
        Bukkit.getConsoleSender().sendMessage("Player Had Data, didnt have a following armorstand, created");
        event.getClickedBlock().setType(Material.AIR);
        event.setCancelled(true);
    }

    @EventHandler
    public void playerMoveEvent(PlayerMoveEvent event) {
        if (!PlayerData.exists(event.getPlayer().getUniqueId())) return;
        if (!PlayerData.getPlayerData(event.getPlayer().getUniqueId()).hasFollowingArmorStand()) {
        }
        //CarryUtils.forceFollowPlayer(PlayerData.getPlayerData(event.getPlayer().getUniqueId()));
    }
}

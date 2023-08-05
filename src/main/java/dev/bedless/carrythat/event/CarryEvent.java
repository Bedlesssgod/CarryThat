package dev.bedless.carrythat.event;

import dev.bedless.carrythat.CarryThat;
import dev.bedless.carrythat.util.data.PlayerData;
import dev.bedless.carrythat.util.player.CarryUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;

import static dev.bedless.carrythat.util.player.CarryUtils.removeBlock;

public class CarryEvent implements Listener {

    /**
     * Registers the Event with the Bukkit EventHandler
     */
    public CarryEvent() {
        CarryThat.getInstance().getServer().getPluginManager().registerEvents(this, CarryThat.getInstance());
    }

    /**
     * Checks if the Player Shift Rightclick with the Mainhand, if so then checks if the player already has a picked up Item
     * if the player does, then try placing the block otherwise it picks up the Block if it is allowed to pick it up
     *
     * @param event PlayerInteractEvent the event to trigger the code on
     */
    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || !event.getPlayer().isSneaking() || event.getHand() != EquipmentSlot.HAND)
            return;
        if (PlayerData.exist(event.getPlayer()) && PlayerData.getPlayerData(event.getPlayer()).hasFollowingArmorStand()) {
            PlayerData.getPlayerData(event.getPlayer()).handlePlacement(event);
            event.setCancelled(true);
            return;
        }
        if (!CarryUtils.canBePickedUp(event)) return;
        if (!PlayerData.exist(event.getPlayer())) {
            new PlayerData(event.getPlayer(), event.getClickedBlock().getType()).createFollowingArmorStand();
            removeBlock(event.getClickedBlock());
            event.setCancelled(true);
            return;
        }
        if (!PlayerData.getPlayerData(event.getPlayer()).hasFollowingArmorStand()) {
            new PlayerData(event.getPlayer(), event.getClickedBlock().getType()).handlePickup(event.getClickedBlock());
            removeBlock(event.getClickedBlock());
            event.setCancelled(true);
            return;
        }
    }

    /**
     * Makes the Armorstand follow the Player
     * @param event PlayerMoveEvent the event to trigger the code on
     */
    @EventHandler
    public void playerMoveEvent(PlayerMoveEvent event) {
        if (!PlayerData.exists(event.getPlayer().getUniqueId())) return;
        if (!PlayerData.getPlayerData(event.getPlayer().getUniqueId()).hasFollowingArmorStand()) return;
        //CarryUtils.forceFollowPlayer(PlayerData.getPlayerData(event.getPlayer().getUniqueId()));
    }
}

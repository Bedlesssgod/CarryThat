package dev.bedless.carrythat.util.player;

import dev.bedless.carrythat.CarryThat;
import dev.bedless.carrythat.config.Carry;
import dev.bedless.carrythat.util.data.PlayerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class CarryUtils {

    public static boolean canBePickedUp(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return false;
        if (!event.getPlayer().isSneaking()) return false;
        if (event.getClickedBlock() == null) return false;
        return Carry.getAllowedMaterials().contains(event.getClickedBlock().getType());
    }

    public static ArmorStand summonArmorStand(Location location, Material headMaterial) {
        return location.getWorld().spawn(location, ArmorStand.class, armorStand -> {
            armorStand.setVisible(false);
            armorStand.setCanPickupItems(false);
            armorStand.setInvulnerable(true);
            armorStand.setGravity(false);
            armorStand.setCustomName(headMaterial.name());
            armorStand.setCustomNameVisible(true);
            armorStand.getEquipment().setHelmet(new ItemStack(headMaterial));
            armorStand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.ADDING_OR_CHANGING);
            armorStand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.REMOVING_OR_CHANGING);
        });
    }

    public static void forceFollowPlayer(PlayerData playerData) {
        playerData.getFollowingArmorStand().teleport(playerData.getPlayer().getLocation());
        CarryThat.getInstance().getTPS();
    }
}

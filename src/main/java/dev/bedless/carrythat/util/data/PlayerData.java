package dev.bedless.carrythat.util.data;

import dev.bedless.carrythat.util.player.CarryUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings("All")
public class PlayerData {

    private static HashMap<UUID, PlayerData> cachedPlayerData = new HashMap<>();
    private boolean hasPickedUp;
    private boolean hasArmorStand;
    private Material currentPickedUp;
    private ArmorStand followingArmorStand;
    private Player player;

    public PlayerData(Player player, Material currentPickedUp) {
        hasPickedUp = false;
        hasArmorStand = false;
        this.player = player;
        this.currentPickedUp = currentPickedUp;
        cachedPlayerData.put(player.getUniqueId(), this);
    }

    public PlayerData(UUID uuid, Material currentPickedUp) {
        hasPickedUp = false;
        hasArmorStand = false;
        this.player = Bukkit.getPlayer(uuid);
        this.currentPickedUp = currentPickedUp;
        cachedPlayerData.put(player.getUniqueId(), this);
    }

    public static boolean exists(UUID uuid) {
        return cachedPlayerData.containsKey(uuid);
    }

    public static boolean exist(Player player) {
        return cachedPlayerData.containsKey(player.getUniqueId());
    }

    public static PlayerData getPlayerData(UUID uuid) {
        return cachedPlayerData.get(uuid);
    }

    public static PlayerData getPlayerData(Player player) {
        return cachedPlayerData.get(player.getUniqueId());
    }

    public static HashMap<UUID, PlayerData> getSavedPlayerData() {
        return cachedPlayerData;
    }

    /*@Setter Functions*/
    public void setHasPickedUp(boolean hasPickedUp) {
        this.hasPickedUp = hasPickedUp;
    }

    public void setHasFollowingArmorStand(boolean hasArmorStand) {
        this.hasArmorStand = hasArmorStand;
    }

    /*@Is Functions*/
    public boolean hasFollowingArmorStand() {
        return hasArmorStand;
    }

    /*@Getter Functions*/
    public ArmorStand getFollowingArmorStand() {
        return followingArmorStand;
    }

    public void setFollowingArmorStand(ArmorStand armorStand) {
        hasArmorStand = true;
        followingArmorStand = armorStand;
    }

    public Material getPickedUp() {
        return currentPickedUp;
    }

    public Player getPlayer() {
        return player;
    }

    /*@Remove Functions*/
    public void removeArmorStand() {
        followingArmorStand.remove();
        player.removePotionEffect(PotionEffectType.SLOW);
        hasArmorStand = false;
    }

    /*@Util Functions*/
    public PlayerData createFollowingArmorStand() {
        followingArmorStand = CarryUtils.summonArmorStand(player.getLocation(), currentPickedUp);
        hasArmorStand = true;
        return this;
    }
}

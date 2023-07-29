package dev.bedless.carrythat.util.data;

import dev.bedless.carrythat.util.player.CarryUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

import static dev.bedless.carrythat.util.player.CarryUtils.removeBlock;

@SuppressWarnings("All")
public class PlayerData {

    private static HashMap<UUID, PlayerData> cachedPlayerData = new HashMap<>();
    private boolean hasPickedUp;
    private boolean hasArmorStand;
    private Material currentPickedUp;
    private ArmorStand followingArmorStand;
    private Player player;

    /**
     * Creates PlayerData and adds it to the cachedPlayerData HashMap
     *
     * @param player          The Player to base the PlayerData on
     * @param currentPickedUp The Material, that the player currently picked up
     * @return The Player Data Object
     */
    public PlayerData(Player player, Material currentPickedUp) {
        hasPickedUp = false;
        hasArmorStand = false;
        this.player = player;
        this.currentPickedUp = currentPickedUp;
        cachedPlayerData.put(player.getUniqueId(), this);
    }

    /**
     * Creates PlayerData and adds it to the cachedPlayerData HashMap
     * @param uuid The Player UUID to base the PlayerData on
     * @param currentPickedUp The Material, that the player currently picked up
     * @return The Player Data Object
     */
    public PlayerData(UUID uuid, Material currentPickedUp) {
        hasPickedUp = false;
        hasArmorStand = false;
        this.player = Bukkit.getPlayer(uuid);
        this.currentPickedUp = currentPickedUp;
        cachedPlayerData.put(player.getUniqueId(), this);
    }

    /**
     * Checks if the PlayerData exists for a give UUID
     * @param uuid The UUID to the PlayerData to check for
     * @return Returns if the PlayerData Exists
     */
    public static boolean exists(UUID uuid) {
        return cachedPlayerData.containsKey(uuid);
    }

    /**
     * Checks if the PlayerData exists for a given Player
     * @param player The player to the PlayerData to check for
     * @return Returns if the PlayerData Exists
     */
    public static boolean exist(Player player) {
        return cachedPlayerData.containsKey(player.getUniqueId());
    }

    /**
     * Gets the PlayerData from a give UUID
     * @param uuid The UUID to the PlayerData to get
     * @return If the PlayerData exists, will return it
     */
    public static PlayerData getPlayerData(UUID uuid) {
        return cachedPlayerData.get(uuid);
    }

    /**
     * Gets the PlayerData from a given Player
     * @param player The UUID to the PlayerData to get
     * @return If the PlayerData exists, will return it
     */
    public static PlayerData getPlayerData(Player player) {
        return cachedPlayerData.get(player.getUniqueId());
    }

    /**
     * Gets the cachedPlayerData HashMap
     * @return Returns the raw list of cached PlayerData
     */
    public static HashMap<UUID, PlayerData> getSavedPlayerData() {
        return cachedPlayerData;
    }


    /**
     * Sets if the Player has Picked up any Block
     * @param hasPickedUp The new Value of hasPickedUP
     */
    public void setHasPickedUp(boolean hasPickedUp) {
        this.hasPickedUp = hasPickedUp;
    }

    /**
     * Sets if an Armorstand is following the Player
     * @param hasArmorStand The new Value of hasArmorStand
     */
    public void setHasFollowingArmorStand(boolean hasArmorStand) {
        this.hasArmorStand = hasArmorStand;
    }

    /**
     * Gets if an Armorstand is following the Player
     * @return If the player has a following Armorstand
     */
    public boolean hasFollowingArmorStand() {
        return hasArmorStand;
    }

    /**
     * Gets if the Armorstand that is following the Player
     * @return Returns the armorstand that is following the player of this PlayerData
     */
    public ArmorStand getFollowingArmorStand() {
        return followingArmorStand;
    }

    /**
     * Sets the Armorstand that is following the player
     * @param armorStand Sets the given armorstand to follow the Player
     */
    public void setFollowingArmorStand(ArmorStand armorStand) {
        hasArmorStand = true;
        followingArmorStand = armorStand;
    }

    /**
     * Gets the Material of the Block the Player currently has Picked up
     * @return Returns the material that the Player has picked up
     */
    public Material getPickedUp() {
        return currentPickedUp;
    }

    /**
     * Gets the Player from the PlayerData
     * @return Returns the Player from the PlayerData
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Removes the Armorstand and removes the Slowness from the player
     *
     * @return The Player Data Object
     */
    public PlayerData removeArmorStand() {
        followingArmorStand.remove();
        player.removePotionEffect(PotionEffectType.SLOW);
        hasArmorStand = false;
        currentPickedUp = Material.AIR;
        return this;
    }

    /**
     * Creates an Armorstand to follow the Player, binds it to Player PlayerData
     * @return The Player Data Object
     */
    public PlayerData createFollowingArmorStand() {
        followingArmorStand = CarryUtils.summonArmorStand(player.getLocation(), currentPickedUp);
        hasArmorStand = true;
        return this;
    }

    /**
     * Handles the placement of a block that was picked up
     *
     * @return The Player Data Object
     */
    public PlayerData handlePlacement(Block block) {
        Block block1 = block.getLocation().add(0, 1, 0).getBlock();
        if (block1.getType() == Material.AIR) {
            block1.setType(getPickedUp());
            getPlayer().sendMessage(ChatColor.RED + "Set block one above");
            return this;
        }
        Block block2 = block.getLocation().add(1, 0, 0).getBlock();
        if (block2.getType() == Material.AIR) {
            block2.setType(getPickedUp());
            getPlayer().sendMessage(ChatColor.RED + "Set block x 1");
            return this;
        }
        Block block3 = block.getLocation().add(0, 0, 1).getBlock();
        if (block3.getType() == Material.AIR) {
            block3.setType(getPickedUp());
            getPlayer().sendMessage(ChatColor.RED + "Set block z 1");
            return this;
        }
        getPlayer().sendMessage(ChatColor.RED + "Couldn't Place the " + getPickedUp().name() + " you were holding!");
        return this;
    }

    /**
     * Handles the pickup of a block, binds it to player PlayerData
     *
     * @return The Player Data Object
     */
    public PlayerData handlePickup(Block block) {
        if (hasFollowingArmorStand()) {
            removeBlock(block);
            return this;
        }
        createFollowingArmorStand();
        removeBlock(block);
        return this;
    }
}

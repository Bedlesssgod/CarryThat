package dev.bedless.carrythat.util.data;

import dev.bedless.carrythat.config.Carry;
import dev.bedless.carrythat.util.player.CarryUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

import static dev.bedless.carrythat.util.player.CarryUtils.removeBlock;

@SuppressWarnings("All")
public class PlayerData {

    private static HashMap<UUID, PlayerData> cachedPlayerData = new HashMap<>();
    private boolean hasPickedUp;
    private boolean hasArmorStand;
    private Block currentPickedUp;
    private ArmorStand followingArmorStand;
    private Player player;

    /**
     * Creates PlayerData and adds it to the cachedPlayerData HashMap
     *
     * @param player          The Player to base the PlayerData on
     * @param currentPickedUp The Block, that the player currently picked up
     * @return The Player Data Object
     */
    public PlayerData(Player player, Block currentPickedUp) {
        hasPickedUp = false;
        hasArmorStand = false;
        this.player = player;
        this.currentPickedUp = canBePickedUp(currentPickedUp) ? currentPickedUp : null;
        cachedPlayerData.put(player.getUniqueId(), this);
    }

    /**
     * Creates PlayerData and adds it to the cachedPlayerData HashMap
     *
     * @param uuid            The Player UUID to base the PlayerData on
     * @param currentPickedUp The Block, that the player currently picked up
     * @return The Player Data Object
     */
    public PlayerData(UUID uuid, Block currentPickedUp) {
        hasPickedUp = false;
        hasArmorStand = false;
        this.player = Bukkit.getPlayer(uuid);
        this.currentPickedUp = currentPickedUp;
        cachedPlayerData.put(player.getUniqueId(), this);
    }

    /**
     * Checks if the PlayerData exists for a given UUID
     *
     * @param uuid The UUID to the PlayerData to check for
     * @return Returns if the PlayerData Exists
     */
    public static boolean exists(UUID uuid) {
        return cachedPlayerData.containsKey(uuid);
    }

    /**
     * Checks if the PlayerData exists for a given Player
     *
     * @param player The player to the PlayerData to check for
     * @return Returns if the PlayerData Exists
     */
    public static boolean exist(Player player) {
        return cachedPlayerData.containsKey(player.getUniqueId());
    }

    /**
     * Gets the PlayerData from a given UUID
     *
     * @param uuid The UUID to the PlayerData to get
     * @return If the PlayerData exists, will return it
     */
    public static PlayerData getPlayerData(UUID uuid) {
        return cachedPlayerData.get(uuid);
    }

    /**
     * Gets the PlayerData from a given Player
     *
     * @param player The UUID to the PlayerData to get
     * @return If the PlayerData exists, will return it
     */
    public static PlayerData getPlayerData(Player player) {
        return cachedPlayerData.get(player.getUniqueId());
    }

    /**
     * Gets the cachedPlayerData HashMap
     *
     * @return Returns the raw list of cached PlayerData
     */
    public static HashMap<UUID, PlayerData> getSavedPlayerData() {
        return cachedPlayerData;
    }


    /**
     * Sets if the Player has Picked up any Block
     *
     * @param hasPickedUp The new Value of hasPickedUP
     */
    public void setHasPickedUp(boolean hasPickedUp) {
        this.hasPickedUp = hasPickedUp;
    }

    /**
     * Sets if an Armorstand is following the Player
     *
     * @param hasArmorStand The new Value of hasArmorStand
     */
    public void setHasFollowingArmorStand(boolean hasArmorStand) {
        this.hasArmorStand = hasArmorStand;
    }

    /**
     * Gets if an Armorstand is following the Player
     *
     * @return If the player has a following Armorstand
     */
    public boolean hasFollowingArmorStand() {
        return hasArmorStand;
    }

    /**
     * Gets if the Armorstand that is following the Player
     *
     * @return Returns the armorstand that is following the player of this PlayerData
     */
    public ArmorStand getFollowingArmorStand() {
        return followingArmorStand;
    }

    /**
     * Sets the Armorstand that is following the player
     *
     * @param armorStand Sets the given armorstand to follow the Player
     */
    public void setFollowingArmorStand(ArmorStand armorStand) {
        hasArmorStand = true;
        followingArmorStand = armorStand;
    }

    /**
     * Gets the Block the Player currently has Picked up
     *
     * @return Returns the Block that the Player has picked up
     */
    public Block getPickedUp() {
        return currentPickedUp;
    }

    /**
     * Gets the Material of the Block currently Pickedup
     *
     * @return Returns the Material of the Block the Player picked up
     */
    public Material getPickedUpType() {
        return currentPickedUp.getType();
    }

    /**
     * Gets the Player from the PlayerData
     *
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
        currentPickedUp = null;
        return this;
    }

    /**
     * Creates an Armorstand to follow the Player, binds it to Player PlayerData
     *
     * @return The Player Data Object
     */
    public PlayerData createFollowingArmorStand() {
        followingArmorStand = CarryUtils.summonArmorStand(player.getLocation(), currentPickedUp.getType());
        hasArmorStand = true;
        return this;
    }

    /**
     * Handles the placement of the block that was picked up
     *
     * @return The Player Data Object
     */
    public PlayerData handlePlacement(PlayerInteractEvent event) {
        try {
            Block subBlock;
            switch (event.getBlockFace()) {
                case UP -> {
                    event.getPlayer().sendMessage(ChatColor.RED + "Clicked Surface: UP + y: +1");
                    subBlock = event.getClickedBlock().getLocation().add(0, +1, 0).getBlock();
                }
                case DOWN -> {
                    event.getPlayer().sendMessage(ChatColor.RED + "Clicked Surface: DOWN + y: -1");
                    subBlock = event.getClickedBlock().getLocation().add(0, -1, 0).getBlock();
                }
                case EAST -> {
                    event.getPlayer().sendMessage(ChatColor.RED + "Clicked Surface: EAST + x: +1");
                    subBlock = event.getClickedBlock().getLocation().add(1, 0, 0).getBlock();
                }
                case WEST -> {
                    event.getPlayer().sendMessage(ChatColor.RED + "Clicked Surface: WEST + x: -1");
                    subBlock = event.getClickedBlock().getLocation().add(-1, 0, 0).getBlock();
                }
                case NORTH -> {
                    event.getPlayer().sendMessage(ChatColor.RED + "Clicked Surface: NORTH + z: -1");
                    subBlock = event.getClickedBlock().getLocation().add(0, 0, -1).getBlock();
                }
                case SOUTH -> {
                    event.getPlayer().sendMessage(ChatColor.RED + "Clicked Surface: SOUTH + x: +1");
                    subBlock = event.getClickedBlock().getLocation().add(0, 0, +1).getBlock();
                }
                default -> {
                    subBlock = null;
                    getPlayer().sendMessage(ChatColor.RED + "Couldn't Place the " + getPickedUpType().name() + " you were holding!");
                    return this;
                }
            }
            if (subBlock.getType() != Material.AIR) {
                getPlayer().sendMessage(ChatColor.RED + "Couldn't Place the " + getPickedUpType().name() + " you were holding!");
                return this;
            }
            getPlayer().sendMessage("PlayerData: 251 - " + getPickedUpType().toString());
            subBlock.setType(getPickedUpType());
            BlockData data = subBlock.getBlockData();
            getPlayer().sendMessage(data.getAsString());
            //Directional directionalData = (Directional) data;
            //directionalData.setFacing(player.getFacing().getOppositeFace());
            //subBlock.setBlockData(data);
        } catch (Exception ex) {
            ex.printStackTrace();
            getPlayer().playSound(player.getLocation(), Sound.ENTITY_GOAT_SCREAMING_HURT, 0.5f, 0.5f);
        }
        return this;
    }

    /**
     * Handles the pickup of a block, binds it to player PlayerData
     * @param block The Block to use
     * @return The Player Data Object
     */
    public PlayerData handlePickup(Block block) {
        if (hasPickedUp) return this;
        if (canBePickedUp(block)) return this;
        currentPickedUp = block;
        createFollowingArmorStand();
        removeBlock(block);
        getPlayer().sendMessage("PlayerData: 276 - " + block.getType().toString());
        return this;
    }

    /**
     * Handles the pickup of a block, binds it to player PlayerData
     *
     * @return The Player Data Object
     */
    public PlayerData handlePickup() {
        if (hasPickedUp) return this;
        if (canBePickedUp(getPickedUp())) return this;
        currentPickedUp = getPickedUp();
        createFollowingArmorStand();
        removeBlock(getPickedUp());
        getPlayer().sendMessage("PlayerData: 276 - " + getPickedUp().getType().toString());
        return this;
    }

    /**
     * Gets if the given Block can be picked up
     *
     * @param block Block to check if is Allowed
     * @return Returns if the Block from the event can be picked up as a boolean
     */
    public boolean canBePickedUp(Block block) {
        getPlayer().sendMessage("CarryUtils:27 -> " + block.getType().name());
        Carry.POOP.isEnabled();
        return Carry.getAllowedMaterials().contains(block.getType());
    }
}

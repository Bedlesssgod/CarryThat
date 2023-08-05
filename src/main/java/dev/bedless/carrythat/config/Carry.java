package dev.bedless.carrythat.config;

import org.bukkit.Material;

import java.util.ArrayList;

@SuppressWarnings("all")
public enum Carry {

    CHEST(CarryGroup.TILE_ENTITY, "Chest", Material.CHEST, true),
    DISPENSER(CarryGroup.TILE_ENTITY, "Dispenser", Material.DISPENSER, false),
    SMOKER(CarryGroup.TILE_ENTITY, "Smoker", Material.SMOKER, true),
    HOPPER(CarryGroup.TILE_ENTITY, "Hopper", Material.HOPPER, false),
    BARREL(CarryGroup.TILE_ENTITY, "Barrel", Material.BARREL, true),
    BLAST_FURNACE(CarryGroup.TILE_ENTITY, "Blast Furnace", Material.BLAST_FURNACE, true),
    SHULKER_BOX(CarryGroup.TILE_ENTITY, "Shulker Box", Material.SHULKER_BOX, false),
    FURNACE(CarryGroup.TILE_ENTITY, "Furnace", Material.FURNACE, true),
    BREWING_STAND(CarryGroup.TILE_ENTITY, "Brewing Stand", Material.BREWING_STAND, false),
    DROPPER(CarryGroup.TILE_ENTITY, "Dropper", Material.DROPPER, false),

    VILLAGER(CarryGroup.ENTITY, "Villager", Material.VILLAGER_SPAWN_EGG, false);

    private final String displayName;
    private final CarryGroup group;
    private final Material material;
    private final boolean fallbackIsEnabled;
    private boolean configIsEnabled;


    /**
     * Creates an Constant with the give values
     *
     * @param group       The Group the Constant gets sorted into
     * @param displayName The Displayname, used in the Configuration
     * @param material    The Material, used to check if an attempted pick is valid to pickup
     * @param isEnabled   If the item can be picked up
     */
    Carry(CarryGroup group, String displayName, Material material, boolean isEnabled) {
        this.group = group;
        this.displayName = displayName;
        this.material = material;
        this.fallbackIsEnabled = isEnabled;
    }

    /**
     * Gets an ArrayList with the Constants that belong to the given group
     * @param carryGroup The group of items to look for
     * @return Returns a List of the constants that are in the searcged group
     */
    public static ArrayList<Carry> getGroup(CarryGroup carryGroup) {
        ArrayList<Carry> tileEntity = new ArrayList<>();
        ArrayList<Carry> entity = new ArrayList<>();
        for (Carry carry : Carry.values()) {
            switch (carry.getRawGroup()) {
                case TILE_ENTITY -> tileEntity.add(carry);
                case ENTITY -> entity.add(carry);
            }
        }
        switch (carryGroup) {
            case TILE_ENTITY -> {
                return tileEntity;
            }
            case ENTITY -> {
                return entity;
            }
            default -> {
                return tileEntity;
            }
        }
    }

    /**
     * Gets an ArrayList with the Material that are activated, retrieves the data from the Constants
     * @return Returns a List of the materials that are activated, gotten from the constants
     */
    public static ArrayList<Material> getAllowedMaterials() {
        ArrayList<Material> materials = new ArrayList<>();
        for (Carry carry : Carry.values()) {
            if (!carry.isEnabled()) continue;
            materials.add(carry.getMaterial());
        }
        return materials;
    }

    /**
     * Sets if the Constant is enabled or disabled with given value
     * @param isEnabled Sets the new value of isEnabled
     * @return Returns this
     */
    public Carry setIsEnabled(boolean isEnabled) {
        this.configIsEnabled = isEnabled;
        return this;
    }

    /**
     * Gets if the Constant is enabled
     *
     * @return Returns a boolean, that tells if the constant is enabled
     */
    public boolean isEnabled() {
        return configIsEnabled;
    }

    /**
     * Gets the is enabled value that was hardcoded into the Constant
     *
     * @return Returns a boolean, that was set on the creation of the Constant
     */
    public boolean getFallBackValue() {
        return fallbackIsEnabled;
    }

    /**
     * Gets the Nice name of the Constant, with spaces and capitalization
     * @return Returns the Name of the Constant, with spaces and capitalization
     */
    public String getFancyName() {
        String cap = "capatalization";
        return displayName;
    }

    /**
     * Gets the group of the Constant
     * @return Returns the group of this Constant
     */
    public CarryGroup getRawGroup() {
        return group;
    }

    /**
     * Gets the Material of the Constant
     *
     * @return Returns the Material of the Constant
     */
    public Material getMaterial(){
        return material;
    }

}

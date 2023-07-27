package dev.bedless.carrythat.config;

import lombok.Getter;
import org.bukkit.Material;

import java.util.ArrayList;

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

    VILLAGER(CarryGroup.ENTITY, "Villager", Material.VILLAGER_SPAWN_EGG, true);

    private final String displayName;
    private final CarryGroup group;
    @Getter
    private final Material material;
    private boolean isEnabled;

    Carry(CarryGroup group, String displayName, Material material, boolean isEnabled) {
        this.group = group;
        this.displayName = displayName;
        this.material = material;
        this.isEnabled = isEnabled;
    }

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

    public static ArrayList<Material> getAllowedMaterials() {
        ArrayList<Material> materials = new ArrayList<>();
        for (Carry carry : Carry.values()) {
            if (!carry.getIsEnableConfig()) continue;
            materials.add(carry.getMaterial());
        }
        return materials;
    }

    public Carry setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    public boolean getIsEnableConfig() {
        return isEnabled;
    }

    public String getFancyName() {
        return displayName;
    }

    public CarryGroup getRawGroup() {
        return group;
    }

}

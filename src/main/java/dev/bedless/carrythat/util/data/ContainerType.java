package dev.bedless.carrythat.util.data;

import lombok.Getter;

@Getter
public enum ContainerType {

    BARREL("Barrel"),
    BLAST_FURNACE("Blast Furnace"),
    BREWING_STAND("Brewing Stand"),
    CHEST("Chest"),
    DISPENSER("Dispenser"),
    DROPPER("Dropper"),
    FURNACE("Furnace"),
    HOPPER("Hopper"),
    SHULKER_BOX("Shulker Box"),
    SMOKER("Smoker");

    private final String name;

    ContainerType(String name) {
        this.name = name;
    }

}

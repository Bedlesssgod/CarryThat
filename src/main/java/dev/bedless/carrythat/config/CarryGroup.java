package dev.bedless.carrythat.config;

import lombok.Getter;

@Getter
public enum CarryGroup {

    TILE_ENTITY("TileEntity"),
    ENTITY("Entity");

    private final String configName;

    CarryGroup(String configName) {
        this.configName = configName;
    }

}

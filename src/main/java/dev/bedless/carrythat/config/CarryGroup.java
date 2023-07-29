package dev.bedless.carrythat.config;

public enum CarryGroup {

    TILE_ENTITY("TileEntity"),
    ENTITY("Entity");

    private final String configName;

    /**
     * Creates a constant with the given data
     *
     * @param configName The Nice name to use in the Configuration
     */
    CarryGroup(String configName) {
        this.configName = configName;
    }

    /**
     * Gets the Configuration name of the Constant
     *
     * @return Returns the Config name of the Constant
     */
    public String getConfigName() {
        return configName;
    }

}

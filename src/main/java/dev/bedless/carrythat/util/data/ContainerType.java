package dev.bedless.carrythat.util.data;

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

    /**
     * Creates a Constant with the given Data
     *
     * @param name The name to return when using getName()
     */
    ContainerType(String name) {
        this.name = name;
    }

    /**
     * Gets the Name of the Constant
     *
     * @return Returns the name set by The Constant
     */
    public String getName() {
        return name;
    }

}

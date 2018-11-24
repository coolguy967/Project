package ca.uoit.project.enumeration;

public enum Atmosphere {

    ANY             (0, "Any"),
    FAST_FOOD       (1, "Fast Food"),
    FAST_CASUAL     (2, "Fast Casual"),
    CASUAL          (3, "Casual"),
    PREMIUM_CASUAL  (4, "Premium Casual"),
    FINE_DINING     (5, "Fine Dining");

    // Database value for this enum value
    public final int value;
    // UI Description for this enum value
    public final String description;

    private Atmosphere(int value, String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * Gets the enum value associated with the given integer
     * @param value The integer value to find
     * @return The enum value
     */
    public static Atmosphere get(int value) {
        for(Atmosphere atmosphere : values()) {
            if(value == atmosphere.value) {
                return atmosphere;
            }
        }

        return ANY;
    }

    /**
     * Get the enum value for the given description string
     * @param string The description of the enum value
     * @return The enum value
     */
    public static Atmosphere fromString(String string) {
        for(Atmosphere atmosphere : values()) {
            if(string.equals(atmosphere.description)) {
                return atmosphere;
            }
        }

        return ANY;
    }

}

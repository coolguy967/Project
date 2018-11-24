package ca.uoit.project.enumeration;

public enum ServingMethod {

    ANY     (0, "Any"),
    TAKE_OUT(1, "Take-Out"),
    DINE_IN (2, "Dine-In");

    // Database value for this enum value
    public final int value;
    // UI Description for this enum value
    public final String description;

    private ServingMethod(int value, String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * Gets the enum value associated with the given integer
     * @param value The integer value to find
     * @return The enum value
     */
    public static ServingMethod get(int value) {
        for(ServingMethod servingMethod : values()) {
            if(value == servingMethod.value) {
                return servingMethod;
            }
        }

        return ANY;
    }

    /**
     * Get the enum value for the given description string
     * @param string The description of the enum value
     * @return The enum value
     */
    public static ServingMethod fromString(String string) {
        for(ServingMethod servingMethod : values()) {
            if(string.equals(servingMethod.description)) {
                return servingMethod;
            }
        }

        return ANY;
    }
}

package ca.uoit.project.enumeration;

public enum Atmosphere {

    ANY             (0, "Any"),
    FAST_FOOD       (1, "Fast Food"),
    FAST_CASUAL     (2, "Fast Casual"),
    CASUAL          (3, "Casual"),
    PREMIUM_CASUAL  (4, "Premium Casual"),
    FINE_DINING     (5, "Fine Dining");

    public final int value;
    public final String description;

    private Atmosphere(int value, String description) {
        this.value = value;
        this.description = description;
    }
}

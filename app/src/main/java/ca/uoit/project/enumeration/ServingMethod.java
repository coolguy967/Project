package ca.uoit.project.enumeration;

public enum ServingMethod {

    ANY     (0, "Any"),
    TAKE_OUT(1, "Take Out"),
    DINE_IN (2, "Dine In");

    public final int value;
    public final String description;

    private ServingMethod(int value, String description) {
        this.value = value;
        this.description = description;
    }
}

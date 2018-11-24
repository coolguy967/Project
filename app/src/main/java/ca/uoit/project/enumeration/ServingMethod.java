package ca.uoit.project.enumeration;

public enum ServingMethod {

    TAKE_OUT(0),
    DINE_IN (1);

    public final int value;

    private ServingMethod(int value) {
        this.value = value;
    }
}

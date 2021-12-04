package lapr.project.model;

public class TypeContainer {

    private final String type;
    private String temperature;

    public TypeContainer(String type, String temperature) {
        this.type = type;
        this.temperature = temperature;
    }

    public TypeContainer(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return " Type = " + type + '\'';
    }
}

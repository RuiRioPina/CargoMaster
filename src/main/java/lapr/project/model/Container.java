package lapr.project.model;

public class Container {

    private final String numberContainer;
    private final Dimension dimension;
    private final Client client;
    private final TypeContainer type;
    private final String iso;
    private final String certificate;
    private final String load;


    private final Position position;
    private final Port nextPort;
    private final String date;

    public Container(String numberContainer, Dimension idDimension, Client idClient, TypeContainer type, String iso, String certificate, String load) {
        this.numberContainer = numberContainer;
        this.dimension = idDimension;
        this.client = idClient;
        this.type = type;
        this.iso = iso;
        this.certificate = certificate;
        this.load = load;
        this.position = null;
        this.nextPort = null;
        this.date = null;
    }

    public Container(String numberContainer, TypeContainer type, String load, Position position, Port nextPort, String date) {
        this.numberContainer = numberContainer;
        this.dimension = null;
        this.client = null;
        this.type = type;
        this.iso = null;
        this.certificate = null;
        this.load = load;
        this.position = position;
        this.nextPort = nextPort;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Container - " +
                "nrContainer = " + numberContainer + ","
                + type +
                ", Load = " + load + ", Position "
                + position + ", "
                + nextPort +
                ", Date = " + date
                ;
    }
}

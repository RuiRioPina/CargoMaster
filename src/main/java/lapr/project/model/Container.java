package lapr.project.model;

public class Container {

    private final String numberContainer;
    private String typeOfVehicle;
    private Dimension dimension;
    private Client client;
    private final TypeContainer type;
    private String iso;
    private String certificate;
    private final String load;

    private String typeManifest;
    private final Position position;
    private final Port nextPort;
    private String date;
    private String arrivalDate;
    private String departureDate;

    public Container(String numberContainer, Client idClient, TypeContainer type, String iso, String certificate, String load) {
        this.numberContainer = numberContainer;
        this.dimension = new Dimension(iso);
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

    public Container(String numberContainer, TypeContainer type, String load, Position position, Port nextPort, String arrivalDate, String departureDate) {
        this.numberContainer = numberContainer;
        this.type = type;
        this.load = load;
        this.position = position;
        this.nextPort = nextPort;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
    }

    public Container(String numberContainer, TypeContainer type, String load, String typeManifest, Position position, String typeOfVehicle, Port nextPort, String arrivalDate, String departureDate) {
        this.numberContainer = numberContainer;
        this.type = type;
        this.load = load;
        this.typeManifest = typeManifest;
        this.position = position;
        this.typeOfVehicle = typeOfVehicle;
        this.nextPort = nextPort;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
    }
    public Container(String numberContainer, TypeContainer type, String load, Position position, Port nextPort, String arrivalDate, String departureDate,double length,double width,double height) {
        this.numberContainer = numberContainer;
        this.type = type;
        this.load = load;
        this.position = position;
        this.nextPort = nextPort;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.dimension=new Dimension(length,width,height);
    }

    public String getIso() {
        return iso;
    }



    @Override
    public String toString() {
        return "Container{" +
                "numberContainer='" + numberContainer + '\'' +
                ", typeOfVehicle='" + typeOfVehicle + '\'' +
                ", dimension=" + dimension +
                ", client=" + client +
                ", type=" + type +
                ", iso='" + iso + '\'' +
                ", certificate='" + certificate + '\'' +
                ", load='" + load + '\'' +
                ", typeManifest='" + typeManifest + '\'' +
                ", position=" + position +
                ", nextPort=" + nextPort +
                ", date='" + date + '\'' +
                ", arrivalDate='" + arrivalDate + '\'' +
                ", departureDate='" + departureDate + '\'' +
                '}';
    }
    public String centerOfMass(){
        return String.format("(%.2f;%.2f;%.2f)",this.dimension.getLength()/2,this.dimension.getWidth()/2,this.dimension.getHeight()/2);
    }
}

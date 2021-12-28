package lapr.project.model;



public class Port implements GraphLocation{
    private Continent continent;
    private String country;
    private int code;
    private String namePort;
    private Location location;
    private double averageCloseness;

    private Port() {
        // not expected to use the empty Construct of the port class
    }

    public Port(String continent, String country, int code, String namePort, Location location) {
        this.continent = new Continent(continent);
        this.country = country;
        this.code = code;
        this.namePort = namePort;
        this.location = location;
    }
    //Used to know the name of next Port to load/offload containers.
    public Port(String namePort) {
        this.namePort = namePort;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getNamePort() {
        return namePort;
    }

    public String getName() {
        return namePort;
    }

    @Override
    public Double getCloseness() {
        return averageCloseness;
    }

    public void setNamePort(String namePort) {
        this.namePort = namePort;
    }

    public Location getLocation() {
        return location;
    }

    public String getLatitude() {
        return location.getLatitude();
    }

    public String getLongitude() {
        return location.getLongitude();
    }


    @Override
    public String toString() {
        return "Port{" +
                "continent=" + continent +
                ", country='" + country + '\'' +
                ", code=" + code +
                ", namePort='" + namePort + '\'' +
                ", location=" + location +
                ", averageCloseness=" + averageCloseness +
                '}';
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setAverageCloseness(double v) {
        averageCloseness = v;
    }

    public double getAverageCloseness() {
        return averageCloseness;
    }
}

package lapr.project.model;



public class Port implements GraphLocation{
    private String continent;
    private String country;
    private int code;
    private String namePort;
    private Location location;

    private Port() {
        // not expected to use the empty Construct of the port class
    }

    public Port(String continent, String country, int code, String namePort, Location location) {
        this.continent = continent;
        this.country = country;
        this.code = code;
        this.namePort = namePort;
        this.location = location;
    }
    //Used to know the name of next Port to load/offload containers.
    public Port(String namePort) {
        this.namePort = namePort;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
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
        return "Port = " + namePort;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}

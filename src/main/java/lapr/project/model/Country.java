package lapr.project.model;

public class Country implements  GraphLocation{
    private final Continent continent;
    private final Location capitalLocation;
    private final String name;
    private final String alpha2code;
    private final String alpha3code;
    private final double population;
    private final String capital;
    private double averageCloseness;


    public Country(Continent continent, Location capitalLocation, String name, String alpha2code, String alpha3code,
                   double population, String capital) {
        this.continent = continent;
        this.capitalLocation = capitalLocation;
        this.name = name;
        this.alpha2code = alpha2code;
        this.alpha3code = alpha3code;
        this.population = population;
        this.capital = capital;
    }


    @Override
    public String toString() {
        return "Country{" +
                "continent=" + continent +
                ", capitalLocation=" + capitalLocation +
                ", name='" + name + '\'' +
                ", alpha2code='" + alpha2code + '\'' +
                ", alpha3code='" + alpha3code + '\'' +
                ", population=" + population +
                ", capital='" + capital + '\'' +
                ", averageCloseness=" + averageCloseness +
                '}';
    }

    public Location getLocation(){
        return this.capitalLocation;
    }
    public String getName(){
        return this.name;
    }

    @Override
    public Double getCloseness() {
        return averageCloseness;
    }

    public Continent getContinent(){
        return this.continent;
    }

    public void setAverageCloseness(double averageCloseness) {
        this.averageCloseness = averageCloseness;
    }
}

package lapr.project.model;

public class Country {
    private Continent continent;
    private Location capitalLocation;
    private String name;
    private String alpha2code;
    private String alpha3code;
    private double population;
    private String capital;


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
        return "Country{" + continent + '\'' +
                ", capitalLocation=" + capitalLocation +
                ", name='" + name + '\'' +
                ", alpha2code='" + alpha2code + '\'' +
                ", alpha3code='" + alpha3code + '\'' +
                ", population=" + population +
                ", capital='" + capital + '\'' + "\n" +
                '}';
    }
}

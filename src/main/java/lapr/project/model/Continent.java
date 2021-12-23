package lapr.project.model;

public class Continent {

    private final String continent;

    public Continent(String continent) {
        this.continent = continent;
    }

    @Override
    public String toString() {
        return "Continent{" +
                "continent=" + continent +
                "}";
    }
    public String getName(){
        return this.continent;
    }

}


package lapr.project.controller;

import lapr.project.model.GraphLocation;
import lapr.project.utils.graph.Graph;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ClosenessTest {
    Closeness closeness = new Closeness();

    @Test
    void calculateClosenss() {
        List<String> continentsOftheWorld = new ArrayList<>();
        continentsOftheWorld.add("Europe");
        continentsOftheWorld.add("America");
        continentsOftheWorld.add("Asia");
        continentsOftheWorld.add("Africa");
        continentsOftheWorld.add("Oceania");
        List<GraphLocation> closenessByContinent;

        for (String continent : continentsOftheWorld) {
            System.out.printf("\n %s \n", continent);
            closenessByContinent = closeness.calculateCloseness(10, continent);
            if (closenessByContinent.size() > 0) {
                for (GraphLocation country : closenessByContinent) {
                    System.out.println(country);
                }
            } else {
                System.out.printf("There are no locations located in %s. Please try adding some locations and try again.", continent);
                System.out.println();
            }
        }

        //Closeness.calculateClosenss(10, continent);
    }//É este teste que está a fazer com que isto dê mal
}
package lapr.project.controller;

import lapr.project.model.CountryPortGraph;
import lapr.project.model.GraphLocation;
import lapr.project.utils.graph.Algorithms;
import lapr.project.utils.graph.matrix.MatrixGraph;

import java.util.*;

public class Closeness {

    MatrixGraph<GraphLocation, Double> matrixGraph;
    CountryPortGraph portGraph;
    LinkedList<GraphLocation> vLinkedList;

    public Closeness() {
        portGraph = new CountryPortGraph();
        matrixGraph = portGraph.createGraphWithPortsAndCountries(2);

    }

    List<GraphLocation> graphLocations;

    public List<GraphLocation> calculateCloseness(int numberOfGraphs, String continent) {
        vLinkedList = Algorithms.breadthFirstSearch(matrixGraph, matrixGraph.vertex(3));
        assert vLinkedList != null;
        vLinkedList.sort(Comparator.comparingDouble(GraphLocation::getCloseness));
        graphLocations = getAllCountriesFromContinent(continent, vLinkedList);
        if (graphLocations.size() >= numberOfGraphs) {
            return getAllCountriesFromContinent(continent, vLinkedList).subList(0, numberOfGraphs);
        } else {
            return Collections.emptyList();
        }
    }

    private static List<GraphLocation> getAllCountriesFromContinent(String continent, LinkedList<GraphLocation> vLinkedList) {
        List<GraphLocation> countriesInContinent = new ArrayList<>();
        for (GraphLocation graph : vLinkedList) {
            if (graph.getContinent().getName().equals(continent)) {
                countriesInContinent.add(graph);
            }
        }
        return countriesInContinent;
    }
}

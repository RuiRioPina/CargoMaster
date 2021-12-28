package lapr.project.utils.graph;

import lapr.project.model.Country;
import lapr.project.model.GraphLocation;
import lapr.project.model.Port;
import lapr.project.utils.Calculator;
import lapr.project.utils.graph.matrix.MatrixGraph;

import java.util.*;
import java.util.function.BinaryOperator;

/**
 * @author DEI-ISEP
 */
public class Algorithms {
    private Algorithms() {

    }

    /**
     * Performs breadth-first search of a Graph starting in a vertex
     *
     * @param g    Graph instance
     * @param vert vertex that will be the source of the search
     * @return a LinkedList with the vertices of breadth-first search
     */
    public static <V, E> LinkedList<V> breadthFirstSearch(Graph<V, E> g, V vert) {
        if (!g.validVertex(vert)) {
            return null;
        }

        LinkedList<V> qbfs = new LinkedList<>();
        LinkedList<V> qaux = new LinkedList<>();
        qbfs.add(vert);
        qaux.add(vert);
        while (!qaux.isEmpty()) {
            vert = qaux.getFirst();
            qaux.removeFirst();

            for (V vAdj : g.adjVertices(vert)) {
                if (!qbfs.contains(vAdj)) {
                    qbfs.add(vAdj);
                    qaux.add(vAdj);
                }
            }
        }


        calculateCloseness(qbfs);
        return qbfs;
    }

    private static <V> void calculateCloseness(LinkedList<V> vectors) {
        for (V country1 : vectors) {
            GraphLocation country1Casted;
            double cont = 0;
            double sumDistance = 0;

            if(country1 instanceof Country) {
                country1Casted = (Country) country1;
            }else{
                country1Casted = (Port) country1;
            }
            for (V country2 : vectors) {
                GraphLocation country2Casted;

                if(country2 instanceof Country) {
                    country2Casted = (Country) country2;
                }else{
                    country2Casted = (Port) country2;
                }
                if ((!(country1Casted).getName().equals((country2Casted).getName())) && (country1Casted).getContinent().getName().equals((country2Casted).getContinent().getName())) {
                    sumDistance += Calculator.calculateLocationDifference((country1Casted).getLocation(), (country2Casted).getLocation());
                    cont++;
                }
                
            }
            if (cont != 0) {
                country1Casted.setAverageCloseness(sumDistance / cont);
            }
        }
    }


}
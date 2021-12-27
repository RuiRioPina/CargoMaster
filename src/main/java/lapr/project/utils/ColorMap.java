package lapr.project.utils;

import lapr.project.model.Country;
import lapr.project.model.GraphLocation;
import lapr.project.utils.graph.Edge;
import lapr.project.utils.graph.Graph;

import java.util.*;

public class ColorMap {
    public static Map<GraphLocation, Integer> colorMap(Graph<GraphLocation, Double> G) {

        GraphLocation v = G.vertices().get(0);
        for (GraphLocation vertex : G.vertices()) {
            Collection<GraphLocation> adjV = G.adjVertices(vertex);
            adjV.removeIf(vert -> !vert.getClass().equals(Country.class));
            Collection<GraphLocation> vSize = G.adjVertices(v);
            vSize.removeIf(vert -> !vert.getClass().equals(Country.class));
            if (adjV.size() > vSize.size()) {
                v = vertex;
            } }

        Map<GraphLocation, Integer> coloredMap = new HashMap<>();
        Queue<GraphLocation> queue = new LinkedList<>();
        queue.add(v);

        for (GraphLocation country : G.vertices()) {    // Atribuição de -1 como cor a todos os países (default)
            coloredMap.put(country, -1); }

        int n = 100;                                    // Número máximo de cores
        int colorsCardinality = 0;

        while (!queue.isEmpty()) {
            GraphLocation i = queue.poll();
            Collection<Edge<GraphLocation, Double>> edges = G.incomingEdges(i);
            Collection<GraphLocation> adjV = G.adjVertices(i);

            for (GraphLocation e : adjV) {
                int colorEnd = coloredMap.get(e);
                if (colorEnd == -1) {
                    queue.add(e);
                } }

            for (int j = 0; j < n; j++) {
                coloredMap.put(i, j);
                if (colorsCardinality < j) {
                    colorsCardinality = j;
                }
                if (isSafe(edges, coloredMap)) {
                    break;
                } } }

        Map<GraphLocation, Integer> countriesColored = new HashMap<>();
        for (GraphLocation g : coloredMap.keySet()) {
            if (g.getClass().equals(Country.class) && coloredMap.get(g) != -1) {
                countriesColored.put(g, coloredMap.get(g));
            } else if (g.getClass().equals(Country.class)) {
                countriesColored.put(g, 0);
            } }

        return countriesColored;
    }

    public static boolean isSafe(Collection<Edge<GraphLocation, Double>> edges, Map<GraphLocation, Integer> coloredMap) {
        for (Edge<GraphLocation, Double> e : edges) {
            GraphLocation startVertex = e.getVOrig();
            GraphLocation endVertex = e.getVDest();
            int colorStart = coloredMap.get(startVertex);
            int colorEnd = coloredMap.get(endVertex);
            if (colorStart == colorEnd)
                return false;
        }
        return true;
    }
}

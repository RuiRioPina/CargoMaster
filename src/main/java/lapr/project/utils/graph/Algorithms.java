package lapr.project.utils.graph;

import lapr.project.model.Country;
import lapr.project.model.GraphLocation;
import lapr.project.model.Port;
import lapr.project.utils.Calculator;

import java.util.*;

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


        assignCloseness(qbfs);
        return qbfs;
    }

    private static <V> void assignCloseness(LinkedList<V> vectors) {
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
                if ((!(country1Casted).getName().equals((country2Casted).getName())) &&
                        (country1Casted).getContinent().getName().equals((country2Casted).getContinent().getName())) {
                    sumDistance += Calculator.calculateLocationDifference((country1Casted).getLocation(), (country2Casted).getLocation());
                    cont++;
                }
                
            }
            if (cont != 0) {
                country1Casted.setAverageCloseness(sumDistance / cont);
            }
        }
    }
    /**
     * Shortest-path between a vertex and all the other vertices
     *
     * @param g     graph
     * @param vOrig start vertex
     * @param paths returns all the minimum paths
     * @param dists returns the corresponding minimum distances
     * @return if vOrig exists in the graph true, false otherwise
     */
    public static <V, E> boolean shortestPaths(Graph<V, E> g, V vOrig, ArrayList<LinkedList<V>> paths, ArrayList<Double> dists) {
        if (!g.validVertex(vOrig)) return false;

        int nverts = g.numVertices();
        boolean[] visited = new boolean[nverts];
        @SuppressWarnings("unchecked")
        V[] pathKeys = (V[]) new Object[nverts];
        Double[] dist = new Double[nverts];

        for (int i = 0; i < nverts; i++) {
            dist[i] = Double.MAX_VALUE;
            pathKeys[i] = null;
        }

        shortestPathLength(g, vOrig, visited, pathKeys, dist);
        dists.clear();
        paths.clear();

        for (int i = 0; i < nverts; i++) {
            paths.add(null);
            dists.add(Double.MAX_VALUE);
        }

        for (V vDst : g.vertices()) {
            int i = g.key(vDst);
            if (dist[i] != Double.MAX_VALUE) {
                LinkedList<V> shortPath = new LinkedList<>();
                getPath(g, vOrig, vDst, pathKeys, shortPath);
                paths.set(i, shortPath);
                dists.set(i, dist[i]);
            }
        }

        return true;
    }

    protected static <V, E> void shortestPathLength(Graph<V, E> g, V vOrig, boolean[] visited,
                                                    V[] pathKeys, Double[] dist) {
        int vKey = g.key(vOrig);
        dist[vKey] = 0.0;
        pathKeys[vKey] = vOrig;
        while (vOrig != null) {
            vKey = g.key(vOrig);
            visited[vKey] = true;
            for (V vAdj : g.adjVertices(vOrig)) {
                int vKeyAdj = g.key(vAdj);
                Edge<V, E> edge = g.edge(vOrig, vAdj);
                if (!visited[vKeyAdj] && dist[vKeyAdj] > dist[vKey] + (Double)edge.getWeight()) {
                    dist[vKeyAdj] = dist[vKey] + (Double)edge.getWeight();
                    pathKeys[vKeyAdj] = vOrig;
                }
            }
            double minDist = Double.MAX_VALUE;
            vOrig = null;
            for (V vert : g.vertices()) {
                int i = g.key(vert);
                if (!visited[i] && dist[i] < minDist) {
                    minDist = dist[i];
                    vOrig = vert;
                }
            }
        }
    }
    /**
     * Extracts from pathKeys the minimum path between voInf and vdInf
     * The path is constructed from the end to the beginning
     *
     * @param g        Graph instance
     * @param vOrig    information of the Vertex origin
     * @param vDest    information of the Vertex destination
     * @param pathKeys minimum path vertices keys
     * @param path     stack with the minimum path (correct order)
     */
    private static <V, E> void getPath(Graph<V, E> g, V vOrig, V vDest,
                                       V[] pathKeys, LinkedList<V> path) {

        if (vOrig.equals(vDest)) {
            path.push(vOrig);
        } else {
            path.push(vDest);
            int vKey = g.key(vDest);
            vDest = pathKeys[vKey];
            getPath(g, vOrig, vDest, pathKeys, path);
        }
        //throw new UnsupportedOperationException("Not supported yet.");
    }

}
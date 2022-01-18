package lapr.project.model;

import lapr.project.controller.App;
import lapr.project.data.CountryStore;
import lapr.project.data.DatabaseConnection;
import lapr.project.utils.Calculator;

import lapr.project.utils.Pair;
import lapr.project.utils.graph.Edge;
import lapr.project.utils.graph.Graph;
import lapr.project.utils.graph.matrix.MatrixGraph;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.BinaryOperator;


public class CountryPortGraph {
    private  PortStore portStore=null;
    private  CountryStore countryStore=null;

    public CountryPortGraph() {

    }

    public MatrixGraph<GraphLocation, Double> createGraphWithPortsAndCountries(int n) {
        portStore = App.getInstance().getCompany().getPortStore();
        countryStore = App.getInstance().getCompany().getCountryStore();
        MatrixGraph<GraphLocation, Double> graph = new MatrixGraph<>(false);
        countryStore.getCountriesFromDatabase();
        for (Country country : countryStore.getCountryStore()) {
            graph.addVertex(country);
        }
        portStore.getPortsFromDatabase();
        for (Port port : portStore.getPortList()) {
            graph.addVertex(port);
        }
        makeBorderEdges(graph);
        for (Country country : countryStore.getCountryStore()) {
            double temp = Double.MAX_VALUE;
            Port portfacade = new Port("ContinentFacade", "CountryFacade", 99999, "PortFacade", new Location("-86.6222", "-128.48"));
            Location capitalLocation = country.getLocation();
            for (Port port : portStore.getPortList()) {
                if (port.getCountry().equals(country.getName())) {
                    if (Calculator.calculateLocationDifference(capitalLocation, port.getLocation()) < temp) {
                        temp = Calculator.calculateLocationDifference(capitalLocation, port.getLocation());
                        portfacade = port;
                    }
                }
            }
            if (portfacade.getCode() != 99999) {
                graph.addEdge(country, portfacade, temp);
            }
        }
        makeSameCountryPortDistance(graph);
        makeClosestPortOutsideOfCountryDistance(graph, n);
        return graph;

    }

    private void makeBorderEdges(MatrixGraph<GraphLocation, Double> graph) {
        DatabaseConnection databaseConnection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
        ResultSet rSet;
        try (CallableStatement callStmtAux = databaseConnection.getConnection().prepareCall("{ ? = call fnc_getAllBorders()}")) {
            callStmtAux.registerOutParameter(1, OracleTypes.CURSOR);
            callStmtAux.execute();
            rSet = (ResultSet) callStmtAux.getObject(1);
            while (rSet.next()) {
                Country country1 = countryStore.getCountryByName(rSet.getString(1));
                Country country2 = countryStore.getCountryByName(rSet.getString(2));
                graph.addEdge(country1, country2, Calculator.calculateLocationDifference(country1.getLocation(), country2.getLocation()));
            }
        } catch (SQLException ignored) {
            ignored.printStackTrace();
        }
    }

    private void makeSameCountryPortDistance(MatrixGraph<GraphLocation, Double> graph) {
        DatabaseConnection databaseConnection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
        ResultSet rSet;
        try (CallableStatement callStmtAux = databaseConnection.getConnection().prepareCall("{ ? = call fnc_getAllPortDistance()}")) {
            callStmtAux.registerOutParameter(1, OracleTypes.CURSOR);
            callStmtAux.execute();
            rSet = (ResultSet) callStmtAux.getObject(1);
            while (rSet.next()) {
                Port port1 = portStore.getPortByCode(Integer.parseInt(rSet.getString(1)));
                Port port2 = portStore.getPortByCode(Integer.parseInt(rSet.getString(2)));
                if (port1.getCountry().equals(port2.getCountry())) {
                    graph.addEdge(port1, port2, Double.parseDouble(rSet.getString(3)));
                }
            }

        } catch (SQLException ignored) {
            ignored.printStackTrace();
        }
    }

    private void makeClosestPortOutsideOfCountryDistance(MatrixGraph<GraphLocation, Double> graph, int n) {
        for (Port port : portStore.getPortList()) {
            List<Pair<Port, Double>> pairList = new ArrayList<>();
            int cont = 0;
            DatabaseConnection databaseConnection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
            ResultSet rSet;
            try (CallableStatement callStmtAux = databaseConnection.getConnection().prepareCall("{ ? = call fnc_getAllPortDistanceSorted()}")) {
                callStmtAux.registerOutParameter(1, OracleTypes.CURSOR);
                callStmtAux.execute();
                rSet = (ResultSet) callStmtAux.getObject(1);
                while (rSet.next()) {
                    if (rSet.getInt(1) == port.getCode() && cont < n) {
                        Pair<Port, Double> pair = new Pair<>(portStore.getPortByCode(rSet.getInt(2)), rSet.getDouble(3));
                        pairList.add(pair);
                        cont++;
                    }

                }
                for (Pair<Port, Double> pair : pairList) {
                    graph.addEdge(port, pair.getFirst(), pair.getSecond());
                }
            } catch (SQLException ignored) {
                ignored.printStackTrace();
            }
        }
    }

    /**
     * Computes shortest-path distance from a source vertex to all reachable
     * vertices of a graph g with non-negative edge weights
     * This implementation uses Dijkstra's algorithm
     */
    public List<String> shortestPath(Graph<GraphLocation, Double> g, GraphLocation vOrig, GraphLocation vDest) {
        int keyCopy = g.key(vDest);
        int size = g.numVertices();
        boolean[] visited = new boolean[size];
        int[] path = new int[size];
        double[] dist = new double[size];
        for (int i = 0; i < size; i++) {
            dist[i] = Double.MAX_VALUE;
            path[i] = -1;
            visited[i] = false;
        }
        dist[g.key(vOrig)] = 0;
        while (g.key(vOrig) != 0) {
            visited[g.key(vOrig)] = true;
            for (GraphLocation vAdj : g.adjVertices(vOrig)) {
                Edge<GraphLocation, Double> edge = g.edge(vOrig, vAdj);
                if (!visited[g.key(vAdj)] && dist[g.key(vAdj)] > dist[g.key(vOrig)] + edge.getWeight()) {
                    dist[g.key(vAdj)] = dist[g.key(vOrig)] + edge.getWeight();
                    path[g.key(vAdj)] = g.key(vOrig);
                }
            }
            vOrig = getVertMinDist(g,dist, visited);
        }
        List<Integer> shortestPathList= new ArrayList<>();
        shortestPathList.add(keyCopy);
        int keyToBeAdded=g.key(vDest);
        while (keyToBeAdded!=-1){
            keyToBeAdded=path[g.key(vDest)];
            shortestPathList.add(keyToBeAdded);
            vDest=g.vertex(keyToBeAdded);
        }
        shortestPathList.remove(shortestPathList.size()-1);
        Collections.reverse(shortestPathList);
        List<String > GraphLocationPathList= new ArrayList<>();
        for (int i=0;i<shortestPathList.size();i++){
            GraphLocationPathList.add(g.vertex(shortestPathList.get(i)).getName());
        }

        return GraphLocationPathList;
    }
   /* public List<String> shortestPathMaritime(Graph<GraphLocation, Double> g, GraphLocation vOrig, GraphLocation vDest) {
        int keyCopy = g.key(vDest);
        int size = g.numVertices();
        boolean[] visited = new boolean[size];
        int[] path = new int[size];
        double[] dist = new double[size];
        for (int i = 0; i < size; i++) {
            dist[i] = Double.MAX_VALUE;
            path[i] = -1;
            visited[i] = false;
        }
        dist[g.key(vOrig)] = 0;
        while (g.key(vOrig) != 0) {
            visited[g.key(vOrig)] = true;
            for (GraphLocation vAdj : g.adjVertices(vOrig)) {
                if (vAdj instanceof Port || vAdj.equals(vDest)) {
                    Edge<GraphLocation, Double> edge = g.edge(vOrig, vAdj);
                    if (!visited[g.key(vAdj)] && dist[g.key(vAdj)] > dist[g.key(vOrig)] + edge.getWeight()) {
                        dist[g.key(vAdj)] = dist[g.key(vOrig)] + edge.getWeight();
                        path[g.key(vAdj)] = g.key(vOrig);
                    }
                }
            }
            vOrig = getVertMinDist(g,dist, visited);
        }
        List<Integer> shortestPathList= new ArrayList<>();
        shortestPathList.add(keyCopy);
        int keyToBeAdded=g.key(vDest);
        while (keyToBeAdded!=-1){
            keyToBeAdded=path[g.key(vDest)];
            shortestPathList.add(keyToBeAdded);
            vDest=g.vertex(keyToBeAdded);
        }
        shortestPathList.remove(shortestPathList.size()-1);
        Collections.reverse(shortestPathList);
        List<String > GraphLocationPathList= new ArrayList<>();
        for (int i=0;i<shortestPathList.size();i++){
            GraphLocationPathList.add(g.vertex(shortestPathList.get(i)).getName());
        }

        return GraphLocationPathList;
    }
    *
    */
    public List<String> shortestPathLand(Graph<GraphLocation, Double> g, GraphLocation vOrig, GraphLocation vDest) {
        int keyCopy = g.key(vDest);
        int size = g.numVertices();
        boolean[] visited = new boolean[size];
        int[] path = new int[size];
        double[] dist = new double[size];
        for (int i = 0; i < size; i++) {
            dist[i] = Double.MAX_VALUE;
            path[i] = -1;
            visited[i] = false;
        }
        dist[g.key(vOrig)] = 0;
        while (g.key(vOrig) != 0) {
            visited[g.key(vOrig)] = true;
            for (GraphLocation vAdj : g.adjVertices(vOrig)) {
                if (vAdj instanceof Country || vAdj.equals(vDest)) {
                    Edge<GraphLocation, Double> edge = g.edge(vOrig, vAdj);
                    if (!visited[g.key(vAdj)] && dist[g.key(vAdj)] > dist[g.key(vOrig)] + edge.getWeight()) {
                        dist[g.key(vAdj)] = dist[g.key(vOrig)] + edge.getWeight();
                        path[g.key(vAdj)] = g.key(vOrig);
                    }
                }
            }
            vOrig = getVertMinDist(g,dist, visited);
        }
        List<Integer> shortestPathList= new ArrayList<>();
        shortestPathList.add(keyCopy);
        int keyToBeAdded=g.key(vDest);
        while (keyToBeAdded!=-1){
            keyToBeAdded=path[g.key(vDest)];
            shortestPathList.add(keyToBeAdded);
            vDest=g.vertex(keyToBeAdded);
        }
        shortestPathList.remove(shortestPathList.size()-1);
        Collections.reverse(shortestPathList);
        List<String > GraphLocationPathList= new ArrayList<>();
        for (int i=0;i<shortestPathList.size();i++){
            GraphLocationPathList.add(g.vertex(shortestPathList.get(i)).getName());
        }

        return GraphLocationPathList;
    }

    private GraphLocation getVertMinDist(Graph<GraphLocation,Double> graph,double[] dist, boolean[] visited) {
        int copy=0;
        int tempMinKey=-1;
        Double minDist= Double.MAX_VALUE;
        GraphLocation location =null;
        for (int i=0;i<visited.length;i++){
            if (!visited[i]&& dist[i]!=Double.MAX_VALUE){
            if (dist[i]<minDist){
                copy=i;
                minDist=dist[i];
            }

            }
        }
        return graph.vertices().get(copy);
    }


    /** Shortest-path between two vertices
     *
     * @param g graph
     * @param vOrig origin vertex
     * @param vDest destination vertex
     * @param ce comparator between elements of type E
     * @param sum sum two elements of type E
     * @param zero neutral element of the sum in elements of type E
     * @param shortPath returns the vertices which make the shortest path
     * @return if vertices exist in the graph and are connected, true, false otherwise
     */
    public static <V, E> E shortestPath(Graph<V, E> g, V vOrig, V vDest,
                                        Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                        LinkedList<V> shortPath) {

        throw new UnsupportedOperationException("Not supported yet.");
    }
    /*
    public static void main(String[] args) {
        CountryPortGraph countryPortGraph= new CountryPortGraph();
        MatrixGraph<GraphLocation,Double> graph = countryPortGraph.createGraphWithPortsAndCountries(3);
        System.out.println("xd");
        System.out.println("lao");
    }
*/

}

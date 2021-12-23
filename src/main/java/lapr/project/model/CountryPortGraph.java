package lapr.project.model;

import lapr.project.controller.App;
import lapr.project.data.CountryStore;
import lapr.project.data.DatabaseConnection;
import lapr.project.utils.Calculator;

import lapr.project.utils.Pair;
import lapr.project.utils.graph.matrix.MatrixGraph;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CountryPortGraph {
    private final PortStore portStore;
    private final CountryStore countryStore;
    public CountryPortGraph(){
        portStore= App.getInstance().getCompany().getPortStore();
        countryStore= App.getInstance().getCompany().getCountryStore();
    }
    public MatrixGraph<GraphLocation,Double> createGraphWithPortsAndCountries(int n){
        MatrixGraph<GraphLocation,Double> graph= new MatrixGraph<>(false);
        countryStore.getCountriesFromDatabase();
        for (Country country: countryStore.getCountryStore()){
            graph.addVertex(country);
        }
        portStore.getPortsFromDatabase();
        for (Port port: portStore.getPortList()){
            graph.addVertex(port);
        }
        makeBorderEdges(graph);
        for (Country country:countryStore.getCountryStore()){
            double temp =Double.MAX_VALUE;
            Port portfacade= new Port("ContinentFacade","CountryFacade",99999,"PortFacade",new Location("-86.6222","-128.48"));
            Location capitalLocation = country.getLocation();
            for (Port port:portStore.getPortList()){
                if (port.getCountry().equals(country.getName())){
                    if (Calculator.calculateLocationDifference(capitalLocation,port.getLocation())<temp){
                        temp=Calculator.calculateLocationDifference(capitalLocation,port.getLocation());
                        portfacade=port;
                    }
                }
            }
            if (!portfacade.getContinent().equals("ContinentFacade")){
                graph.addEdge(country,portfacade,temp);
            }
        }
        makeSameCountryPortDistance(graph);
        makeClosestPortOutsideOfCountryDistance(graph,n);
        return graph;

    }
    private void makeBorderEdges(MatrixGraph<GraphLocation,Double> graph){
        DatabaseConnection databaseConnection= new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
        ResultSet rSet;
        try(CallableStatement callStmtAux = databaseConnection.getConnection().prepareCall("{ ? = call fnc_getAllBorders()}")){
            callStmtAux.registerOutParameter(1, OracleTypes.CURSOR);
            callStmtAux.execute();
            rSet = (ResultSet) callStmtAux.getObject(1);
            while(rSet.next()){
                Country country1= countryStore.getCountryByName(rSet.getString(1));
                Country country2= countryStore.getCountryByName(rSet.getString(2));
                graph.addEdge(country1,country2, Calculator.calculateLocationDifference(country1.getLocation(),country2.getLocation()));
            }
        }catch(SQLException ignored) {
            ignored.printStackTrace();
        }
    }
    private void makeSameCountryPortDistance(MatrixGraph<GraphLocation,Double> graph){
        DatabaseConnection databaseConnection= new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
        ResultSet rSet;
        try(CallableStatement callStmtAux = databaseConnection.getConnection().prepareCall("{ ? = call fnc_getAllPortDistance()}")){
            callStmtAux.registerOutParameter(1, OracleTypes.CURSOR);
            callStmtAux.execute();
            rSet = (ResultSet) callStmtAux.getObject(1);
            while(rSet.next()){
                Port port1= portStore.getPortByCode(Integer.parseInt(rSet.getString(1)));
                Port port2= portStore.getPortByCode(Integer.parseInt(rSet.getString(2)));
                if (port1.getCountry().equals(port2.getCountry())) {
                    graph.addEdge(port1, port2, Double.parseDouble(rSet.getString(3)));
                }
                }

        }catch(SQLException ignored) {
            ignored.printStackTrace();
        }
    }
    private void makeClosestPortOutsideOfCountryDistance(MatrixGraph<GraphLocation,Double> graph,int n){
        for (Port port: portStore.getPortList()){
            List< Pair<Port,Double>> pairList=new ArrayList<>();
            int cont =0;
            DatabaseConnection databaseConnection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
            ResultSet rSet;
            try (CallableStatement callStmtAux = databaseConnection.getConnection().prepareCall("{ ? = call fnc_getAllPortDistanceSorted()}")) {
                callStmtAux.registerOutParameter(1, OracleTypes.CURSOR);
                callStmtAux.execute();
                rSet = (ResultSet) callStmtAux.getObject(1);
                while (rSet.next()) {
                if (rSet.getInt(1)==port.getCode()&&cont <n){
                    Pair<Port,Double> pair= new Pair<>(portStore.getPortByCode(rSet.getInt(2)),rSet.getDouble(3) );
                    pairList.add(pair);
                    cont++;
                }

                }
            for (Pair<Port,Double> pair:pairList){
                graph.addEdge(port,pair.getFirst(), pair.getSecond());
            }
            } catch (SQLException ignored) {
                ignored.printStackTrace();
            }
        }
    }


}

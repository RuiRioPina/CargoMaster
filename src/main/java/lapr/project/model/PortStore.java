package lapr.project.model;


import lapr.project.data.DatabaseConnection;
import lapr.project.data.PortStoreDB;
import lapr.project.utils.KDTree;

import java.util.ArrayList;
import java.util.List;

public class PortStore {
    lapr.project.utils.KDTree<Port> store = new KDTree<>();
    List<Port> portList= new ArrayList<>();

    public void insert() {
        store.insert();
    }

    public int getSize() {
        return store.size();
    }

    public void addToList(Port port, double x, double y) {
        store.addToList(port,x,y);
    }

    public List<Port> getPortList(){
        return portList;
    }

    public boolean codeExists(int code){
        for (Port port : portList){
            if (port.getCode()==code) return true;
        }
        return false;
    }
    public Port getPortByCode(int code){
        for (Port port : portList){
        if (port.getCode()==code) return port;
    }
        return null;
}
    public void  getPortsFromDatabase(){
        PortStoreDB portStoreDB= new PortStoreDB();
        this.portList=portStoreDB.getPorts( new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword"));
    }
}

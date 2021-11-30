package lapr.project.model;


import lapr.project.utils.KDTree;

public class PortStore {
    lapr.project.utils.KDTree<Port> store = new KDTree<>();


    public void insert() {
        store.insert();
    }

    public int getSize() {
        return store.size();
    }

    public void addToList(Port port, double x, double y) {
        store.addToList(port,x,y);
    }
}

package lapr.project.model;


import lapr.project.utils.KDTree;

public class PortStore {
    lapr.project.utils.KDTree<Port> store = new KDTree<>();


    public void insert(Port port, Double x, Double y) {
        store.insert(port, x, y);
    }

    public int getSize() {
        return store.size();
    }
}

package lapr.project.data;

import lapr.project.model.Ship;
import lapr.project.utils.BST;



public class ShipStore {
    private BST<Ship> shipStore = new BST<>();

    public void addShipToBST(Ship ship) {
        shipStore.insert(ship);
    }

    public void printShips() {
        System.out.println(shipStore.inOrder());
    }

    public int size() {
       return shipStore.size();
    }
}

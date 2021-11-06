package lapr.project.model;


import lapr.project.utils.AVL;
import lapr.project.utils.BST;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class ShipStore {
    private AVL<Ship> store = new AVL<>();

    public void addShipToBST(Ship ship) {
        store.insert(ship);
    }

    public int size() {
        return store.size();
    }

    public AVL<Ship> getStore() {
        return store;
    }

    public void organizeShipMessage() {
        for (int i = 0; i < store.height() + 1; i++) {
            Ship temp = store.nodesByLevel().get(i).get(0);
            if (temp.getRoute() != null) {
                int size = temp.getRoute().getRoute().size();
                for (int j = 0; j < size; j++) {
                    store.nodesByLevel().get(i).get(0).getRoute().getRoute().sort(
                            Comparator.comparing(ShipDynamic::getBaseDateTime));
                }
            }
        }
    }

    public Location getPositionOfShipData(String mMSI, String baseDateTime) {
        ShipDynamic shipDynamics = null;
        for (int i = 0; i < store.height() + 1; i++) {
            Ship temp = store.nodesByLevel().get(i).get(0);
            if (temp.getShipId().getMmsi().equals(mMSI)) {
                for (int j = 0; j < temp.getRoute().getRoute().size(); j++)
                    if (temp.getRoute().getRoute().get(j).getBaseDateTime().equals(baseDateTime)) {
                        shipDynamics = temp.getRoute().getRoute().get(j);
                    }
            }
        }
        assert shipDynamics != null;
        return shipDynamics.getLocation();
    }

    public List<Location> getPositionOfShipPeriod(String mMSI, String baseDateTime1, String baseDateTime2) throws ParseException {
        organizeShipMessage();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date d1 = sdf.parse(baseDateTime1);
        Date d2 = sdf.parse(baseDateTime2);

        List<ShipDynamic> shipDynamics = new ArrayList<>();
        for (int i = 0; i < store.height() + 1; i++) {
            Ship temp = store.nodesByLevel().get(i).get(0);
            if (temp.getShipId().getMmsi().equals(mMSI)) {
                for (int j = 0; j < temp.getRoute().getRoute().size(); j++)
                    if (sdf.parse(temp.getRoute().getRoute().get(j).getBaseDateTime()).after(d1) &&
                            sdf.parse(temp.getRoute().getRoute().get(j).getBaseDateTime()).before(d2)) {
                        shipDynamics.add(temp.getRoute().getRoute().get(j));
                    }
            }
        }
        List<Location> position = new ArrayList<>();
        for (ShipDynamic shipDynamic : shipDynamics) {
            position.add(shipDynamic.getLocation());
        }
        return position;
    }

    public AVL<Ship> convertSortingMethod(String sortingMethod) {
        AVL<Ship> shipAVL = new AVL<>();
        Map<Integer, List<Ship>> shipsByLevel = store.nodesByLevel();
        for (Map.Entry<Integer, List<Ship>> entry : shipsByLevel.entrySet()) {
            for (Ship ship : entry.getValue()) {
                ship.getShipId().setSearchCode(sortingMethod);
                shipAVL.insert(ship);
            }

        }
        return shipAVL;
    }

    public Ship findShipDetails(String code) {
        BST.Node<Ship> s;
        Ship ship = null;
        for (Ship value : store.posOrder()) {
            s = store.find(value);
            if (s.getElement().getShipId().getImoID().equals(code) || s.getElement().getShipId().getCallsign().equals(code) || s.getElement().getShipId().getMmsi().equals(code)) {
                ship = s.getElement();
            }
        }

        return ship;
    }

}

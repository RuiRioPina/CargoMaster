package lapr.project.model;


import lapr.project.utils.AVL;
import lapr.project.utils.BST;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class ShipStore{
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
        Map<Integer, List<Ship>> shipsByLevel = store.nodesByLevel();
        for (Map.Entry<Integer, List<Ship>> entry : shipsByLevel.entrySet()) {
            for (Ship ship : entry.getValue()) {
                ship.getRoute().getRoute().sort(Comparator.comparing(ShipDynamic::getBaseDateTime));
            }
       }
    }

    public Location getPositionOfShipData(String mMSI, String baseDateTime) {
        Location location = null;

            for (Ship ship : store.inOrder()) {
                for (int i = 0; i < ship.getRoute().getRoute().size(); i++)
                    if (ship.getRoute().getRoute().get(i).getBaseDateTime().equals(baseDateTime) && ship.getShipId().getMmsi().equals(mMSI)) {
                        location = ship.getRoute().getRoute().get(i).getLocation();
                    }
            }

        assert location != null;
        return location;
    }

    public List<Location> getPositionOfShipPeriod(String mMSI, String baseDateTime1, String baseDateTime2) throws ParseException {
       organizeShipMessage();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date d1 = sdf.parse(baseDateTime1);
        Date d2 = sdf.parse(baseDateTime2);

        List<Location> position = new ArrayList<>();

        for (Ship ship : store.inOrder()) {
                for (int j = 0; j < ship.getRoute().getRoute().size(); j++)
                    if (sdf.parse(ship.getRoute().getRoute().get(j).getBaseDateTime()).after(d1) &&
                            sdf.parse(ship.getRoute().getRoute().get(j).getBaseDateTime()).before(d2) &&ship.getShipId().getMmsi().equals(mMSI)) {
                        position.add(ship.getRoute().getRoute().get(j).getLocation());
                    }
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

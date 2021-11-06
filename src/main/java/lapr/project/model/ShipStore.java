package lapr.project.model;

import lapr.project.model.Location;
import lapr.project.model.Ship;
import lapr.project.model.ShipDynamic;
import lapr.project.utils.BST;

import javax.swing.text.Position;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class ShipStore {
    private BST<Ship> shipStore = new BST<>();

    public void addShipToBST(Ship ship) {
        shipStore.insert(ship);
    }

    public void printShips() {
        System.out.println(shipStore.toString());
    }

    public int size() {
       return shipStore.size();
    }

    public BST<Ship> getShipStore() {
        return shipStore;
    }

    public void organizeShipMessage() {
        for (int i = 0; i < shipStore.height() + 1; i++) {
            Ship temp = shipStore.nodesByLevel().get(i).get(0);
            if (temp.getRoute() != null) {
            int size = temp.getRoute().getRoute().size();
                for (int j = 0; j < size; j++) {
                    shipStore.nodesByLevel().get(i).get(0).getRoute().getRoute().sort(
                            Comparator.comparing(ShipDynamic::getBaseDateTime));
                }
            }
        }
    }

    public Location getPositionOfShipData(String MMSI, String BaseDateTime){
        ShipDynamic shipDynamics = null;
        for (int i = 0; i < shipStore.height() + 1; i++) {
            Ship temp = shipStore.nodesByLevel().get(i).get(0);
            if (temp.getShipId().getMmsi().equals(MMSI)) {
                for (int j = 0; j < temp.getRoute().getRoute().size(); j++)
                    if (temp.getRoute().getRoute().get(j).getBaseDateTime().equals(BaseDateTime)){
                        shipDynamics = temp.getRoute().getRoute().get(j);
                    }
            }
        }
        assert shipDynamics != null;
        return shipDynamics.getLocation();
    }

    public List<Location> getPositionOfShipPeriod(String MMSI, String BaseDateTime1,String BaseDateTime2) throws ParseException {
        organizeShipMessage();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date d1 = sdf.parse(BaseDateTime1);
        Date d2 = sdf.parse(BaseDateTime2);

        List <ShipDynamic> shipDynamics= new ArrayList<>();
        for (int i = 0; i < shipStore.height() + 1; i++) {
            Ship temp = shipStore.nodesByLevel().get(i).get(0);
            if (temp.getShipId().getMmsi().equals(MMSI)) {
                for (int j = 0; j < temp.getRoute().getRoute().size(); j++)
                    if (sdf.parse(temp.getRoute().getRoute().get(j).getBaseDateTime()).after(d1) &&
                            sdf.parse(temp.getRoute().getRoute().get(j).getBaseDateTime()).before(d2)){
                        shipDynamics.add(temp.getRoute().getRoute().get(j));
                    }
            }
        }
        List <Location> position = new ArrayList<>();
        for (ShipDynamic shipDynamic : shipDynamics) {
            position.add(shipDynamic.getLocation());
        }
        return position;
    }

}

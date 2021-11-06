package lapr.project.controller;

import lapr.project.model.ShipStore;
import lapr.project.model.*;

import java.text.ParseException;
import java.util.List;

public class ShipController {

    //private Company company (); create company
    private Identification shipId;
    private ShipCharacteristics characteristics;
    private Route route;

    private ShipStore sp;


    public ShipController() {
        sp = new ShipStore();
    }

    public void organizeShipMessage () {
        sp.organizeShipMessage();
    }

    public Location getPositionOfShipData(String MMSI, String BaseDateTime){
        return sp.getPositionOfShipData(MMSI,BaseDateTime);
    }

    public List<Location> getPositionOfShipPeriod(String MMSI, String BaseDateTime1,String BaseDateTime2) throws ParseException {
        return sp.getPositionOfShipPeriod(MMSI,BaseDateTime1,BaseDateTime2);
    }
}

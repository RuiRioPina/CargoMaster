package lapr.project.model;


import lapr.project.utils.AVL;
import lapr.project.utils.BST;
import lapr.project.utils.Pair;
import lapr.project.utils.PrintToFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The ShipStore class which will have an instance of an AVL
 */
public class ShipStore {
    /**
     * The AVL itself
     */
    private AVL<Ship> store = new AVL<>();
    private static final Logger LOGGER = Logger.getLogger("MainLog");

    /**
     * Inserting a ship to the AVL
     * @param ship the ship being inserted
     */
    public void addShipToAVL(Ship ship) {
        store.insert(ship);
    }

    /**
     * Creates a new AVL reorganized by the search code of the ships
     * @param ship the ships to be reorganized
     * @return an AVL reorganized by the type of code pretended
     */
    public AVL<Ship> reorganizeShipAVLAccordingToTheCode(List<Ship> ship) {
        AVL<Ship> newShipAVL = new AVL<>();
        for (Ship ship3 : ship) {
            newShipAVL.insert(ship3);
        }
        return newShipAVL;
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
        final String fileToBeWrittenTo = "shipsOrganized.txt";
        try {
            PrintToFile.print(store.inOrder().toString(), fileToBeWrittenTo);
        } catch (IllegalArgumentException | IOException e) {
            LOGGER.log(Level.INFO, "An unexpected error occured.");
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

        if (location != null) {
            return location;
        } else return new Location("0", "0");
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
                        sdf.parse(ship.getRoute().getRoute().get(j).getBaseDateTime()).before(d2) && ship.getShipId().getMmsi().equals(mMSI)) {
                    position.add(ship.getRoute().getRoute().get(j).getLocation());
                }
        }

        return position;
    }

    /**
     * With a certain code it searches for the ship in the AVL containing that code
     * @param code the code being searched for
     * @return the Ship if it was found or throws an Exception saying it wasn't found
     */

    public Ship findShipDetails(String code) {
        final String fileToBeWrittenTo = "resultSearchDetails.txt";
        BST.Node<Ship> s;
        AVL<Ship> shipAVL;
        Ship ship = null;
        ArrayList<Ship> ship2 = new ArrayList<>();
        for (Ship ships : store.posOrder()) {
            ships.getShipId().setSearchCode(code);
            ship2.add(ships);
        }
        shipAVL = reorganizeShipAVLAccordingToTheCode(ship2);
        for (Ship value : shipAVL.posOrder()) {
            s = shipAVL.find(value);
            if (s.getElement().getShipId().getImoID().equals(code) || s.getElement().getShipId().getCallsign().equals(code)
                    || s.getElement().getShipId().getMmsi().equals(code)) {
                try {
                    PrintToFile.print(s.getElement().toString(), fileToBeWrittenTo);
                    return s.getElement();
                } catch (IOException e) {
                    LOGGER.log(Level.INFO, "The ship was not found");
                }
            }
        }
        throw new IllegalArgumentException();
    }

    public Map<Integer, List<Ship>> getTopNShips(int n, String start, String end) throws ParseException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date d1 = sdf.parse(start);
        Date d2 = sdf.parse(end);

        AVL<Ship> avl = store;
        organizeShipMessage();

        for (Ship ship : avl.inOrder()) {
            for (int j = 0; j < ship.getRoute().getRoute().size(); j++) {
                if (!(sdf.parse(ship.getRoute().getRoute().get(j).getBaseDateTime()).after(d1) &&
                        sdf.parse(ship.getRoute().getRoute().get(j).getBaseDateTime()).before(d2))) {
                    ship.getRoute().getRoute()//remove()
                    ;
                }
            }
        }
        //bst -> list
        List<Ship> ships = new ArrayList<>();
        for (Ship ship : avl.inOrder()) {
            ships.add(ship);
        }

        //ships ordenados por maior distancia percorrida
        ships.sort(Comparator.comparing(Ship::getTravelledDistance));
        Collections.reverse(ships);

        //ships ordenados por vessel type
        ships.sort(Comparator.comparing(Ship::getVesselType));

        //colocar os vessel types e respetivos navios num map
        Map<Integer, List<Ship>> map = new HashMap<>();
        Integer temp = 0;
        ArrayList<Ship> topNShips = new ArrayList<>();
        for (Ship ship : ships) {
            if (temp != ship.getVesselType()) {
                topNShips = new ArrayList<>();
            }
            topNShips.add(ship);
            map.put(ship.getVesselType(), topNShips);
            temp = ship.getVesselType();
        }

        //get os top n navios
        Map<Integer, List<Ship>> map2 = new HashMap<>();
        topNShips = new ArrayList<>();
        List<Ship> sh = new ArrayList<>();
        for (Integer vesselType : map.keySet()) {
            for (int i = 0; i < n && n <= map.get(vesselType).size(); i++) {
                topNShips.add(map.get(vesselType).get(i));
                sh.add(map.get(vesselType).get(i));
                map2.put(vesselType, topNShips);

            }
            topNShips = new ArrayList<>();
        }

        //escrever a informação necessária para um txt
        final String fileToBeWrittenTo = "getTopNShips.txt";

        StringBuilder sout = new StringBuilder("");
        for (int i = 0; i < sh.size(); i++) {
            sout.append("Vessel Type - ").append(sh.get(i).getVesselType()).append(" MMSI - ").append(sh.get(i).getShipId().getMmsi()).append(" Travelled Distance = ").append(sh.get(i).getTravelledDistance()).append(" Mean SOG = ").append(sh.get(i).getMeanSOG());
            sout.append('\n');
        }
        try {
            PrintToFile.printB(sout, fileToBeWrittenTo);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.INFO, "An unexpected error occured.");
        }

        return map2;
    }


    public List<Pair<Ship, Ship>> getCloseShips() {
        List<Pair<Ship, Ship>> pairList = new ArrayList<>();
        for (Ship ship : store.inOrder()) {
            if (ship.getRoute().getTravelledDistance() > 10) {
                for (Ship ship2 : store.inOrder()) {
                    if (ship.isClose(ship2)) {
                        Pair<Ship, Ship> pair1 = new Pair<>(ship, ship2);
                        pairList.add(pair1);
                    }
                }
            }
        }
        return pairList;
    }

}

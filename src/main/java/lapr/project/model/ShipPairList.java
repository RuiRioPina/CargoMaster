package lapr.project.model;


import lapr.project.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class ShipPairList {
   private final List<Pair<Ship, Ship>> shipPairList1;
   private final List<Double> travelledDistanceList1;

    public ShipPairList(ShipStore shipStore) {
        shipPairList1 = sortShipPairs(shipStore.getCloseShips());
travelledDistanceList1=sortShipList();
removeRepeats();
sortByDescendingOrder();
    }

    private List<Pair<Ship, Ship>> sortShipPairs(List<Pair<Ship, Ship>> shipPairList) {
        List<Pair<Ship, Ship>> pairList = new ArrayList<>();
        for (Pair<Ship, Ship> shipPair : shipPairList) {
            if (shipPair.getSecond().getShipId().getMmsi().compareTo(shipPair.getFirst().getShipId().getMmsi()) > 1) {
                Pair<Ship, Ship> shipPair1 = new Pair<>(shipPair.getSecond(), shipPair.getFirst());
                pairList.add(shipPair1);
            } else {
                pairList.add(shipPair);
            }
        }
        return pairList;
    }

//    public void printList(){
//        for (int i=0;i<shipPairList1.size();i++){
//            System.out.println(shipPairList1.get(i).getFirst().getShipId().getMmsi()+ " "+ shipPairList1.get(i).getSecond().getShipId().getMmsi() + " " + travelledDistanceList1.get(i));
//        }
//    }
    private List<Double> sortShipList(){
        int n = shipPairList1.size();
        for (int i=0;i<n;i++){
            for (int j=1;j<(n-i);j++){
                if (Double.parseDouble(shipPairList1.get(j-1).getFirst().getShipId().getMmsi()) > Double.parseDouble(shipPairList1.get(j).getFirst().getShipId().getMmsi())){
                    Pair<Ship, Ship> tempShip = new Pair<>(shipPairList1.get(j-1).getFirst(), shipPairList1.get(j-1).getSecond());
                    shipPairList1.set(j-1,shipPairList1.get(j));
                    shipPairList1.set(j,tempShip);
                }
            }
        }
        List<Double> travelledDistanceList = new ArrayList<>();
        for (Pair<Ship, Ship> shipShipPair : shipPairList1) {
            travelledDistanceList.add(Math.abs(shipShipPair.getFirst().getRoute().getTravelledDistance() - shipShipPair.getSecond().getRoute().getTravelledDistance()));
        }
        return travelledDistanceList;
    }
    private void removeRepeats(){
        int cont =0;
        for (int i=0;i<shipPairList1.size();i++){
            for (int j=0;j<shipPairList1.size();j++){
                if (equalShipPairs(shipPairList1.get(i),shipPairList1.get(j))){
                    cont++;
                }
                if (cont==2){
                    shipPairList1.remove(j);
                    travelledDistanceList1.remove(j);
                    cont=0;
                }
            }
            cont=0;
        }
    }
    private boolean equalShipPairs(Pair<Ship,Ship> pair1,Pair<Ship,Ship> pair2){
        double sum1 =Integer.parseInt(pair1.getFirst().getShipId().getMmsi())+Integer.parseInt(pair1.getSecond().getShipId().getMmsi());
        double sum2 =Integer.parseInt(pair2.getFirst().getShipId().getMmsi())+Integer.parseInt(pair2.getSecond().getShipId().getMmsi());
        return sum2==sum1;
    }
    private void sortByDescendingOrder(){
        int cont =0;

        for (int i=0;i<travelledDistanceList1.size();i++){
            int j=i;
            while (Integer.parseInt(shipPairList1.get(j).getFirst().getShipId().getMmsi())==Integer.parseInt(shipPairList1.get(i).getFirst().getShipId().getMmsi())){
                cont++;
                 j++;
                 if (j==travelledDistanceList1.size()){
                     break;
                 }
            }
            if (cont>1) {
                for (int b = i; b < i + cont; b++) {
                    for (int d = i + 1; d < (i + cont ); d++) {
                        if (travelledDistanceList1.get(d - 1) <= travelledDistanceList1.get(d)) {
                            Pair<Ship, Ship> tempShip = new Pair<>(shipPairList1.get(d - 1).getFirst(), shipPairList1.get(d - 1).getSecond());
                            double tempDouble = travelledDistanceList1.get(d - 1);
                            shipPairList1.set(d - 1, shipPairList1.get(d));
                            travelledDistanceList1.set(d - 1, travelledDistanceList1.get(d));
                            shipPairList1.set(d, tempShip);
                            travelledDistanceList1.set(d, tempDouble);
                        }
                    }
                }
            }
            cont=0;
        }
    }
    public List<Pair<Ship,Ship>> getShipPairList(){
        return this.shipPairList1;
    }
    public List<Double> getTravelledDistanceList(){
        return this.travelledDistanceList1;
    }
}

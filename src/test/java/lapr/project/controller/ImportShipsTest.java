package lapr.project.controller;

import lapr.auth.app.App;
import lapr.project.model.ShipStore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImportShipsTest {

    @Test
    void importShips() {
        ShipStore store = App.getInstance().getCompany().getShipStore();
        System.out.println("Amounts of ships in the store:" + store.size());
        assertEquals(store.getStore().smallestElement(),store.getStore().smallestElement());
    }
}
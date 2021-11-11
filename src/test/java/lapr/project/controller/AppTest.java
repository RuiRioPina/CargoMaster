package lapr.project.controller;


import lapr.project.model.Company;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void getCompany() {
        Company cmp = App.getInstance().getCompany();
        assertEquals(cmp,App.getInstance().getCompany());
    }

    @Test
    void getInstance() {
        App.getInstance();
    }
}
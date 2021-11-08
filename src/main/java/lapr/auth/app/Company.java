package lapr.auth.app;

import lapr.auth.AuthFacade;
import lapr.project.model.ShipStore;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class Company implements Serializable {

    private int numberOfEmployees;
    private final String designation;
    private final AuthFacade authFacade;
    private final ShipStore shipStore;


    public Company(String designation) {
        this.shipStore = new ShipStore();
        if (StringUtils.isBlank(designation))
            throw new IllegalArgumentException("Designation cannot be blank.");

        this.designation = designation;
        this.authFacade = new AuthFacade();
    }

    /**
     * Getter for the number of Employees in the company.
     *
     * @return number of employees.
     */
    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public String getDesignation() {
        return designation;
    }

    public AuthFacade getAuthFacade() {
        return authFacade;
    }



    /**
     * Setter for the number of Employees in the company
     *
     * @param numberOfEmployees- new number of Employees.
     */
    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;

    }

    public ShipStore getShipStore() {
        return shipStore;
    }
}

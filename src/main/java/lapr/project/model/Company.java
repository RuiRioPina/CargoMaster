package lapr.project.model;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class Company implements Serializable {

    private int numberOfEmployees;
    private final String designation;
    private final ShipStore shipStore;
    private final PortStore portStore;
    private final lapr.project.data.CountryStore countryStore;


    public Company(String designation) {
        this.shipStore = new ShipStore();
        this.portStore = new PortStore();
        this.countryStore= new lapr.project.data.CountryStore();
        if (StringUtils.isBlank(designation))
            throw new IllegalArgumentException("Designation cannot be blank.");

        this.designation = designation;

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

    public PortStore getPortStore() {
        return portStore;
    }

    public lapr.project.data.CountryStore getCountryStore(){return countryStore; }
}

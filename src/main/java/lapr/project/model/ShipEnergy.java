package lapr.project.model;

public class ShipEnergy {
    int i = 0;
    int idEnergy;
    int nrGenerators;
    double power;

    public ShipEnergy(int nrGenerators, double power) {
        this.idEnergy = i++;
        this.nrGenerators = nrGenerators;
        this.power = power;
    }


    public int getNrGenerators() {
        return nrGenerators;
    }

    public double getPower() {
        return power;
    }

    public int getIdEnergy() {
        return idEnergy;
    }
}

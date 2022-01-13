package lapr.project.model;

public class ShipIdleDays {

    private final String mmsi;
    private final Integer idleDays;

    public ShipIdleDays(String mmsi, Integer idleDays) {
        this.mmsi = mmsi;
        this.idleDays = idleDays;
    }

    @Override
    public String toString() {
        return "MMSI = " + mmsi  +
                "  Idle Time = " + idleDays +
                " days";
    }
}

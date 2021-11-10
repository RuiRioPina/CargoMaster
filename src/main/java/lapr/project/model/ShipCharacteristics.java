package lapr.project.model;


import lapr.project.utils.ShipValidation;

import java.util.Objects;

public class ShipCharacteristics {
    private int vesselType;
    private Double length;
    private Double width;
    private Double draft;

    public ShipCharacteristics() {
    }

    public ShipCharacteristics(int vesselType, Double length, Double width, Double draft) {
        ShipValidation.validateVesselType(vesselType);
        ShipValidation.validateShipSizes(length);
        ShipValidation.validateShipSizes(width);
        ShipValidation.validateShipSizes(draft);
        this.vesselType = vesselType;
        this.length = length;
        this.width = width;
        this.draft = draft;
    }
    @Override
    public String toString() {
        return String.format("%nVesselType: %s,Length: %s,Width: %s,Draft: %s",vesselType,length,width,draft);

    }

    public int getVesselType() {
        return vesselType;
    }

    public void setVesselType(int vesselType) {
        this.vesselType = vesselType;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public Double getDraft() {
        return draft;
    }

    public void setDraft(double draft) {
        this.draft = draft;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShipCharacteristics that = (ShipCharacteristics) o;
        return vesselType == that.vesselType && Double.compare(that.length, length) == 0 && Double.compare(that.width, width) == 0 && Double.compare(that.draft, draft) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vesselType, length, width, draft);
    }
}

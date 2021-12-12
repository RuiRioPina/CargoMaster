package lapr.project.model;


import lapr.project.utils.ShipValidation;
import lapr.project.utils.Utils;

import java.util.Objects;

/**
 * This class will contain the characteristics related attributes of a ship
 */
public class ShipCharacteristics {
    private int vesselType;
    private Double length;
    private Double width;
    private Double draft;
    private Integer capacity;
    private int xsize = 0;
    private int ysize = 0;
    private int zsize = 0;

    /**
     * The empty public constructor
     */
    public ShipCharacteristics() {
    }

    /**
     * Public constructor with all its attributes being initialized and verified.
     */
    public ShipCharacteristics(int vesselType, Double length, Double width, Double draft) {
        ShipValidation.validateVesselType(vesselType);
        ShipValidation.validateShipSizes(length);
        ShipValidation.validateShipSizes(width);
        ShipValidation.validateShipSizes(draft);
        this.vesselType = vesselType;
        this.length = length;
        this.width = width;
        this.draft = draft;
        this.capacity = calculateShipCapacity(length, width);
    }

    /**
     * Overriding the toString method for the Characteristics
     *
     * @return the String containing the informations of a Characteristics
     */
    @Override
    public String toString() {
        return String.format("%nVesselType: %s,Length: %s,Width: %s,Draft: %s", vesselType, length, width, draft);

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

    public Integer getCapacity() {
        return capacity;
    }


//    public static int calculateShipCapacity(double length, double width) {
//        int xsize = 0;
//        int ysize = 0;
//        int zsize = 0;
//
//        double localLength = length;
//        double localWidth = width;
//        double localHeight = Utils.convertFeetToMeters(64);
//
//
//        Dimension dimension = new Dimension("20G1");
//        double containerLength = dimension.decodeLengthFromIso("20G1");
//        double containerWidth = dimension.decodeHeightAndWidthFromIso("20G1");
//        double containerHeight = dimension.decodeHeightAndWidthFromIso("20G1");
//
//        while (localLength >= 0) {
//            localLength -= containerLength;
//            xsize++;
//        }
//
//        while (localWidth >= 0) {
//            localWidth -= containerWidth;
//            ysize++;
//        }
//
//        while (localHeight >= 0) {
//            localHeight -= containerHeight;
//            zsize++;
//        }
//        return xsize + ysize + zsize;
//    }


    public int calculateShipCapacity(double length, double width) {
        double volumeContainer;
        double volumeShip;
        double localHeight = Utils.convertFeetToMeters(64);

        volumeShip = length * width * localHeight;


        Dimension dimension = new Dimension("20G1");
        double containerLength = dimension.decodeLengthFromIso("20G1");
        double containerWidth = dimension.decodeHeightAndWidthFromIso("20G1");
        double containerHeight = dimension.decodeHeightAndWidthFromIso("20G1");

        volumeContainer = containerHeight * containerLength * containerWidth;
        while (length >= 0) {
            length -= containerLength;
            xsize++;
        }

        while (width >= 0) {
            width -= containerWidth;
            ysize++;
        }

        while (localHeight >= 0) {
            localHeight -= containerHeight;
            zsize++;
        }

        return (int) (volumeShip / volumeContainer);
    }

}

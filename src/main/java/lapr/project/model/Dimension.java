package lapr.project.model;

import lapr.project.utils.Utils;

public class Dimension {

    private double length;
    private double width;
    private double height;


    public Dimension(String containerIso) {
        length = decodeLengthFromIso(containerIso);
        height = decodeHeightAndWidthFromIso(containerIso);
        width = decodeHeightAndWidthFromIso(containerIso);
    }
    public Dimension(double length,double width ,double height){
        this.length=length;
        this.width=width;
        this.height=height;
    }


    public double getHeight() {
        return height;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public double decodeLengthFromIso(String containerIso) {
        switch (containerIso.charAt(0)) {
            case '2':
                return Utils.convertFeetToMeters(20);
            case '4':
                return Utils.convertFeetToMeters(40);
            case 'L':
                return Utils.convertFeetToMeters(45);

            default:
                throw new IllegalArgumentException("The first digit of the ISO6346 does not exist, please check your " +
                        "container's ISO Code an try again");
        }
    }

    public double decodeHeightAndWidthFromIso(String containerIso) {
        switch (containerIso.charAt(1)) {
            case '0':
                return Utils.convertFeetToMeters(8);
            case '2':
                return Utils.convertFeetToMeters(8.6);
            case '5':
                return Utils.convertFeetToMeters(9.6);
            default:
                throw new IllegalArgumentException("The second digit of the ISO6346 does not exist, please check your " +
                        "container's ISO Code an try again");
        }
    }

}

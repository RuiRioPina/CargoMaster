package lapr.project.model;

public class Position {

    private final int x;
    private final int y;
    private final int z;

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return
                "[X = " + x +
                ", Y = " + y +
                ", Z = " + z + "]"
               ;
    }
}

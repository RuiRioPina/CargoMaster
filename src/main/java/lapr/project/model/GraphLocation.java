package lapr.project.model;

public interface GraphLocation {
     Location getLocation();
     String getName();
     Double getCloseness();

     void setAverageCloseness(double v);

     Continent getContinent();
}

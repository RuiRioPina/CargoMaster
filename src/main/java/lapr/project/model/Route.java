package lapr.project.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Route {
    List<ShipDynamic> route = new ArrayList<>();

    public void add(ShipDynamic shipDynamic) {
        route.add(shipDynamic);
    }

    public List<ShipDynamic> getRoute() {
        return route;
    }

    @Override
    public String toString() {
        return "Route{" +
                "route=" + route +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route1 = (Route) o;
        return Objects.equals(route, route1.route);
    }

    @Override
    public int hashCode() {
        return Objects.hash(route);
    }
}

package proggyGradle.server.Trilateration;

import proggyGradle.server.coordinate;

/**
 * Informazioni riguardanti un Access Point
 */
public class APInfo {

    private final String name;
    private final String MAC;
    private final coordinate coordinate;
    private double distanza;

    public APInfo(String name, String MAC, coordinate coordinate,double distanza) {
        this.name = name;
        this.MAC = MAC;
        this.coordinate = coordinate;
        this.distanza=distanza;
    }

    public String getName() {
        return name;
    }

    public String getMAC() {
        return MAC;
    }

    public coordinate getCoordinate() {
        return coordinate;
    }

    public double getDistanza() {
        return distanza;
    }
}

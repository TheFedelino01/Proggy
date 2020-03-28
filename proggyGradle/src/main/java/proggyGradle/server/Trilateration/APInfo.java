package proggyGradle.server.Trilateration;

import proggyGradle.server.coordinate;

/**
 * Informazioni riguardanti un Access Point
 */
public class APInfo {

    private final String name;
    private final String MAC;
    private final coordinate coordinate;
    private double potenza; //RSSI in db

    public APInfo(String name, String MAC, coordinate coordinate, double potenza) {
        this.name = name;
        this.MAC = MAC;
        this.coordinate = coordinate;
        this.potenza = potenza;
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

    /**
     * 10 ^ ((Measured Power – RSSI)/(10 * N))
     *
     * @return distanza tra l'access point e il dispositivo
     */
    public double getDistanza() {

        final double measuredPower = -69; //TODO misurare la potenza effettiva a 1 metro
        final int n = 2;
        return Math.pow(10, (measuredPower - potenza) / (10 * n)) / 1000;
    }

    public double getPotenza() {
        return potenza;
    }

    @Override
    public String toString() {
        return "APInfo{" +
                "name='" + name + '\'' +
                ", MAC='" + MAC + '\'' +
                ", coordinate=" + coordinate +
                ", potenza=" + potenza +
                ", distanza=" + getDistanza() +
                '}';
    }
}

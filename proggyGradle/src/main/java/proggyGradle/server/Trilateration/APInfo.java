package proggyGradle.server.Trilateration;

import proggyGradle.server.coordinate;

/**
 * Informazioni riguardanti un Access Point
 */
public class APInfo {
    private final String name;
    private final String MAC;
    private final coordinate coordinate;
    private int potenza; //RSSI in db
    private final int measuredPower;
    private final int canale;

    public APInfo(String name, String MAC, coordinate coordinate, int potenza, int measuredPower, int canale) {
        this.name = name;
        this.MAC = MAC;
        this.coordinate = coordinate;
        this.potenza = potenza;
        this.measuredPower = measuredPower;
        this.canale = canale;
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


    public double getDistanzaOld() {
        return WiFiUtils.rssiToDistanceOld(measuredPower, potenza);
    }

    /**
     * @return distanza tra l'access point e il dispositivo
     */
    public double getDistanza() {
        return WiFiUtils.rssiToDistance(WiFiUtils.channelToFrequency(canale), potenza);
    }


    /**
     * @return distanza tra l'access point e il dispositivo
     */
    public double getDistanzaFixed() {
        return WiFiUtils.rssiToDistanceFixed(WiFiUtils.channelToFrequency(canale), potenza);
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
                ", distanzaOld=" + getDistanzaOld() +
                ", measuredPower=" + measuredPower +
                ", canale=" + canale +
                '}';
    }
}

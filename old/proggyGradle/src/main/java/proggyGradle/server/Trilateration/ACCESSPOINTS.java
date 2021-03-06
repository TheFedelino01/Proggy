/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggyGradle.server.Trilateration;

import proggyGradle.server.coordinate;

/**
 * @author broch_mattia
 */
public enum ACCESSPOINTS {
    POINT_UNO("JWML", "44d9e7fa933e", new coordinate(45.68779, 9.18132), -69),
    POINT_DUE("JWML", "22722f94e2", new coordinate(45.68771, 9.1811), -69),
    POINT_TRE("JWML", "22722efd21e", new coordinate(45.68752, 9.18111), -69),
    POINT_CAMERA("Saccani ROG MODEM", "fcecda384364", new coordinate(45.70408, 9.19004), -52),
    POINT_SOTTO("Saccani ROG MODEM - 2.4G", "b8d5261593d", new coordinate(45.70408, 9.18996), -73),
    POINT_SOGGIORNO("Saccani ROG MODEM", "18e82951f2cf", new coordinate(45.70399, 9.1899), -62);


    private final String rete;
    private final String mac;
    private final coordinate rssi;
    private int potenza;
    private final int measuredPower;
    private int canale;

    ACCESSPOINTS(String rete, String mac, coordinate rssi, int messuredPower) {
        this.rete = rete;
        this.mac = mac;
        this.rssi = rssi;
        this.measuredPower = messuredPower;
    }

    public void setPotenza(int potenza) {
        this.potenza = potenza;
    }

    public void setCanale(int canale) {
        this.canale = canale;
    }


    public String getMac() {
        return mac;
    }

    public APInfo toAPInfo() {
        return new APInfo(rete, mac, rssi, potenza, measuredPower, canale);
    }
}

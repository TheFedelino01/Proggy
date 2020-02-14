/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggyGradle.server.Trilateration;

import proggyGradle.server.coordinate;

/**
 *
 * @author broch_mattia
 */
public enum ACCESSPOINTS {
    POINT_UNO("JWML", "44d9e7fa933e", new coordinate(45.68779, 9.18132),0),
    POINT_DUE("JWML", "22722f94e2", new coordinate(45.68771, 9.1811),0),
    POINT_TRE("JWML", "22722efd21e", new coordinate(45.68752, 9.18111),0);

    private final String rete;
    private final String mac;
    private final coordinate rssi;
    private double distance;

    ACCESSPOINTS(String rete, String mac, coordinate rssi,double distance) {
        this.rete = rete;
        this.mac = mac;
        this.rssi = rssi;
        this.distance=distance;
    }
    
    public void setDistance(double distance){
        this.distance=distance;
    }
   

    public String getMac() {
        return mac;
    }
    
    public APInfo toAPInfo() {
        return new APInfo(rete, mac, rssi,distance);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggy.server;

/**
 * @author saccani_federico
 */
public class coordinate {

    private double latitudine;
    private double longitudine;

    public coordinate(double latitudine, double longitudine) {
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    @Override
    public String toString() {
        return latitudine + "," + longitudine;
    }

}

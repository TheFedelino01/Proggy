/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author saccani_federico
 */
public class device {
    private String identificatore;
    private coordinates coordinates;
    
    public device(String identificatore){
        coordinates = new coordinates();
        this.identificatore=identificatore;
    }

    public String getIdentificatore() {
        return identificatore;
    }

    public coordinate getLastCoordinate() {
        return coordinates.getLast();
    }
}

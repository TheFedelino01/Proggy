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
public class salvataggi {
    private devices devices;
    
    public salvataggi(){
        devices = new devices();
        devices.addDevice("PERSONA1");
    }
    
    public coordinate getLastCoordinate(String identificatore){
        return devices.getLastCoordinate(identificatore);
    }
}

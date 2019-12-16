/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author saccani_federico
 */
public class devices {
    private List<device> devices;
    
    public devices(){
        devices = new ArrayList<device>();
    }
    
    public void addDevice(String identificatore){
        devices.add(new device(identificatore));
    }
    
    private device getDevice(String identificatore){
        int i=0;
        while(i<devices.size()){
            if(devices.get(i).getIdentificatore().equals(identificatore))
                return devices.get(i);
            i++;
        }
        return null;
    }

    public coordinate getLastCoordinate(String identificatore) {
        return this.getDevice(identificatore).getLastCoordinate();
    }

    public Integer getLastBattito(String identificatore) {
        return this.getDevice(identificatore).getLastBattito();
    }
}

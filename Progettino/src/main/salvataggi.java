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
        
        devices.addDevice("PERSONA1",1010,"192.168.5.5");
        devices.addEmergencyNumber("PERSONA1", "+393341563626", "PARENTI");
    }
    
    public int getPort(String identificatore){
        return devices.getPort(identificatore);
    }
         
    public String getIP(String identificatore) {
        return devices.getIP(identificatore);
    }
    
    public coordinate getLastCoordinate(String identificatore){
        return devices.getLastCoordinate(identificatore);
    }

    public Integer getLastBattito(String identificatore) {
        return devices.getLastBattito(identificatore);
    }

    public device getDevice(String identificatore) {
        return devices.getDevice(identificatore);
    }

    public String getResoconto(String identificatore, String cosaVoglioOttenere) {
        return devices.getDevice(identificatore).getResoconto(cosaVoglioOttenere);
    }

    public String getMedia(String identificatore) {
        return devices.getDevice(identificatore).getMedia();
    }

    public void salvaCoordinate(String identificatore,String coordinate) {
        devices.addCoordinate(identificatore, coordinate);
    }
}

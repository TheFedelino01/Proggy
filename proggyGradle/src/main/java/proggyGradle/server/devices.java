/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggyGradle.server;

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
    
    public void addDevice(String identificatore, int port, String ip,String nomeTutore){
        devices.add(new device(identificatore,port,ip,nomeTutore));
    }
    
    public List getDevicesWithName(String tutore){
        int i=0;
        List<device> l = new ArrayList<device>();
        while(i<devices.size()){
            if(devices.get(i).getTutore().equals(tutore)){
                l.add(devices.get(i));
            }
            i++;
        }
        return l;
    }
    
    public device getDevice(String identificatore){
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

    public int getPort(String identificatore) {
        return this.getDevice(identificatore).getPort();
    }

    public String getIP(String identificatore) {
        return this.getDevice(identificatore).getIP();
    }
    
    public void addEmergencyNumber(String identificatore,String numero,String tipologia){
        this.getDevice(identificatore).addEmergencyNumber(numero, tipologia);
    }
    
    public void addCoordinate(String identificatore, String coordinate) {
        this.getDevice(identificatore).getCoordinates().addCordinate(coordinate);
    }
    
    public void addBattiti(String identificatore, String battiti) {
        this.getDevice(identificatore).addBattiti(battiti);
    }
    
}

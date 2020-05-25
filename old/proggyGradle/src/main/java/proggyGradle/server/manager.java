/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggyGradle.server;

import java.util.List;

/**
 *
 * @author saccani_federico
 */
public class manager {
    private devices devices;
    
    public manager(){
        devices = new devices();

        //TODO QUERY CHE BECCA I DEVICES DAL DATABASE!
        devices.addDevice("2",1010,"192.168.5.5","BROCH");
        devices.addDevice("13",1010,"192.168.5.5","BROCH");
        //TODO PRENDERE I NUMERI DI EMERGENZA DAL DATABASE. ME LI SALVO ANCHE IN RAM PERCHÉ SE IL DB VA OFFLINE, RIESCO LO STESSO A CHIAMARE
        devices.addEmergencyNumber("2", "+393341563626", "PARENTI");
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
    
    public List getDevicesWithName(String tutore){
        return devices.getDevicesWithName(tutore);
    }
public void salvaCoordinate(String identificatore,coordinate coordinate) {
        devices.addCoordinate(identificatore, coordinate);
    }
    public void salvaBattiti(String identificatore,String battiti) {
        devices.addBattiti(identificatore, battiti);
    }
}

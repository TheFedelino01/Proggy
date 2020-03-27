/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggyGradle.server.utility;

import proggyGradle.Socket.cmdRicevuto;
import proggyGradle.Socket.socketUDP;
import proggyGradle.server.Trilateration.ACCESSPOINTS;
import proggyGradle.server.Trilateration.APInfo;
import proggyGradle.server.Trilateration.Trilateration;
import proggyGradle.server.manager;

import java.util.Arrays;

/**
 *
 * @author broch_mattia
 */
public class ESPManager extends Thread {

    private socketUDP socket; 
    private manager manager;

    public ESPManager(int portaAscolto, manager manager) {
        socket = new socketUDP(portaAscolto);
        this.manager=manager;
    }

    @Override
    public void run() {
        while (true) {
            cmdRicevuto comandoComplesso = socket.receive();
            System.out.println("siii");
            if (comandoComplesso != null) {
                String reti[] = comandoComplesso.getComando().split("!");
                try {

                    
//                    final List<String> foundMac = Arrays.stream(reti)
//                            .map((String rete) -> rete.split("/")[1])
//                            .filter((String macCandidate) -> Arrays.stream(ACCESSPOINTS.values())
//                                    .map(ap -> ap.getMac())
//                                    .anyMatch(mac -> mac.equals(macCandidate)))
//                            .collect(Collectors.toList());
                    //reti[0]
                    for(int i=1;i<reti.length;i++){
                        for(ACCESSPOINTS ap : ACCESSPOINTS.values())
                            if(reti[i].split(";")[0].equals(ap.getMac())) {
                               ap.setPotenza(Double.parseDouble(reti[i].split(";")[1]));
                            }
                    }

                    APInfo ap1 = ACCESSPOINTS.values()[0].toAPInfo();//new APInfo("AP1", "", new coordinate(45.68779, 9.18132), /*0.016*/-85);
                    APInfo ap2 = ACCESSPOINTS.values()[1].toAPInfo(); //new APInfo("AP2", "", new coordinate(45.68771, 9.1811), /*0.011*/-80);
                    APInfo ap3 = ACCESSPOINTS.values()[2].toAPInfo();// new APInfo("AP3", "", new coordinate(45.68752, 9.18111), /*0.018*/-88);
                    System.out.println("COORDINATE TRILATERAZIONE: " + new Trilateration(ap1, ap2, ap3).getPoint());


                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println(Arrays.toString(reti));
                }
            }
        }
    }

}

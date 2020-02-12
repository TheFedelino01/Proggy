/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggyGradle.server.utility;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import proggyGradle.Socket.cmdRicevuto;
import proggyGradle.Socket.socketUDP;
import proggyGradle.server.Trilateration.ACCESSPOINTS;

/**
 *
 * @author broch_mattia
 */
public class ESPManager extends Thread {

    private socketUDP socket; 

    public ESPManager(int portaAscolto) {
        socket = new socketUDP(portaAscolto);
    }

    @Override
    public void run() {
        while (true) {
            cmdRicevuto comandoComplesso = socket.receive();
            if (comandoComplesso != null) {
                try {

                    String reti[] = comandoComplesso.getComando().split("\n");
                    
                    final List<String> foundMac = Arrays.stream(reti)
                            .map((String rete) -> rete.split("/")[1])
                            .filter((String macCandidate) -> Arrays.stream(ACCESSPOINTS.values())
                                    .map(ap -> ap.getMac())
                                    .anyMatch(mac -> mac.equals(macCandidate)))
                            .collect(Collectors.toList());
                    
                    for(int i=0;i<reti.length;i++){
                        for(ACCESSPOINTS ap : ACCESSPOINTS.values())
                            if(reti[i].split("/")[1].equals(ap.getMac())) {
                               ap.toAPInfo();
                            }
                    }
                    String nome = cmdSplitted[0];
                    String mac = cmdSplitted[1];
                    String rssi = cmdSplitted[2];
                    
                } catch (Exception e) {
                    System.out.println(e.getCause());
                }
            }
        }
    }

}

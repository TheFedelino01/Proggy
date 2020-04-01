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
import proggyGradle.server.Trilateration.WiFiUtils;
import proggyGradle.server.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author broch_mattia
 */
public class ESPManager extends Thread {

    private socketUDP socket;
    private manager manager;

    public ESPManager(int portaAscolto, manager manager) {
        socket = new socketUDP(portaAscolto);
        this.manager = manager;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            final ReceivedData received = receiveAPData();
            final List<APInfo> apList = received.apInfoList;

            if (apList.size() < 3)
                throw new RuntimeException("Coordinate non sufficienti per effettuare la trilaterazione");
            else if (apList.size() > 3)     //se sono più di tre prendo quelli più vicini
                apList.sort((a, b) -> {
                    if (a.getDistanza() == b.getDistanza())
                        return 0;
                    return a.getDistanza() > b.getDistanza() ? 1 : -1;
                });
            //APInfo ap1 = ACCESSPOINTS.values()[0].toAPInfo();//new APInfo("AP1", "", new coordinate(45.68779, 9.18132), /*0.016*/-85);
            //APInfo ap2 = ACCESSPOINTS.values()[1].toAPInfo(); //new APInfo("AP2", "", new coordinate(45.68771, 9.1811), /*0.011*/-80);
            //APInfo ap3 = ACCESSPOINTS.values()[2].toAPInfo();// new APInfo("AP3", "", new coordinate(45.68752, 9.18111), /*0.018*/-88);
            System.out.println("COORDINATE TRILATERAZIONE: " + Trilateration.getPoint(apList.get(0), apList.get(1), apList.get(2)));
            manager.salvaCoordinate(received.idDispositivo, Trilateration.getPoint(apList.get(0), apList.get(1), apList.get(2)));
        }
    }

    public ReceivedData receiveAPData() {
        cmdRicevuto comandoComplesso = socket.receive();
        if (comandoComplesso != null) {
            String[] reti = comandoComplesso.getComando().split("!");
            try {

//                    final List<String> foundMac = Arrays.stream(reti)
//                            .map((String rete) -> rete.split("/")[1])
//                            .filter((String macCandidate) -> Arrays.stream(ACCESSPOINTS.values())
//                                    .map(ap -> ap.getMac())
//                                    .anyMatch(mac -> mac.equals(macCandidate)))
//                            .collect(Collectors.toList());
                //reti[0]
                final List<APInfo> apList = new ArrayList<>(3);
                for (int i = 1; i < reti.length; i++) {
                    final String[] rete = reti[i].split(";");
                    for (ACCESSPOINTS ap : ACCESSPOINTS.values())
                        if (rete[0].equals(ap.getMac())) {
                            ap.setPotenza(Integer.parseInt(rete[1]));
                            ap.setCanale(Integer.parseInt(rete[4]));
                            final APInfo apInfo = ap.toAPInfo();
                            apList.add(apInfo);
                        }
                }

                return new ReceivedData(reti[0], apList);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(Arrays.toString(reti));
            }
        }
        return null;
    }

    public static class ReceivedData {
        private final String idDispositivo;
        private final List<APInfo> apInfoList;

        public ReceivedData(String idDispositivo, List<APInfo> apInfoList) {
            this.idDispositivo = idDispositivo;
            this.apInfoList = apInfoList;
        }

        @Override
        public String toString() {
            return "ReceivedData{" +
                    "idDispositivo='" + idDispositivo + '\'' +
                    ", apInfoList=" + apInfoList +
                    '}';
        }

        public String getIdDispositivo() {
            return idDispositivo;
        }

        public List<APInfo> getApInfoList() {
            return apInfoList;
        }
    }

}

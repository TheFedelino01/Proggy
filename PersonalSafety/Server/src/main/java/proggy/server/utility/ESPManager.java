/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggy.server.utility;

import proggy.Database.DbManager;
import proggy.Socket.cmdRicevuto;
import proggy.Socket.socketUDP;
import proggy.server.Trilateration.ACCESSPOINTS;
import proggy.server.Trilateration.APInfo;
import proggy.server.Trilateration.Trilateration;
import proggy.server.manager;

import java.net.InetSocketAddress;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * @author broch_mattia
 */
public class ESPManager extends Thread {

    private final socketUDP socket;
    private final manager manager;
    private final Map<Integer, InetSocketAddress> espAttivi;
    private final DbManager db;


    public ESPManager(int portaAscolto, manager manager) {
        socket = new socketUDP(portaAscolto);
        this.manager = manager;
        espAttivi = new HashMap<>();
        db = DbManager.getInstance();
    }


    /**
     * PROTOCOLLO DI COMUNICAZIONE CON ESP
     *
     * ON;idScheda
     * ATTIVA;idScheda;idUtente
     * DISATTIVA;idScheda
     */
    @Override
    public void run() {
        while (!isInterrupted()) {
            final cmdRicevuto comandoComplesso = socket.receive();
            if (comandoComplesso != null) {
                final String comando = comandoComplesso.getComando();
                final String[] cmdSplitted = comando.split(";");
                switch (cmdSplitted[0]) {
                    case "ON":
                        final int idScheda = Integer.parseInt(cmdSplitted[1]);
                        if (espAttivi.containsKey(idScheda)) {
                            espAttivi.replace(idScheda, new InetSocketAddress(comandoComplesso.getIP(), comandoComplesso.getPorta()));
                        } else {
                            espAttivi.put(idScheda, new InetSocketAddress(comandoComplesso.getIP(), comandoComplesso.getPorta()));
                        }
                        break;
                    case "ATTIVA": {
                        final int idScheda2 = Integer.parseInt(cmdSplitted[1]);
                        final int idUtente = Integer.parseInt(cmdSplitted[2]);
                        final String sql = "INSERT INTO `utilizza` (`idScheda`, `idCliente`) VALUES (?,?);";
                        try (PreparedStatement statement = db.getConnection().prepareStatement(sql)) {
                            statement.setInt(1, idScheda2);
                            statement.setInt(2, idUtente);
                            statement.executeUpdate();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        break;
                    }
                    case "DISATTIVA": {
                        final int idScheda3 = Integer.parseInt(cmdSplitted[1]);
                        final String sql = "UPDATE `utilizza` SET `dataOraFine`=CURRENT_TIMESTAMP WHERE idScheda=? and dataOraFine is null";
                        try (PreparedStatement statement = db.getConnection().prepareStatement(sql)) {
                            statement.setInt(1, idScheda3);
                            statement.executeUpdate();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        break;
                    }
                    default:    //è la lista di AP
                        final ReceivedAPData received = parseAPData(comando);
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
                        break;
                }
            }
        }
    }


    public void attivaScheda(int idScheda, int idUtente) {
        if (!espAttivi.containsKey(idScheda))
            throw new IllegalStateException("ESP NON ATTIVO");
        final int porta = espAttivi.get(idScheda).getPort();
        final String ip = espAttivi.get(idScheda).getAddress().toString();
        socket.send("ATTIVA;" + idScheda + ";" + idUtente, porta, ip);
    }

    public void disattivaScheda(int idScheda) {
        if (!espAttivi.containsKey(idScheda))
            throw new IllegalStateException("ESP NON ATTIVO");
        final int porta = espAttivi.get(idScheda).getPort();
        final String ip = espAttivi.get(idScheda).getAddress().toString();
        socket.send("ATTIVA;" + idScheda, porta, ip);
    }


    @Override
    public void interrupt() {
        super.interrupt();
        socket.closeConnection();
    }

    public ReceivedAPData parseAPData(String comando) {
        String[] reti = comando.split("!");
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

            return new ReceivedAPData(reti[0], apList);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(Arrays.toString(reti));
        }
        return null;
    }

    public static class ReceivedAPData {
        private final String idDispositivo;
        private final List<APInfo> apInfoList;

        public ReceivedAPData(String idDispositivo, List<APInfo> apInfoList) {
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

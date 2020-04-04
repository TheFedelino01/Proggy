import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import proggyGradle.server.Trilateration.APInfo;
import proggyGradle.server.Trilateration.Trilateration;
import proggyGradle.server.Trilateration.WiFiUtils;
import proggyGradle.server.coordinate;
import proggyGradle.server.manager;
import proggyGradle.server.utility.ESPManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TrilaterationTest {

    private static ESPManager espManager;
    private static DatagramSocket socket;

    @BeforeAll
    public static void init() throws SocketException {
        espManager = new ESPManager(1234, new manager());
        //espManager.start();
        socket = new DatagramSocket();
    }

    @ParameterizedTest
    @MethodSource("potenzeGenerator")
    public void testTrilateration(int potenza1, int potenza2, int potenza3) throws IOException {
        final String toSend = "2!fcecda384364;" + potenza1 + ";Saccani ROG MODEM;10;36!b8d5261593d;" + potenza2 + ";Saccani ROG MODEM - 2.4G;10;8!18e82951f2cf;" + potenza3 + ";Saccani ROG MODEM;10;44";
        DatagramPacket dp = new DatagramPacket(toSend.getBytes(), toSend.length(), InetAddress.getLocalHost(), 1234);
        socket.send(dp);
        ESPManager.ReceivedData ap = espManager.receiveAPData();
        List<APInfo> apInfoList = ap.getApInfoList();
//        for (APInfo apInfo : apInfoList)
//            System.out.println(apInfo);
        System.out.println("Potenza1: " + potenza1 + " Potenza2: " + potenza2 + " Potenza3: " + potenza3);
        System.out.println("Distanza1: " + apInfoList.get(0).getDistanza() * 1000 + " Distanza2: " + apInfoList.get(1).getDistanza() * 1000 + " Distanza3: " + apInfoList.get(2).getDistanza() * 1000);
        System.out.println("Trilaterazione: " + Trilateration.getPoint(apInfoList.get(0), apInfoList.get(1), apInfoList.get(2)));
    }


    @ParameterizedTest
    @MethodSource("valuesProvider")
    public void testTrilateration(String toSend) throws IOException {
        DatagramPacket dp = new DatagramPacket(toSend.getBytes(), toSend.length(), InetAddress.getLocalHost(), 1234);
        socket.send(dp);
        ESPManager.ReceivedData ap = espManager.receiveAPData();
        List<APInfo> apInfoList = ap.getApInfoList();
//        for (APInfo apInfo : apInfoList)
//            System.out.println(apInfo);
        final coordinate coord = Trilateration.getPoint(apInfoList.get(0), apInfoList.get(1), apInfoList.get(2), APInfo::getDistanza);
        System.out.println("Trilaterazione: " + coord);

        assertTrue(coord.getLongitudine() > 9.18975 && coord.getLongitudine() < 9.19012);
        assertTrue(coord.getLatitudine() > 45.70393 && coord.getLatitudine() < 45.70421);
    }

    @ParameterizedTest
    @MethodSource("valuesProvider")
    public void testTrilaterationOld(String toSend) throws IOException {
        DatagramPacket dp = new DatagramPacket(toSend.getBytes(), toSend.length(), InetAddress.getLocalHost(), 1234);
        socket.send(dp);
        ESPManager.ReceivedData ap = espManager.receiveAPData();
        List<APInfo> apInfoList = ap.getApInfoList();
//        for (APInfo apInfo : apInfoList)
//            System.out.println(apInfo);
        final coordinate coord = Trilateration.getPoint(apInfoList.get(0), apInfoList.get(1), apInfoList.get(2), APInfo::getDistanzaOld);
        System.out.println("Trilaterazione: " + coord);

        assertTrue(coord.getLongitudine() > 9.18975 && coord.getLongitudine() < 9.19012);
        assertTrue(coord.getLatitudine() > 45.70393 && coord.getLatitudine() < 45.70421);
    }

    @ParameterizedTest
    @MethodSource("valuesProvider")
    public void testTrilaterationFixed(String toSend) throws IOException {
        DatagramPacket dp = new DatagramPacket(toSend.getBytes(), toSend.length(), InetAddress.getLocalHost(), 1234);
        socket.send(dp);
        ESPManager.ReceivedData ap = espManager.receiveAPData();
        List<APInfo> apInfoList = ap.getApInfoList();
//        for (APInfo apInfo : apInfoList)
//            System.out.println(apInfo);
        final coordinate coord = Trilateration.getPoint(apInfoList.get(0), apInfoList.get(1), apInfoList.get(2), APInfo::getDistanzaFixed);
        System.out.println("Trilaterazione: " + coord);

        assertTrue(coord.getLongitudine() > 9.18975 && coord.getLongitudine() < 9.19012);
        assertTrue(coord.getLatitudine() > 45.70393 && coord.getLatitudine() < 45.70421);
    }

    @ParameterizedTest
    @MethodSource("valuesProvider")
    public void testDistances(String toSend) throws IOException {
        DatagramPacket dp = new DatagramPacket(toSend.getBytes(), toSend.length(), InetAddress.getLocalHost(), 1234);
        socket.send(dp);
        ESPManager.ReceivedData ap = espManager.receiveAPData();
        List<APInfo> apInfoList = ap.getApInfoList();
//        for (APInfo apInfo : apInfoList)
//            System.out.println(apInfo);
        for (APInfo apt : apInfoList) {
            System.out.println("AP: " + apt.getDistanza());
            assertTrue(apt.getDistanza() < 0.100);
        }
    }

    @ParameterizedTest
    @MethodSource("valuesProvider")
    public void testDistancesOld(String toSend) throws IOException {
        DatagramPacket dp = new DatagramPacket(toSend.getBytes(), toSend.length(), InetAddress.getLocalHost(), 1234);
        socket.send(dp);
        ESPManager.ReceivedData ap = espManager.receiveAPData();
        List<APInfo> apInfoList = ap.getApInfoList();
//        for (APInfo apInfo : apInfoList)
//            System.out.println(apInfo);
        for (APInfo apt : apInfoList) {
            System.out.println("AP " + apt.getMAC() + ": " + apt.getDistanzaOld());
            assertTrue(apt.getDistanzaOld() < 0.100);
        }
    }

    @ParameterizedTest
    @MethodSource("valuesProvider")
    public void testDistancesFixed(String toSend) throws IOException {
        DatagramPacket dp = new DatagramPacket(toSend.getBytes(), toSend.length(), InetAddress.getLocalHost(), 1234);
        socket.send(dp);
        ESPManager.ReceivedData ap = espManager.receiveAPData();
        List<APInfo> apInfoList = ap.getApInfoList();
//        for (APInfo apInfo : apInfoList)
//            System.out.println(apInfo);
        for (APInfo apt : apInfoList) {
            System.out.println("AP: " + apt.getDistanzaFixed());
            assertTrue(apt.getDistanzaFixed() < 0.100);
        }
    }

    @Test
    public void testDistance() {
        final int frequenza = 2452;
        final int potenza = -55;
        final int measured = -40;
        System.out.println(WiFiUtils.rssiToDistance(frequenza, potenza));
        System.out.println(WiFiUtils.rssiToDistanceFixed(frequenza, potenza));
        System.out.println(WiFiUtils.rssiToDistanceOld(measured, potenza));
    }


    @Test
    public void testDistance2() {
        double K = -27.55;
        int AGrx = 0;
        int Ptx = 16;
        int CLtx = 0;
        int AGtx = 2;
        int CLrx = 0;
        int Prx = -49;
        int FM = 22;
        double FSPL = Ptx - CLtx + AGtx + AGrx - CLrx - Prx - FM - 5;
        double f = 2412;
        double d = Math.pow(10, ((FSPL - K - 20 * Math.log10(f)) / 20.0));
        System.out.println(d);
    }

    @AfterAll
    public static void shutdown() {
        //espManager.interrupt();
        socket.close();
        System.out.println("test completati");
    }

    private static Stream<Arguments> potenzeGenerator() {
        /*return Stream.of(
                arguments(-70,-60,-40)
        );*/

        List<Arguments> args = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            args.add(arguments(getPotenzaRandom(), getPotenzaRandom(), getPotenzaRandom()));
        return args.stream();

    }

    private static int getPotenzaRandom() {
        Random r = new Random();
        return -(r.nextInt((60 - 20) + 1) + 20);
    }

    private static Stream<Arguments> valuesProvider() {
        return Stream.of(
                arguments("2!b8d5261593d;-89;Saccani ROG - 2.4G;149618;9!fcecda384364;-73;Saccani ROG MODEM;152695;11!18e82951f2cf;-86;Saccani ROG MODEM;152661;11!"),
                arguments("2!b8d5261593d;-73;Saccani ROG - 2.4G;25815;9!fcecda384364;-78;Saccani ROG MODEM;25847;11!18e82951f2cf;-90;Saccani ROG MODEM;25257;11!"),
                arguments("2!b8d5261593d;-73;Saccani ROG - 2.4G;25815;9!fcecda384364;-77;Saccani ROG MODEM;35671;11!18e82951f2cf;-88;Saccani ROG MODEM;33648;11!"),
                arguments("2!b8d5261593d;-82;Saccani ROG - 2.4G;55409;9!fcecda384364;-72;Saccani ROG MODEM;57570;11!18e82951f2cf;-86;Saccani ROG MODEM;57492;11!"),
                arguments("2!b8d5261593d;-83;Saccani ROG - 2.4G;66365;9!fcecda384364;-72;Saccani ROG MODEM;73435;11!18e82951f2cf;-89;Saccani ROG MODEM;73353;11!"),
                arguments("2!b8d5261593d;-83;Saccani ROG - 2.4G;83466;9!fcecda384364;-72;Saccani ROG MODEM;89193;11!18e82951f2cf;-87;Saccani ROG MODEM;89112;11!"),
                arguments("2!b8d5261593d;-84;Saccani ROG - 2.4G;103435;9!fcecda384364;-73;Saccani ROG MODEM;105071;11!18e82951f2cf;-91;Saccani ROG MODEM;105077;11!"),
                arguments("2!b8d5261593d;-88;Saccani ROG - 2.4G;115417;9!fcecda384364;-71;Saccani ROG MODEM;120916;11!18e82951f2cf;-85;Saccani ROG MODEM;120937;11!"),
                arguments("2!b8d5261593d;-83;Saccani ROG - 2.4G;133306;9!fcecda384364;-72;Saccani ROG MODEM;136776;11!18e82951f2cf;-91;Saccani ROG MODEM;136799;11!"),
                arguments("2!b8d5261593d;-89;Saccani ROG - 2.4G;149618;9!fcecda384364;-73;Saccani ROG MODEM;152695;11!18e82951f2cf;-86;Saccani ROG MODEM;152661;11!"),
                arguments("2!b8d5261593d;-85;Saccani ROG - 2.4G;159242;9!fcecda384364;-72;Saccani ROG MODEM;168499;11!18e82951f2cf;-87;Saccani ROG MODEM;168522;11!"),
                arguments("2!b8d5261593d;-80;Saccani ROG - 2.4G;182384;9!fcecda384364;-74;Saccani ROG MODEM;184360;11!18e82951f2cf;-87;Saccani ROG MODEM;184383;11!"),
                arguments("2!b8d5261593d;-87;Saccani ROG - 2.4G;196664;9!fcecda384364;-72;Saccani ROG MODEM;198586;11!18e82951f2cf;-86;Saccani ROG MODEM;198607;11!"),
                arguments("2!b8d5261593d;-87;Saccani ROG - 2.4G;196664;9!fcecda384364;-71;Saccani ROG MODEM;214446;11!18e82951f2cf;-90;Saccani ROG MODEM;214469;11!"),
                arguments("2!b8d5261593d;-82;Saccani ROG - 2.4G;226213;9!fcecda384364;-73;Saccani ROG MODEM;230308;11!18e82951f2cf;-86;Saccani ROG MODEM;230228;11!"),
                arguments("2!b8d5261593d;-84;Saccani ROG - 2.4G;237374;9!fcecda384364;-75;Saccani ROG MODEM;246169;11!18e82951f2cf;-87;Saccani ROG MODEM;245885;11!")
        );
    }

}

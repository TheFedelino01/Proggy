import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import proggyGradle.server.Trilateration.APInfo;
import proggyGradle.server.Trilateration.Trilateration;
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
    public void testDistance(int potenza1, int potenza2, int potenza3) throws IOException {
        final String toSend = "2!fcecda384364;" + potenza1 + ";Saccani ROG MODEM;10;36!b8d5261593d;" + potenza2 + ";Saccani ROG MODEM - 2.4G;10;8!18e82951f2cf;" + potenza3 + ";Saccani ROG MODEM;10;44";
        DatagramPacket dp = new DatagramPacket(toSend.getBytes(), toSend.length(), InetAddress.getLocalHost(), 1234);
        socket.send(dp);
        ESPManager.ReceivedData ap = espManager.receiveAPData();
        List<APInfo> apInfoList = ap.getApInfoList();
//        for (APInfo apInfo : apInfoList)
//            System.out.println(apInfo);
        System.out.println("Potenza1: " + potenza1 + " Potenza2: " + potenza2 + " Potenza3: " + potenza3);
        System.out.println("Distanza1: " + apInfoList.get(0).getDistanza()*1000 + " Distanza2: " + apInfoList.get(1).getDistanza()*1000 + " Distanza3: " + apInfoList.get(2).getDistanza()*1000);
        System.out.println("Trilaterazione: " + Trilateration.getPoint(apInfoList.get(0), apInfoList.get(1), apInfoList.get(2)));
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


}

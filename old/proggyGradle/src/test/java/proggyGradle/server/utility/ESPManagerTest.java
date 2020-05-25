package proggyGradle.server.utility;

import org.junit.jupiter.api.*;
import proggyGradle.Socket.socketUDP;
import proggyGradle.server.manager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ESPManagerTest {

    private ESPManager espManager;
    private DatagramSocket socket;

    ESPManagerTest() {
        espManager = new ESPManager(5555, new manager());
    }

    @BeforeEach
    void setUp() throws SocketException {
        socket = new DatagramSocket();
        espManager.start();
    }

    @Test
    @Order(1)
    void attivaScheda() throws IOException {
        final String toSend = "ATTIVA;1;1";
        DatagramPacket dp = new DatagramPacket(toSend.getBytes(), toSend.length(), InetAddress.getLocalHost(), 5555);
        socket.send(dp);
    }

    @Test
    @Order(2)
    void disattivaScheda() throws IOException {
        final String toSend = "DISATTIVA;1";
        DatagramPacket dp = new DatagramPacket(toSend.getBytes(), toSend.length(), InetAddress.getLocalHost(), 5555);
        socket.send(dp);
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        espManager.interrupt();
        socket.close();
        Thread.sleep(100);
    }
}
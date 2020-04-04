package proggyGradle.server.Trilateration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WiFiUtils {
    private static final int n = 2;
    private static final double DISTANCE_MHZ_M = 27.55;

    private static final Map<Integer, Integer> canaliUnmodifiable;

    static {
        final Map<Integer, Integer> canali = new HashMap<>();
        canali.put(1, 2412);
        canali.put(2, 2417);
        canali.put(3, 2422);
        canali.put(4, 2427);
        canali.put(5, 2432);
        canali.put(6, 2437);
        canali.put(7, 2437);
        canali.put(8, 2447);
        canali.put(9, 2452);
        canali.put(10, 2457);
        canali.put(11, 2462);
        canali.put(12, 2467);
        canali.put(13, 2472);
        canali.put(14, 2484);

        int channel = 32;
        int frequency = 5160;
        while (channel <= 140) {
            canali.put(channel, frequency);
            channel += 2;
            frequency += 10;
        }
        canaliUnmodifiable = Collections.unmodifiableMap(canali);
    }

    private WiFiUtils() {
    }

    /**
     * 10 ^ ((Measured Power – RSSI)/(10 * N))
     *
     * @return distanza tra l'access point e il dispositivo
     */
    public static double rssiToDistanceOld(int measuredPower, int potenza) {
        return Math.pow(10, (measuredPower - potenza) / (10.0 * n)) / 1000.0;
    }


    public static double rssiToDistance(int frequenza, int potenza) {
        //System.out.println(frequenza + "----" + potenza);
        return Math.pow(10.0, (DISTANCE_MHZ_M - (20 * Math.log10(frequenza)) + Math.abs(potenza)) / 20.0) / 1000.0;
    }

    public static double rssiToDistanceFixed(int frequenza, int potenza) {
        //System.out.println(frequenza + " Potenza: " + potenza);
        //System.out.println("Fixing...:" + (Math.pow(1.05, -potenza - 20) - 4));
        if (potenza < -50)
            potenza += Math.floor(Math.pow(1.05, -potenza - 20) - 4);
        //System.out.println("Nuova potenza: " + potenza);
        return Math.pow(10.0, (DISTANCE_MHZ_M - (20 * Math.log10(frequenza)) + Math.abs(potenza)) / 20.0) / 1000.0;
    }

    public static int channelToFrequency(int channel) {
        return canaliUnmodifiable.get(channel);
    }
}

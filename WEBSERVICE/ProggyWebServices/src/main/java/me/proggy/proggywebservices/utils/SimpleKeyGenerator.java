package me.proggy.proggywebservices.utils;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;


public class SimpleKeyGenerator {

    /**
     * Genera una chiave con l'algoritmo SHA512
     *
     * @return chiave generata
     */
    public static Key generateKey() {
        final String keyString = "9P5fzJTcz9k/Cxn4&z* chiave molto lunga e molto sicura x5*379dv^hit2^gSynW";
        return new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "HmacSHA512");
    }
}
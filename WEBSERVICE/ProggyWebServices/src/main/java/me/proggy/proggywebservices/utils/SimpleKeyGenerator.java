package me.proggy.proggywebservices.utils;

import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.ApplicationScoped;
import java.security.Key;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
public class SimpleKeyGenerator {

    // ======================================
    // =          Business methods          =
    // ======================================

    public static Key generateKey() {
        final String keyString = "9P5fzJTcz9k/Cxn4&z* chiave molto lunga e molto sicura x5*379dv^hit2^gSynW";
        return new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "HmacSHA512");
    }
}
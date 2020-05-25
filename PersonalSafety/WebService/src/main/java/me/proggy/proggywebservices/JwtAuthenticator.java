package me.proggy.proggywebservices;

import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.core.UriInfo;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Gestisce l'autenticazione con il token JWT
 */
public class JwtAuthenticator {

    private static JwtAuthenticator instance;

    public static JwtAuthenticator getInstance() {
        if (instance == null)
            instance = new JwtAuthenticator();
        return instance;
    }


    private final Key key;
    private final JwtParser jwtParser;

    private JwtAuthenticator() {
        key = generateKey();
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }


    /**
     * Verifica la validità di un token
     *
     * @param token da autenticare
     * @return claims contenuti nel token
     * @throws JwtException in caso di autenticazione fallita
     */
    public Jws<Claims> authenticate(String token) {
        return jwtParser.parseClaimsJws(token);
    }

    /**
     * Genera un token con i parametri specificati
     *
     * @param id    id dell'utente
     * @param user  username dell'utente
     * @param admin booleano che indica se l'utente è un ammistratore
     * @return token generato
     * @see Jwts#builder()
     */
    public String issueToken(int id, String user, boolean admin, UriInfo context) {
        return Jwts.builder()
                .setSubject(user)
                .claim("id", id)
                .claim("admin", admin)
                .setIssuer(context.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Converte un {@link LocalDateTime} in {@link Date}
     *
     * @param localDateTime data da convertire
     * @return data convertita
     */
    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * Genera una chiave con l'algoritmo SHA512
     *
     * @return chiave generata
     */
    private Key generateKey() {
        final String keyString = "9P5fzJTcz9k/Cxn4&z* chiave molto lunga e molto sicura x5*379dv^hit2^gSynW";
        return new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "HmacSHA512");
    }
}

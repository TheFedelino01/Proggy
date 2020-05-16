package me.proggy.proggywebservices;

import io.jsonwebtoken.Jwts;
import me.proggy.proggywebservices.orsenigo.UsersResource;
import me.proggy.proggywebservices.utils.SimpleKeyGenerator;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import java.security.Key;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Classe per testare direttamente i webservice definiti in {@link TestResource}
 */
public class TestResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(TestResource.class, JWTTokenNeededFilter.class, UsersResource.class);
    }

    /**
     * Esegue una richiesta get alla pagine test e verifica il risultato
     */
    @Test
    public void getText() {
        String response = target("/test").request().get(String.class);
        assertEquals(response, "FUNZIA");
    }


    private static String token;

    @Test
    public void invokingEchoShouldFailCauseNoToken() {
        Response response = target("/test/auth").request().get();
        assertEquals(401, response.getStatus());
    }


    @Test
    public void shouldLogUserIn() {

        Response response = target("/users").queryParam("username", "federico").queryParam("password", "password").request().get();

        assertEquals(200, response.getStatus());

        assertNotNull(response.getHeaderString(HttpHeaders.AUTHORIZATION));
        token = response.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Check the JWT Token
        String justTheToken = token.substring("Bearer".length()).trim();
        Key key = SimpleKeyGenerator.generateKey();
        assertEquals(1, Jwts.parser().setSigningKey(key).parseClaimsJws(justTheToken).getHeader().size());
        assertEquals("HS512", Jwts.parser().setSigningKey(key).parseClaimsJws(justTheToken).getHeader().getAlgorithm());
        assertEquals(4, Jwts.parser().setSigningKey(key).parseClaimsJws(justTheToken).getBody().size());
        assertEquals("federico", Jwts.parser().setSigningKey(key).parseClaimsJws(justTheToken).getBody().getSubject());
        //assertEquals(baseURL.toString().concat("api/users/login"), Jwts.parser().setSigningKey(key).parseClaimsJws(justTheToken).getBody().getIssuer());
        assertNotNull(Jwts.parser().setSigningKey(key).parseClaimsJws(justTheToken).getBody().getIssuedAt());
        assertNotNull(Jwts.parser().setSigningKey(key).parseClaimsJws(justTheToken).getBody().getExpiration());
    }

    @Test
    public void invokingEchoShouldSucceedCauseToken() {
        shouldLogUserIn();
        Response response = target("test/auth").request().header(HttpHeaders.AUTHORIZATION, token).get();
        assertEquals(200, response.getStatus());
        assertEquals("Logged: federico", response.readEntity(String.class));
    }
}

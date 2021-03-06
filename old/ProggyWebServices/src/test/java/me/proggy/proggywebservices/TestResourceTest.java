package me.proggy.proggywebservices;

import me.proggy.proggywebservices.orsenigo.UsersResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Classe per testare direttamente i webservice definiti in {@link TestResource}
 */
public class TestResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(TestResource.class, AuthenticationFilter.class, AuthorizationFilter.class, UsersResource.class);
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
    public void shouldFailCauseNoToken() {
        Response response = target("/test/auth/cliente").request().get();
        assertEquals(401, response.getStatus());
    }

    @Test
    public void shouldFailCauseNoToken2() {
        Response response = target("/test/auth/admin").request().get();
        assertEquals(401, response.getStatus());
    }


    @Test
    public void shouldLogClienteIn() {

        Response response = target("/users").queryParam("username", "federico").queryParam("password", "password").request().get();

        assertEquals(200, response.getStatus());

        assertNotNull(response.getHeaderString(HttpHeaders.AUTHORIZATION));
        token = response.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Check the JWT Token
        String justTheToken = token.substring("Bearer".length()).trim();

        assertEquals(1, JwtAuthenticator.getInstance().authenticate(justTheToken).getHeader().size());
        assertEquals("HS512", JwtAuthenticator.getInstance().authenticate(justTheToken).getHeader().getAlgorithm());
        assertEquals(6, JwtAuthenticator.getInstance().authenticate(justTheToken).getBody().size());
        assertEquals("federico", JwtAuthenticator.getInstance().authenticate(justTheToken).getBody().getSubject());
        assertEquals(18, JwtAuthenticator.getInstance().authenticate(justTheToken).getBody().get("id"));
        assertEquals(false, JwtAuthenticator.getInstance().authenticate(justTheToken).getBody().get("admin"));
        //assertEquals(baseURL.toString().concat("api/users/login"), JwtAuthenticator.getInstance().authenticate(justTheToken).getBody().getIssuer());
        assertNotNull(JwtAuthenticator.getInstance().authenticate(justTheToken).getBody().getIssuedAt());
        assertNotNull(JwtAuthenticator.getInstance().authenticate(justTheToken).getBody().getExpiration());
    }

    @Test
    public void shouldSucceedCauseLogged() {
        shouldLogClienteIn();
        Response response = target("test/auth/cliente").request().header(HttpHeaders.AUTHORIZATION, token).get();
        assertEquals(200, response.getStatus());
        assertEquals("Logged: federico", response.readEntity(String.class));
    }


    @Test
    public void shouldFaildCauseNotAdmin() {
        shouldLogClienteIn();
        Response response = target("test/auth/admin").request().header(HttpHeaders.AUTHORIZATION, token).get();
        assertEquals(403, response.getStatus());
    }

    @Test
    public void shouldLogAdminIn() {

        Response response = target("/users").queryParam("username", "admin").queryParam("password", "password").request().get();

        assertEquals(200, response.getStatus());

        assertNotNull(response.getHeaderString(HttpHeaders.AUTHORIZATION));
        token = response.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Check the JWT Token
        String justTheToken = token.substring("Bearer".length()).trim();

        assertEquals(1, JwtAuthenticator.getInstance().authenticate(justTheToken).getHeader().size());
        assertEquals("HS512", JwtAuthenticator.getInstance().authenticate(justTheToken).getHeader().getAlgorithm());
        assertEquals(6, JwtAuthenticator.getInstance().authenticate(justTheToken).getBody().size());
        assertEquals("admin", JwtAuthenticator.getInstance().authenticate(justTheToken).getBody().getSubject());
        assertEquals(1, JwtAuthenticator.getInstance().authenticate(justTheToken).getBody().get("id"));
        assertEquals(true, JwtAuthenticator.getInstance().authenticate(justTheToken).getBody().get("admin"));
        //assertEquals(baseURL.toString().concat("api/users/login"), Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(justTheToken).getBody().getIssuer());
        assertNotNull(JwtAuthenticator.getInstance().authenticate(justTheToken).getBody().getIssuedAt());
        assertNotNull(JwtAuthenticator.getInstance().authenticate(justTheToken).getBody().getExpiration());
    }


    @Test
    public void shouldSucceedCauseAdmin() {
        shouldLogAdminIn();
        Response response = target("test/auth/admin").request().header(HttpHeaders.AUTHORIZATION, token).get();
        assertEquals(200, response.getStatus());
        assertEquals("Logged: admin", response.readEntity(String.class));
    }


    @Test
    public void shouldFaildCauseNotCliente() {
        shouldLogAdminIn();
        Response response = target("test/auth/cliente").request().header(HttpHeaders.AUTHORIZATION, token).get();
        assertEquals(403, response.getStatus());
    }

}

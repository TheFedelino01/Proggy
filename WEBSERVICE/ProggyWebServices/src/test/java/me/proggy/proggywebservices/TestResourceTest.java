package me.proggy.proggywebservices;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;

import static org.junit.Assert.assertEquals;

/**
 * Classe per testare direttamente i webservice definiti in {@link TestResource}
 */
public class TestResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(TestResource.class);
    }

    /**
     * Esegue una richiesta get alla pagine test e verifica il risultato
     */
    @Test
    public void getText() {
        String response = target("/test").request().get(String.class);
        assertEquals(response, "FUNZIA");
    }
}
package me.proggy.proggywebservices;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Classe per testare direttamente i webservice definiti in {@link TestDbResource}
 */
public class TestDbResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(TestDbResource.class);
    }

    /**
     * Esegue una richiesta get alla pagina testdb e verifica il risultato
     */
    @Test
    public void getTest() {
        final String xml = target("testdb").request().get(String.class);
        System.out.println(xml);
        assertThat(xml, containsString("<scheda>"));
        assertThat(xml, containsString("</scheda>"));
    }

    /**
     * Esegue una richiesta post alla pagine testdb passandogli l'xml e verifica il risultato
     */
    @Test
    public void postTest() {
        Response response = target("testdb").request().post(Entity.xml("<scheda><idEnte>2</idEnte></scheda>"));
        System.out.println(response);
        assertEquals("Http Response should be 200", response.getStatus(), 200);
    }

}
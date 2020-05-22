package proggyGradle.server.utility;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public class WebServiceConsumer {
    private static final Client client = ClientBuilder.newClient();
    private static final WebTarget target = client.target("http://localhost:8080/ProggyWebServices/");
    private static final WebTarget deviceAssociaTarget = target.path("api/devices/associa");
    private static final WebTarget deviceDissociaTarget = target.path("api/devices/dissocia");
    private static final WebTarget userTarget = target.path("api/users");

    private final String token;

    public WebServiceConsumer() {
        token = login();
    }

    public void deviceAssocia(int idScheda, int idUtente) {
        Response response = deviceAssociaTarget
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .post(Entity.xml("<associazione><idScheda>" + idScheda + "</idScheda><idUtente>" + idUtente + "</idUtente></associazione>"));
        System.out.println(response);
        if (response.getStatus() != 200)
            throw new RuntimeException("Associazione fallita, " + response.readEntity(String.class));
    }

    public void deviceDissocia(int idScheda) {
        System.out.println("dissocia...");
        Response response = deviceDissociaTarget
                .queryParam("idScheda", idScheda)
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .put(Entity.text(""));
        System.out.println(response);
        if (response.getStatus() != 200)
            throw new RuntimeException("Dissociazione fallita, " + response.readEntity(String.class));
    }

    private String login() {
        Response response = userTarget.queryParam("username", "admin").queryParam("password", "password").request().get();
        if (response.getStatus() != 200)
            throw new RuntimeException("Autenticazione fallita, " + response.readEntity(String.class));
        return response.getHeaderString(HttpHeaders.AUTHORIZATION);
    }

}

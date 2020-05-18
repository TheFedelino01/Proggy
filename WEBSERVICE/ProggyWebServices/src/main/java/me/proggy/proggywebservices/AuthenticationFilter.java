package me.proggy.proggywebservices;

import io.jsonwebtoken.Jwts;
import me.proggy.proggywebservices.utils.SimpleKeyGenerator;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.security.Key;
import java.security.Principal;

/**
 * Filtro che gestisce l'autenticazione per l'accesso alle risorse
 * Consente l'accesso alla risorsa richiesta solo se l'utente è autenticato
 *
 * @author Giacomo Orsenigo
 */
@Provider
@Secured
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String AUTHENTICATION_SCHEME = "Bearer";
    private static final String REALM = "proggy";


    /**
     * Intercetta le richieste effettuate ai metodi o classi annotati con {@link Secured}
     * Se l'utente non è loggato, la richiesta viene rifiutata con un errore 401 - UNAUTHORIZED {@link Response.Status#UNAUTHORIZED}
     *
     * Richiama {@link #isTokenBasedAuthentication(String)} (AnnotatedElement)}
     *
     * @param requestContext richiesta ricevuta
     */
    @Override
    public void filter(ContainerRequestContext requestContext) {
        System.out.println("filtro");


        // Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (isTokenBasedAuthentication(authorizationHeader)) {

            // Extract the token from the HTTP Authorization header
            String token = authorizationHeader.substring("Bearer".length()).trim();

            try {

                // Validate the token
                Key key = SimpleKeyGenerator.generateKey();
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
                System.out.println("#### valid token : " + token);


                final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
                String username = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
                requestContext.setSecurityContext(new SecurityContext() {
                    @Override
                    public Principal getUserPrincipal() {
                        return () -> username;
                    }

                    @Override
                    public boolean isUserInRole(String role) {
                        return true;
                    }

                    @Override
                    public boolean isSecure() {
                        return currentSecurityContext.isSecure();
                    }

                    @Override
                    public String getAuthenticationScheme() {
                        return AUTHENTICATION_SCHEME;
                    }
                });


            } catch (Exception e) {
                System.err.println("#### invalid token : " + token);
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } else {
            System.err.println("#### Nessun token");
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"").build());

        }
    }


    /**
     * Controlla che l'Authorization header sia valido
     * Non deve essere nullo e deve essere preceduto da "Bearer" più uno spazio bianco
     *
     * @param authorizationHeader stringa di autenticazione
     * @return true se è valido, false in caso contrario
     */
    private boolean isTokenBasedAuthentication(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }


}


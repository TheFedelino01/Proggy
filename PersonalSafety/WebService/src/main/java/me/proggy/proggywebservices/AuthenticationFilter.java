package me.proggy.proggywebservices;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import me.proggy.proggywebservices.utils.MyPrincipal;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
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
    private static final String AUTHENTICATION_COOKIE_NAME = "Biscotto";
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
        System.out.println("AuthenticationFilter");


        // Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (isTokenBasedAuthentication(authorizationHeader)) {
            // Extract the token from the HTTP Authorization header
            auth(authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim(), requestContext);
        } else {
            final Cookie cookie = requestContext.getCookies().get(AUTHENTICATION_COOKIE_NAME);
            if (cookie != null)
                auth(cookie.getValue(), requestContext);
            else {
                System.err.println("#### Nessun token");
                throw new NotAuthorizedException(AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"");
            }
        }
    }


    private void auth(String token, ContainerRequestContext requestContext) {
        try {

            // Validate the token
            final Claims claims = JwtAuthenticator.getInstance().authenticate(token).getBody();
            System.out.println("#### valid token : " + token);


            final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
            final MyPrincipal principal = new MyPrincipal(claims.getSubject(),
                    claims.get("id", Integer.class),
                    claims.get("admin", Boolean.class));
            requestContext.setSecurityContext(new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return principal;
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


        } catch (JwtException e) {
            e.printStackTrace();
            System.err.println("#### invalid token : " + token);
            throw new NotAuthorizedException(AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"");
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


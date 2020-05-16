package me.proggy.proggywebservices;

import io.jsonwebtoken.*;
import me.proggy.proggywebservices.utils.SimpleKeyGenerator;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.proggy.proggywebservices.Role.ADMIN;
import static me.proggy.proggywebservices.Role.CLIENTE;

@Provider
@Secured
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        System.out.println("AuthorizationFilter");

        // Get the resource class which matches with the requested URL
        // Extract the roles declared by it
        Class<?> resourceClass = resourceInfo.getResourceClass();
        List<Role> classRoles = extractRoles(resourceClass);

        // Get the resource method which matches with the requested URL
        // Extract the roles declared by it
        Method resourceMethod = resourceInfo.getResourceMethod();
        List<Role> methodRoles = extractRoles(resourceMethod);

        try {

            // Check if the user is allowed to execute the method
            // The method annotations override the class annotations
            if (methodRoles.isEmpty()) {
                checkPermissions(classRoles, requestContext);
            } else {
                checkPermissions(methodRoles, requestContext);
            }

        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
        }
    }

    // Extract the roles from the annotated element
    private List<Role> extractRoles(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return new ArrayList<Role>();
        } else {
            Secured secured = annotatedElement.getAnnotation(Secured.class);
            if (secured == null) {
                return new ArrayList<Role>();
            } else {
                Role[] allowedRoles = secured.value();
                return Arrays.asList(allowedRoles);
            }
        }
    }

    private void checkPermissions(List<Role> allowedRoles, ContainerRequestContext requestContext) {
        // Check if the user contains one of the allowed roles
        // Throw an Exception if the user has not permission to execute the method


        if (isAuthenticated(requestContext)) {

            // Get the HTTP Authorization header from the request
            String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

            // Extract the token from the HTTP Authorization header
            String token = authorizationHeader.substring("Bearer".length()).trim();

            // Validate the token
            Key key = SimpleKeyGenerator.generateKey();
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
            for (Role r : allowedRoles) {
                if (r == ADMIN && claims.get("admin") == Boolean.FALSE)
                    throw new SecurityException("Non autorizzato");

                if (r == CLIENTE && claims.get("admin") == Boolean.TRUE)
                    throw new SecurityException("Non autorizzato");
            }
        } else throw new SecurityException("Utente non loggato. Unreachable");
    }

    /**
     * Check if the user is authenticated.
     *
     * @param requestContext context
     * @return boolean
     */
    private boolean isAuthenticated(final ContainerRequestContext requestContext) {
        return requestContext.getSecurityContext().getUserPrincipal() != null;
    }

}
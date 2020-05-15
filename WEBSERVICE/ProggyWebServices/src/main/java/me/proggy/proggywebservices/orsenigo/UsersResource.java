/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.proggy.proggywebservices.orsenigo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import me.proggy.proggywebservices.DbManager;
import me.proggy.proggywebservices.utils.KeyGenerator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.security.Key;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

/**
 * REST Web Service
 *
 * @author giaco
 */
@Path("users")
@Transactional
public class UsersResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UserResource
     */
    public UsersResource() {
    }

    @Inject
    private KeyGenerator keyGenerator;

    /**
     * Retrieves representation of an instance of me.proggy.proggywebservices.orsenigo.UserResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response login(@QueryParam("username") String username,
                          @QueryParam("password") String password) {
        try {

            // Authenticate the user using the credentials provided
            authenticate(username, password);

            // Issue a token for the user
            String token = issueToken(username);

            // Return the token on the response
            return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();

        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }
    }

    private void authenticate(String username, String password) throws SQLException {
        final DbManager db = DbManager.getInstance();
        try (PreparedStatement statement = db.getConnection().prepareStatement("SELECT COUNT(*) FROM cliente WHERE username = ? AND password = ?")) {
            statement.setString(1, username);
            statement.setString(1, password);
            try (ResultSet result = statement.executeQuery()) {
                if (result.getInt(1) != 1)
                    throw new SecurityException("Invalid user/password");
            }
        }
    }

    private String issueToken(String user) {
        Key key = keyGenerator.generateKey();
        return Jwts.builder()
                .setSubject(user)
                .setIssuer(context.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


}

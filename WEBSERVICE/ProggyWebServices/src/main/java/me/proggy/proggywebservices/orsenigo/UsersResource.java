/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.proggy.proggywebservices.orsenigo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import me.proggy.proggywebservices.DbManager;
import me.proggy.proggywebservices.utils.SimpleKeyGenerator;

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
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
            // Issue a token for the user
            String token = authenticate(username, password);

            //issueToken(username);

            // Return the token on the response
            return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(UNAUTHORIZED).build();
        }
    }

    private String authenticate(String username, String password) throws SQLException {
        final DbManager db = DbManager.getInstance();
        try (PreparedStatement statement = db.getConnection().prepareStatement("SELECT * FROM admin WHERE username = ? AND password = ?")) {
            statement.setString(1, username);
            statement.setString(2, md5(password));

            try (ResultSet result = statement.executeQuery()) {
                if (result.next())
                    return issueToken(result.getInt("id"), result.getString("username"), true);
                else
                    try (PreparedStatement statement2 = db.getConnection().prepareStatement("SELECT * FROM cliente WHERE username = ? AND password = ?")) {
                        statement2.setString(1, username);
                        statement2.setString(2, md5(password));

                        try (ResultSet result2 = statement2.executeQuery()) {
                            if (result2.next())
                                return issueToken(result2.getInt("id"), result2.getString("username"), false);
                            else
                                throw new SecurityException("Invalid user/password");
                        }
                    }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String issueToken(int id, String user, boolean admin) {
        Key key = SimpleKeyGenerator.generateKey();
        return Jwts.builder()
                .setSubject(user)
                .claim("id", Integer.toString(id))
                .claim("admin", admin)
                .setIssuer(context.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private String md5(String string) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(string.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

}

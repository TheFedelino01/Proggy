/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.proggy.proggywebservices.orsenigo;

import me.proggy.proggywebservices.DbManager;
import me.proggy.proggywebservices.JwtAuthenticator;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

/**
 * REST Web Service
 * Conente l'autenticazione di un utente
 *
 * @author Giacomo Orsenigo
 */
@Path("users")
public class UsersResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UserResource
     */
    public UsersResource() {
    }


    /**
     * Effettua il login con username e password
     * Richiama {@link #authenticate(String, String)}
     *
     * @return token se l'autenticazione è andata a buon fine, {@link Response.Status#UNAUTHORIZED} in caso contrario
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
            return Response
                    .ok()
                    .header(AUTHORIZATION, "Bearer " + token)
                    .cookie(new NewCookie("Biscotto", token))
                    .build();

        } catch (SecurityException e) {
            e.printStackTrace();
            return Response.status(UNAUTHORIZED).build();
        }
    }

    /**
     * Effutta l'autenticazione
     * Controlla che lo username e password specificati appartengano a un cliente o a un amministratore
     *
     * Richiama {@link JwtAuthenticator#issueToken(int, String, boolean, UriInfo)}
     *
     * @param username username
     * @param password password
     * @return token
     * @throws SecurityException nel caso l'autenticazione non sia riuscita
     */
    private String authenticate(String username, String password) {
        final DbManager db = DbManager.getInstance();
        if (!db.isConnected()) {
            System.err.println("DB non connesso");
            throw new InternalServerErrorException("DB non connesso!");
        }
        try (PreparedStatement statement = db.getConnection().prepareStatement("SELECT * FROM admin WHERE username = ? AND password = ?")) {
            statement.setString(1, username);
            statement.setString(2, md5(password));

            try (ResultSet result = statement.executeQuery()) {
                if (result.next())
                    return JwtAuthenticator.getInstance().issueToken(result.getInt("id"), result.getString("username"), true, context);
                else
                    try (PreparedStatement statement2 = db.getConnection().prepareStatement("SELECT * FROM cliente WHERE username = ? AND password = ?")) {
                        statement2.setString(1, username);
                        statement2.setString(2, md5(password));

                        try (ResultSet result2 = statement2.executeQuery()) {
                            if (result2.next())
                                return JwtAuthenticator.getInstance().issueToken(result2.getInt("id"), result2.getString("username"), false, context);
                            else
                                throw new SecurityException("Invalid user/password");
                        }
                    }
            }
        } catch (NoSuchAlgorithmException | SQLException e) {
            e.printStackTrace();
            throw new InternalServerErrorException(e);
        }
    }

    /**
     * Calcola l'md5 di una stringa
     *
     * @param string stringa data
     * @return mdr della stringa
     * @throws NoSuchAlgorithmException in caso l'md5 non venga trovato
     */
    private String md5(String string) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(string.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

}

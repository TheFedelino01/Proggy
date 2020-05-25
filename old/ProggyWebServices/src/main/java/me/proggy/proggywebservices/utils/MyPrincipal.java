package me.proggy.proggywebservices.utils;

import java.security.Principal;

/**
 * Rappresenta un utente
 */
public class MyPrincipal implements Principal {

    final String username;
    final int id;
    final boolean admin;

    public MyPrincipal(String username, int id, boolean admin) {
        this.username = username;
        this.id = id;
        this.admin = admin;
    }

    @Override
    public String getName() {
        return username;
    }

    public int getId() {
        return id;
    }

    public boolean isAdmin() {
        return admin;
    }

    @Override
    public String toString() {
        return "MyPrincipal{" +
                "username='" + username + '\'' +
                ", id=" + id +
                ", admin=" + admin +
                '}';
    }
}

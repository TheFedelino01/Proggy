/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.proggy.proggywebservices;

import proggy.server.main.ServerManager;
import proggy.server.main.main;
import proggy.server.manager;
import proggy.server.utility.ESPManager;

import javax.ws.rs.core.Application;
import java.util.Set;

/**
 * @author giaco
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    public ApplicationConfig() {
        ServerManager serverManager = ServerManager.getINSTANCE();
        serverManager.start();
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(AuthenticationFilter.class);
        resources.add(AuthorizationFilter.class);
        resources.add(CorsFilter.class);
        resources.add(TestDbResource.class);
        resources.add(TestResource.class);
        resources.add(me.proggy.proggywebservices.orsenigo.ContattiResource.class);
        resources.add(me.proggy.proggywebservices.orsenigo.DevicesResource.class);
        resources.add(me.proggy.proggywebservices.orsenigo.UsersResource.class);
        resources.add(me.proggy.proggywebservices.saccani.DevicesAssocia.class);
        resources.add(me.proggy.proggywebservices.saccani.DevicesAttivi.class);
        resources.add(me.proggy.proggywebservices.saccani.DevicesDissocia.class);
        resources.add(me.proggy.proggywebservices.broch.addUtente.class);
        resources.add(me.proggy.proggywebservices.broch.getPosisioni.class);
        resources.add(me.proggy.proggywebservices.broch.salvaPosizione.class);
    }

}

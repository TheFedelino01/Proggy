/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.proggy.proggywebservices;

import javax.ws.rs.core.Application;
import java.util.Set;

/**
 * @author giaco
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

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
        resources.add(me.proggy.proggywebservices.AuthenticationFilter.class);
        resources.add(me.proggy.proggywebservices.AuthorizationFilter.class);
        resources.add(me.proggy.proggywebservices.CorsFilter.class);
        resources.add(me.proggy.proggywebservices.TestDbResource.class);
        resources.add(me.proggy.proggywebservices.TestResource.class);
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

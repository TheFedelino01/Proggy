/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.proggy.proggywebservices;

import me.proggy.proggywebservices.orsenigo.UsersResource;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
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
        resources.add(me.proggy.proggywebservices.TestDbResource.class);
        resources.add(me.proggy.proggywebservices.TestResource.class);
        resources.add(me.proggy.proggywebservices.orsenigo.ContattiResource.class);
        resources.add(me.proggy.proggywebservices.orsenigo.DevicesResource.class);
        resources.add(UsersResource.class);
        resources.add(me.proggy.proggywebservices.saccani.DevicesAssocia.class);
        resources.add(me.proggy.proggywebservices.saccani.DevicesAttivi.class);
        resources.add(me.proggy.proggywebservices.saccani.DevicesDissocia.class);
    }
    
}

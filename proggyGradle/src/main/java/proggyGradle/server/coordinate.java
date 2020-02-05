/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggyGradle.server;

/**
 *
 * @author saccani_federico
 */
public class coordinate {

    private String latitudine;
    private String longitudine;

    public coordinate(String latitudine, String longitudine) {
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    public String getLatitudine() {
        return latitudine;
    }

    public String getLongitudine() {
        return longitudine;
    }
    
    @Override
    public String toString(){
        return latitudine+","+longitudine;
    }

}

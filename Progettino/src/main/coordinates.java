/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author saccani_federico
 */
public class coordinates {
    private List<coordinate> coordinate;
    
    public coordinates(){
        coordinate = new ArrayList<coordinate>();
        
        //TO DO RIMUOVERE QUESTA COORDINATA QUANDO ARDUINO LE PASSA PER DAVVERO
        coordinate.add(new coordinate("45.688233", "9.178756"));
    }
    
    public coordinate getLast(){
        return coordinate.get(coordinate.size()-1);
    }
}

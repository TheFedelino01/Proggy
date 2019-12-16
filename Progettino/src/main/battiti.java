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
public class battiti {
    private List<Integer> battiti;
    
    public battiti(){
        battiti = new ArrayList<Integer>();
        
        add(85);//TODO RIMUOVERE
    }
    
    public void add(int battito){
        battiti.add(battito);
    }
    
    public Integer getLast(){
        return battiti.get(battiti.size()-1);
    }
            
}

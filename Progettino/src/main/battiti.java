/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import static java.lang.Math.random;
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
        
        int casuale = (int)(Math.random()*100);
        
        add(casuale);//TODO RIMUOVERE
    }
    
    public void add(int battito){
        battiti.add(battito);
    }
    
    public Integer getLast(){
        return battiti.get(battiti.size()-1);
    }

    public String getResoconto() {
        String ris="";
        for (int i = 0; i < battiti.size(); i++) {
            ris+=battiti.get(i);
            //Non aggiungo ; all'ultimo
            if(i!=battiti.size()-1)
                ris+=";";
        }
        
        return ris;
    }
            
}

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
        String[] vett = {"45.687840,9.181465","45.687840,9.181465","45.687840,9.181465","45.687840,9.181465","45.687840,9.181465","45.687840,9.181465","45.687840,9.181465","45.687840,9.181465","45.687840,9.181465","45.687840,9.181465","45.687840,9.181465","45.687840,9.181465","45.687840,9.181465","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391"};
        
        for(int i=0; i<vett.length;i++){
            String[] split = vett[i].split(",");
            coordinate.add(new coordinate(split[0], split[1]));
        }
        int x=1;
        //coordinate.add(new coordinate("45.688233", "9.178756"));
    }
    
    private int c=-1;
    public coordinate getLast(){
        //return coordinate.get(coordinate.size()-1); DA SCOMMENTARE
        System.out.println(c);
        if(c+1!=82)
            c++;
        else
            c=0;
        return coordinate.get(c);
        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggyGradle.server;

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
        //String[] vett = {"45.687840,9.181465","45.687840,9.181465","45.687840,9.181465","45.687840,9.181465","45.687840,9.181465","45.687840,9.181465","45.687840,9.181465","45.687840,9.181465","45.687840,9.181465","45.687840,9.181465","45.687840,9.181465","45.687840,9.181465","45.687840,9.181465","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391","45.687805,9.181391"};
        String[] vett = {"45.68728,9.18199","45.68737,9.18207","45.68754,9.18213","45.68756,9.18204","45.68759,9.18196","45.68762,9.18189","45.68765,9.18181","45.68767,9.18173","45.68767,9.18166","45.68771,9.18156","45.68774,9.18148","45.68773,9.18139","45.6877,9.18137","45.68767,9.18135","45.68764,9.18133", "45.68762,9.18132", "45.6876,9.18129", "45.68757,9.18126", "45.68754,9.18124", "45.68751,9.18121", "45.68748,9.1812", "45.68744,9.18118", "45.68743,9.18114", "45.68742,9.1811", "45.68741,9.18106", "45.68741,9.18101", "45.68741,9.18096", "45.68742,9.18088", "45.68745,9.18081", "45.68747,9.18073", "45.6875,9.18064", "45.68754,9.18052", "45.68758,9.18045", "45.68761,9.18038", "45.68762,9.1803", "45.68765,9.18023", "45.6878,9.18031", "45.6879,9.18039", "45.68796,9.18047", "45.68808,9.18039", "45.68806,9.18035", "45.68806,9.18029", "45.68803,9.1803", "45.68805,9.18022", "45.68818,9.18013", "45.68817,9.18007", "45.68812,9.18004", "45.68806,9.18", "45.68804,9.17995", "45.688,9.17994", "45.68796,9.17991", "45.68792,9.17989", "45.68788,9.17988", "45.68783,9.17991", "45.6878,9.17995", "45.68777,9.18", "45.68772,9.18008", "45.6877,9.1801", "45.68766,9.18013", "45.68765,9.18015", "45.68762,9.18017", "45.68759,9.18017", "45.68756,9.18015", "45.68753,9.18014", "45.6875,9.18013", "45.68746,9.1801", "45.68743,9.18009", "45.6874,9.18007", "45.68737,9.18005", "45.68734,9.18002", "45.68731,9.17999", "45.68727,9.17995", "45.68723,9.17991", "45.68719,9.17988", "45.68716,9.17986", "45.68714,9.17984", "45.68714,9.17981", "45.68712,9.17975", "45.68715,9.17965", "45.68726,9.17958", "45.68728,9.17949", "45.68731,9.17941", "45.68734,9.17933", "45.68738,9.17926", "45.6874,9.17917"};
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

    public String getResoconto() {
        String ris="";
        for (int i = 0; i < coordinate.size(); i++) {
            ris+=coordinate.get(i);
            //Non aggiungo ; all'ultimo
            if(i!=coordinate.size()-1)
                ris+=";";
        }
        
        return ris;
    }
    
    public void addCordinate(String cord){
        String[] split = cord.split(",");
        coordinate.add(new coordinate(split[0], split[1]));
    }
}

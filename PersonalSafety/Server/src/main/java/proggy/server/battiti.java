/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggy.server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author saccani_federico
 */
public class battiti {
    private List<Integer> battiti;
    private List<String> orario;

    public battiti() {
        battiti = new ArrayList<Integer>();
        orario = new ArrayList<String>();

        //TODO RIMUOVERE
        for (int i = 0; i < 50; i++) {
            int casuale = (int) (Math.random() * 100);

            add(casuale);
        }
        orario.sort(String::compareTo);
    }

    public void add(int battito) {
        battiti.add(battito);

        //Prendo la data attuale
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        date.setTime(date.getTime() - (int) (Math.random() * 10000000));     //TODO: TOGLIERE QUESTA RIGA QUANDO I BATTITI SARANNO VERI
        orario.add(formatter.format(date));
    }

    public Integer getLastBattito() {
        return battiti.get(battiti.size() - 1);
    }

    public String getLastOra() {
        return orario.get(orario.size() - 1);
    }

    public String getResoconto() {
        String ris = "";
        for (int i = 0; i < battiti.size(); i++) {
            ris += orario.get(i) + "-" + battiti.get(i);
            //Non aggiungo ; all'ultimo
            if (i != battiti.size() - 1)
                ris += ";";
        }

        return ris;
    }

    public String getMedia() {
        String ris = "";
        Integer totale = 0, media = 0;
        Integer maxValue = -1, minValue = battiti.get(0);
        Integer battito;

        //Calcolo la media, il valore MAX e il valore minimo
        for (int i = 0; i < battiti.size(); i++) {
            battito = battiti.get(i);
            totale += battito;
            if (battito > maxValue)
                maxValue = battito;

            if (battito < minValue)
                minValue = battito;
        }

        media = totale / battiti.size();

        return media.toString() + ";" + maxValue.toString() + ";" + minValue.toString();
    }

}

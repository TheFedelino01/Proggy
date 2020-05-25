/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggyGradle.Socket;

import java.util.List;
import proggyGradle.server.device;
import proggyGradle.server.emergencyNumber;
import proggyGradle.server.utility.phone;

/**
 *
 * @author feder
 */
public class thPhone extends Thread {
    private device dispositivo;
    private String tipologia;
            
    public thPhone(device dispositivoInEmergenza, String tipologiaDaChiamare) {
        this.dispositivo=dispositivoInEmergenza;
        tipologia=tipologiaDaChiamare;
    }

    //Effettua la chiamata di emergenza e poi muore il th
    //tipologiaDaChiamare:
    // *  -> chiamo tutti
    // AMICI -> chiamo gli amici
    // etc...
    @Override
    public void run() {
        List<emergencyNumber> lista = dispositivo.getEmergencyNumbers();
        
        for (int i = 0; i < lista.size(); i++) {
            String tipolLocal = lista.get(i).getInfo();
            
            if(tipolLocal.toUpperCase().equals(tipologia.toUpperCase()) || tipolLocal.equals("*"))
                phone.chiama(lista.get(i).getNumero());
            else
                System.out.println("Non chiamo: "+lista.get(i).getNumero() + " perche' tipologia="+tipolLocal);
        }
        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggyGradle.server;

/**
 *
 * @author feder
 */
public class emergencyNumber {
    private String numero;
    private String info;//PARENTI,AMICI,OSPEDALI,SOCCORSI  ... 
    
    public emergencyNumber(String numero, String tipologia){
        this.numero=numero;
        info=tipologia;
    } 

    public String getNumero() {
        return numero;
    }

    public String getInfo() {
        return info;
    }
}

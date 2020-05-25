/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggyGradle.server.utility;

import java.net.URI;
import java.net.URI;
import java.net.URISyntaxException;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;
/**
 *
 * @author feder
 */
public class phone {
    public static final String ACCOUNT_SID = "ACacb63d0a737c0b3478297a958c170dcb";
    public static final String AUTH_TOKEN = "7fa4ed4fa4daf9522095abaf7b54d7f8";
    
    public static void chiama(String numero) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String from = "+12057084073";

        try{
            //TODO RIMUOVERE COMMENTO!!!!!
            Call call = Call.creator(new PhoneNumber(numero), new PhoneNumber(from), new URI("https://handler.twilio.com/twiml/EH75c3841ce8cab3a1bb7c5b16ac94536f")).create();
            
            System.out.println("Chiamato: "+numero /*+" ["+call.getSid()+"]"*/);  
        
        }catch(URISyntaxException e){System.out.println(e.toString());}
    }
}

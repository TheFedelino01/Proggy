package Server;

// Install the Java helper library from twilio.com/docs/libraries/java
import java.net.URI;
import java.net.URISyntaxException;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VoipCall {
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "AC3e19c6b63d4fb8f99c39ef34bc45e73c";
    public static final String AUTH_TOKEN = "bf0c5676add14033dd955269840bb13c";
    public static void chiama() throws URISyntaxException{
        
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String to = "+393703071743";
        String from = "+393459499843";

        Call call = Call.creator(new PhoneNumber(to), new PhoneNumber(from),
                    new URI("http://demo.twilio.com/docs/voice.xml")).create();
        

        System.out.println(call.getSid());
    }
    
}
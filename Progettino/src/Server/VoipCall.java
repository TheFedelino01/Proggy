package Server;

// Install the Java helper library from twilio.com/docs/libraries/java
import java.net.URI;
import java.net.URISyntaxException;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.Client;
import com.twilio.type.PhoneNumber;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VoipCall {
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "AC8f1763f88e1ec828a20ccf527b7d5ec9";
    public static final String AUTH_TOKEN = "ec317b9d5dbf5ef226deca9a611d2303";
    public static void chiama() throws URISyntaxException{
        
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+393703071743"),
                new com.twilio.type.PhoneNumber("+15005550006"),
                "Hi there!")
            .create();

        System.out.println(message.getSid());
    }

    
    
}
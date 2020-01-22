/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
 /*
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import java.io.IOException;
import java.util.List;

public class Telegram {
   
    private long id;
    private int codDispositivo;    
    
    public Telegram(){
        
        codDispositivo=0;
        id=0;
    }

    public void login() throws IOException {
        // Create your bot passing the token received from @BotFather
        final String TOKEN="1002412328:AAErI2IPOMwyZ8bXYWnFxAHKGws1elinSPo";

        //final byte[] audio = Telegram.class.getClassLoader().getResourceAsStream("audio.oga").readAllBytes();

        TelegramBot bot = new TelegramBot(TOKEN);
// Register for updates
        bot.setUpdatesListener(updates -> {
            // ... process updates
            // return id of last processed update or confirm them all
            for (Update update : updates) {
                Message message=update.message();
                System.out.println(message.text());
                id=message.chat().id();
                if(message.text().equals("/login")){
                     bot.execute(new SendMessage(id, "Inserisci il tuo"));//saccani deve parlare su un codice o CON EMAIL RICAVO UN CODICE DAL DB E LO SPARO DENTRO
                }else if(codDispositivo==0){//codice del dispositivo = 0
                    bot.execute(new SendMessage(id, "Registrati prima con /login"));
                }else{
                bot.execute(new SendMessage(id, "Non dovresti essere qui"));
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

    
}

}*/
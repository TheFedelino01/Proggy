package proggyGradle.server.utility;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import proggyGradle.Database.dbManager;

public class Telegram extends Thread {

    private static final String TOKEN = "1002412328:AAErI2IPOMwyZ8bXYWnFxAHKGws1elinSPo";
    private static TelegramBot bot = new TelegramBot(TOKEN);
    private long chatId = 0;
    private boolean isName=false;


    @Override
    public void run() {
        // Create your bot passing the token received from @BotFather
        System.out.println("Server telegram started");
        //final byte[] audio = Telegram.class.getClassLoader().getResourceAsStream("audio.oga").readAllBytes();
// Register for updates
        bot.setUpdatesListener(updates -> {
            // ... process updates
            // chatId of last processed update or confirm them all
            for (Update update : updates) {
                Message message = update.message();
                chatId = message.chat().id();
                if (message.text().equals("/start")) {
                    bot.execute(new SendMessage(chatId, "Inserisci il tuo username:"));
                    isName=true;
                    break;
                } else if(isName) {
                    String nome = update.message().text();
                    String query="SELECT count(*) FROM utenti WHERE username='"+nome+"';";
                    String ack=dbManager.getIstance().getFromDb(query);
                    if(ack.equals("OK")){
                        String query2="UPDATE `utenti` SET `idTelegram` = '"+ chatId+"' WHERE `utenti`.`username` = '"+nome+"';";
                        dbManager.getIstance().writeOnDb(query2);
                        bot.execute(new SendMessage(chatId, "Ti sei registrato correttamente"));
                    }
                        //@AleColo controllo se username esiste e in tal caso ASSOCIO ID CHAT A USERNAME
                    isName=false;
                }else{
                        bot.execute(new SendMessage(chatId, "/start se vuoi registrarti"));

                    }
                }

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

    }

    public static void emergenza(long chatId) {
        bot.execute(new SendMessage(chatId, "EMERGENZA!"));
    }

}

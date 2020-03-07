package proggyGradle.server.utility;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import proggyGradle.Database.DbManager;
import proggyGradle.Database.DbManagerRemote;

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
                    String nome = message.text();
                    String query="SELECT count(*) FROM utenti WHERE username='"+nome+"';";
                    String ack= DbManagerRemote.getInstance().getFromDb(query);
                    if(ack.equals("[{\"count(*)\":\"1\"}]")){
                        bot.execute(new SendMessage(chatId, "Benvenuto: "+nome));
                        String query2="UPDATE utenti SET idTelegram = '"+chatId+"' WHERE username = '"+nome+"'";
                        String ris = DbManagerRemote.getInstance().writeOnDb(query2);
                        if(ris.equals("1")) {
                            bot.execute(new SendMessage(chatId, "Ti sei registrato correttamente. Id chat:" + chatId));
                        }else{
                            bot.execute(new SendMessage(chatId, "Errore! Non sono riuscito ad associare questa Chat al tuo username sul DataBase."));
                        }
                    }else{
                        bot.execute(new SendMessage(chatId, "Username non esiste"));
                    }

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

    public static void scrivi(String msg,long identificativoChat) {
        bot.execute(new SendMessage(identificativoChat, msg));
    }


}

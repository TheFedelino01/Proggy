package main;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class Telegram extends Thread {

    private static final String TOKEN = "1002412328:AAErI2IPOMwyZ8bXYWnFxAHKGws1elinSPo";
    private static TelegramBot bot = new TelegramBot(TOKEN);
    private long chatId = 0;

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
                System.out.println(message.chat().id());
                chatId = message.chat().id();
                if (message.text().equals("/start")) {
                    bot.execute(new SendMessage(chatId, "Inserisci il tuo"));//saccani deve parlare su un codice o CON EMAIL RICAVO UN CODICE DAL DB E LO SPARO DENTRO
                } else {
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

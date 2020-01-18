/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

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

    public static void send() throws IOException {
        // Create your bot passing the token received from @BotFather
        String TOKEN="1002412328:AAErI2IPOMwyZ8bXYWnFxAHKGws1elinSPo";

        //final byte[] audio = Telegram.class.getClassLoader().getResourceAsStream("audio.oga").readAllBytes();

        TelegramBot bot = new TelegramBot(TOKEN);
// Register for updates
        bot.setUpdatesListener(updates -> {
            // ... process updates
            // return id of last processed update or confirm them all
            for (Update update : updates) {
                final long id = update.message().chat().id();
                bot.execute(new SendMessage(id, "AAAAAHAH"));
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

    }
}


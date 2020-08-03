package com.hutkovich.shakealert.logic.messaging.impl;

import com.hutkovich.shakealert.logic.messaging.api.IMessenger;
import com.hutkovich.shakealert.logic.messaging.common.Message;
import com.hutkovich.shakealert.model.Shake;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hutkovich.shakealert.util.Constant.TELEGRAM_BOT_TOKEN;
import static com.hutkovich.shakealert.util.Constant.TELEGRAM_BOT_USERNAME;
import static com.hutkovich.shakealert.util.DateUtil.printDate;
import static com.hutkovich.shakealert.util.PropertiesHelper.getInstance;

public class ShakeAlertBot extends TelegramLongPollingBot implements IMessenger {
  static {
    ApiContextInitializer.init();
  }

  private static final String MESSAGE_PATTERN = "Time:%1$35s%nFedotov class:%2$26s%nDepth:%3$34s%nMagnitude:%4$30s";
  private final String token = getInstance().getProperty(TELEGRAM_BOT_TOKEN);
  private final String username = getInstance().getProperty(TELEGRAM_BOT_USERNAME);

  private Map<Long, Chat> subscribers = new HashMap<>();

  public ShakeAlertBot() {
    TelegramBotsApi api = new TelegramBotsApi();
    try {
      api.registerBot(this);
    } catch (TelegramApiRequestException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean send(Message<String, Shake> message) {
    try {
      System.out.println("Subscribers:" + subscribers);
      Shake shake = message.getBody();
      for (Map.Entry<Long, Chat> entry : subscribers.entrySet()) {
        SendLocation sl = new SendLocation();
        sl
            .setChatId(entry.getKey())
            .setLatitude(shake.getLatitude())
            .setLongitude(shake.getLongitude());
        SendMessage sm = new SendMessage();
        sm
            .setChatId(entry.getKey())
            .setText(String.format(MESSAGE_PATTERN,
                printDate(shake.getDateTime()),
                shake.getAmplitudeClass(),
                shake.getDepth(),
                shake.getMagnitude()));
        sendApiMethod(sl);
        sendApiMethod(sm);
      }
    } catch (TelegramApiException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public void onUpdateReceived(Update update) {
    System.out.println(update.getMessage());
    if (update.hasMessage()) {
      subscribers.put(update.getMessage().getChatId(), update.getMessage().getChat());
    }
    SendMessage sm = new SendMessage();
    sm.setChatId(update.getMessage().getChatId());
    sm.setText("You've been added to subscribers list");
    try {
      sendApiMethod(sm);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onUpdatesReceived(List<Update> updates) {
    for (Update upd : updates) {
      onUpdateReceived(upd);
    }
  }

  @Override
  public String getBotUsername() {
    return username;
  }

  @Override
  public String getBotToken() {
    return token;
  }

  @Override
  public void onClosing() {
    System.out.println("Closing bot...");
  }
}

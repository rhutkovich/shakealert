package me.shakealert.logic.messaging.api;

import me.shakealert.logic.messaging.common.Message;
import me.shakealert.model.Shake;

public interface IMessenger {
  boolean send(Message<String, Shake> message);
}

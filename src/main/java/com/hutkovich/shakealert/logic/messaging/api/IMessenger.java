package com.hutkovich.shakealert.logic.messaging.api;

import com.hutkovich.shakealert.logic.messaging.common.Message;
import com.hutkovich.shakealert.model.Shake;

public interface IMessenger {
  boolean send(Message<String, Shake> message);
}

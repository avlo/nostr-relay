package com.prosilion.nostrrelay.service.message;

import jakarta.websocket.Session;
import lombok.extern.java.Log;
import nostr.event.message.CloseMessage;

import java.util.logging.Level;

@Log
public class CloseMessageService<T extends CloseMessage> implements MessageService<CloseMessage> {
  private final CloseMessage closeMessage;

  public CloseMessageService(CloseMessage closeMessage) {
    log.log(Level.INFO, "CLOSE service initiated");
    this.closeMessage = closeMessage;
  }

  @Override
  public CloseMessage processIncoming(String sessionId) {
    log.log(Level.INFO, "processing CLOSE event");
    return new CloseMessage(closeMessage.getSubscriptionId());
  }
}

package com.prosilion.nostrrelay.service;

import lombok.extern.java.Log;
import nostr.event.message.EventMessage;

import java.util.logging.Level;

@Log
public class EventMessageServiceImpl implements MessageService {
  @Override
  public EventMessage getMessage(EventMessage message) {
    log.log(Level.INFO, "EventMessageService getMessage: {0}", message.getEvent());
//          var subId = msg.getSubscriptionId();
    return message;
  }
}
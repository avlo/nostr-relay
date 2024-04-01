package com.prosilion.nostrrelay.util;

import com.prosilion.nostrrelay.service.message.CloseMessageService;
import com.prosilion.nostrrelay.service.message.EventMessageService;
import com.prosilion.nostrrelay.service.message.MessageService;
import com.prosilion.nostrrelay.service.message.ReqMessageService;
import jakarta.websocket.Decoder;
import lombok.extern.java.Log;
import nostr.event.BaseMessage;
import nostr.event.json.codec.BaseMessageDecoder;
import nostr.event.message.CloseMessage;
import nostr.event.message.EventMessage;
import nostr.event.message.ReqMessage;

import java.util.logging.Level;

@Log
public class DecodedMessageMarshaller<T extends BaseMessage> implements Decoder.Text<MessageService<T>> {
  @Override
  public MessageService<T> decode(String s) {
    log.log(Level.INFO, "attempting to decode string: {0}", s);
    BaseMessage message = new BaseMessageDecoder(s).decode();
    switch (message.getCommand()) {
      case "REQ" -> {
        log.log(Level.INFO, "REQ decoded, contents: {0}", message);
        return new ReqMessageService((ReqMessage) message);
      }
      case "EVENT" -> {
        log.log(Level.INFO, "EVENT decoded, contents: {0}", message);
        return new EventMessageService((EventMessage) message);
      }
      case "CLOSE" -> {
        log.log(Level.INFO, "CLOSE decoded, contents: {0}", message);
        return new CloseMessageService((CloseMessage) message);
      }
      default -> throw new AssertionError("Unknown command " + message.getCommand());
    }
  }

  @Override
  public boolean willDecode(String s) {
    return (s != null);
  }
}
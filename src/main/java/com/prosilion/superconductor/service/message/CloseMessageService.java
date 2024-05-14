package com.prosilion.superconductor.service.message;

import com.prosilion.superconductor.pubsub.RemoveSubscriberFilterEvent;
import com.prosilion.superconductor.service.request.NoExistingUserException;
import com.prosilion.superconductor.service.request.SubscriberService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nostr.event.message.CloseMessage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Getter
@Service
public class CloseMessageService<T extends CloseMessage> implements MessageService<T> {
  private final ApplicationEventPublisher publisher;
  private final SubscriberService subscriberService;
  public final String command = "CLOSE";

  public CloseMessageService(
      SubscriberService subscriberService,
      ApplicationEventPublisher publisher) {
    this.publisher = publisher;
    this.subscriberService = subscriberService;
  }

  @Override
  public void processIncoming(T closeMessage, String sessionId) {
    log.info("processing CLOSE event");
    removeSubscriberBySubscriberId(closeMessage.getSubscriptionId());
  }

  public void removeSubscriberBySessionId(String sessionId) {
    List<Long> subscriberBySessionId = subscriberService.removeSubscriberBySessionId(sessionId);
    subscriberBySessionId.forEach(subscriber -> publisher.publishEvent(new RemoveSubscriberFilterEvent(
        subscriber)));
  }

  public void removeSubscriberBySubscriberId(String subscriberId) {
    try {
      publisher.publishEvent(
          new RemoveSubscriberFilterEvent(
              subscriberService.removeSubscriberBySubscriberId(subscriberId)));
    } catch (NoExistingUserException e) {
      log.info("no match to remove for subscriberId [{}]", subscriberId);
    }
  }

//  public void deactivateSubscriberBySessionId(String sessionId) throws NoResultException {
//    subscriberService.deactivateSubscriberBySessionId(sessionId);
//  }
}
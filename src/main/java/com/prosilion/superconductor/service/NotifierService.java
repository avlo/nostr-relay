package com.prosilion.superconductor.service;

import com.prosilion.superconductor.pubsub.AddNostrEvent;
import com.prosilion.superconductor.service.event.RedisEventEntityService;
import lombok.Getter;
import nostr.event.impl.GenericEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Service
public class NotifierService<T extends GenericEvent> {
  private final SubscriberNotifierService<T> subscriberNotifierService;
  private final RedisEventEntityService<T> redisEventEntityService;

  @Autowired
  public NotifierService(SubscriberNotifierService<T> subscriberNotifierService, RedisEventEntityService<T> redisEventEntityService) {
    this.subscriberNotifierService = subscriberNotifierService;
    this.redisEventEntityService = redisEventEntityService;
  }

  public void nostrEventHandler(AddNostrEvent<T> addNostrEvent) {
    redisEventEntityService.updateEventMap(addNostrEvent);
    subscriberNotifierService.nostrEventHandler(addNostrEvent);
  }

  public void subscriptionEventHandler(Long subscriberId) {
    redisEventEntityService.getGottaProperlyDAOImplThisKindEventMap().forEach((kind, eventMap) ->
        eventMap.forEach((eventId, event) ->
            subscriberNotifierService.subscriptionEventHandler(subscriberId, new AddNostrEvent<>(kind, eventId, event))));
  }
}

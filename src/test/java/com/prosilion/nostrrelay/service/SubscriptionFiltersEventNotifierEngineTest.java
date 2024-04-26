package com.prosilion.nostrrelay.service;

import com.prosilion.nostrrelay.pubsub.AddSubscriberFiltersEvent;
import nostr.base.PublicKey;
import nostr.event.BaseEvent;
import nostr.event.Kind;
import nostr.event.impl.Filters;
import nostr.event.list.EventList;
import nostr.event.list.FiltersList;
import nostr.event.list.KindList;
import nostr.event.list.PublicKeyList;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
class SubscriptionFiltersEventNotifierEngineTest {
  public static PublicKey PUB_KEY_TEXTNOTE_1;
  public static String hexPubKey1 = "aaa73464e0688bb3f585f683e57fe1b95e1b47301172ccbe29b30a14ce358c70";
  public static PublicKey PUB_KEY_TEXTNOTE_2;
  public static String hexPubKey2 = "bbb73464e0688bb3f585f683e57fe1b95e1b47301172ccbe29b30a14ce358c70";
  public static final String TEXT_NOTE_EVENT_1 = "TEXT-NOTE-EVENT-11111";
  public static final String TEXT_NOTE_EVENT_2 = "TEXT-NOTE-EVENT-2222";
  @MockBean
  private static ApplicationEventPublisher publisher;
  private static EventNotifierEngine eventNotifierEngine;

  @BeforeAll
  public static void setup() {
    eventNotifierEngine = new EventNotifierEngine(publisher);
    PUB_KEY_TEXTNOTE_1 = new PublicKey(hexPubKey1);
    PUB_KEY_TEXTNOTE_2 = new PublicKey(hexPubKey2);
  }

  @Test
  @Order(1)
  void addSubscriberFilterEvent() {
    final var filtersList = new FiltersList();
    filtersList.add(Filters.builder()
        .events(new EventList(new BaseEvent.ProxyEvent(hexPubKey1)))
        .authors(new PublicKeyList(PUB_KEY_TEXTNOTE_1))
        .kinds(new KindList(Kind.TEXT_NOTE.getValue(), Kind.CLASSIFIED_LISTING.getValue()))
        .referencedEvents(new EventList(new BaseEvent.ProxyEvent(TEXT_NOTE_EVENT_1)))
        .since(1712006760L)
        .until(2712006760L)
        .limit(1)
        .build()
    );
    eventNotifierEngine.addSubscriberFiltersHandler(new AddSubscriberFiltersEvent(1L, filtersList));

    Map<Long, FiltersList> subscribersFiltersMapState = eventNotifierEngine.getSubscribersFiltersMap();
    Assertions.assertEquals(subscribersFiltersMapState.size(), 1);
  }

  @Test
  @Order(2)
  void addTwoSubscriberFiltersEvent() {
    final var filtersList = new FiltersList();
    filtersList.add(Filters.builder()
        .events(new EventList(new BaseEvent.ProxyEvent(hexPubKey2)))
        .authors(new PublicKeyList(PUB_KEY_TEXTNOTE_2))
        .kinds(new KindList(Kind.TEXT_NOTE.getValue(), Kind.CLASSIFIED_LISTING.getValue()))
        .referencedEvents(new EventList(new BaseEvent.ProxyEvent(TEXT_NOTE_EVENT_2)))
        .since(1712006760L)
        .until(2712006760L)
        .limit(1)
        .build()
    );
    eventNotifierEngine.addSubscriberFiltersHandler(new AddSubscriberFiltersEvent(2L, filtersList));

    Map<Long, FiltersList> subscribersFiltersMapState = eventNotifierEngine.getSubscribersFiltersMap();
    Assertions.assertEquals(subscribersFiltersMapState.size(), 2);
  }

  @Test
  @Order(3)
  void addDuplicateSubscriberFiltersEvent() {

  }
}
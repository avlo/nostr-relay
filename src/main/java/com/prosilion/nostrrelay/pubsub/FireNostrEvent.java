package com.prosilion.nostrrelay.pubsub;

import nostr.event.impl.GenericEvent;

public record FireNostrEvent<T extends GenericEvent>(Long subscriberId, T event) {
}
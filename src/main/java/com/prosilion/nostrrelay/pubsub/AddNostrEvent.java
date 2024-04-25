package com.prosilion.nostrrelay.pubsub;

import nostr.event.Kind;
import nostr.event.impl.GenericEvent;

public record AddNostrEvent<T extends GenericEvent>(
    Long id,
    T event,
    Kind kind
) {}

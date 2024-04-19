package com.prosilion.nostrrelay.service.event;

import lombok.extern.java.Log;
import nostr.event.Kind;
import nostr.event.impl.GenericEvent;
import nostr.event.impl.TextNoteEvent;
import nostr.event.message.EventMessage;
import org.springframework.stereotype.Service;

@Log
@Service
public class TextNoteEventService<T extends EventMessage> implements EventServiceIF<T> {

	EventService<T, TextNoteEvent> eventService;

	public TextNoteEventService(EventService<T, TextNoteEvent> eventService) {
		this.eventService = eventService;
	}

	@Override
	public void processIncoming(T eventMessage) {
		GenericEvent event = (GenericEvent) eventMessage.getEvent();
		event.setNip(1);
		event.setKind(Kind.TEXT_NOTE.getValue());
		TextNoteEvent textNoteEvent = new TextNoteEvent(
				event.getPubKey(),
				event.getTags(),
				event.getContent()
		);
		Long id = eventService.saveEventEntity(event);
		textNoteEvent.setId(event.getId());
		eventService.publishEvent(id, textNoteEvent);
	}
}

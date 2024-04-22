package com.prosilion.nostrrelay.service.message;

import com.prosilion.nostrrelay.service.request.ReqService;
import lombok.extern.java.Log;
import nostr.event.message.ReqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log
@Service
public class ReqMessageService<T extends ReqMessage> {
	private final ReqService<T> reqService;

	@Autowired
	public ReqMessageService(ReqService<T> reqService) {
		this.reqService = reqService;
	}

	public void processIncoming(T reqMessage, String sessionId) {
		reqService.processIncoming(reqMessage, sessionId);
	}
}
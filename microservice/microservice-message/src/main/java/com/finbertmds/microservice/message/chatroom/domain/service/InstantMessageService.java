package com.finbertmds.microservice.message.chatroom.domain.service;

import java.util.List;

import com.finbertmds.microservice.message.entity.InstantMessage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
public interface InstantMessageService {
	
	void appendInstantMessageToConversations(InstantMessage instantMessage);
	List<InstantMessage> findAllInstantMessagesFor(String username, String chatRoomId);
	Slice<InstantMessage> findInstantMessagesFor(String username, String chatRoomId, Pageable pageable);
}

package com.finbertmds.microservice.message.chatroom.domain.service;

import java.util.List;

import com.finbertmds.microservice.message.chatroom.domain.model.ChatRoom;
import com.finbertmds.microservice.message.chatroom.domain.model.InstantMessage;
import com.finbertmds.microservice.message.chatroom.domain.repository.InstantMessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CassandraInstantMessageService implements InstantMessageService {

	@Autowired
	private InstantMessageRepository instantMessageRepository;
	
	@Autowired
	private ChatRoomService chatRoomService;
	
	@Override
	public void appendInstantMessageToConversations(InstantMessage instantMessage) {
		if (instantMessage.isFromAdmin() || instantMessage.isPublic()) {
			ChatRoom chatRoom = chatRoomService.findById(instantMessage.getChatRoomId());
			
			chatRoom.getConnectedUsers().forEach(connectedUser -> {
				instantMessage.setUsername(connectedUser.getUsername());
				instantMessageRepository.save(instantMessage);
			});
		} else {
			instantMessage.setUsername(instantMessage.getFromUser());
			instantMessageRepository.save(instantMessage);
			
			instantMessage.setUsername(instantMessage.getToUser());
			instantMessageRepository.save(instantMessage);
		}
	}

	@Override
	public List<InstantMessage> findAllInstantMessagesFor(String username, String chatRoomId) {
		return instantMessageRepository.findInstantMessagesByUsernameAndChatRoomId(username, chatRoomId);
	}
}

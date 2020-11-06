package com.finbertmds.microservice.message.chatroom.domain.service;

import java.util.List;

import com.finbertmds.microservice.message.chatroom.domain.model.ChatRoom;
import com.finbertmds.microservice.message.chatroom.domain.repository.InstantMessageRepository;
import com.finbertmds.microservice.message.entity.InstantMessage;
import com.finbertmds.microservice.message.entity.InstantMessageKey;

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
			ChatRoom chatRoom = chatRoomService.findById(instantMessage.getInstantMessageKey().getChatRoomId());
			
			chatRoom.getConnectedUsers().forEach(connectedUser -> {
				InstantMessageKey instantMessageKey = instantMessage.getInstantMessageKey();
				instantMessageKey.setUsername(connectedUser.getUsername());
				instantMessage.setInstantMessageKey(instantMessageKey);
				instantMessageRepository.save(instantMessage);
			});
		} else {
			InstantMessageKey instantMessageKey = instantMessage.getInstantMessageKey();
			instantMessageKey.setUsername(instantMessage.getFromUser());
			instantMessage.setInstantMessageKey(instantMessageKey);
			instantMessageRepository.save(instantMessage);
			
			instantMessageKey.setUsername(instantMessage.getToUser());
			instantMessage.setInstantMessageKey(instantMessageKey);
			instantMessageRepository.save(instantMessage);
		}
	}

	@Override
	public List<InstantMessage> findAllInstantMessagesFor(String username, String chatRoomId) {
		return instantMessageRepository.findByInstantMessageKeyUsernameAndInstantMessageKeyChatRoomId(username, chatRoomId);
	}
}

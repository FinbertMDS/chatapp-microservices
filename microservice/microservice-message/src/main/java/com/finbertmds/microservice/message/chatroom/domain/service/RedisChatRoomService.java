package com.finbertmds.microservice.message.chatroom.domain.service;

import java.util.List;

import com.finbertmds.microservice.message.chat.model.MessageResponse;
import com.finbertmds.microservice.message.chatroom.domain.model.ChatRoom;
import com.finbertmds.microservice.message.chatroom.domain.model.ChatRoomUser;
import com.finbertmds.microservice.message.chatroom.domain.repository.ChatRoomRepository;
import com.finbertmds.microservice.message.entity.InstantMessage;
import com.finbertmds.microservice.message.utils.Destinations;
import com.finbertmds.microservice.message.utils.SystemMessages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisChatRoomService implements ChatRoomService {
	private final Logger log = LoggerFactory.getLogger(RedisChatRoomService.class);

	@Autowired
	private SimpMessagingTemplate webSocketMessagingTemplate;

	@Autowired
	private ChatRoomRepository chatRoomRepository;

	@Autowired
	private InstantMessageService instantMessageService;

	@Override
	public ChatRoom save(ChatRoom chatRoom) {
		return chatRoomRepository.save(chatRoom);
	}

	@Override
	public ChatRoom findById(String chatRoomId) {
		return chatRoomRepository.findById(chatRoomId)
				.orElseThrow(() -> new RuntimeException("Error: Chat room is not found."));
	}

	@Override
	public ChatRoom join(ChatRoomUser joiningUser, ChatRoom chatRoom) {
		chatRoom.addUser(joiningUser);
		chatRoomRepository.save(chatRoom);

		sendPublicMessage(SystemMessages.welcome(chatRoom.getId(), joiningUser.getUsername()));
		updateConnectedUsersViaWebSocket(chatRoom);
		return chatRoom;
	}

	@Override
	public ChatRoom leave(ChatRoomUser leavingUser, ChatRoom chatRoom) {
		sendPublicMessage(SystemMessages.goodbye(chatRoom.getId(), leavingUser.getUsername()));

		chatRoom.removeUser(leavingUser);
		chatRoomRepository.save(chatRoom);

		updateConnectedUsersViaWebSocket(chatRoom);
		return chatRoom;
	}

	@Override
	public void sendPublicMessage(InstantMessage instantMessage) {
		webSocketMessagingTemplate.convertAndSend(
				Destinations.ChatRoom.publicMessages(instantMessage.getInstantMessageKey().getChatRoomId()),
				new MessageResponse(instantMessage));
		sendPublicMessageToAllUser(instantMessage);
		instantMessageService.appendInstantMessageToConversations(instantMessage);
	}

	private void sendPublicMessageToAllUser(InstantMessage instantMessage) {
		ChatRoom chatRoom = findById(instantMessage.getInstantMessageKey().getChatRoomId());
		if (!chatRoom.getConnectedUsers().isEmpty()) {
			for (ChatRoomUser chatRoomUser : chatRoom.getConnectedUsers()) {
				if (!instantMessage.getFromUser().equals(chatRoomUser.getUsername())) {
					MessageResponse messageResponse = new MessageResponse(instantMessage);
					messageResponse.setChatRoomName(chatRoom.getName());
					webSocketMessagingTemplate.convertAndSendToUser(chatRoomUser.getUsername(),
							Destinations.ChatRoom.replyMessages(),
							messageResponse);
				}
			}
		}
	}

	@Override
	public void sendPrivateMessage(InstantMessage instantMessage) {
		webSocketMessagingTemplate.convertAndSendToUser(instantMessage.getToUser(),
				Destinations.ChatRoom.privateMessages(instantMessage.getInstantMessageKey().getChatRoomId()),
				new MessageResponse(instantMessage));

		webSocketMessagingTemplate.convertAndSendToUser(instantMessage.getFromUser(),
				Destinations.ChatRoom.privateMessages(instantMessage.getInstantMessageKey().getChatRoomId()),
				new MessageResponse(instantMessage));

		instantMessageService.appendInstantMessageToConversations(instantMessage);
	}

	@Override
	public List<ChatRoom> findAll() {
		return (List<ChatRoom>) chatRoomRepository.findAll();
	}

	private void updateConnectedUsersViaWebSocket(ChatRoom chatRoom) {
		webSocketMessagingTemplate.convertAndSend(Destinations.ChatRoom.connectedUsers(chatRoom.getId()),
				chatRoom.getConnectedUsers());
	}
}

package com.finbertmds.microservice.message.chatroom.domain.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.finbertmds.microservice.message.chat.model.MessageResponse;
import com.finbertmds.microservice.message.chatroom.domain.model.ChatRoom;
import com.finbertmds.microservice.message.chatroom.domain.model.ChatRoomUser;
import com.finbertmds.microservice.message.chatroom.domain.model.LastMessage;
import com.finbertmds.microservice.message.chatroom.domain.repository.ChatRoomRepository;
import com.finbertmds.microservice.message.entity.InstantMessage;
import com.finbertmds.microservice.message.services.AndroidPushNotificationsService;
import com.finbertmds.microservice.message.utils.Destinations;
import com.finbertmds.microservice.message.utils.SystemMessages;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
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

	@Autowired
	AndroidPushNotificationsService androidPushNotificationsService;

	@Value("${finbertmds.app.firebaseNotificationTopic}")
	private String FIREBASE_NOTIFICATION_TOPIC_PREFIX;

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
		LastMessage lastMessage = new LastMessage(instantMessage.getFromUser(), instantMessage.getText(),
				instantMessage.getInstantMessageKey().getDate());
		chatRoom.setLastMessage(lastMessage);
		save(chatRoom);
		if (!chatRoom.getConnectedUsers().isEmpty()) {
			for (ChatRoomUser chatRoomUser : chatRoom.getConnectedUsers()) {
				if (!instantMessage.getFromUser().equals(chatRoomUser.getUsername())) {
					MessageResponse messageResponseWS = new MessageResponse(instantMessage);
					messageResponseWS.setChatRoomName(chatRoom.getName());
					webSocketMessagingTemplate.convertAndSendToUser(chatRoomUser.getUsername(),
							Destinations.ChatRoom.replyMessages(), messageResponseWS);

					// MessageResponse messageResponseFCM = new MessageResponse(instantMessage);
					// messageResponseFCM.setChatRoomName(chatRoom.getName());
					// messageResponseFCM.setToUser(chatRoomUser.getUsername());
					// pushNotificationToAndroid(messageResponseFCM);
				}
			}
		}
	}

	private String getTitleNotification(MessageResponse messageResponse) {
		return "[" + messageResponse.getChatRoomName() + "] " + messageResponse.getFromUser();
	}

	private void pushNotificationToAndroid(MessageResponse messageResponse) {
		JSONObject body = new JSONObject();
		body.put("to", "/topics/" + FIREBASE_NOTIFICATION_TOPIC_PREFIX + "_" + messageResponse.getToUser() + "_reply");
		body.put("priority", "high");

		JSONObject notification = new JSONObject();
		notification.put("title", getTitleNotification(messageResponse));
		notification.put("body", messageResponse.getText());

		JSONObject data = new JSONObject(messageResponse);

		body.put("notification", notification);
		body.put("data", data);

		HttpEntity<String> request = new HttpEntity<>(body.toString());

		CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
		CompletableFuture.allOf(pushNotification).join();
		try {
			String firebaseResponse = pushNotification.get();
			log.info("Sent message to firebase {}", firebaseResponse);
		} catch (InterruptedException e) {
			log.error("Error: Sent message to firebase {}", e.getMessage());
		} catch (ExecutionException e) {
			log.error("Error: Sent message to firebase {}", e.getMessage());
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

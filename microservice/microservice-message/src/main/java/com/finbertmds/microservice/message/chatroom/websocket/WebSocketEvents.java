package com.finbertmds.microservice.message.chatroom.websocket;

import com.finbertmds.microservice.message.chatroom.domain.service.ChatRoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebSocketEvents {

	@Autowired
	private ChatRoomService chatRoomService;
	
	// @EventListener
	// private void handleSessionConnected(SessionConnectEvent event) {
	// 	SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
	// 	String chatRoomId = headers.getNativeHeader("chatRoomId").get(0);
	// 	headers.getSessionAttributes().put("chatRoomId", chatRoomId);
	// 	ChatRoomUser joiningUser = new ChatRoomUser(event.getUser().getName());
		
	// 	chatRoomService.join(joiningUser, chatRoomService.findById(chatRoomId));
	// }

	// @EventListener
	// private void handleSessionDisconnect(SessionDisconnectEvent event) {
	// 	SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
	// 	String chatRoomId = headers.getSessionAttributes().get("chatRoomId").toString();
	// 	ChatRoomUser leavingUser = new ChatRoomUser(event.getUser().getName());

	// 	chatRoomService.leave(leavingUser, chatRoomService.findById(chatRoomId));
	// }
}

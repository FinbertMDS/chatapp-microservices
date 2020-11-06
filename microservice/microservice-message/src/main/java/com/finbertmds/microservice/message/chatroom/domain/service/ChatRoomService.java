package com.finbertmds.microservice.message.chatroom.domain.service;

import java.util.List;

import com.finbertmds.microservice.message.chatroom.domain.model.ChatRoom;
import com.finbertmds.microservice.message.chatroom.domain.model.ChatRoomUser;
import com.finbertmds.microservice.message.entity.InstantMessage;

public interface ChatRoomService {
	
	ChatRoom save(ChatRoom chatRoom);
	ChatRoom findById(String chatRoomId);
	ChatRoom join(ChatRoomUser joiningUser, ChatRoom chatRoom);
	ChatRoom leave(ChatRoomUser leavingUser, ChatRoom chatRoom);
	void sendPublicMessage(InstantMessage instantMessage);
	void sendPrivateMessage(InstantMessage instantMessage);
	List<ChatRoom> findAll();
}

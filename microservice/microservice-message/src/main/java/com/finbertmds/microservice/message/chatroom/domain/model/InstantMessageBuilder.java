package com.finbertmds.microservice.message.chatroom.domain.model;

import com.finbertmds.microservice.message.entity.InstantMessage;
import com.finbertmds.microservice.message.entity.InstantMessageKey;
import com.finbertmds.microservice.message.utils.SystemUsers;

public class InstantMessageBuilder {
	
	private InstantMessage instantMessage;
	private InstantMessageChatRoom instantMessageChatRoom;
	private InstantMessageType instantMessageType;
	private InstantMessageText instantMessageText;
	private InstantMessageFromUser instantMessageFromUser;
	private InstantMessageToUser instantMessageToUser;
	
	public InstantMessageBuilder() {
		
	}
	
	public InstantMessageChatRoom newMessage() {
		instantMessage = new InstantMessage();
		InstantMessageKey instantMessageKey = new InstantMessageKey();
		instantMessage.setInstantMessageKey(instantMessageKey);
		instantMessageChatRoom = new InstantMessageChatRoom();
		return instantMessageChatRoom;
	}
	
	public class InstantMessageChatRoom {
		public InstantMessageType withChatRoomId(String chatRoomId) {
			InstantMessageKey instantMessageKey = instantMessage.getInstantMessageKey();
			instantMessageKey.setChatRoomId(chatRoomId);
			instantMessage.setInstantMessageKey(instantMessageKey);
			instantMessageType = new InstantMessageType();
			return instantMessageType;
		}
	}
	
	public class InstantMessageType {
		public InstantMessageText systemMessage(){
			instantMessage.setFromUser(SystemUsers.ADMIN.getUsername());
			instantMessage.setIsNotification(true);
			instantMessageText = new InstantMessageText();
			return instantMessageText;
		}
		
		public InstantMessageFromUser publicMessage() {
			instantMessage.setToUser(null);
			instantMessageFromUser = new InstantMessageFromUser();
			return instantMessageFromUser;
		}
		
		public InstantMessageToUser privateMessage(){
			instantMessageToUser = new InstantMessageToUser();
			return instantMessageToUser;
		}
	}
	
	public class InstantMessageToUser {
		public InstantMessageFromUser toUser(String username) {
			instantMessage.setToUser(username);
			instantMessageFromUser = new InstantMessageFromUser();
			return instantMessageFromUser;
		}
	}
	
	public class InstantMessageFromUser {
		public InstantMessageText fromUser(String username) {
			instantMessage.setFromUser(username);
			instantMessageText = new InstantMessageText();
			return instantMessageText;
		}
	}
	
	public class InstantMessageText {
		public InstantMessage withText(String text) {
			instantMessage.setText(text);
			return instantMessage;
		}
	}
}

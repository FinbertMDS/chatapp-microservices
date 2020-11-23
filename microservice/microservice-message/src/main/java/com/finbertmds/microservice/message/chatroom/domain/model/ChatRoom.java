package com.finbertmds.microservice.message.chatroom.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("chatrooms")
public class ChatRoom {
	
	@Id
	private String id;
	private String name;
	private String description;
	private LastMessage lastMessage;
	private Date updatedAt;
	private List<ChatRoomUser> connectedUsers = new ArrayList<>();
	
	public ChatRoom() {
		this.updatedAt = new Date();
	}
	
	@VisibleForTesting
	public ChatRoom(String id, String name, String description, LastMessage lastMessage, Date updatedAt, List<ChatRoomUser> connectedUsers) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.lastMessage = lastMessage;
		this.updatedAt = updatedAt;
		this.connectedUsers = connectedUsers;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<ChatRoomUser> getConnectedUsers() {
		return connectedUsers;
	}
	public void addUser(ChatRoomUser user) {
		this.connectedUsers.add(user);
	}
	public void removeUser(ChatRoomUser user) {
		this.connectedUsers.remove(user);
	}
	public int getNumberOfConnectedUsers(){
		return this.connectedUsers.size();
	}

	public LastMessage getLastMessage() {
		return this.lastMessage;
	}

	public void setLastMessage(LastMessage lastMessage) {
		this.lastMessage = lastMessage;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
}

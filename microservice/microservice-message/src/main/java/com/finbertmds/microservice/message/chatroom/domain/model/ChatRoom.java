package com.finbertmds.microservice.message.chatroom.domain.model;

import java.util.ArrayList;
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
	private List<ChatRoomUser> connectedUsers = new ArrayList<>();
	
	public ChatRoom() {
	
	}
	
	@VisibleForTesting
	public ChatRoom(String id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
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

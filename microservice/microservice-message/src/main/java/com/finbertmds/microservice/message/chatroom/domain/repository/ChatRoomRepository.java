package com.finbertmds.microservice.message.chatroom.domain.repository;

import com.finbertmds.microservice.message.chatroom.domain.model.ChatRoom;

import org.springframework.data.repository.CrudRepository;

public interface ChatRoomRepository extends CrudRepository<ChatRoom, String> {

}

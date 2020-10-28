package com.finbertmds.microservice.message.chatroom.domain.repository;

import java.util.List;

import com.finbertmds.microservice.message.chatroom.domain.model.InstantMessage;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface InstantMessageRepository extends CassandraRepository<InstantMessage> {
	
	List<InstantMessage> findInstantMessagesByUsernameAndChatRoomId(String username, String chatRoomId);
}

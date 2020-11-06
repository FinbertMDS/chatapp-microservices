package com.finbertmds.microservice.message.chatroom.domain.repository;

import java.util.List;

import com.finbertmds.microservice.message.entity.InstantMessage;
import com.finbertmds.microservice.message.entity.InstantMessageKey;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface InstantMessageRepository extends CassandraRepository<InstantMessage, InstantMessageKey> {
	
	List<InstantMessage> findByInstantMessageKeyUsernameAndInstantMessageKeyChatRoomId(String username, String chatRoomId);
}

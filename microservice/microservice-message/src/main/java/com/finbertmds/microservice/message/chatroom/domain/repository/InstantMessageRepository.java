package com.finbertmds.microservice.message.chatroom.domain.repository;

import java.util.List;

import com.finbertmds.microservice.message.entity.InstantMessage;
import com.finbertmds.microservice.message.entity.InstantMessageKey;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
public interface InstantMessageRepository extends CassandraRepository<InstantMessage, InstantMessageKey> {
	
	List<InstantMessage> findByInstantMessageKeyUsernameAndInstantMessageKeyChatRoomId(String username, String chatRoomId);
	Slice<InstantMessage> findByInstantMessageKeyUsernameAndInstantMessageKeyChatRoomId(String username, String chatRoomId, Pageable pageable);
}

package com.finbertmds.microservice.message.repository;

import com.finbertmds.microservice.message.model.Message;

import org.springframework.data.cassandra.repository.CassandraRepository;


public interface MessageRepository extends CassandraRepository<Message, String> {
}

package com.finbertmds.microservice.message.repository;

import java.util.UUID;

import com.finbertmds.microservice.message.model.User;

import org.springframework.data.cassandra.repository.CassandraRepository;


public interface UserRepository extends CassandraRepository<User, UUID> {
}

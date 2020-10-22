package com.finbertmds.microservice.message.repository;

import java.util.UUID;

import com.finbertmds.microservice.message.model.Room;

import org.springframework.data.cassandra.repository.CassandraRepository;


public interface RoomRepository extends CassandraRepository<Room, UUID> {
}

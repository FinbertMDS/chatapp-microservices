package com.finbertmds.microservice.message.authentication.domain.repository;

import com.finbertmds.microservice.message.authentication.domain.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}

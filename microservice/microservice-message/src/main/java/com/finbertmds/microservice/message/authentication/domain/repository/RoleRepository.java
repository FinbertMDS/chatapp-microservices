package com.finbertmds.microservice.message.authentication.domain.repository;

import com.finbertmds.microservice.message.authentication.domain.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	Role findByName(String name);
}

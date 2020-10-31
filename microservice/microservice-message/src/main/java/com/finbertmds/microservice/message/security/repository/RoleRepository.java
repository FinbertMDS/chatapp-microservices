package com.finbertmds.microservice.message.security.repository;

import java.util.Optional;

import com.finbertmds.microservice.message.security.model.ERole;
import com.finbertmds.microservice.message.security.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}

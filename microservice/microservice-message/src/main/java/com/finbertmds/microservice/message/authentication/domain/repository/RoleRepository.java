package com.finbertmds.microservice.message.authentication.domain.repository;

import java.util.Optional;

import com.finbertmds.microservice.message.authentication.domain.model.ERole;
import com.finbertmds.microservice.message.authentication.domain.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}

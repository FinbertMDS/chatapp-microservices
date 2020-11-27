package com.finbertmds.microservice.contact.security.repository;

import java.util.Optional;

import com.finbertmds.microservice.contact.security.model.ERole;
import com.finbertmds.microservice.contact.security.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}

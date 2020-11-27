package com.finbertmds.microservice.contact.repository;

import java.util.Optional;

import com.finbertmds.microservice.contact.models.ContactUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContactUserRepository extends JpaRepository<ContactUser, Long> {
	Optional<ContactUser> findByUsername(String username);

	Optional<ContactUser> findByEmail(String email);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}

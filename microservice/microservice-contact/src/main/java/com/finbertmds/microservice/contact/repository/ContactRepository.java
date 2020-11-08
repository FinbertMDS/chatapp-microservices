package com.finbertmds.microservice.contact.repository;

import java.util.Optional;

import com.finbertmds.microservice.contact.models.Contact;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContactRepository extends PagingAndSortingRepository<Contact, Long> {
	Optional<Contact> findByUsername(String username);

	Optional<Contact> findByEmail(String email);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	Page<Contact> findByUsernameContainingOrEmail(String username, String email, Pageable pageable);
}

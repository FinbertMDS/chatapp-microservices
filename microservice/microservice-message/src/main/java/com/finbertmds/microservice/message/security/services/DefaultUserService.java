package com.finbertmds.microservice.message.security.services;

import java.util.HashSet;
import java.util.Set;

import com.finbertmds.microservice.message.security.model.ERole;
import com.finbertmds.microservice.message.security.model.Role;
import com.finbertmds.microservice.message.security.model.User;
import com.finbertmds.microservice.message.security.repository.RoleRepository;
import com.finbertmds.microservice.message.security.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultUserService implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	@Transactional
	public User createUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		Role userRole = roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		Set<Role> roles = new HashSet<>();
		roles.add(userRole);
		user.setRoles(roles);
		return userRepository.save(user);
	}
}

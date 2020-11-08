package com.finbertmds.microservice.security.services;

import java.util.Optional;

import com.finbertmds.microservice.security.models.ERole;
import com.finbertmds.microservice.security.models.Role;
import com.finbertmds.microservice.security.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

  @Autowired
  RoleRepository roleRepository;

  public Optional<Role> findByName(ERole name) {
    return roleRepository.findByName(name);
  }
}

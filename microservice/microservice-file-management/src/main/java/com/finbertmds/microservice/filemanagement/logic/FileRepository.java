package com.finbertmds.microservice.filemanagement.logic;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, String> {
}

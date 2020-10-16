package com.finbertmds.microservice.filestorage.controllers.storage;

import java.nio.file.Path;
import java.util.stream.Stream;

import com.finbertmds.microservice.filestorage.logic.File;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	void init();

	File store(MultipartFile file);

	Stream<Path> loadAll();

	Path load(String fileId, String filename);

	Resource loadAsResource(String fileId, String filename);

	void deleteAll();

}

package com.finbertmds.microservice.filestorage.controllers.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import com.finbertmds.microservice.filestorage.logic.File;
import com.finbertmds.microservice.filestorage.utils.UUIDGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

	private final String location;

	private KafkaTemplate<String, File> kafkaTemplate;

	@Autowired
	public FileSystemStorageService(StorageProperties properties, KafkaTemplate<String, File> kafkaTemplate) {
		super();
		this.rootLocation = Paths.get(properties.getLocation());
		this.location = properties.getLocation();
		this.kafkaTemplate = kafkaTemplate;
	}

	@Override
	public File store(MultipartFile file) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + filename);
			}
			if (filename.contains("..")) {
				// This is a security check
				throw new StorageException("Cannot store file with relative path outside current directory " + filename);
			}
			String fileId = UUIDGenerator.generateType4UUID().toString();
			Files.createDirectories(this.rootLocation.resolve(fileId));
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, this.rootLocation.resolve(fileId + "/" + filename),
						StandardCopyOption.REPLACE_EXISTING);

				String fileDownloadUri = location + "/" + fileId + "/" + filename;
				File fileEventObject = new File(fileId, filename, fileDownloadUri, file.getContentType(),
						file.getSize());
				fireFileUploadedEvent(fileEventObject);

				return fileEventObject;
			}
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + filename, e);
		}
	}

	private void fireFileUploadedEvent(File file) {
		kafkaTemplate.send("file", file.getFileId() + "created", file);
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 2).filter(path -> !path.equals(this.rootLocation))
					.map(this.rootLocation::relativize);
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String fileId, String filename) {
		return rootLocation.resolve(fileId + "/" + filename);
	}

	@Override
	public Resource loadAsResource(String fileId, String filename) {
		try {
			Path file = load(fileId, filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException("Could not read file: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}

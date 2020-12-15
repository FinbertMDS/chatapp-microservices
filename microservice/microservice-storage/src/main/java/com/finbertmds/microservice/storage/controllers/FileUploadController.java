package com.finbertmds.microservice.storage.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.finbertmds.microservice.storage.controllers.storage.StorageFileNotFoundException;
import com.finbertmds.microservice.storage.controllers.storage.StorageService;
import com.finbertmds.microservice.storage.logic.File;
import com.finbertmds.microservice.storage.payload.UploadFileResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FileUploadController {
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	private final StorageService storageService;

	@Value("${spring.application.name}")
	private String appName;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/uploadFile")
	public UploadFileResponse uploadFile(@RequestPart("file") MultipartFile file) {
		File fileObject = storageService.store(file);

		return new UploadFileResponse(fileObject.getFileId(), fileObject.getFileName(), getFileDownloadUri(fileObject),
				file.getContentType(), file.getSize());
	}

	private String getFileDownloadUri(File file) {
		return appName + "/api/downloadFile/" + file.getFileId() + "/" + file.getFileName();
	}

	@PostMapping("/uploadMultipleFiles")
	public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList());
	}

	@GetMapping("/downloadFile/{fileId:.+}/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileId, @PathVariable String fileName,
			HttpServletRequest request) {
		// Load file as Resource
		Resource resource = storageService.loadAsResource(fileId, fileName);

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logger.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}
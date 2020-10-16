package com.finbertmds.microservice.filemanagement.events;

import com.finbertmds.microservice.filemanagement.logic.File;
import com.finbertmds.microservice.filemanagement.logic.FileService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class FileKafkaListener {

	private final Logger log = LoggerFactory.getLogger(FileKafkaListener.class);

	private FileService fileService;

	public FileKafkaListener(FileService fileService) {
		super();
		this.fileService = fileService;
	}

	@KafkaListener(topics = "file")
	public void file(File file, Acknowledgment acknowledgment) {
		log.info("Received file {}", file.getFileId());
		fileService.generateFile(file);
		acknowledgment.acknowledge();
	}

}

package com.finbertmds.microservice.storage.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileService {

	private final Logger log = LoggerFactory.getLogger(FileService.class);

	private FileRepository fileRepository;

	@Autowired
	public FileService(FileRepository fileRepository) {
		super();
		this.fileRepository = fileRepository;
	}

	@Transactional
	public void generateFile(File file) {
		if (fileRepository.existsById(file.getFileId())) {
			log.info("File id {} already exists - ignored", file.getFileId());
		} else {
			fileRepository.save(file);
		}
	}

}

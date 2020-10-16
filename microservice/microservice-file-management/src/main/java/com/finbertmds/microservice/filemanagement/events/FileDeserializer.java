package com.finbertmds.microservice.filemanagement.events;

import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.finbertmds.microservice.filemanagement.logic.File;

public class FileDeserializer extends JsonDeserializer<File> {

	public FileDeserializer() {
		super(File.class);
	}

}

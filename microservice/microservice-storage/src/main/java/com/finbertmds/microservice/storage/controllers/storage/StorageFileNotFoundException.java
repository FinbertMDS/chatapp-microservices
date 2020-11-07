package com.finbertmds.microservice.storage.controllers.storage;

public class StorageFileNotFoundException extends StorageException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1519616828683258940L;

	public StorageFileNotFoundException(String message) {
		super(message);
	}

	public StorageFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}

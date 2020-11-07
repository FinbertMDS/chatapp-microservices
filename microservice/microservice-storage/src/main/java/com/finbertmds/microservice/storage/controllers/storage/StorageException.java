package com.finbertmds.microservice.storage.controllers.storage;

public class StorageException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 8016617700192030499L;

	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}
}

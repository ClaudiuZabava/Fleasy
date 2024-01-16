package com.backend.fleasy.exception;

public class DuplicateUserException extends RuntimeException {

	public DuplicateUserException() {
		super("A user with the same email already exists.");
	}

}

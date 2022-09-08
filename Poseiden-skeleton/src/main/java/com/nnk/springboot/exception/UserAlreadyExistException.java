package com.nnk.springboot.exception;

/**
 * The type User already exist exception.
 */
public class UserAlreadyExistException extends RuntimeException {
    /**
     * Instantiates a new User already exist exception.
     *
     * @param message the message
     */
    public UserAlreadyExistException(String message) {
		super(message);
	}
}

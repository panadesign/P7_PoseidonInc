package com.nnk.springboot.exception;

/**
 * The type Invalid password exception.
 */
public class InvalidPasswordException extends RuntimeException{
    /**
     * Instantiates a new Invalid password exception.
     *
     * @param message the message
     */
    public InvalidPasswordException(String message) {
		super(message);
	}
}

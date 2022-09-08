package com.nnk.springboot.exception;

/**
 * The type Resource exist exception.
 */
public class ResourceExistException extends RuntimeException{
    /**
     * Instantiates a new Resource exist exception.
     *
     * @param message the message
     */
    public ResourceExistException(String message) {
		super(message);
	}
}

package com.nnk.springboot.exception;

/**
 * The type Resource not exist exception.
 */
public class ResourceNotExistException extends RuntimeException{
    /**
     * Instantiates a new Resource not exist exception.
     *
     * @param message the message
     */
    public ResourceNotExistException(String message) {
		super(message);
	}
}

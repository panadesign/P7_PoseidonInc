package com.nnk.springboot.exception;

public class ResourceNotExistException extends RuntimeException{
	public ResourceNotExistException(String message) {
		super(message);
	}
}

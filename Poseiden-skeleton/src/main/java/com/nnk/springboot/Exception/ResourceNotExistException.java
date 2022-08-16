package com.nnk.springboot.Exception;

public class ResourceNotExistException extends RuntimeException{
	public ResourceNotExistException(String message) {
		super(message);
	}
}

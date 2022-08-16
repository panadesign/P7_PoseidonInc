package com.nnk.springboot.exception;

public class ResourceExistException extends RuntimeException{
	public ResourceExistException(String message) {
		super(message);
	}
}

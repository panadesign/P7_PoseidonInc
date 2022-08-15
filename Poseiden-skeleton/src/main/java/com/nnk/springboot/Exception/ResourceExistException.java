package com.nnk.springboot.Exception;

public class ResourceExistException extends RuntimeException{
	public ResourceExistException(String message) {
		super(message);
	}
}

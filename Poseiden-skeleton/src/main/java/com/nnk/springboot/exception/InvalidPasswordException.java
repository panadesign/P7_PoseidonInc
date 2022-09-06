package com.nnk.springboot.exception;

public class InvalidPasswordException extends RuntimeException{
	public InvalidPasswordException(String message) {
		super(message);
	}
}

package com.nnk.springboot.Exception;

public class BidListNotExistException extends RuntimeException{
	public BidListNotExistException(String message) {
		super(message);
	}
}

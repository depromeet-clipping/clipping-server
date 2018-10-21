package com.depromeet.clippingserver.exception;

@SuppressWarnings("serial")
public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
		super("UserNotFoundException");
	}
}

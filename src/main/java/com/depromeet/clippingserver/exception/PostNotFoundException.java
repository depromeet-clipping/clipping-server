package com.depromeet.clippingserver.exception;

@SuppressWarnings("serial")
public class PostNotFoundException extends RuntimeException {

	public PostNotFoundException() {
		super("존재하지 않는 포스트입니다.");
	}
}

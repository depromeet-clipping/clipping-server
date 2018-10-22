package com.depromeet.clippingserver.exception;

@SuppressWarnings("serial")
public class CategoryNotFoundException extends RuntimeException {
	
	public CategoryNotFoundException() {
		super("카테고리가 존재하지 않습니다.");
	}
	
}

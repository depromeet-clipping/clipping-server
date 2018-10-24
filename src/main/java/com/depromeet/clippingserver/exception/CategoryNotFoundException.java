package com.depromeet.clippingserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends RuntimeException {
	
	public CategoryNotFoundException() {
		super("카테고리가 존재하지 않습니다.");
	}
	
}

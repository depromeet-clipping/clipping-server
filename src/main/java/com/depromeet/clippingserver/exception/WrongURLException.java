package com.depromeet.clippingserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongURLException extends RuntimeException {

	public WrongURLException() {
		super("입력한 URL이 무효합니다.");
	}

}

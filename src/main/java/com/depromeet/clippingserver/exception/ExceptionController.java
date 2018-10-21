package com.depromeet.clippingserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ExceptionController {
	
	@ResponseStatus(code=HttpStatus.FORBIDDEN)
	@ExceptionHandler(value=UserNotFoundException.class)
	public String handleUserNotFound(UserNotFoundException e) {
		return e.getMessage();
	}
}

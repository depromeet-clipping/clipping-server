package com.depromeet.clippingserver.exception;

import java.net.MalformedURLException;

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
	@ResponseStatus(code=HttpStatus.NOT_FOUND)
	@ExceptionHandler(value=CategoryNotFoundException.class)
	public String handleCategoryNotFound(CategoryNotFoundException e) {
		return e.getMessage();
	}
	
	@ResponseStatus(code=HttpStatus.NOT_FOUND)
	@ExceptionHandler(value=PostNotFoundException.class)
	public String handleConstraintViolationException(PostNotFoundException e) {
		return e.getMessage();
	}
	
	@ExceptionHandler(value=MalformedURLException.class)
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	public String handleMalformedURLException(MalformedURLException e) {
		return e.getMessage();
	}
}

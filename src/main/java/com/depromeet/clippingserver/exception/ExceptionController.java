package com.depromeet.clippingserver.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RestController
public class ExceptionController {

	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<ErrorDetail> handleUserNotFound(UserNotFoundException e, WebRequest request) {
		HttpStatus status = HttpStatus.FORBIDDEN;
		ErrorDetail body = new ErrorDetail(LocalDateTime.now(), status.value(), e.getMessage(),
				request.getDescription(false));
		return ResponseEntity.status(status).body(body);
	}

	@ExceptionHandler(value = CategoryNotFoundException.class)
	public ResponseEntity<ErrorDetail> handleCategoryNotFound(CategoryNotFoundException e, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorDetail body = new ErrorDetail(LocalDateTime.now(), status.value(), e.getMessage(),
				request.getDescription(false));
		return ResponseEntity.status(status).body(body);
	}

	@ExceptionHandler(value = PostNotFoundException.class)
	public ResponseEntity<ErrorDetail> handleConstraintViolationException(PostNotFoundException e, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorDetail body = new ErrorDetail(LocalDateTime.now(), status.value(), e.getMessage(),
				request.getDescription(false));
		return ResponseEntity.status(status).body(body);
	}
	

	@ExceptionHandler(value = WrongURLException.class)
	public ResponseEntity<ErrorDetail> handleMalformedURLException(WrongURLException e, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ErrorDetail body = new ErrorDetail(LocalDateTime.now(), status.value(), e.getMessage(),
				request.getDescription(false));
		return ResponseEntity.status(status).body(body);
	}
}

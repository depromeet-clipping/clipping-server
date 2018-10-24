package com.depromeet.clippingserver.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorDetail {
	private LocalDateTime timestamp;
	private int status;
	private String message;
	private String details;

}

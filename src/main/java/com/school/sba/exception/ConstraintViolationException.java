package com.school.sba.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ConstraintViolationException extends RuntimeException {
	private HttpStatus status; 
	private String message;
	private String rootCause;

}

package com.school.sba.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UnauthorizedException extends RuntimeException {
	private String message;
	private HttpStatus status;
	private String rootCause;
}

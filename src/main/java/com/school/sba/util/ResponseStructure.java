package com.school.sba.util;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class ResponseStructure<T> {
	private int status;
	private T data;
	private String message;

}

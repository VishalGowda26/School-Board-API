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
//	public ResponseStructure<T> setStatus(int status) {
//		this.status = status;
//		return this;
//	}
//	public ResponseStructure<T> setData(T data) {
//		this.data = data;
//		return this;
//	}
//	public ResponseStructure<T> setMessage(String message) {
//		this.message = message;
//		return this;
//	}
//	
//	

}

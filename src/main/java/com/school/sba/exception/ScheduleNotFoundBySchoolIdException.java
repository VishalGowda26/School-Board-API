package com.school.sba.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ScheduleNotFoundBySchoolIdException extends RuntimeException {
	private String message;

}

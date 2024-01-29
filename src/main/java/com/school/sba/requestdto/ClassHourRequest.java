package com.school.sba.requestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassHourRequest {

	private int userId;
	private int classHourId;
	private int subjectId;
	private int roomNo;
}

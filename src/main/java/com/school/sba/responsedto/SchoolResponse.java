package com.school.sba.responsedto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchoolResponse {
	private int schoolId;
	private String schoolName;
	private Long contactNo;
	private String emailId;
	private String address;

	private List<String> subjectNames;
}

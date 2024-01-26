package com.school.sba.requestdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolRequest {
	private String schoolName;
	private Long contactNo;
	private String emailId;
	private String address;
}

package com.school.sba.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.school.sba.serviceimpl.AcademicProgramServiceImpl;

@Component
public class ScheduledJobs {
	
	@Autowired
	AcademicProgramServiceImpl impl;

	@Scheduled(fixedDelay = 1000l)
	public void test() {
		System.out.println("Test for Scheduled Jobs");
//		System.out.println(impl.isDeleted());
	}

}

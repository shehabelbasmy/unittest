package com.luv2code.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.dao.ApplicationDao;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import com.luv2code.component.service.ApplicationService;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
class MockAnnotationTest {

	@Mock
	private ApplicationDao applicationDao;
	
	@InjectMocks
	private ApplicationService applicationService;
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private CollegeStudent studentOne;
	
	@Autowired
	private StudentGrades grades;
	
	@BeforeEach
	void beforeEach() {
		studentOne.setFirstname("shehab");
		studentOne.setLastname("elbasmy");
		studentOne.setEmailAddress("shehabelbasmy123@gmail.com");
		studentOne.setStudentGrades(grades);
	}
	
	@Test
	@DisplayName("When & verify")
	void assertEqualsTestAndGrades() {
		when(applicationDao.addGradeResultsForSingleClass(grades.getMathGradeResults())).thenReturn(100.00);
		assertEquals(100,applicationService.addGradeResultsForSingleClass(studentOne.getStudentGrades().getMathGradeResults()));
		verify(applicationDao).addGradeResultsForSingleClass(grades.getMathGradeResults());
		verify(applicationDao,times(1)).addGradeResultsForSingleClass(grades.getMathGradeResults());
		
	}
}

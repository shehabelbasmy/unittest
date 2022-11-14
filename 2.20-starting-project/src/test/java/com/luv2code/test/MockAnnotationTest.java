package com.luv2code.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.dao.ApplicationDao;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import com.luv2code.component.service.ApplicationService;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
class MockAnnotationTest {

	@MockBean
	private ApplicationDao applicationDao;
	
	@Autowired
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
	
	@DisplayName("Find Gpa")
	@Test
	void assertEqualTestFindGpa() {
		when(applicationDao.findGradePointAverage(grades.getMathGradeResults())).thenReturn(100.0);
		assertEquals(100.0, applicationService.findGradePointAverage(studentOne.getStudentGrades().getMathGradeResults()));
	}
	
	@DisplayName("Not Null")
	@Test
	void testAssertNotNull() {
		when(applicationDao.checkNull(grades.getMathGradeResults())).thenReturn(true);
		assertNotNull(applicationService.checkNull(studentOne.getStudentGrades().getMathGradeResults()),"Object Shoud not be Null");
		
	}
	@DisplayName("Throw Runtime error")
	@Test
	void throwRuntimeError() {
		CollegeStudent collegeStudent = context.getBean("collegeStudent", CollegeStudent.class);
		doThrow(new RuntimeException()).when(applicationDao).checkNull(collegeStudent);
		assertThrows(RuntimeException.class, ()->applicationService.checkNull(collegeStudent));
		verify(applicationDao,times(1)).checkNull(collegeStudent);
	}
	@DisplayName("Multiple Stubbing")
	@Test
	void stubbingConsecutiveCalls() {
		CollegeStudent nullStudent = context.getBean("collegeStudent", CollegeStudent.class);
		when(applicationDao.checkNull(nullStudent))
			.thenThrow(new RuntimeException())
			.thenReturn("Message");
		
		assertThrows(RuntimeException.class, ()->applicationService.checkNull(nullStudent));
		
		assertEquals("Message", applicationService.checkNull(nullStudent));
		
		verify(applicationDao,times(2)).checkNull(nullStudent);
	}
	
	
	
	
	
	
	
	
	
	
	
}

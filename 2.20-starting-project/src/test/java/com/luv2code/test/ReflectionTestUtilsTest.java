package com.luv2code.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
class ReflectionTestUtilsTest {

	@Autowired
	private CollegeStudent student;

	@Autowired
	private StudentGrades grades;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@BeforeEach
	void beforeEach() {
		student.setFirstname("shehab");
		student.setLastname("elbasmy");
		student.setEmailAddress("shehabelbasmy123@gmail.com");
		student.setStudentGrades(grades);
		ReflectionTestUtils.setField(student, "id", 1);
		ReflectionTestUtils.setField(student, "studentGrades", new StudentGrades(List.of(10.0,20.0)));
	}
	
	@Test
	void getPrivateField() {
		assertEquals(1, ReflectionTestUtils.getField(student, "id"));
	}
	
	@Test
	void invokePrivateMethod() {
		assertEquals("shehab 1", ReflectionTestUtils.invokeGetterMethod(student, "getFirstNameAndId"));;
	}
}

package com.luv2code.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.luv2code.component.Application;
import com.luv2code.component.model.CollegeStudent;
import com.luv2code.component.model.StudentGrades;

@SpringBootTest(classes = Application.class)
class ApplicationExampleTest {
	
	private static int count = 0;
	@Value("${info.app.name}")
	private String appInfo;
	@Value("${info.app.description}")
	private String appDescription;
	@Autowired
	private CollegeStudent student;
	@Autowired
	private StudentGrades grades;
	@Autowired
	private ApplicationContext context;
	
	@BeforeEach
	void beforeEach() {
		count += 1;
		System.err.println("Testing: " + appInfo + " Which is " + appDescription + " Version "
				+ ". Execution of test Method " + count);
		student.setFirstName("shehab");
		student.setLastName("elbasmy");
		student.setEmailAddress("shehabelbasmy123@gmail.com");
		grades.setMathGradeResults(List.of(100.0, 200.0));
		student.setStudentGrades(grades);
	}

	@Test
	@DisplayName("add grade result to student grades")
	void addGradeResultsForStudentsGrade() {
		assertEquals(300, grades.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults()));
	}

	@Test
	@DisplayName("add grade result to student grades not equal")
	void addGradeResultsForStudentsGradeAssertNotEqual() {
		assertNotEquals(30, grades.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults()));
	}

	@Test
	@DisplayName("Is Grade Greater")
	void isGradeGreaterStudentGrade() {
		assertTrue(grades.isGradeGreater(90, 75), "failure - shoudl be true");
	}

	@Test
	@DisplayName("Is Grade Greater false")
	void isGradeGreaterStudentGradeAssertFalse() {
		assertFalse(grades.isGradeGreater(40, 75), "failure - shoudl be false");
	}

	@Test
	@DisplayName("check null for student grade")
	void checkNullForStudentGrade() {
		assertNotNull(grades.checkNull(student.getStudentGrades().getMathGradeResults()), "Object Should not be null");
	}

	@Test
	@DisplayName("check for prototype")
	void checkForProtoType() {
		CollegeStudent d=context.getBean("collegeStudent", CollegeStudent.class);
		assertNotSame(d, student);
	}

}

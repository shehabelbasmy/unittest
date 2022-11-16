package com.luv2code.springmvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Streamable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;

@TestPropertySource("/application.properties")
@SpringBootTest
class StudentAndGradeServiceTest {
	
	@Autowired
	private StudentAndGradeService studentAndGradeService;
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@BeforeEach
	void beforeSetup() {
		jdbcTemplate.execute("insert into student(id,firstname,lastname,email_address) "
				+ "values(1,'Shehab','El Basmy','shehabelbasmy@gmail.com')");
	}
	@AfterEach
	void afterEach() {
		jdbcTemplate.execute("delete from student");
	}
	
	@Test
	void createStudentService() {
		studentAndGradeService.createStudent("shehab","elbasmy","shehabelbasmy123@gmail.com");
		
		CollegeStudent collegeStudent = studentDao.findByEmailAddress("shehabelbasmy123@gmail.com");
		
		assertEquals("shehabelbasmy123@gmail.com", collegeStudent.getEmailAddress());
	}
	
	@Test
	void deleteStudentService() {
		Optional<CollegeStudent> studnet = studentDao.findById(1);
		assertTrue(studnet.isPresent());
		studentAndGradeService.deleteStudent(1);
		Optional<CollegeStudent> deletedStudnet = studentDao.findById(1);
		assertFalse(deletedStudnet.isPresent());
	}
	
	@Test
	void isStudentCheckNull() {
		assertTrue(studentAndGradeService.checkIfStudentIsNull(1));
		assertFalse(studentAndGradeService.checkIfStudentIsNull(0));
	}
	
	@Sql("/insertData.sql")
	@Test
	void getGradeBookService() {
		Iterable<CollegeStudent> students= studentAndGradeService.getGradeBook();
		assertEquals(5, Streamable.of(students).toList().size());
	}
}

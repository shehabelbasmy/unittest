package com.luv2code.springmvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.GradebookCollegeStudent;
import com.luv2code.springmvc.repository.MathGradeDao;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;

@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource("/application-test.properties")
class GradeBookContorllerTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MockMvc mockMvc;
	@Mock
	private StudentAndGradeService studentAndGradeServiceMock;
	@Autowired
	private StudentDao studentDao;
	@Value("${sql.scripts.create.student}")
	private String sqlAddStudent;
	@Value("${sql.scripts.create.math.grade}")
	private String sqlAddMathGrade;
	@Value("${sql.scripts.create.science.grade}")
	private String sqlAddscienceGrade;
	@Value("${sql.scripts.create.history.grade}")
	private String sqlAddHistoryGrade;
	@Autowired
	private StudentAndGradeService studentService;

	@Autowired
	private MathGradeDao mathGradeDao;
	@BeforeEach
	void beforeSetup() {
		jdbcTemplate.execute(sqlAddStudent);
		jdbcTemplate.execute(sqlAddMathGrade);
		jdbcTemplate.execute(sqlAddHistoryGrade);
		jdbcTemplate.execute(sqlAddscienceGrade);
	}

	@AfterEach
	void afterEach() {
		jdbcTemplate.execute("delete from student");
		jdbcTemplate.execute("delete from math_grade");
		jdbcTemplate.execute("delete from science_grade");
		jdbcTemplate.execute("delete from history_grade");
	}

	@Test
	void getStudentHttpRequest() throws Exception {
		MvcResult m = mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk()).andReturn();
		ModelAndView modelAndView = m.getModelAndView();
		ModelAndViewAssert.assertViewName(modelAndView, "index");
	}

	@Test
	void createStudentHttpRequest() throws Exception {

		CollegeStudent collegeStudent = new CollegeStudent("ahmed", "mohamed", "ahmed@gmail.com");

		List<CollegeStudent> collegeStudentList = new ArrayList<>(Arrays.asList(collegeStudent));

		when(studentAndGradeServiceMock.getGradeBook()).thenReturn(collegeStudentList);

		assertIterableEquals(collegeStudentList, studentAndGradeServiceMock.getGradeBook());

		MvcResult result = mockMvc
				.perform(post("/").contentType(MediaType.APPLICATION_JSON).param("firstname", "shehab")
						.param("lastname", "elbasmy").param("emailAddress", "emailAddress"))
				.andExpect(status().isOk()).andReturn();
		ModelAndView modelAndView = result.getModelAndView();

		ModelAndViewAssert.assertViewName(modelAndView, "index");

		CollegeStudent student = studentDao.findByEmailAddress("shehabelbasmy@gmail.com");

		assertNotNull(student);
	}

	@Test
	void deleteStudentHttpRequest() throws Exception {
		assertTrue(studentDao.findById(1).isPresent());

		MvcResult result = mockMvc.perform(get("/delete/student/{id}", 1)).andExpect(status().isOk()).andReturn();

		ModelAndView modelAndView = result.getModelAndView();

		ModelAndViewAssert.assertViewName(modelAndView, "index");

		assertFalse(studentDao.findById(1).isPresent());
	}

	@Test
	void deleteStudentHttpReuqetErrorPage() throws Exception {
		MvcResult result = mockMvc.perform(get("/delete/student/{id}", 0)).andExpect(status().isOk()).andReturn();
		ModelAndView modelAndView = result.getModelAndView();
		ModelAndViewAssert.assertViewName(modelAndView, "error");
	}
	
	@Test
	void studentInformation() throws Exception {
		MvcResult result = mockMvc.perform(get("/studentInformation/{1}",1))
		.andExpect(status().isOk()).andReturn();
		
		ModelAndView modelAndView = result.getModelAndView();
		
		ModelAndViewAssert.assertViewName(modelAndView, "studentInformation");
	}
	@Test
	void studentInformationHttpStudentDoesNotExistRequest() throws Exception {
		
		assertFalse(studentDao.findById(0).isPresent());
		MvcResult result = mockMvc.perform(get("/studentInformation/{1}",0))
				.andExpect(status().isOk()).andReturn();
		
		ModelAndView modelAndView = result.getModelAndView();
		
		ModelAndViewAssert.assertViewName(modelAndView, "error");
	}

	@Test
	void createValidGradeHttpRequest() throws Exception {
		assertTrue(studentDao.findById(1).isPresent());
		GradebookCollegeStudent student = studentService.studentInformation(1);
		assertTrue(student.getStudentGrades().getHistoryGradeResults().size()==1);
		MvcResult result = mockMvc.perform(post("/grades").contentType(MediaType.APPLICATION_JSON)
				.param("grade", "100.0")
				.param("studentId", "1")
				.param("gradeType", "math"))
		.andExpect(status().isOk()).andReturn();
		ModelAndViewAssert.assertViewName(result.getModelAndView(), "studentInformation");
		
		studentService.studentInformation(1);
		assertEquals(2,studentService.studentInformation(1).getStudentGrades().getMathGradeResults().size());
	}
	@Test
	void createValidGradeHttpRequestStudentDoesNotExistEmptyResponse() throws Exception {
		MvcResult result = mockMvc.perform(post("/grades")
			.contentType(MediaType.APPLICATION_JSON)
			.param("grade", "19.0")
			.param("gradeType", "math")
			.param("studentId", "2"))
			.andExpect(status().isOk()).andReturn();
		ModelAndViewAssert.assertViewName(result.getModelAndView(), "error");
	}
	@Test
	void createValidGradeHttpRequestGradeTypeDoesNotExistEmptyResponse() throws Exception {
		MvcResult result = mockMvc.perform(post("/grades")
				.contentType(MediaType.APPLICATION_JSON)
				.param("grade", "19.0")
				.param("gradeType", "physics")
				.param("studentId", "1"))
				.andExpect(status().isOk()).andReturn();
		ModelAndViewAssert.assertViewName(result.getModelAndView(), "error");
	}
	
	@Test
	void deleteValidGradeHttpRequest() throws Exception {
		assertTrue(mathGradeDao.findById(1).isPresent());
		MvcResult result = mockMvc.perform(get("/grades/{id}/{gradeType}",1,"math"))
		.andExpect(status().isOk()).andReturn();
		
		ModelAndViewAssert.assertViewName(result.getModelAndView(), "studentInformation");
		
		assertFalse(mathGradeDao.findById(1).isPresent());
		
	}
	@Test
	void deleteValidGradeHttpRequestStudentIdDoenNotExistEmptyResponse() throws Exception {
		assertFalse(mathGradeDao.findById(0).isPresent());
		MvcResult result = mockMvc.perform(get("/grades/{id}/{gradeType}",2,"math"))
				.andExpect(status().isOk()).andReturn();
		
		ModelAndViewAssert.assertViewName(result.getModelAndView(), "error");
		
	}
	@Test
	void deleteInValidGradeHttpRequest() throws Exception {
		MvcResult result = mockMvc.perform(get("/grades/{id}/{gradeType}",1,"physics"))
				.andExpect(status().isOk()).andReturn();
		
		ModelAndViewAssert.assertViewName(result.getModelAndView(), "error");
		
	}
}

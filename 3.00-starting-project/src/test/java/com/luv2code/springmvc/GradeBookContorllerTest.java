package com.luv2code.springmvc;

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
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;

@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource("/application.properties")
	class GradeBookContorllerTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MockMvc mockMvc;
	@Mock
	private StudentAndGradeService studentAndGradeServiceMock;
	@Autowired
	private StudentDao studentDao;
	
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
	void getStudentHttpRequest() throws Exception {
		MvcResult m =mockMvc.perform(MockMvcRequestBuilders.get("/"))
			.andExpect(status().isOk()).andReturn();
		ModelAndView modelAndView = m.getModelAndView();
		ModelAndViewAssert.assertViewName(modelAndView, "index");
	}
	
	@Test
	void createStudentHttpRequest() throws Exception {
		
		CollegeStudent collegeStudent = new CollegeStudent("ahmed", "mohamed", "ahmed@gmail.com");
		
		List<CollegeStudent> collegeStudentList =new ArrayList<>(Arrays.asList(collegeStudent));
		
		when(studentAndGradeServiceMock.getGradeBook()).thenReturn(collegeStudentList);
		
		assertIterableEquals(collegeStudentList,studentAndGradeServiceMock.getGradeBook());
		
		MvcResult result=mockMvc.perform(post("/")
				.contentType(MediaType.APPLICATION_JSON)
				.param("firstname", "shehab")
				.param("lastname", "elbasmy")
				.param("emailAddress", "emailAddress"))
				.andExpect(status().isOk()).andReturn();
		ModelAndView modelAndView = result.getModelAndView();
		
		ModelAndViewAssert.assertViewName(modelAndView, "index");
		
		CollegeStudent student= studentDao.findByEmailAddress("shehabelbasmy@gmail.com");
		
		assertNotNull(student);
	}
	
	@Test
	void deleteStudentHttpRequest() throws Exception {
		assertTrue(studentDao.findById(1).isPresent());
		
		MvcResult result = mockMvc.perform(get("/delete/student/{id}",1))
			.andExpect(status().isOk()).andReturn();
		
		ModelAndView modelAndView =result.getModelAndView();
		
		ModelAndViewAssert.assertViewName(modelAndView, "index");
		
		assertFalse(studentDao.findById(1).isPresent());
	}
	
	@Test
	void deleteStudentHttpReuqetErrorPage() throws Exception {
		MvcResult result = mockMvc.perform(get("/delete/student/{id}",0))
		.andExpect(status().isOk()).andReturn();
		ModelAndView modelAndView = result.getModelAndView();
		ModelAndViewAssert.assertViewName(modelAndView, "error");
	}
	
}

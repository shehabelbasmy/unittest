package com.luv2code.springmvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.HistoryGradesDao;
import com.luv2code.springmvc.repository.MathGradesDao;
import com.luv2code.springmvc.repository.ScienceGradesDao;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Transactional
class GradeBookRestControllerTest {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private MockMvc mockMvc;
	@Mock
	private StudentAndGradeService studentAndGradeService;
	
	@Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private MathGradesDao mathGradeDao;

    @Autowired
    private ScienceGradesDao scienceGradeDao;

    @Autowired
    private HistoryGradesDao historyGradeDao;
    
    @Autowired
    private StudentAndGradeService studentService;
    
    @Autowired
    private ObjectMapper mapper;
    
    @Autowired
    private CollegeStudent student;
    
    @Value("${sql.script.create.student}")
    private String sqlAddStudent;

    @Value("${sql.script.create.math.grade}")
    private String sqlAddMathGrade;

    @Value("${sql.script.create.science.grade}")
    private String sqlAddScienceGrade;

    @Value("${sql.script.create.history.grade}")
    private String sqlAddHistoryGrade;

    @Value("${sql.script.delete.student}")
    private String sqlDeleteStudent;

    @Value("${sql.script.delete.math.grade}")
    private String sqlDeleteMathGrade;

    @Value("${sql.script.delete.science.grade}")
    private String sqlDeleteScienceGrade;

    @Value("${sql.script.delete.history.grade}")
    private String sqlDeleteHistoryGrade;


    @BeforeEach
    public void setupDatabase() {
        jdbc.execute(sqlAddStudent);
        jdbc.execute(sqlAddMathGrade);
        jdbc.execute(sqlAddScienceGrade);
        jdbc.execute(sqlAddHistoryGrade);
    }
    @Test
    void getStudentHttpReuqest() throws Exception{
    	student.setFirstname("ahmed");
    	student.setLastname("mohamed");
    	student.setFirstname("ahmed@gmail.com");
    	entityManager.persist(student);
    	entityManager.flush();
    	
    	mockMvc.perform(MockMvcRequestBuilders. get("/"))
    		.andExpect(status().isOk())
    		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    		.andExpect(jsonPath("$",hasSize(2)));
    }
    
    @Test
    void createStudentHttpRequest() throws JsonProcessingException, Exception {
    	student.setFirstname("yasser");
    	student.setLastname("mostafa");
    	student.setEmailAddress("yasser@gmail.com");
    	mockMvc.perform(post("/")
			.contentType(MediaType.APPLICATION_JSON)
			.content(mapper.writeValueAsString(student)))
    	.andExpect(status().isOk())
    	.andExpect(jsonPath("$", hasSize(2)));
    	assertNotNull(studentDao.findByEmailAddress("yasser@gmail.com"));
    }
    
    @Test
    void deleteStudentHttpReuqest() throws Exception {
    	assertTrue(studentDao.findById(1).isPresent());
    	
    	mockMvc.perform(delete("/student/{id}", 1))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$", hasSize(0)))
    		.andReturn();
    	
    	assertFalse(studentDao.findById(1).isPresent());
    }
    @Test
    void deleteStudentHttpReuqestErrorPage() throws Exception {
    	assertFalse(studentDao.findById(0).isPresent());
    	
    	mockMvc.perform(delete("/student/{id}", 0))
    	.andExpect(status().is4xxClientError())
    	.andExpect(jsonPath("$.status", is(404)))
    	.andReturn();
    	
    }
    @Test
    void getStudentInfoHttpReuqest() throws Exception {
    	assertTrue(studentDao.findById(1).isPresent());
    	
    	mockMvc.perform(get("/studentInformation/{id}", 1))
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    	.andExpect(jsonPath("$.id",is(1)))
    	.andReturn();
    	
    }
    @Test
    void getStudentInfoHttpReuqestErrorPage() throws Exception {
    	assertFalse(studentDao.findById(0).isPresent());
    	
    	mockMvc.perform(get("/studentInformation/{id}", 0))
    	.andExpect(status().is4xxClientError())
    	.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    	.andExpect(jsonPath("$.status",is(404)))
    	.andReturn();
    	
    }
    
    @Test
    void createGradeHttpRequest() throws Exception {
    	mockMvc.perform(post("/grades")
    		.contentType(MediaType.APPLICATION_JSON)
    		.param("grade", "80")
    		.param("gradeType", "math")
    		.param("studentId", "1"))
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    	.andExpect(jsonPath("$.id",is(1)))
    	.andExpect(jsonPath("$.firstname",is("Eric")))
    	.andExpect(jsonPath("$.lastname",is("Roby")))
    	.andExpect(jsonPath("$.studentGrades.mathGradeResults",hasSize(2)));
    }
    @Test
    void createGradeHttpRequestStudentDoesNotExist() throws Exception {
    	mockMvc.perform(post("/grades")
    			.contentType(MediaType.APPLICATION_JSON)
    			.param("grade", "80")
    			.param("gradeType", "math")
    			.param("studentId", "2"))
    	.andExpect(status().is4xxClientError())
    	.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    	.andExpect(jsonPath("$.status",is(404)));
    }
    @Test
    void createGradeHttpRequestWithInvalidGrade() throws Exception {
    	mockMvc.perform(post("/grades")
    			.contentType(MediaType.APPLICATION_JSON)
    			.param("grade", "80")
    			.param("gradeType", "physics")
    			.param("studentId", "1"))
    	.andExpect(status().is4xxClientError())
    	.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    	.andExpect(jsonPath("$.status",is(404)));
    }
    @Test
    void deleteValidGradeHttpRequest() throws Exception{
    	mockMvc.perform(delete("/grades/{id}/{gradeType}",1,"math"))
    		.andExpect(status().isOk())
    		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    		.andExpect(jsonPath("$.id",is(1)))
    		.andExpect(jsonPath("$.firstname",is("Eric")))
    		.andExpect(jsonPath("$.lastname",is("Roby")))
    		.andExpect(jsonPath("$.studentGrades.mathGradeResults",hasSize(0)));
    }
    @Test
    void deleteValidGradeHttpRequestStudnetDoesNotExist() throws Exception{
    	mockMvc.perform(delete("/grades/{id}/{gradeType}",0,"math"))
    	.andExpect(status().is4xxClientError())
    	.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    	.andExpect(jsonPath("$.status",is(404)));
    }
    @Test
    void deleteValidGradeHttpRequestInvalidGradeType() throws Exception{
    	mockMvc.perform(delete("/grades/{id}/{gradeType}",1,"physics"))
    	.andExpect(status().is4xxClientError())
    	.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    	.andExpect(jsonPath("$.status",is(404)));
    }
    @AfterEach
    public void setupAfterTransaction() {
        jdbc.execute(sqlDeleteStudent);
        jdbc.execute(sqlDeleteMathGrade);
        jdbc.execute(sqlDeleteScienceGrade);
        jdbc.execute(sqlDeleteHistoryGrade);
    }

}

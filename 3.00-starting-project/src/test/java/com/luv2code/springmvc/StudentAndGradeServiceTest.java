package com.luv2code.springmvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Streamable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.GradebookCollegeStudent;
import com.luv2code.springmvc.models.HistoryGrade;
import com.luv2code.springmvc.models.MathGrade;
import com.luv2code.springmvc.models.ScienceGrade;
import com.luv2code.springmvc.repository.HistoryGradeDao;
import com.luv2code.springmvc.repository.MathGradeDao;
import com.luv2code.springmvc.repository.ScienceGradeDao;
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
	@Autowired
	private MathGradeDao mathGradeDao;
	@Autowired
	private ScienceGradeDao scienceGradeDao;
	@Autowired
	private HistoryGradeDao historyGradeDao;
	@Value("${sql.scripts.create.student}")
	private String sqlAddStudent;
	@Value("${sql.scripts.create.math.grade}")
	private String sqlAddMathGrade;
	@Value("${sql.scripts.create.science.grade}")
	private String sqlAddscienceGrade;
	@Value("${sql.scripts.create.history.grade}")
	private String sqlAddHistoryGrade;
	
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
		jdbcTemplate.execute("delete from history_grade");
		jdbcTemplate.execute("delete from science_grade");
		jdbcTemplate.execute("delete from math_grade");
	}
	
	@Test
	void createStudentService() {
		studentAndGradeService.createStudent("shehab","elbasmy","shehabelbasmy123@gmail.com");
		
		CollegeStudent collegeStudent = studentDao.findByEmailAddress("shehabelbasmy123@gmail.com");
		
		assertEquals("shehabelbasmy123@gmail.com", collegeStudent.getEmailAddress());
	}
	
	@Test
	void deleteStudentService() {
		Optional<CollegeStudent> deletedStudnet = studentDao.findById(1);
		Optional<MathGrade> mathGrade = mathGradeDao.findById(1);
		Optional<ScienceGrade> scienceGrade = scienceGradeDao.findById(1);
		Optional<HistoryGrade> historyGrade = historyGradeDao.findById(1);
		assertTrue(deletedStudnet.isPresent());
		assertTrue(mathGrade.isPresent());
		assertTrue(scienceGrade.isPresent());
		assertTrue(historyGrade.isPresent());
		studentAndGradeService.deleteStudent(1);
		deletedStudnet = studentDao.findById(1);
		mathGrade=mathGradeDao.findById(1);
		scienceGrade= scienceGradeDao.findById(1);
		historyGrade=historyGradeDao.findById(1);
		assertFalse(deletedStudnet.isPresent());
		assertFalse(mathGrade.isPresent());
		assertFalse(scienceGrade.isPresent());
		assertFalse(historyGrade.isPresent());
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
	@Test
	void createGradeService() {
		assertTrue(studentAndGradeService.createGrade(80.2,1,"math"));
		assertTrue(studentAndGradeService.createGrade(100, 1, "science"));
		assertTrue(studentAndGradeService.createGrade(46.0, 1, "history"));
		
		Iterable<MathGrade> mathGrade= mathGradeDao.findGradeByStudentId(1);
		Iterable<ScienceGrade> scienceGrade= scienceGradeDao.findGradeByStudentId(1);
		Iterable<HistoryGrade> historyGrade= historyGradeDao.findGradeByStudentId(1);
		
		assertTrue(((Collection<MathGrade>)mathGrade).size()==2,"student has math grade");
		assertTrue(((Collection<ScienceGrade>)scienceGrade).size()==2,"student has science grade");
		assertTrue(((Collection<HistoryGrade>)historyGrade).size()==2,"student has history grade");
	}
	
	@Test
	void createGradeServiceReturnFalse() {
		assertFalse(studentAndGradeService.createGrade(-1, 1, "history"));
		assertFalse(studentAndGradeService.createGrade(12, 2, "science"));
		assertFalse(studentAndGradeService.createGrade(90, 1, "physics"));

	}
	@Test
	void deleteGradeService() {
		assertEquals(1,studentAndGradeService.deleteGrade(1,"math"),"Should Return Student Id after deletion");
		assertEquals(1,studentAndGradeService.deleteGrade(1,"science"),"Should Return Student Id after deletion");
		assertEquals(1,studentAndGradeService.deleteGrade(1,"history"),"Should Return Student Id after deletion");
	}
	
	@Test
	void deleteGradeServiceReturnFalse() {
		assertEquals(0,studentAndGradeService.deleteGrade(2,"math"),"Should Return Student Id after deletion");
		assertEquals(0,studentAndGradeService.deleteGrade(1,"physics"),"Should Return Student Id after deletion");
	}
	@Test
	void studentInformation() {
		GradebookCollegeStudent gradebookCollegeStudent = studentAndGradeService.studentInformation(1);
		assertNotNull(gradebookCollegeStudent);
		assertEquals(1, gradebookCollegeStudent.getId());
		assertEquals("Shehab", gradebookCollegeStudent.getFirstname());
		assertEquals("El Basmy", gradebookCollegeStudent.getLastname());
		assertEquals("shehabelbasmy@gmail.com", gradebookCollegeStudent.getEmailAddress());
		assertTrue(gradebookCollegeStudent.getStudentGrades().getHistoryGradeResults().size()==1);
		assertTrue(gradebookCollegeStudent.getStudentGrades().getMathGradeResults().size()==1);
		assertTrue(gradebookCollegeStudent.getStudentGrades().getScienceGradeResults().size()==1);
	}
	@Test
	void studentInformationReturnFalse() {
		GradebookCollegeStudent gradebookCollegeStudent = studentAndGradeService.studentInformation(0);
		
		assertNull(gradebookCollegeStudent);;
	}
}

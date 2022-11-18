package com.luv2code.springmvc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.Grade;
import com.luv2code.springmvc.models.GradebookCollegeStudent;
import com.luv2code.springmvc.models.HistoryGrade;
import com.luv2code.springmvc.models.MathGrade;
import com.luv2code.springmvc.models.ScienceGrade;
import com.luv2code.springmvc.models.StudentGrades;
import com.luv2code.springmvc.repository.HistoryGradeDao;
import com.luv2code.springmvc.repository.MathGradeDao;
import com.luv2code.springmvc.repository.ScienceGradeDao;
import com.luv2code.springmvc.repository.StudentDao;

@Service
@Transactional
public class StudentAndGradeService {

	@Autowired
	private StudentDao studentDao;
	@Autowired
	private StudentGrades studentGrades;
	@Autowired
	private MathGradeDao mathGradeDao;
	@Autowired
	private MathGrade mathGrade;
	@Autowired
	private ScienceGrade scienceGrade;
	@Autowired
	private ScienceGradeDao scienceGradeDao;
	@Autowired
	private HistoryGrade historyGrade;
	@Autowired
	private HistoryGradeDao historyGradeDao;
	
	public void createStudent(String firstName,String lastName,String email) {
		CollegeStudent collegeStudent=new CollegeStudent(firstName, lastName, email);
		collegeStudent.setId(3);
		studentDao.save(collegeStudent);
	}
	public Boolean checkIfStudentIsNull(int i) {
		Optional<CollegeStudent> student = studentDao.findById(i);
		if (student.isPresent()) {
			return true;
		}
		return false;
	}
	public void deleteStudent(int id) {
		if (checkIfStudentIsNull(id)) {
			studentDao.deleteById(id);
			scienceGradeDao.deleteByStudentId(id);
			mathGradeDao.deleteByStudentId(id);
			historyGradeDao.deleteByStudentId(id);
		}
		
	}
	public Iterable<CollegeStudent> getGradeBook() {
		return studentDao.findAll();
	}
	
	public boolean createGrade(double grade, int studentId, String gradeType) {
		if (!checkIfStudentIsNull(studentId)) {
			return false;
		}
		if (grade>=0&&grade<=100) {
			if (gradeType.equals("math")) {
				mathGrade.setId(0);
				mathGrade.setGrade(grade);
				mathGrade.setStudentId(studentId);
				mathGradeDao.save(mathGrade);
				return true;
			}
			if (gradeType.equals("science")) {
				scienceGrade.setId(0);
				scienceGrade.setGrade(grade);
				scienceGrade.setStudentId(studentId);
				scienceGradeDao.save(scienceGrade);
				return true;
			}
			if (gradeType.equals("history")) {
				historyGrade.setId(0);
				historyGrade.setGrade(grade);
				historyGrade.setStudentId(studentId);
				historyGradeDao.save(historyGrade);
				return true;
			}
		}
		return false;
	}
	public int deleteGrade(int id, String gradeType) {
		if (gradeType.equals("math")) {
			Optional<MathGrade> grade = mathGradeDao.findById(id);
			if (grade.isPresent()) {
				mathGradeDao.deleteById(id);
				return grade.get().getStudentId();
			}
		}
		if (gradeType.equals("history")) {
			Optional<HistoryGrade> grade = historyGradeDao.findById(id);
			if (grade.isPresent()) {
				historyGradeDao.deleteById(id);
				return grade.get().getStudentId();
			}
		}
		if (gradeType.equals("science")) {
			Optional<ScienceGrade> grade = scienceGradeDao.findById(id);
			if (grade.isPresent()) {
				scienceGradeDao.deleteById(id);
				return grade.get().getStudentId();
			}
		}
		return 0;
	}
	public GradebookCollegeStudent studentInformation(int id) {
		if (!checkIfStudentIsNull(id)) {
			return null;
		}
		Optional<CollegeStudent> student = studentDao.findById(id);
		List<Grade> mathGradesList=new ArrayList<>();
		List<Grade> scienceGradesList=new ArrayList<>();
		List<Grade> historyGradesList=new ArrayList<>();
		mathGradeDao.findGradeByStudentId(id)
			.forEach(mathGradesList::add);
		scienceGradeDao.findGradeByStudentId(id)
			.forEach(scienceGradesList::add);
		historyGradeDao.findGradeByStudentId(id)
			.forEach(historyGradesList::add);
		studentGrades.setHistoryGradeResults(historyGradesList);
		studentGrades.setMathGradeResults(mathGradesList);
		studentGrades.setScienceGradeResults(scienceGradesList);
		return new GradebookCollegeStudent(student.get().getId(),
			student.get().getFirstname(),
			student.get().getLastname(),
			student.get().getEmailAddress(),
			studentGrades);
	}
	
	public void configureStudentInformationModel(int studentId,Model m) {
		GradebookCollegeStudent studenInformation = studentInformation(studentId);
		m.addAttribute("student", studenInformation);
		if (studenInformation.getStudentGrades().getMathGradeResults().size()>0) {
			m.addAttribute("mathAverage", studenInformation.getStudentGrades().findGradePointAverage(
				studenInformation.getStudentGrades().getMathGradeResults()
			));
		}
		else {
			m.addAttribute("mathAverage", "N/A");
			
		}
		if (studenInformation.getStudentGrades().getScienceGradeResults().size()>0) {
			m.addAttribute("scienceAverage", studenInformation.getStudentGrades().findGradePointAverage(
					studenInformation.getStudentGrades().getScienceGradeResults()
			));
		}
		else {
			m.addAttribute("scienceAverage", "N/A");
			
		}
		if (studenInformation.getStudentGrades().getHistoryGradeResults().size()>0) {
			m.addAttribute("historyAverage", studenInformation.getStudentGrades().findGradePointAverage(
					studenInformation.getStudentGrades().getHistoryGradeResults()
			));
		}
		else {
			m.addAttribute("historyAverage", "N/A");
			
		}
	}
}

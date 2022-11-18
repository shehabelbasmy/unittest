package com.luv2code.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.Gradebook;
import com.luv2code.springmvc.service.StudentAndGradeService;

@Controller
public class GradebookController {

	@Autowired
	private Gradebook gradebook;

	@Autowired
	private StudentAndGradeService studentAndGradeService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getStudents(Model m) {
		m.addAttribute("students", studentAndGradeService.getGradeBook());
		return "index";
	}

	@PostMapping("/")
	public String post(@ModelAttribute("student") CollegeStudent collegeStudent,Model m) {
		studentAndGradeService.createStudent(collegeStudent.getFirstname(), collegeStudent.getLastname(), collegeStudent.getEmailAddress());
		m.addAttribute("students", studentAndGradeService.getGradeBook());
		return "index";
	}

	
	@GetMapping("/delete/student/{id}")
	public String delete(@PathVariable("id")int id,Model m) {
		if (!studentAndGradeService.checkIfStudentIsNull(id)) {
			return "error";
		}
		studentAndGradeService.deleteStudent(id);
		m.addAttribute("students", studentAndGradeService.getGradeBook());
		return "index";
	}
	@GetMapping("/studentInformation/{id}")
	public String studentInformation(@PathVariable int id, Model m) {
		if (!studentAndGradeService.checkIfStudentIsNull(id)) {
			return "error";
		}
		
		studentAndGradeService.configureStudentInformationModel(id, m);
		
		return "studentInformation";
	}

	@PostMapping("/grades")
	public String addGrade(@RequestParam("grade") double grade,
			@RequestParam("gradeType") String gradeType,
			@RequestParam("studentId") int studentId,
			Model m) {
		if(!studentAndGradeService.checkIfStudentIsNull(studentId)) {
			return "error";
		}
		if (!studentAndGradeService.createGrade(grade, studentId, gradeType)) {
			return "error";
		}
		studentAndGradeService.configureStudentInformationModel(studentId, m);
		return "studentInformation";
		
	}
	
	@GetMapping("/grades/{id}/{gradeType}")
	public String deleteGrade(@PathVariable int id,@PathVariable String gradeType,Model m) {
		int studentId = studentAndGradeService.deleteGrade(id, gradeType);
		if (studentId==0) {
			return "error";
		}
		studentAndGradeService.configureStudentInformationModel(studentId, m);
		return "studentInformation";
	}
}

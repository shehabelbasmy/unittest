package com.luv2code.springmvc.controller;

import com.luv2code.springmvc.models.*;
import com.luv2code.springmvc.service.StudentAndGradeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/studentInformation/{id}")
	public String studentInformation(@PathVariable int id, Model m) {
		return "studentInformation";
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

}

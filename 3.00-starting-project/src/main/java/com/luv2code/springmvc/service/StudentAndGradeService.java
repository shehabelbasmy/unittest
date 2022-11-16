package com.luv2code.springmvc.service;

import java.util.Optional;
import java.util.function.BooleanSupplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.StudentDao;

@Service
@Transactional
public class StudentAndGradeService {

	@Autowired
	private StudentDao studentDao;
	
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
		studentDao.deleteById(id);
	}

	public Iterable<CollegeStudent> getGradeBook() {
		
		return studentDao.findAll();
	}
	
	
}

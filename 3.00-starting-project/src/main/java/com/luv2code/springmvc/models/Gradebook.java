package com.luv2code.springmvc.models;

import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class Gradebook {

    private List<GradebookCollegeStudent> students = new ArrayList<>();

    public Gradebook(List<GradebookCollegeStudent> students) {
        this.students = students;
    }

    public List<GradebookCollegeStudent> getStudents() {
        return students;
    }

    public void setStudents(List<GradebookCollegeStudent> students) {
        this.students = students;
    }
	public void deleteStudent(int id) {
		
	}
}

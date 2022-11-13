package com.luv2code.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CollegeStudent implements Student {

	private String firstName;
	
	private String lastName;
	
	private String emailAddress;
	
	private StudentGrades studentGrades;

	public CollegeStudent(String firstName, String lastName, String emailAddress) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
	}
	
	@Override
	public String getStudentInformation() {
		return getFullName()+" "+getEmailAddress();
	}

	@Override
	public String getFullName() {
		
		return getFirstName()+" "+getLastName();
	}


}

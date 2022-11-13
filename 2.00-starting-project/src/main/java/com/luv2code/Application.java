package com.luv2code;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import com.luv2code.model.CollegeStudent;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean(name="collegeStudent")
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	CollegeStudent getCollegeStudent() {
		return new CollegeStudent();
	}
}

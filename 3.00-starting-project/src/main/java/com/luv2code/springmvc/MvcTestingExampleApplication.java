package com.luv2code.springmvc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.Grade;
import com.luv2code.springmvc.models.HistoryGrade;
import com.luv2code.springmvc.models.MathGrade;
import com.luv2code.springmvc.models.ScienceGrade;

@SpringBootApplication
public class MvcTestingExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcTestingExampleApplication.class, args);
	}

	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	CollegeStudent getCollegeStudent() {
		return new CollegeStudent();
	}

	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	Grade getMathGrade(double grade) {
		return new MathGrade(grade);
	}

	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Qualifier("mathGrades")
	MathGrade getGrade() {
		return new MathGrade();
	}

	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Qualifier("scienceGrades")
	ScienceGrade getScienceGrade() {
		return new ScienceGrade();
	}

	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Qualifier("historyGrades")
	HistoryGrade getHistoryGrade() {
		return new HistoryGrade();
	}

}

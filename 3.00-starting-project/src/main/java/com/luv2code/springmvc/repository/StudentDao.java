package com.luv2code.springmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luv2code.springmvc.models.CollegeStudent;

public interface StudentDao extends JpaRepository<CollegeStudent, Integer> {

	CollegeStudent findByEmailAddress(String string);

}

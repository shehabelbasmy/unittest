package com.luv2code.springmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luv2code.springmvc.models.MathGrade;

public interface MathGradeDao extends JpaRepository<MathGrade, Integer> {

	public Iterable<MathGrade> findGradeByStudentId(int studentId);

	public void deleteByStudentId(int id);

}

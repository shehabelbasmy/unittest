package com.luv2code.springmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luv2code.springmvc.models.ScienceGrade;

public interface ScienceGradeDao extends JpaRepository<ScienceGrade, Integer> {

	public Iterable<ScienceGrade> findGradeByStudentId(int i);

	public void deleteByStudentId(int id);

}

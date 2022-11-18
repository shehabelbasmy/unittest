package com.luv2code.springmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luv2code.springmvc.models.HistoryGrade;

public interface HistoryGradeDao extends JpaRepository<HistoryGrade, Integer> {

	public Iterable<HistoryGrade> findGradeByStudentId(int i);

	public void deleteByStudentId(int id);

}

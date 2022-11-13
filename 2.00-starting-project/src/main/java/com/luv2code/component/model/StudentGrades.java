package com.luv2code.component.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Component
@AllArgsConstructor
@ToString
public class StudentGrades {

	@Getter
	@Setter
	List<Double> mathGradeResults;
	
	public StudentGrades() {
		System.out.println("\n\n inside StudentGrades");
	}
	
	public double addGradeResultsForSingleClass(List<Double> grades) {
		double result=0;
		for (Double double1 : grades) {
			result+=double1;
		}
		return result;
	}
	
	public double findGradePointAvaerage(List<Double> grades) {
		int lengthOfGrades = grades.size();
		double sum = addGradeResultsForSingleClass(grades);
		double result=sum/lengthOfGrades;
		
		BigDecimal resultRound = BigDecimal.valueOf(result);
		resultRound=resultRound.setScale(2, RoundingMode.HALF_UP);
		return resultRound.doubleValue();
	}
	public Object checkNull(Object obj) {
		if (obj!=null) {
			return obj;
		}
		return null;
	}
	public Boolean isGradeGreater(double gradeOne, double gradeTwo) {
        if (gradeOne > gradeTwo) {
            return true;
        }
        return false;
    }
}

package com.shehab;

import java.util.List;

public class DemoUtils {

	private String academy="Luv2code Academy";
	
	private String academyDuplicate=academy;
	
	private String [] firstThreeLetterOfAlphabet= {"a","b","c"};
	
	private List<String> academyInList=List.of("luv","2","code");

	public String getAcademy() {
		return academy;
	}

	public String getAcademyDuplicate() {
		return academyDuplicate;
	}

	public String[] getFirstThreeLetterOfAlphabet() {
		return firstThreeLetterOfAlphabet;
	}

	public List<String> getAcademyInList() {
		return academyInList;
	}
	
	public int add(int a, int b) {
		return a+b;
	}
	
	public Object checkNull(Object obj) {
		if (obj!=null) {
			return obj;
		}
		return null;
	}
	public Boolean isGreater(int n1,int n2) {
		if (n1>n2) {
			return true;
		}
		return false;
	}
	
	public String throwException(int a) throws Exception {
		if (a<0) {
			throw new Exception("Value must be Greater than or equal 0");
		}
		return "Value is Greater than or equal 0";
	}
	
	public void checkTimeout() throws InterruptedException {
		System.err.println("i'm going to sleep");
		Thread.sleep(1000);
		System.err.println("sleeping over");
	}
}

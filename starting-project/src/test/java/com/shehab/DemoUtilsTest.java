package com.shehab;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DemoUtilsTest {

	private DemoUtils demoUtils;
	
	@BeforeEach
	void setupBeforeEach() {
		System.err.println("Before Each");
		demoUtils=new DemoUtils();
	}
	@AfterEach
	void setupAfterEach() {
		System.err.println("After Each");
	}
	
	@BeforeAll
	static void setupBeforAll() {
		System.out.println("DemoUtilsTest.setupBeforAll()");
	}
	@AfterAll
	static void setupAfterAll() {
		System.out.println("DemoUtilsTest.setupAfterAll()");
	}
	@Test
	void testEquelAndNotEqual() {
		System.out.println("testEquelAndNotEqual");
		assertEquals(5,demoUtils.add(2, 3));
	}
	
	@Test
	void testNullAndNotNull() {
		System.out.println("testNullAndNotNull");
		String str1=null;
		String str2="luv2code";
		assertNull(demoUtils.checkNull(str1),"This String Must Be Null");
		assertNotNull(demoUtils.checkNull(str2),"This String Must Be Not Null");
	}
	
}

package com.shehab;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
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
	@DisplayName("Equal and not equal")
	void test_Equel_And_Not_Equal() {
		System.out.println("testEquelAndNotEqual");
		assertEquals(5,demoUtils.add(2, 3));
	}
	
	@Test
	@DisplayName("Null and not null")
	void testNullAndNotNull() {
		System.out.println("testNullAndNotNull");
		String str1=null;
		String str2="luv2code";
		assertNull(demoUtils.checkNull(str1),"This String Must Be Null");
		assertNotNull(demoUtils.checkNull(str2),"This String Must Be Not Null");
	}
	
}

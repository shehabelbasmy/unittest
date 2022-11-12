package com.shehab;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DemoUtilsTest {

	private static DemoUtils demoUtils;
	
	@BeforeAll
	static void setupBeforeAll() {
		demoUtils=new DemoUtils();
	}
	@Test
	void test_Equel_And_Not_Equal() {
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
	@Test
	@DisplayName("Same and not same")
	void testSameAndNotSame() {
		String str="shehab";
		
		assertSame(demoUtils.getAcademy(), demoUtils.getAcademyDuplicate(),"objects should refer to same object");
		
		assertNotSame(str, demoUtils.getAcademyDuplicate(),"objects not should refer to same object");
	}
	
}

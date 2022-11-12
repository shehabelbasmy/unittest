package com.shehab;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
	@DisplayName("True & False")
	@Test
	void testTrueAndFalse() {
		int gradeOne=10;
		int gradeTwo=5;
		assertTrue(demoUtils.isGreater(gradeOne, gradeTwo), "This should return true");
		assertFalse(demoUtils.isGreater(gradeTwo, gradeOne), "This should return false");
	}
	@DisplayName("Array Equals")
	@Test
	void testArrayEquals() {
		String []stringArray= {"a","b","c"};
		assertArrayEquals(stringArray, demoUtils.getFirstThreeLetterOfAlphabet(),"Array Should Be Same");
	}
	
	@DisplayName("Iterable Equals")
	@Test
	void testIterableEquals() {
		List<String> theList=List.of("luv","2","code");
		assertIterableEquals(demoUtils.getAcademyInList(), theList,"Expected list should be the same as actual list");
	}
	@DisplayName("Line Match")
	@Test
	void testLineMatch() {
		List<String> theList=List.of("luv","2","code");
		assertLinesMatch(demoUtils.getAcademyInList(), theList);
	}
	@DisplayName("Throw Exception")
	@Test
	void testThrowException() {
		assertThrows(Exception.class, ()->demoUtils.throwException(-1), "value must be greater or equal 0");
		assertDoesNotThrow(()->demoUtils.throwException(0), "value must be greater or equal 0");
	}
	
	
}

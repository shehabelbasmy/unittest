package com.shehab;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DemoUtilsTest {

	private static DemoUtils demoUtils;
	
	@BeforeAll
	static void setupBeforeAll() {
		demoUtils=new DemoUtils();
	} 
	@Test
	@Order(1)
	void test_Equel_And_Not_Equal() {
		assertEquals(5,demoUtils.add(2, 3));
	}
	
	@Test
	@Order(-1)
	void testNullAndNotNull() {
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
	
	@DisplayName("Time Out")
	@Test
	void testTimeOut() {
		assertTimeoutPreemptively(Duration.ofSeconds(3), ()->demoUtils.checkTimeout());
	}

	
}

package tdd;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FizzBuzzTest {

	
	@DisplayName("Divisable by three")
	@Test
	@Order(1)
	void testForDivisableByThree() {
		String expected = "Fizz";
		
		assertEquals(expected, FizzBuzz.compute(3),"Should Return Fizz");
	}
	@DisplayName("Divisable by Five")
	@Test
	@Order(2)
	void testForDivisableByFive() {
		String expected = "Buzz";
		
		assertEquals(expected, FizzBuzz.compute(5),"Should Return Buzz");
	}
	@DisplayName("Divisable by Five & Three")
	@Test
	@Order(3)
	void testForDivisableByFiveAndThree() {
		String expected = "FizzBuzz";
		
		assertEquals(expected, FizzBuzz.compute(15),"Should Return FizzBuzz");
	}
	@DisplayName("Not Divisable by Five Or Three")
	@Test
	@Order(3)
	void testForNotDivisableByFiveOrThree() {
		String expected = "4";
		
		assertEquals(expected, FizzBuzz.compute(4),"Should Return FizzBuzz");
	}
	
	@DisplayName("Testing with CSV Data")
	@Order(6)
	@ParameterizedTest(name="value{0},expected{1}")
	@CsvSource({"1,1","2,2","3,Fizz","4,4","5,Buzz","15,FizzBuzz",})
	void testCsvData(int i,String expected) {
		assertEquals(expected, FizzBuzz.compute(i));
	}
	
	@DisplayName("Testing with CSV Data")
	@Order(6)
	@ParameterizedTest(name="value{0},expected{1}")
	@CsvFileSource(resources = "/file.csv")
	void testCsvFile(int i,String expected) {
		assertEquals(expected, FizzBuzz.compute(i));
	}
	 
}

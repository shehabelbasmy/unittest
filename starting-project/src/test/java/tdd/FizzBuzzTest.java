package tdd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

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
	
	 
}

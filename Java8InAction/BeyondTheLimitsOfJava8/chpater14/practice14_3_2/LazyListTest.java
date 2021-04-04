package chpater14.practice14_3_2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LazyListTest {

	@Test
	public void testLazyList() {
		LazyList<Integer> numbers = LazyList.from(2);
		int two = numbers.head();
		int three = numbers.tail().head();
		int four = numbers.tail().tail().head();
		
		assertEquals(2, two);
		assertEquals(3, three);
		assertEquals(4, four);
	}
	
	@Test
	public void testPrimes() {
		LazyList<Integer> numbers = LazyList.from(2);
		int two = LazyList.primes(numbers).head();
		int three = LazyList.primes(numbers).tail().head();
		int five = LazyList.primes(numbers).tail().tail().head();

		assertEquals(2, two);
		assertEquals(3, three);
		assertEquals(5, five);
	}
	
//	@Test
	public void testPrintAll() {
		LazyList.printAll(LazyList.primes(LazyList.from(2)));
	}
}

package BeyondTheLimitsOfJava8.chpater14.practice14_3_1;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Prime {

	public static void main(String[] args) {
		List<Integer> result = primes(10).collect(Collectors.toList());
		System.out.println(result);
	}
	
	public static Stream<Integer> primes(int n) {
		return Stream.iterate(2, i -> i + 1)
					 .filter(Prime::isPrime)
					 .limit(n);
	}
	
	public static boolean isPrime(int candidate) {
		int candidateRoot = (int) Math.sqrt((double) candidate);
		return IntStream.rangeClosed(2, candidateRoot)
						.noneMatch(i -> candidate % i == 0);
	}
	
}

package quiz5_4;

import java.util.Arrays;
import java.util.stream.Stream;

public class FibonacciSequenceSet {

	public static void main(String[] args) {
		Stream
			.iterate(new int[] {0, 1}, pair -> new int[] {pair[1], pair[0] + pair[1]} )
			.limit(20)
			.forEach(pair -> System.out.println(Arrays.toString(pair)));
		
		Stream
			.iterate(new int[] {0, 1}, pair -> new int[] {pair[1], pair[0] + pair[1]} )
			.limit(20)
			.map(pair -> pair[0])
			.forEach(num -> System.out.print(num + " "));
	}
	
}

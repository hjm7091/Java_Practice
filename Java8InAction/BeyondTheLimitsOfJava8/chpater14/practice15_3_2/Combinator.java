package chpater14.practice15_3_2;

import java.util.function.Function;

public class Combinator {

	public static void main(String[] args) {
		Integer result1 = repeat(3, (Integer x) -> 2 * x).apply(10);
		System.out.println(result1);
		Integer result2 = repeatMine(3, (Integer x) -> 2 * x).apply(10);
		System.out.println(result2);
	}
	
	private static <A, B, C> Function<A, C> compose(Function<B, C> g, Function<A, B> f) {
		return x -> g.apply(f.apply(x));
	}
	
	private static <A> Function<A, A> repeat(int n, Function<A, A> f) {
		return n == 0 ? x -> x : compose(f, repeat(n - 1, f));
	}
	
	private static <A> Function<A, A> repeatMine(int n, Function<A, A> f) {
		return n == 0 ? x -> x : f.andThen(repeat(n - 1, f));
	}
}

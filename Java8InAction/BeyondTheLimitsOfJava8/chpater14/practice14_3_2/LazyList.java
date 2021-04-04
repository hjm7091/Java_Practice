package chpater14.practice14_3_2;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class LazyList<T> implements MyList<T> {
	
	private final T head;
	private final Supplier<MyList<T>> tail;
		
	public LazyList(T head, Supplier<MyList<T>> tail) {
		this.head = head;
		this.tail = tail;
	}

	@Override
	public T head() {
		return head;
	}

	@Override
	public MyList<T> tail() {
		return tail.get();
	}

	public boolean isEmpty() {
		return false;
	}
	
	public static LazyList<Integer> from(int n) {
		return new LazyList<Integer>(n, () -> from(n + 1));
	}
	
	public static MyList<Integer> primes(MyList<Integer> numbers) {
		return new LazyList<Integer>(
			numbers.head(), 
			() -> primes(
				numbers.tail().filter(n -> n % numbers.head() != 0)
			)
		);
	}
	
	public MyList<T> filter(Predicate<T> p) {
		return isEmpty() ? this : p.test(head()) ? 
									new LazyList<T>(head(), () -> tail().filter(p)) :
									tail().filter(p);
	}
	
	public static <T> void printAll(MyList<T> list) {
		while(!list.isEmpty()) {
			System.out.println(list.head());
			list = list.tail();
		}
	}
}

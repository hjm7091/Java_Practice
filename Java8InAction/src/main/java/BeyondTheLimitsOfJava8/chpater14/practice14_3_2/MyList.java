package BeyondTheLimitsOfJava8.chpater14.practice14_3_2;

import java.util.function.Predicate;

public interface MyList<T> {
	
	T head();
	
	MyList<T> tail();
	
	default boolean isEmpty() {
		return true;
	}

	MyList<T> filter(Predicate<T> p);
}

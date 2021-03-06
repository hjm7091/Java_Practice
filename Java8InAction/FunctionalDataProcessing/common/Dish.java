package common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString(of = {"name"})
@RequiredArgsConstructor
public class Dish {

	private final String name;
	private final boolean vegetarian;
	private final int calories;
	private final Type type;
	
	public enum Type {
		MEAT, FISH, OTHER
	}
	
	public enum CaloricLevel {
		DIET, NORMAL, FAT
	}
}

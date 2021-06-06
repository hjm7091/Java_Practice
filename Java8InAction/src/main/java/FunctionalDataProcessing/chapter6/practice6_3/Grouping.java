package FunctionalDataProcessing.chapter6.practice6_3;

import FunctionalDataProcessing.common.Dish;
import FunctionalDataProcessing.common.Dish.CaloricLevel;
import FunctionalDataProcessing.common.Dish.Type;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

public class Grouping {

	static List<Dish> menu = Arrays.asList(
		new Dish("pork", false, 800, Type.MEAT),
		new Dish("beef", false, 700, Type.MEAT),
		new Dish("chicken", false, 400, Type.MEAT),
		new Dish("french fries", true, 530, Type.OTHER),
		new Dish("rice", true, 350, Type.OTHER),
		new Dish("season fruit", true, 120, Type.OTHER),
		new Dish("pizza", true, 550, Type.OTHER),
		new Dish("prawns", false, 300, Type.FISH),
		new Dish("salmon", false, 450, Type.FISH)
	);
	
	public static void main(String[] args) {
		Map<Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = menu.stream().collect(
			groupingBy(Dish::getType, 
				groupingBy(dish -> {
					if(dish.getCalories() <= 400) {
						return CaloricLevel.DIET;
					} else if(dish.getCalories() <= 700) {
						return CaloricLevel.NORMAL;
					} else {
						return CaloricLevel.FAT;
					} 
				})
			)
		);
		
		System.out.println(dishesByTypeCaloricLevel);
		
		Map<Type, Optional<Dish>> mostCaloricByType = menu.stream()
			.collect(groupingBy(Dish::getType, maxBy(comparingInt(Dish::getCalories))));
		
		System.out.println(mostCaloricByType);
		
		Map<Type, Dish> mostCaloricByType1 = menu.stream()
				.collect(toMap(Dish::getType, Function.identity(), BinaryOperator.maxBy(comparingInt(Dish::getCalories))));
		
		System.out.println(mostCaloricByType1);
		
		Map<Type, HashSet<CaloricLevel>> caloricLevelByType = menu.stream().collect(
			groupingBy(Dish::getType, mapping(dish -> {
				if(dish.getCalories() <= 400) {
					return CaloricLevel.DIET;
				} else if(dish.getCalories() <= 700) {
					return CaloricLevel.NORMAL;
				} else {
					return CaloricLevel.FAT;
				}
			}, toCollection(HashSet::new)))
		);
		
		System.out.println(caloricLevelByType);
	}
	
}

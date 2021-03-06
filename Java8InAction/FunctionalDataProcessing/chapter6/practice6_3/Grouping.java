package chapter6.practice6_3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

import common.Dish;
import common.Dish.CaloricLevel;
import common.Dish.Type;

public class Grouping {

	static List<Dish> menu = Arrays.asList(
		new Dish("pork", false, 800, Dish.Type.MEAT),
		new Dish("beef", false, 700, Dish.Type.MEAT),
		new Dish("chicken", false, 400, Dish.Type.MEAT),
		new Dish("french fries", true, 530, Dish.Type.OTHER),
		new Dish("rice", true, 350, Dish.Type.OTHER),
		new Dish("season fruit", true, 120, Dish.Type.OTHER),
		new Dish("pizza", true, 550, Dish.Type.OTHER),
		new Dish("prawns", false, 300, Dish.Type.FISH),
		new Dish("salmon", false, 450, Dish.Type.FISH)
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
				.collect(groupingBy(Dish::getType, collectingAndThen(maxBy(comparingInt(Dish::getCalories)), Optional::get)));
		
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

package com.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;import java.util.stream.Collector;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;

interface StreamMaker {
	StreamExample make();
}

public class StreamExample {

	public List<Dish> getDishes() {
		return Arrays.asList(new Dish("pork", false, 800, Dish.Type.MEAT), new Dish("beef", false, 700, Dish.Type.MEAT),
				new Dish("chicken", false, 400, Dish.Type.MEAT), new Dish("french fries", true, 530, Dish.Type.OTHER),
				new Dish("rice", true, 350, Dish.Type.OTHER), new Dish("season fruit", true, 120, Dish.Type.OTHER),
				new Dish("pizza", true, 550, Dish.Type.OTHER), new Dish("prawns", false, 300, Dish.Type.FISH),
				new Dish("salmon", false, 450, Dish.Type.FISH));
	}
	
	public static void getVegNonVegDishes() {
		List<Dish> data = new StreamExample().getDishes();
		Map<Boolean, Map<Dish.Type, List<Dish>>> output = data.stream().collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
		System.out.println(output);
	}

	public static void main(String arg[]) {
		StreamMaker streamObject = StreamExample::new;
		StreamExample sObject = streamObject.make();
		List<Dish> dishes = sObject.getDishes();
		
		getVegNonVegDishes();
		
		List<String> filteredDishes = dishes.stream()
				.filter(e -> e.getCalories() > 300)
				.map(Dish::getName)
				.collect(Collectors.toList());
		
		System.out.println(filteredDishes);
		displayVegDishes(dishes);
		
		List<Integer> numbers = Arrays.asList(1,2,3,3,2,6,7,7,8);
		System.out.println(numbers.stream().filter(i -> i % 2 == 0).distinct().collect(Collectors.toList()));
		
		String[] s1 = {"hello", "world"};
		System.out.println(Arrays.asList(s1).stream().map(w -> w.split("")).distinct().collect(Collectors.toList()));
		
		List<Integer> numberss = Arrays.asList(1,2,3,4,5);
		System.out.println(numberss.stream().map(it -> it * it).collect(Collectors.toList()));
		
		Map<Character, Integer> mp = new HashMap<Character, Integer>();
		mp.put(new Character('C'), 3);
		mp.put(new Character('D'), 2);
		mp.put(new Character('G'), 5);
		List<Entry<Character, Integer>> list = new ArrayList<Entry<Character, Integer>>(mp.entrySet());
		
		Collections.sort(list, new Comparator<Entry<Character, Integer>>() {
			public int compare(Entry<Character, Integer> o1, Entry<Character, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			};
		});
		System.out.println(list);
		for(Entry<Character, Integer> s : list) {
			System.out.println(s.getKey() + " -> " + s.getValue());
		}
		
		final int i;
		i = 10;
		System.out.println(i);
		
		String x = null;
		giveMeAString(x);
		System.out.println(x);
		
		Hacker h = new Teacher();
		h.disp();
		
		List list2 = new ArrayList();
		list2.add(2);
		list2.add(new Integer(3));
		
		System.out.println(list2);
		
		try {
			aMethod();
		} catch(Exception e) {
			System.out.println("Exception");
		}
		System.out.println("Finished");
	}
	
	public static void displayVegDishes(List<Dish> dishes) {
		System.out.println(dishes.stream().filter(it -> it.isVegetarian()).collect(Collectors.toList()));
	}
	
	static void giveMeAString(String y) {
		y = "GeeksQuiz";
	}
	
	static void aMethod() throws Exception {
		try {
			throw new Exception();
		} finally {
			System.out.println("finally");
		}
	}
}

class Hacker {
	public void disp() {
	System.out.print("I am a Hacker");
	}
}

class Teacher extends Hacker {
	public void disp() {
	System.out.print("I am a Teacher");
	}
}


class Dish {
	private final String name;
	private final boolean vegetarian;
	private final int calories;
	private final Type type;

	public Dish(String name, boolean vegetarian, int calories, Type type) {
		this.name = name;
		this.vegetarian = vegetarian;
		this.calories = calories;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public boolean isVegetarian() {
		return vegetarian;
	}

	public int getCalories() {
		return calories;
	}

	public Type getType() {
		return type;
	}

	public enum Type {
		MEAT, FISH, OTHER
	}

	@Override
	public String toString() {
		return "Dish [name=" + name + ", vegetarian=" + vegetarian + ", calories=" + calories + ", type=" + type + "]";
	}
	
	
}

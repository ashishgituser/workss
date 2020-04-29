package com.lambda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LambdaExpressionPractice {

	public static void main(String[] args) {

		try {
			Function<BufferedReader, String> singleLinderReader = (BufferedReader br) -> {
				try {
					return br.readLine();
				} catch (Exception e) {
					throw new RuntimeException(e.getLocalizedMessage());
				}
			};

			String singleLine = processFile(singleLinderReader);
			// String twoLine = processFile((BufferedReader br) -> br.readLine() +
			// br.readLine());
			// System.out.println(singleLine);

			Map<Character, Integer> freq = new HashMap<>();
			String s = "welcome to the new alignement of the wel doing here.";

			char[] cArray = s.toCharArray();
			for (int i = 0; i < cArray.length; i++) {
				Character c = cArray[i];
				if (freq.containsKey(c)) {
					int count = freq.get(c) + 1;
					freq.put(c, count);
				} else {
					freq.put(c, 1);
				}
			}

			System.out.println(freq);

			List<String> st = Arrays.asList(s.split(" ")).stream()
					.sorted(Comparator.comparing(String::length).reversed()).collect(Collectors.toList());
			System.out.println(st);

			Character[] cArr = s.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
			Map<Character, Long> data = Arrays.asList(cArr).stream()
					.collect(Collectors.groupingBy(Character::charValue, Collectors.counting()));

			System.out.println(data);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String processFile(Function<BufferedReader, String> b) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader("D:\\input.txt"))) {
			return b.apply(br);
		}
	}

	public static String processFile(BufferReaderProcessor b) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader("D:\\input.txt"))) {
			return b.process(br);
		}
	}

}

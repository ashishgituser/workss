package com.lambda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamPracticeTraders {

	public static void main(String[] args) {
		partitionNumberIntoEvenOdd();
	}
	
	public static void partitionNumberIntoEvenOdd() {
		Map<Boolean, List<Integer>> data = IntStream.range(1, 20).boxed().collect(Collectors.partitioningBy(it -> it %  2 == 0));
		System.out.println(data);
	}
	
	public static void groupTransactionsByCurrency() {
		List<Transaction> trans = getTransactions();
		Map<String, List<Transaction>> output = trans.stream().collect(Collectors.groupingBy(Transaction::getCurrency));
		System.out.println(output);
	}

	public static void transactionMinValue() {
		List<Transaction> trans = getTransactions();
		Optional<Transaction> output = trans.stream().min(Comparator.comparing(Transaction::getValue));
		System.out.println(output.get().getValue());
	}

	public static void transactionHigestValue() {
		List<Transaction> trans = getTransactions();
		Optional<Transaction> output = trans.stream().max(Comparator.comparing(Transaction::getValue));
		System.out.println(output.get().getValue());
	}

	public static void transactionValuesFromACity() {
		List<Transaction> trans = getTransactions();
		List<Integer> output = trans.stream().filter(it -> it.getTrader().getCity().equals("Cambridge"))
				.map(it -> it.getValue()).collect(Collectors.toList());
		System.out.println(output);
	}

	public static void anyMatchForTrader() {
		List<Transaction> trans = getTransactions();
		boolean anyMatch = trans.stream().anyMatch(it -> it.getTrader().getCity().equals("Milan"));
		System.out.println(anyMatch);
	}

	public static void getStringOfAllTradersSorted() {
		List<Transaction> trans = getTransactions();
		String output1 = trans.stream().map(it -> it.getTrader().getName()).distinct().sorted()
				.reduce("", (n1, n2) -> n1 + n2);
		String output2 = trans.stream().map(it -> it.getTrader().getName()).distinct().sorted()
				.collect(Collectors.joining(","));
		System.out.println(output2);
	}

	public static void findTradersByCityAndSortByName() {
		List<Transaction> trans = getTransactions();
		List<Trader> output = trans.stream().map(Transaction::getTrader).filter(it -> it.getCity().equals("Cambridge"))
				.distinct().sorted(Comparator.comparing(Trader::getName)).collect(Collectors.toList());
		System.out.println(output);
	}

	public static void filterByYearAndSort() {
		List<Transaction> trans = getTransactions();
		List<Transaction> output = trans.stream().filter(it -> it.getYear() == 2011)
				.sorted(Comparator.comparing(Transaction::getValue)).collect(Collectors.toList());
		System.out.println(output);
	}

	public static void getUniqueCities() {
		List<Transaction> trans = getTransactions();
		List<String> output = trans.stream().map(it -> it.getTrader().getCity()).distinct()
				.collect(Collectors.toList());
		System.out.println(output);
	}

	public static List<Transaction> getTransactions() {
		Trader raoul = new Trader("Raoul", "Cambridge");
		Trader mario = new Trader("Mario", "Milan");
		Trader alan = new Trader("Alan", "Cambridge");
		Trader brian = new Trader("Brian", "Cambridge");
		List<Transaction> transactions = Arrays.asList(new Transaction(brian, 2011, 300, "MUR"),
				new Transaction(raoul, 2012, 1000, "MUR"), new Transaction(raoul, 2011, 400, "INR"),
				new Transaction(mario, 2012, 710, "INR"), new Transaction(mario, 2012, 700, "USD"), new Transaction(alan, 2012, 950, "AUD"));
		return transactions;
	}

}

class Trader {
	private final String name;
	private final String city;

	public Trader(String n, String c) {
		this.name = n;
		this.city = c;
	}

	public String getName() {
		return this.name;
	}

	public String getCity() {
		return this.city;
	}

	public String toString() {
		return "Trader:" + this.name + " in " + this.city;
	}
}

class Transaction {
	private final Trader trader;
	private final int year;
	private final int value;
	private final String currency;

	public Transaction(Trader trader, int year, int value, String currency) {
		this.trader = trader;
		this.year = year;
		this.value = value;
		this.currency = currency;
	}

	public Trader getTrader() {
		return this.trader;
	}

	public int getYear() {
		return this.year;
	}

	public int getValue() {
		return this.value;
	}
	
	public String getCurrency() {
		return this.currency;
	}

	public String toString() {
		return "{" + this.trader + ", " + "year: " + this.year + ", " + "value:" + this.value + ", " + "currency:" + this.currency + "}";
	}
}
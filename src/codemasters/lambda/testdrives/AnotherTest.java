package codemasters.lambda.testdrives;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import static java.util.Arrays.asList;

public class AnotherTest {
	
	public static void main(String[] args) {
		
		List<Integer> myInts = asList(-5,-4,-3,-2,-1,0,1,2,3,4,5);
		
		Predicate<Integer> isOdd = x -> x % 2 != 0;
		List<Integer> odds = filter(myInts, isOdd);
		System.out.println(odds);
		
		Predicate<Integer> isPositive = x -> x >= 0;
		List<Integer> positives = filter(myInts, isPositive);
		System.out.println(positives);
		
		Predicate<Integer> isOddAndPositive = Predicates.and(isOdd, isPositive);
		List<Integer> oddAndPositives = filter(myInts, isOddAndPositive);
		System.out.println(oddAndPositives);
		
		List<Integer> again = filter(myInts, isOdd.and(isPositive));
		System.out.println(again);
		
		
	}
	
	static <T> List<T> filter(List<T> items, Predicate<T> predicate) {
		List<T> results = new ArrayList<>();
		for(T item : items) {
			if(predicate.test(item)){
				results.add(item);
			}
		}
		return results;
	}
	
	/*
	static class IsPositive implements Predicate<Integer> {
		public boolean test(Integer value) {
			return value >= 0;
		}
	}
	
	static class IsOdd implements Predicate<Integer> {
		public boolean test(Integer value) {
			return value % 2 != 0;
		}
	}*/
	
	interface Predicate<T> {
	
		boolean test(T value);
		
		default Predicate<T> and(Predicate<T> other) {
			return Predicates.and(this, other);
		}
		
		default Predicate<T> or(Predicate<T> other) {
			return Predicates.or(this, other);
		}
		
		default Predicate<T> not() {
			return Predicates.not(this);
		}
		
	}
	
	
	static class Predicates {
		
		static <T> Predicate<T> and(Predicate<T> first, Predicate<T> second) {
			return new Predicate<T>() {
				public boolean test(T value) {
					return first.test(value) && second.test(value);
				}
			};
		}
		
		static <T> Predicate<T> or(Predicate<T> first, Predicate<T> second) {
			return new Predicate<T>() {
				public boolean test(T value) {
					return first.test(value) || second.test(value);
				}
			};
		}
		
		static <T> Predicate<T> not(Predicate<T> other) {
			return new Predicate<T>() {
				public boolean test(T value) {
					return !other.test(value);
				}
			};
		}
		
	}
	
}



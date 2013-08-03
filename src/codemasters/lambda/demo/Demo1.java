package codemasters.lambda.demo;

import org.junit.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.BinaryOperator;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class Demo1 {
	
	@Test
	public void testFilter() {
		
		List<Integer> myInts = asList(1,2,3,4,5,6,7,8,9,0);
		/*
		List<Integer> onlyOdds = filter(new Function<Integer, Boolean>(){
				@Override
				public Boolean apply(Integer value) {
					return value % 2 != 0;
				}
		}, myInts);
		*/
		
		//Function<Integer,Boolean> predicate = (Integer t) -> t % 2 != 0;
		//Function<Integer, Boolean> predicate = t -> t % 2 != 0;
		List<Integer> onlyOdds = filter(t -> t % 2 != 0, myInts);
		
		assertThat(onlyOdds, contains(1,3,5,7,9));
		
	}
	
	public static <T> List<T> filter(Predicate<T> predicate, List<T> items) {
		List<T> results = new ArrayList<>();
		for(T item : items) {
			if(predicate.test(item)) {
				results.add(item);
			}
		}
		return results;
	}
	
	@Test
	public void testMapper() {
		
		List<String> text = asList("0","1","2","3","4","5","6","7","8","9");
		
		/*
		List<Integer> myInts = map(new Function<String, Integer>(){
				@Override
				public Integer apply(String value) {
					return Integer.valueOf(value);
				}
		},text);
		*/
		/*
		Function<String, Integer> mapper = (String t) -> Integer.valueOf(t);
		List<Integer> myInts = map(mapper, text);
		*/
		
		/*
		Function<String, Integer> mapper = t -> Integer.valueOf(t);
		List<Integer> myInts = map(mapper, text);
		*/
		
		List<Integer> myInts = map(t -> Integer.valueOf(t), text);
		assertThat(myInts, contains(0,1,2,3,4,5,6,7,8,9));
	}
	
	public static <U,T> List<T> map(Function<U,T> mapper, List<U> items) {
		List<T> results = new ArrayList<>();
		for(U item : items) {
			T mapped = mapper.apply(item);
			results.add(mapped);
		}
		return results;
	}
	
	@Test
	public void testReduce() {
		List<Integer> myInts = asList(1,2,3,4,5);
		
		/*
		Integer summation = reduce(new Function2<Integer, Integer, Integer>() {
				@Override
				public Integer apply(Integer left, Integer right) {
					return left + right;
				}
		}, 0, myInts);
		*/
		/*
		Function2<Integer, Integer, Integer> sum = (Integer left, Integer right) -> left + right;
		Integer summation = reduce(sum, 0, myInts);
		*/
		/*
		Function2<Integer, Integer, Integer> sum = (left, right) -> left + right;
		Integer summation = reduce(sum, 0, myInts);
		*/
		
		Integer summation = reduce((left, right) -> left + right, 0, myInts);
		Integer factorial = reduce( (left, right) -> left * right, 1, myInts);
		
		assertThat(summation, is(15));
		assertThat(factorial, is(120));
	}
	
	public static <T> T reduce(BinaryOperator<T> combiner, T seed, List<T> items) {
		T left = seed;
		for(T right : items) {
			left = combiner.apply(left, right);
		}
		return left;
	}
	/*
	interface Function<T,U> {
		U apply(T t);
	}
	*/
	
	
}

package codemasters.lambda.demo;

import org.junit.Test;

import codemasters.lambda.domain.Person;
import codemasters.lambda.domain.Car;
import java.util.Collection;
import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;
import java.util.function.BinaryOperator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class Demo2 {
	
	@Test
	public void testLambdaArrayInitializer() throws Exception {
		//List<Callable<String>> calls = Arrays.<Callable<String>>asList( ()->"Hello world!", () -> "Good-bye world!" );
		
		Runnable[] tasks = { () -> { System.out.println("Hello world"); },  () -> { System.out.println("Good-bye world"); } };
		ExecutorService service = null;
		
		for(Runnable task : tasks) {
			service.submit(task);
		}
		
		
		
		
	}
	
	@SuppressWarnings({"unchecked"})
	Callable<String>[] getCalls() {
		return new Callable[]{ () -> "Hello world", () -> "Good-bye world" };
	}
	
	@Test
	public void testLambdas() {
		
		Predicate<Integer> isOdd = n -> n % 2 != 0;
		
		assertThat(isOdd.test(3), is(true));
		assertThat(isOdd.test(4), is(false));
		
		//java.util.Comparator<T>: int compare(T left, T right)
		//Comparator<Integer> compare = (Integer left, Integer right) -> left > right ? 1 : (left < right ? -1 : 0);
		Comparator<Integer> compare = (left, right) -> left > right ? 1 : (left < right ? -1 : 0);
		
		assertThat(compare.compare(1,1), is(0));
		assertThat(compare.compare(1,2), is(-1));
		assertThat(compare.compare(2,1), is(1));
		
		BinaryOperator<Integer> sum = (left, right) -> left + right;
		
		assertThat(sum.apply(1,2), is(3));
		assertThat(sum.apply(3,2), is(5));
		
		
		//Callable<T> : T call()
		Callable<String> sayHello = () -> "Hello World";
		try {
			assertThat(sayHello.call(), is("Hello World"));
		}catch(Exception e) {
			
		}
		
		Runnable runMe = () -> { System.out.println("Hello from runMe"); };
		runMe.run();
		
		int test = 9;
		Runnable perhapsRun = () -> {
			if(test < 10){
				System.out.println("Hello from run");
			} else {
				System.out.println("Good-bye from run");
			}
		};
		
		new Thread(perhapsRun).start();
		
		Predicate<Integer> moreThanTest = n -> {
			if(n > test)
				return true;
			else
				return false;
		};
		
		assertThat(moreThanTest.test(10), is(true));
		assertThat(moreThanTest.test(8), is(false));
		
		Runnable r = () -> { System.out.println(this); };
		
		r.run();
		/*
		final Mapper<Integer, Integer> fib = n -> {
			if (n == 0) return 0;
			if (n == 1) return 1;
			return fib.map(n-1) + fib.map(n-2);
		};
		*/
		/*
		List<Runnable> runnables = new ArrayList<>();
		final Runnable auto = () -> { runnables.add(auto); }; 
		*/
		
		List<Person> persons = new ArrayList<>();
		//persons.sort( comparing( (IntMapper<Person>) p -> p.getAge() ) );
		
		Comparator<Car> byAge = comparing( (ToIntFunction<Car>) car -> car.getYear());
		
		persons.sort( comparing( (ToIntFunction<Person>) p -> p.getAge() ) );
		
	}
	
	public static class Predicates {
		
		public static <T> Predicate<T> and(final Predicate<T> left, final Predicate<T> right) {
			/*
			return new Predicate<T>(){
				@Override
				public boolean test(T value) {
					return left.test(value) && right.test(value);
				}
			};
			*/
			//return (T t) -> left.test(t) && right.test(t);
			return t -> left.test(t) && right.test(t);
		}
		
	}
	
	@Override
	public String toString() {
		return "Demo2";
	}
	
}

package codemasters.lambda.testdrives;

import org.junit.Test;
import org.junit.Before;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.Consumer;

import codemasters.lambda.streams.*;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

//10724317
public class LazinessTest {
	
	private Stream<String> text;
	
	@Before
	public void before(){
		this.text = Streams.wrap(asList("0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17"));
	}
	
	@Test
	public void testGetFirst() {
		Integer firstPrime = text.map(new TextToInteger())
								 .filter(new OnlyPrimes())
								 .getFirst();
								 
		assertThat(firstPrime, is(2));
	}
	
	@Test
	public void testGetLast() {
		Integer lastPrime = text.map(new TextToInteger())
								 .filter(new OnlyPrimes())
								 .getLast();
		
		assertThat(lastPrime, is(17));						 
	}
	
	@Test
	public void testOnlyPrimes() {
		Set<Integer> onlyPrimes = text.map(new TextToInteger())
									 .filter(new OnlyPrimes())
									 .into(new LinkedHashSet<Integer>());
									 
		assertThat(onlyPrimes,contains(2,3,5,7,11,13,17));
	}
	
	@Test
	public void testPrimesSmallerThan10() {
		Set<Integer> primes = text.map(Integer::new) //lazy
							      .filter(new OnlyPrimes()) //lazy
								  .takeWhile(x -> x <= 10) //lazy
								  .into(new LinkedHashSet<Integer>()); //eager
		
		assertThat(primes, contains(2,3,5,7));									  
	}
	
	@Test
	public void testPrimesBiggerThan10() {
		Set<Integer> primes = text.map(Integer::new)
								  .filter(new OnlyPrimes())
								  .dropWhile(x -> x <= 10)
								  .into(new LinkedHashSet<Integer>());
								  
		assertThat(primes, contains(11, 13,17));
	}
	
	@Test
	public void testSumOfPrimesSmallerThan10() {
		Integer sum = text.map(Integer::new)
						  .filter(new OnlyPrimes())
						  .takeWhile(x -> x <= 10)
						  .reduce(0, (x, y) -> x + y);
											  
		assertThat(sum, is(17));

	}
	
	@Test
	public void testSumOfPrimesBiggerThan10() {
		Integer sum = text.map(Integer::new)
						  .filter(new OnlyPrimes())
						  .dropWhile(x -> x <= 10)
						  .reduce( (x,y) -> x + y);
		
		assertThat(sum, is(41));
	}
	
	@Test 
	public void testMaxPrime() {
		Integer maxPrime = text.map(Integer::new).filter(new OnlyPrimes()).max(Integer::compare);
		assertThat(maxPrime, is(17));
	}
	
	@Test public void testMinPrime() {
		Integer minPrime = text.map(Integer::new).filter(new OnlyPrimes()).min(Integer::compare);
		assertThat(minPrime, is(2));
	}
	
	
	static class OnlyOdds implements Predicate<Integer> {
		public boolean test(Integer value) {
			return value % 2 != 0;
		}
	}
	
	static class OnlyPrimes implements Predicate<Integer> {
		public boolean test(Integer value) {
			int number = value;
			if(number < 2) {
				return false;
			}
			if(number == 2) {
				return true;
			}
			int divisor = 2;
			while(true){
				if(number % divisor == 0){
					return false;
				} else if (divisor * divisor > number) {
					return true;
				} else if (divisor % 2 == 0){
					divisor += 1;
				} else {
					divisor += 2;
				}
			}
		}
	}
	
	static class TextToInteger implements Function<String,Integer> {
		public Integer apply(String value) {
			return Integer.valueOf(value);
		}
	}
}



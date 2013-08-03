package codemasters.lambda.showtime;

import codemasters.lambda.domain.*;
import java.util.*;
import java.util.function.*;
import org.junit.*;
import static java.lang.System.out;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class Demo5 {
	
	@Test
	public void testStream() {
		
		Stream<String> myStream = () -> asList("0","1","2","3","4","5").iterator();
		
		Integer number = myStream.map( Integer::new).filter( n -> n % 2 != 0).getFirst();
		
		assertThat(number, is(1));
	}
	
	interface Stream<T> {
		
		public default Stream<T> filter(Predicate<T> predicate) {
			
			return () -> Iterators.filter(this.iterator(), predicate);
			
		}
		
		public default <U> Stream<U> map(Function<T,U> mapper)  {
			return () -> Iterators.map(this.iterator(), mapper);
		}
		
		public default T getFirst()  {
			return Iterators.getFirst(this.iterator());
		}
		
		public Iterator<T> iterator();
		
	}
	
	
	public static class Iterators {
		
		public static <T> Iterator<T> filter(Iterator<T> source, Predicate<T> predicate) {
			return new Iterator<T>() {
				
				private T next;
				
				public boolean hasNext() {
					while(source.hasNext()){
						next = source.next();
						if(predicate.test(next)){
							return true;
						}
					}
					next = null;
					return false;
				}
				
				public T next() {
					if(next!=null || hasNext()){
						return next;
					}
					throw new NoSuchElementException();
				}
			};
		}
		
		public static <T,U> Iterator<U> map(Iterator<T> source, Function<T,U> mapper) {
			
			return new Iterator<U>() {
				
				public boolean hasNext() {
					return source.hasNext();
				}
				
				public U next() {
					return mapper.apply(source.next());
				}
			};
		}
		
		public static <T> T getFirst(Iterator<T> source) {
			if(source.hasNext()) {
				return source.next();
			}
			return null;
		}
		
		private Iterators(){};
	}
	
	
	
	
	
	
	
	
	
	
	
	@Test
	public void testSum() {
		
		//int result = sum(getOne(), getTwo());
		int result = sum( ()-> getOne(), () -> getTwo() );
		
	}
	
	public int sum(Lazy<Integer> one, Lazy<Integer> two) {
		System.out.println("summing");
		return one.get() + two.get();
	}
	
	interface Lazy<T> {
		T get();
	}
	
	public int sum(int one, int two){
		System.out.println("summing");
		return one + two;
	}
	
	public Integer getOne() {
		System.out.println("one");
		return 1;
	}
	
	public Integer getTwo() {
		System.out.println("two");
		return 2;
	}
	
	
}

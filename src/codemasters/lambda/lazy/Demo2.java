package codemasters.lambda.lazy;

import java.util.*;
import java.util.function.*;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class Demo2 {
	
	
	@Test
	public void testDropingStream() {
		Stream<String> text = () -> asList("1","2","3","4","5").iterator();
		List<Integer> firstThree = text.map(Integer::new).drop(3).into(new ArrayList<Integer>());
		assertThat(firstThree, contains(4,5));
	}
	
	@Test
	public void testTakingStream() {
		Stream<String> text = new BasicStream<>(asList("1","2","3","4","5"));
		List<Integer> firstThree = text.map(Integer::new).take(3).into(new ArrayList<Integer>());
		assertThat(firstThree, contains(1,2,3));
	}
	
	@Test
	public void testFilterMapIntoStream() {
		
		Stream<String> text = new BasicStream<>(asList("1","2","3","4","5"));
		Stream<Integer> onlyOdds = text.map(Integer::new).filter(n -> n % 2 != 0);
		
		//eager
		List<Integer> odds = onlyOdds.into(new ArrayList<Integer>());
		assertThat(odds, contains(1,3,5));
	}
	
	
	
	@Test
	public void testFilterMapFirstStream() {
		
		Stream<String> text = new BasicStream<>(asList("0","1","2","3","4","5"));
		
		Integer firstOdd = text.map(Integer::new).filter(n -> n % 2 != 0).getFirst();
		assertThat(firstOdd, is(1));
		
	}
	
	interface Stream<T> {
		
		default Stream<T> filter(Predicate<T> predicate) {
			return () -> Iterators.filter(this.iterator(), predicate);
		}
		
		default <U> Stream<U> map(Function<T,U> mapper) {
			return () -> Iterators.map(this.iterator(), mapper);
		}
		
		default <C extends Collection<T>> C into(C sink) {
			return Iterators.into(this.iterator(), sink);
		}
		
		default T getFirst() {
			return Iterators.getFirst(this.iterator());
		}
		
			
		public default Stream<T> take(int count) {
			return () -> Iterators.take(this.iterator(), count);
		}
		
		public default Stream<T> drop(int count) {
			return () -> Iterators.drop(this.iterator(), count);
		}
		
		//public Stream<T> takeWhile(Predicate<T> predicate);
		//public Stream<T> dropWhile(Predicate<T> predicate);
		//public T reduce(T seed, BinaryOperator<T> operator);
		
		Iterator<T> iterator();
	}
	
	static class BasicStream<T> implements Stream<T> {
		private final Collection<T> items;
		
		BasicStream(Collection<T> items){
			this.items = items;
		}
		
		@Override
		public Iterator<T> iterator() {
			return this.items.iterator();
		}
	}
	
	
	
	static class Iterators {
		
		//Lazy
		static <T,U> Iterator<U> map(Iterator<T> source, Function<T,U> mapper) {
			return new Iterator<U>(){
				
				@Override
				public boolean hasNext() {
					return source.hasNext();
				}
				
				@Override
				public U next() {
					return mapper.apply(source.next());
				}
				
			};
		}
		
		//Lazy
		static <T> Iterator<T> filter(Iterator<T> source, Predicate<T> predicate) {
			return new Iterator<T>() {
				
				private T next;
				
				@Override
				public boolean hasNext() {
					while(source.hasNext()){
						next = source.next();
						if(predicate.test(next)) {
							return true;
						}
					}
					next = null;
					return false;
					
				}
				
				@Override
				public T next() {
					if(next != null || hasNext()){
						T result = next;
						next = null;
						return result;
					}
					throw new NoSuchElementException();
				}
				
			};
		}
		
		static <T> Iterator<T> take(Iterator<T> source, int count) {
			return new Iterator<T>() {
				
				private int spent = 0;
				
				@Override
				public boolean hasNext() {
					return spent < count && source.hasNext();
				}
				
				@Override
				public T next() {
					if(spent < count){
						T result = source.next();
						spent++;
						return result;
					}
					throw new NoSuchElementException();
				}
				
			};
		}
		
		static <T> Iterator<T> drop(Iterator<T> source, int count) {
			return new Iterator<T>() {
				
				private int spent = 0;
				
				@Override
				public boolean hasNext() {
					while(spent < count && source.hasNext()){
						spent++;
						source.next();
					}
					return spent == count && source.hasNext();
				}
				
				@Override
				public T next() {
					if(spent == count || hasNext()){
						return source.next();
					}
					throw new NoSuchElementException();
				}
				
			};
		}
		
		//Eager
		static <E, C extends Collection<E>> C into(Iterator<E> source, C sink) {
			while(source.hasNext()){
				sink.add(source.next());
			}
			return sink;
		}
		
		//Eager and short-circuit
		static <T> T getFirst(Iterator<T> source) {
			if(source.hasNext()){
				return source.next();
			}
			return null;
		}
		
		//prevents instantiation
		private Iterators() {}
	}
}

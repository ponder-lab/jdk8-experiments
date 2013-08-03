package codemasters.lambda.lazy;

import java.util.*;
import java.util.function.*;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class Demo1 {
	
	@Test
	public void testApplicativeOrder() {
		int sum = sum(getOne(), getTwo());
		assertThat(sum, is(3));
	}
	
	int sum(int param1, int param2) {
		System.out.println("summing");
		return param1 + param2;
	}
	
	int getOne() {
		System.out.println("one");
		return 1;
	}
	
	int getTwo() {
		System.out.println("two");
		return 2;
	}

	@Test	
	public void testNormalOrder() {
		
		int sum = sum( () -> getOne(), () -> getTwo() );
		assertThat(sum, is(3));
		
	}
	
	int sum(Lazy<Integer> one, Lazy<Integer> two) {
		System.out.println("summing");
		return one.get() + two.get();
	}
	
	interface Lazy<T> {
		T get();
	}
	
	
	@Test
	public void testFilterIterator() {
		
		List<Integer> myInts = asList(1,2,3,4,5,6,7,8,9,0);
		Predicate<Integer> isOdds = n -> n % 2 != 0;
		
		Iterator<Integer> onlyOddsIter =  Iterators.filter(myInts.iterator(), isOdds);
		
		List<Integer> result = Iterators.into(onlyOddsIter, new ArrayList<Integer>());
		assertThat(result, contains(1,3,5,7,9));
		
		/*
		assertTrue(onlyOddsIter.hasNext());
		assertThat(onlyOddsIter.next(), is(1));
		assertThat(onlyOddsIter.next(), is(3));
		assertThat(onlyOddsIter.next(), is(5));
		assertThat(onlyOddsIter.next(), is(7));
		assertThat(onlyOddsIter.next(), is(9));
		assertFalse(onlyOddsIter.hasNext());
		*/
		
	}
	
	@Test 
	public void testMapIterator() {
		
		List<String> text = asList("1","2","3","4","5");
		Function<String,Integer> mapper = Integer::new;
		
		Iterator<Integer> asInts = Iterators.map(text.iterator(), mapper);
		List<Integer> result = Iterators.into(asInts, new ArrayList<Integer>());
		
		assertThat(result, contains(1,2,3,4,5));
		
		
		/*
		assertTrue(asInts.hasNext());
		assertThat(asInts.next(), is(1));
		assertThat(asInts.next(), is(2));
		assertThat(asInts.next(), is(3));
		assertThat(asInts.next(), is(4));
		assertThat(asInts.next(), is(5));
		assertFalse(asInts.hasNext());
		*/
		
		
		
	}
	
	@Test
	public void testStream() {
		
		Stream<String> text = new BasicStream<String>(asList("1","2","3","4","5"));
		List<Integer> onlyOdds = text.map(Integer::new).filter(n -> n % 2 != 0).into(new ArrayList<Integer>());
		assertThat(onlyOdds, contains(1,3,5));
		
	}
	
	public interface Stream<T> {
		
		
		public default Stream<T> filter(Predicate<T> predicate) {
			return Streams.filter(this.iterator(), predicate);
		}
		
		public default  <U> Stream<U> map(Function<T,U> mapper) {
			return Streams.map(this.iterator(), mapper);
		}
		
		public default <C extends Collection<T>> C into(C sink) {
			return Streams.into(this.iterator(), sink);
		}
		
		public Iterator<T> iterator();
	}
	
	public static class Streams {
		
		public static <T> Stream<T> filter(Iterator<T> iter, Predicate<T> predicate) {
			return new Stream<T>() {
				@Override
				public Iterator<T> iterator() {
					return Iterators.filter(iter, predicate);
				}
			};
		}
		
		public static <T,U> Stream<U> map(Iterator<T> iter, Function<T,U> mapper) {
			return new Stream<U>() {
				
				@Override
				public Iterator<U> iterator() {
					return Iterators.map(iter, mapper);
				}
				
			};
		}
		
		public static <E, C extends Collection<E>> C into(Iterator<E> iter, C sink) {
			return Iterators.into(iter, sink);
		}
		
	}
	
	
	
	public static class BasicStream<T> implements Stream<T> {
		private final Collection<T> items;
		
		public BasicStream(Collection<T> source) {
			this.items = source;
		}
		
		public Iterator<T> iterator() {
			return this.items.iterator();
		}
		
	}
	
	
	private static class Iterators {
		
		public static <T> Iterator<T> filter(Iterator<T> source, Predicate<T> predicate) {
			return new Iterator<T>() {
				
				private T next;
				
				public boolean hasNext() {
					while(source.hasNext()) {
						next = source.next();
						if(predicate.test(next)) {
							return true;
						}
					}
					return false;
				}
				
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
		
		public static <T,U> Iterator<U> map(Iterator<T> source, Function<T,U> mapper) {
			return new Iterator<U>(){
				
				public boolean hasNext() {
					return source.hasNext();
				}
				
				public U next() {
					return mapper.apply(source.next());
				}
			};
		}
		
		
		public static <E, T extends Collection<E>> T into(Iterator<E> source, T sink) {
			while(source.hasNext()){
				sink.add( source.next() );
			}
			return sink;
		}
		
	}
}

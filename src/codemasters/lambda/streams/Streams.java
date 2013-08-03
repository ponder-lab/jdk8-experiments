package codemasters.lambda.streams;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.BinaryOperator;

public class Streams {
	
	//Prevents instantiation
	private Streams() { } 
	
	public static <C extends Collection<E>, E> C into(Streamable<E> source, C sink){
		return Iterators.into(source.iterator(), sink);
	}
	
	public static <T> T reduce(Streamable<T> source, T seed, BinaryOperator<T> operator) {
		return Iterators.reduce(source.iterator(), seed, operator);
	}
	
	public static <T> T reduce(Streamable<T> source,  BinaryOperator<T> operator) {
		return Iterators.reduce(source.iterator(), operator);
	}
	
	public static <T> T max(Streamable<T> source, Comparator<T> comparator) {
		return Iterators.max(source.iterator(), comparator);
	}
	
	public static <T> T min(Streamable<T> source, Comparator<T> comparator) {
		return Iterators.min(source.iterator(), comparator);
	}
	
	public static <T extends Comparable<? super T>> T max(final Streamable<T> source) {
		return Iterators.max(source.iterator());
	}
	
	public static <T extends Comparable<? super T>> T min(final Streamable<T> source) {
		return Iterators.min(source.iterator());
	}
	
	public static <E> E getFirst(final Streamable<E> source) {
		return Iterators.getFirst(source.iterator());
	}
	
	public static <E> E getLast(final Streamable<E> source) {
		return Iterators.getLast(source.iterator());
	}
	
	public static <S,D> Stream<D> map(final Streamable<S> source, final Function<S,D> mapper) {
		return new Stream<D>() {
			
			@Override
			public Iterator<D> iterator() {
				return Iterators.map(source.iterator(), mapper);
			}
		};
		
	}
	
	public static <E> Stream<E> filter(final Streamable<E> source, final Predicate<E> predicate) {
		return new Stream<E>() {
			
			@Override
			public Iterator<E> iterator() {
				return Iterators.filter(source.iterator(), predicate);
			}
		};
	}
	
	public static <E> Stream<E> takeWhile(final Streamable<E> source, final Predicate<E> predicate) {
		return new Stream<E>() {
			@Override
			public Iterator<E> iterator() {
				return Iterators.takeWhile(source.iterator(), predicate);
			}
		};
	}
	
	public static <E> Stream<E> dropWhile(final Streamable<E> source, final Predicate<E> predicate) {
		return new Stream<E>() {
			@Override
			public Iterator<E> iterator() {
				return Iterators.dropWhile(source.iterator(), predicate);
			}
		};
	}
	
	public static <E> Stream<E> wrap(Collection<E> items) {
		return new WrapperStream<>(items);
	}
	
	private static class WrapperStream<E> implements Stream<E> {
		private Collection<E> items;
		
		WrapperStream(Collection<E> items) {
			this.items = items;
		}
		
		@Override
		public Iterator<E> iterator() {
			return items.iterator();
		}
	}
}



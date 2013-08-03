package codemasters.lambda.streams;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.BinaryOperator;

public interface Stream<E> extends Streamable<E> {
	
	public default Stream<E> dropWhile(Predicate<E> predicate) {
		return Streams.dropWhile(this, predicate);
	}
	
	public default Stream<E> takeWhile(Predicate<E> predicate) {
		return Streams.takeWhile(this, predicate);
	}
	
	public default Stream<E> filter(Predicate<E> predicate) {
		return Streams.filter(this, predicate);
	}
	
	public default <D> Stream<D> map(Function<E,D> mapper) {
		return Streams.map(this, mapper);
	}
	
	public default E getFirst() {
		return Streams.getFirst(this);
	}
	
	public default E getLast() {
		return Streams.getLast(this);
	}
	
	public default <C extends Collection<E>> C into(C sink) {
		return Streams.into(this, sink);
	}
	
	public default E reduce(E seed, BinaryOperator<E> operator) {
		return Streams.reduce(this, seed, operator);
	}
	
	public default E reduce(BinaryOperator<E> operator) {
		return Streams.reduce(this, operator);
	}
	
	public default E max(Comparator<E> comparator) {
		return Streams.max(this, comparator);
	}
	
	public default E min(Comparator<E> comparator) {
		return Streams.min(this, comparator);
	}
}

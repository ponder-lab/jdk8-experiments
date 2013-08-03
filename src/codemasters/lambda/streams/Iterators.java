package codemasters.lambda.streams;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.NoSuchElementException;

public final class Iterators {
	
	//Prevents Instantiation.
	private Iterators() { }
	
	public static <C extends Collection<E>, E> C into(Iterator<E> source, C sink){
		while(source.hasNext()){
			sink.add(source.next());
		}
		return sink;
	}
	
	public static <E> E reduce(final Iterator<E> source, final E seed, final BinaryOperator<E> operator) {
		if(source != null){
			E accum = seed;
			while(source.hasNext()){
				accum = operator.apply(accum, source.next());
			}
			return accum;
		}
		return null;
	}
	
	public static <E> E reduce(final Iterator<E> source, final BinaryOperator<E> operator) {
		if(source != null) {
			if(source.hasNext()){
				E seed = source.next();
				return reduce(source, seed, operator);
			}
			throw new NoSuchElementException();
		}
		return null;
	}
	
	public static <E> E max(final Iterator<E> source, final Comparator<E> comparator) {
		return reduce(source, (x, y) -> comparator.compare(x, y) > 0 ? x : y);
	}
	
	public static <E> E min(final Iterator<E> source, final Comparator<E> comparator) {
		return reduce(source, (x, y) -> comparator.compare(x, y) > 0 ? y : x);
	}
	
	public static <E extends Comparable<? super E>> E max(final Iterator<E> source) {
		return reduce(source, (x , y) -> x.compareTo(y) > 0 ? x : y);
	}
	
	public static <E extends Comparable<? super E>> E min(final Iterator<E> source) {
		return reduce(source, (x , y) -> x.compareTo(y) > 0 ? y : x);
	}
	
	public static <E> E getFirst(final Iterator<E> source) {
		if(source != null){
			if(source.hasNext()){
				return source.next();
			}else {
				throw new NoSuchElementException();
			}
		}
		return null;
	}
	
	public static <E> E getLast(final Iterator<E> source) {
		if(!source.hasNext()){
			throw new NoSuchElementException();
		}
		E last = null;
		do {
			last = source.next();
		} while( source.hasNext() );
		
		return last;
	}
	
	public static <E> void forEach(final Iterator<E> source, final Consumer<E> block) {
		while(source.hasNext()) {
			E next = source.next();
			block.accept(next);
		}
	}
	
	public static <S,D> Iterator<D> map(final Iterator<S> source, final Function<S,D> mapper) {
		return new Iterator<D>(){
			
			@Override
			public boolean hasNext() {
				return source.hasNext();
			}
			
			@Override
			public D next() {
				return mapper.apply(source.next());
			}
		};
	}
	
	public static <E> Iterator<E> filter(final Iterator<E> source, final Predicate<E> predicate) {
		return new Iterator<E>() {
			
			private boolean found;
			private E finding;
			
			@Override
			public boolean hasNext(){
				while(!found && source.hasNext()){
					finding = source.next();
					found = predicate.test(finding);
				}
				return found;
			}
			
			@Override
			public E next() {
				if(found || hasNext()){
					found = false;
					return finding;
				}
				throw new NoSuchElementException();
			}
		};
	}
	
	public static <E> Iterator<E> takeWhile(final Iterator<E> source, final Predicate<E> predicate) {
		return new Iterator<E>(){
			
			private E next;
			private boolean found = true;
			
			@Override
			public boolean hasNext() {
				if(found && source.hasNext()) {
					next = source.next();
					found = predicate.test(next);
				}
				return found;
			}
			
			@Override
			public E next() {
				if( (found && next != null) || hasNext() ){
					return next;
				}
				throw new NoSuchElementException();
			}
		};
	}
	
	public static <E> Iterator<E> dropWhile(final Iterator<E> source, final Predicate<E> predicate) {
		return new Iterator<E>(){
			
			private E next;
			private boolean found;
			
			@Override
			public boolean hasNext() {
				if(!found) {
					while(!found && source.hasNext()){
						next = source.next();
						found = !predicate.test(next);
					}
					return found;
				} else {
					if(source.hasNext()){
						next = source.next();
						return true;
					}
				}
				return false;
			}
			
			@Override
			public E next() {
				if( (found && next != null) || hasNext() ){
					E result = next;
					next = null;
					return result;
				}
				throw new NoSuchElementException();
			}
		};
	}
}

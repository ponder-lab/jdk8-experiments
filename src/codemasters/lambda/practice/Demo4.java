package codemasters.lambda.practice;

import java.util.function.*;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Stream;
import static java.util.Arrays.asList;
import static java.util.Arrays.asList;
import static java.lang.System.out;

public class Demo4 {
	
	public static void main(String[] args) {
		
		Stream<Integer> test = asList(1,2,3,4,5,6).stream();
		Integer max = asList(1,2,3,4,5,6).parallelStream().max(Integer::compare).get();
		Integer min = asList(1,2,3,4,5,6).parallelStream().min(Integer::compare).get();
		
		System.out.printf("Min: %d -  Max: %d%n", min,max);
		
		/*
		Stream<Integer> s = repeatedly(()-> 10);
		s.limit(10).forEach( out::println );
		
		
		Stream<Integer> s = sieve1(iterate(2, n -> n + 1),18);
		s.forEach( n -> { System.out.print( n +" "); } );
		
		
		System.out.printf("%nListing stream t:%n");
		Stream<Integer> t = sieve2(iterate(2, n -> n + 1).limit(10));
		t.forEach( n -> { System.out.print( n +" "); } );
		
		System.out.println();
		Stream<Integer> infinite = iterate(2, n -> n + 1);
		
		
		
		Iterable<String> obiwan = () -> { System.out.println("obi-wan");
										return asList("Obi-wan").iterator(); };
												
										
		Iterable<String> luke = () -> { System.out.println("luke");
										return asList("Luke").iterator(); };
		
		System.out.println("Before tail");
		//Stream<String> tail = cycle(obiwan).concat( cycle(luke) ) ;
		System.out.println("After tail");
		
		//Stream<String> h = repeatedly(1, ()-> "Anakin").concat(tail);
		
		//h.findFirst();						
				
		
		//Optional<Integer> sum = iterate(1, n -> n + 1).filter(n -> n < 1000).filter(n -> n % 3 == 0 || n % 5 == 0).reduce( (x,y) -> x + y);
		Optional<Integer> sum = iterate(1, n -> n + 1).limit(1000).filter(n -> n % 3 == 0 || n % 5 == 0).reduce( (x,y) -> x + y);
		System.out.println(sum.get());
		
		*/
	}
	
	
	public static Stream<Integer> sieve0(Stream<Integer> s, int n) {
		List<Integer> primes = new LinkedList<>();
		for(int i = 0; i < n; i++) {
			int prime = s.findFirst().get();
			primes.add(prime);
			s = s.filter(d -> d % prime != 0);
		}
		return primes.stream();
	}
	/*
	public static Stream<Integer> sieve1(Stream<Integer> s, int n) {
		if(n == 0) return s;
		else {
			int prime = s.findFirst().get();
			return repeatedly(1, () -> prime).concat( sieve1( s.filter( x -> x % prime != 0 ), n-1) ).limit(n); 
		}
	}*/
	
	/*
	public static Stream<Integer> sieve2(Stream<Integer> s) {
		
		Optional<Integer> head = s.findFirst();
		if(!head.isPresent()) return s;
		else {
			int prime = head.get();
			Stream<Integer> tail = s.filter( x -> x % prime != 0 );
			return repeatedly(1, () -> prime).concat( sieve2( tail ) );
		}
	}
	*/	
	
	
	
	
}

package codemasters.lambda.testdrives;

import java.util.stream.*;
import java.util.List;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.Before;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.IsEqual.equalTo;

public class IterableTest {
	
	private List<Integer> allInts;
	
	@Before
	public void before() {
		allInts = asList(0,1,2,3,4,5,6,7,8,9);
	}
	
	@Test
	public void testFiltering() {
		
		Stream<Integer> onlyOdds = allInts.stream().filter( x -> x % 2 != 0 );
		assertThat( () -> onlyOdds.iterator(), contains(1,3,5,7,9) );
	}
	
	@Test
	public void testMatches() {
		assertTrue( allInts.stream().allMatch( x -> x >= 0 ) );
		//assertThat( allInts.count(), is( equalTo( 10L ) ) ); 
	}
	
	/*
	@Test
	public void testReduction() {
		Stream<Integer> acc = allInts.stream().filter( x -> x <= 5 )
									   .cumulate( (x , y) -> x + y );
		assertThat(() -> acc.iterator(), contains(0, 1, 3, 6, 10, 15) );
	}
	*/
	
	@Test
	public void testFlatMap() {
		/*
		List<String> shopList = asList("apples,grapes,pineapple,lemons,oranges","tuna,fries,rice,beans");
		
		List<String> list = shopList.stream().flatMap( joined -> asList( joined.split(",") ) )
								    .into(new ArrayList<String>());
								    
		assertThat(list, contains("apples","grapes","pineapple","lemons","oranges","tuna","fries","rice","beans") );
		*/
		/*
		//review recursion.
		java.util.functions.Mapper<Integer, Integer> fib = x -> {
			if ( x == 0 ) return 0;
			if ( x == 1 ) return 1;
			return fib.map( fib.map(x - 1) );
		};
		*/
	}
	
	@Test
	public void testForEach() {
		
		List<Integer> fibs = new ArrayList<>();
		allInts.stream().filter(n -> n <= 5).forEach( n -> { fibs.add(fibonacci(n)); });
		assertThat(fibs, contains(0, 1, 1, 2, 3, 5));
			
			
	}
	
	private static int fibonacci(int n){
		if ( n == 0 ) return 0;
		if ( n == 1 ) return 1;
		return fibonacci(n-1) + fibonacci(n-2);
	}
	
}

package codemasters.lambda.testdrives;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Arrays.asList;
//import static java.util.Arrays.parallel;

public class RandomTest {
	
	/*
	public static void main(String[] args) {
		
		//List<String> text = asList("1","2","3","4","5","6","7","8","9","0");
		//ParallelIterable<String> text = parallel(new String[]{"1","2","3","4","5","6","7","8","9","0"});
		
		List<Integer> odds = //asList("1","2","3","4","5","6","7","8","9","0")
							 parallel(new String[]{"1","2","3","4","5","6","7","8","9","0"})
							.map( x -> Integer.valueOf(x) )
							.filter( x -> x % 2 != 0)
							.into(new ArrayList<Integer>());
							
		odds.sort(Integer::compare);
		System.out.println(odds);
		
		List<String> shopList = asList("apples,grapes,pineapple,lemons,oranges","tuna,fries,rice,beans");
		//List<String> list = shopList.flatMap( joined -> asList( joined.split(",") ) ).into(new ArrayList<String>());
		//System.out.println(list);
		
	}
	*/
	
}


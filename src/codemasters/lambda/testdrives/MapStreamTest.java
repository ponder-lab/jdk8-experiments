package codemasters.lambda.testdrives;

import java.util.*;
import java.util.concurrent.*;

import static java.util.Arrays.asList;

public class MapStreamTest {
	
	public static void main(String[] args) {
		testIntoMulti();
	}
	
	public static void testIntoMulti(){
		
		Map<Integer, Collection<String>> inLetters1 = new LinkedHashMap<>();
		inLetters1.put(1, new CopyOnWriteArrayList<String>(new String[]{"one","uno"}));
		inLetters1.put(2, new CopyOnWriteArrayList<String>(new String[]{"two","dos"}));
		inLetters1.put(3, new CopyOnWriteArrayList<String>(new String[]{"three","tres"}));
		inLetters1.put(4, new CopyOnWriteArrayList<String>(new String[]{"four","cuatro"}));
		inLetters1.put(5, new CopyOnWriteArrayList<String>(new String[]{"five","cinco"}));
		
		Map<Integer, String> inFrench = new LinkedHashMap<>();
		inFrench.put(3, "trois");
		inFrench.put(4, "quatre");
		inFrench.put(5, "cinq");
		inFrench.put(6, "six");
		inFrench.put(7, "sept");
		
		//inFrench.intoMulti(inLetters1, () -> (Collection<String>) new CopyOnWriteArrayList<String>());
		
		System.out.println(inLetters1);
		
		
		
		
		
		
		
	}
	
}

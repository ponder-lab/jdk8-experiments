package codemasters.lambda.demo;

import java.util.function.*;

public class Demo4 {
	
	public static void main(String[] args) {
		
		Predicate<Integer> isOdd = n -> n % 2 != 0;
		isOdd.test(19);
		
		System.out.println(isOdd.getClass());
	}
	
}

package codemasters.lambda.showtime;

import java.util.*;
import java.util.function.*;
import java.util.concurrent.*;
import org.junit.Test;
import static java.lang.System.out;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;


public class Demo2 {
	
	public void typesOfLambdaExpressions() throws Exception  {
		
		Predicate<Integer> isOdd = n -> n % 2 != 0; //assignment
		BinaryOperator<Integer> sum = (left, right) -> left + right;
		Callable<String> sayHello = () -> "Hello World";
		Consumer<String> printer = text -> { System.out.println(text); };
		Runnable runner = () -> { System.out.println("Hello world"); };
		
		doAction( () -> "Hello World" ); //parameter
		Runnable[] task = { ()-> {}, ()-> {} };
		Predicate<Integer> p = 'a' > 'b' ? n -> n % 2 != 0 : n -> n > 0;
		
	}
	
	public static <T> Callable<T> createAction(T value) {
		return () -> value; //return value
	}
	
	public static <T> T doAction(Callable<T> task) throws Exception {
		return task.call();
	}
	
	
	
	
	
	
}

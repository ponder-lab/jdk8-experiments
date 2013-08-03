package codemasters.lambda.practice;

import java.util.*;
import java.util.function.*;
import java.util.concurrent.*;
import org.junit.Test;

import static java.lang.System.out;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class Demo1 {
	
	//Types of Lambda Expressions
	public void reviewTypesOfLambdaExpressions() {
		
		//assigned to a variable
		Predicate<Integer> isOdd = n -> n % 2 != 0;
		BinaryOperator<Integer> sum = (left, right) -> left + right;
		Callable<String> sayHello = () -> "Hello World";
		Consumer<String> printer = text -> { System.out.println(text); };
		Runnable runner = () -> { System.out.println("Hello World!"); };
	}
	
	//Use of lambda expressions
	public static <T> T doAction(Callable<T> task) throws Exception {
		return task.call();
	}
	
	public static <T> Callable<T> createPrinterAction(T value) {
		return () -> value; //as return object 
	}
	
	public void reviewLambdaUsage() throws Exception {
		doAction( () -> "Hello World" ); //passed as parameter
		Runnable[] tasks =  { () -> {out.println("Hello");}, () -> {out.println("World");} }; //arrays initializers.
		Predicate<Integer> p = 'a' > 'b' ? n -> n % 2 == 0 : n -> n >= 100; //ternary conditional operators.
	}
}

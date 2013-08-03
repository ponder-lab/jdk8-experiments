package codemasters.lambda.questions;

import codemasters.lambda.domain.*;
import java.util.*;
import java.util.function.*;
import java.lang.reflect.*;
import org.junit.Test;
import static java.lang.System.out;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class Answers {
	
	Consumer<Car> printer = out::println;
	
	@Test
	public void testLambda() throws Exception {
		
		printClass(printer.getClass());
	}
	
	
	public static void printClass(Class<?> klass) {
		out.println("Class:");
		out.println(klass);
		out.println();
		out.println("Extends: " + klass.getSuperclass());
		out.println();
		out.println("Implements:");
		for(Class<?> implemented : klass.getInterfaces()){
			out.println(implemented);
		}
		out.println();
		out.println("Fields:");
		out.println();
		for(Field field : klass.getDeclaredFields()) {
			out.println(field);
		}
		out.println();
		out.println("Constructors:");
		for(Constructor constructor : klass.getConstructors()){
			out.println(constructor);
		}
		out.println();
		out.println("Methods:");
		out.println();
		for(Method method : klass.getDeclaredMethods()){
			out.println(method);	
		}
	}
	
}
/*
class Answers$$Lambda$2 extends java.lang.invoke.MagicLambdaImpl implements Block{
	private final java.io.PrintStream arg$1;
	 
	public Answers$$Lambda$1(java.io.PrintStream out) {
		this.arg$1 = out;
	}
	
	public void apply(Object object) {
		
	}
}
*/

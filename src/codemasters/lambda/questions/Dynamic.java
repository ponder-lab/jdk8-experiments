package codemasters.lambda.questions;

import java.lang.reflect.*;
import java.lang.invoke.*;

public class Dynamic {
	
	public static void main(String args[]) {
		
		if(args.length == 0){
			args = new String[]{ "world" };
		}
		greeter(args[0] +  " (from statically linked call site)");
		
		try {
			for( String arg : args ) {
				greeter.invoke(arg);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
	}
	
	static void greeter(String x) {
		System.out.println("Hello " + x);
	}
	
	static MethodHandle greeter;
	static {
		try {
			greeter = MethodHandles.lookup().findStatic(Dynamic.class, "greeter", 
				MethodType.methodType(void.class, String.class));
		}catch(NoSuchMethodException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	private static CallSite bootstrapDynamic(MethodHandles.Lookup caller, String name, MethodType type) {
		return new ConstantCallSite(greeter.asType(type));
	}
}

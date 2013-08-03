package codemasters.lambda.hackday;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;

public class Memoization {
	
	
	public static class Memo<T> implements Supplier<T> {
		
		private T value;
		private Supplier<T> thunk;
		
		public Memo(Supplier<T> thunk) {
			this.thunk = thunk;
		}
		
		public T get() {
			synchronized(this) {
				if(value == null) {
					value = thunk.get(); 
				}
			}
			return value;
		}
	}
	
	public static <T> Supplier<T> memoize(Supplier<T> thunk) {
		return new Memo<>(thunk);
	}
	
	public static int calculateX() {
		System.out.println("Calculate x");
		return 1;
	}
	
	public static int calculateY() {
		System.out.println("Calculate y");
		return 2;
	}
	
	
	public static int calculate(Supplier<Integer> thunkX, Supplier<Integer> thunkY) {
		if(thunkX.get() == 0) {
			return 0;
		} else  {
			return thunkX.get() + thunkY.get();
		}
	}
	
	private static Map<Integer,Long> memo = new HashMap<>();
	static {
		memo.put(0,0L);
		memo.put(1,1L);
	}
	
	
	public static long fibonacci(int x) {
		return memo.computeIfAbsent(x, n -> Math.addExact(fibonacci(n-1), fibonacci(n-2)));
	}
	
	
	
	public static long fibonacci0(int x) {
		if(x==0 || x==1) 
			return x;
		return fibonacci0(x-1) + fibonacci0(x-2);
	}
	
	
	final static double fiveSqrt = Math.sqrt(5.0);
	final static double fi =  (1.0 + fiveSqrt) / 2.0;
	final static double psi = (1.0 - fiveSqrt) / 2.0;
	
	public static long fib(int x) {
		return Math.round((Math.pow(fi,x) - Math.pow(psi,x)) / fiveSqrt);
	}
	
	
	
	
	public static void main(String[] args) {
		
		//7540113804746346429
		//7540113804746346429 
		System.out.println(fibonacci(92));
		
		for(int i=0; i<=100; i++){
			try {
				long res01 = fibonacci(i);
			}catch(Exception e) {
				System.out.println(i);
				e.printStackTrace();
				System.exit(0);
			}
		}
		
		
			
	}
	
	
	
}

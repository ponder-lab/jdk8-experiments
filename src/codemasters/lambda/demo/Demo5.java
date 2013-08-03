package codemasters.lambda.demo;

public class Demo5 {
	
	interface A {
		
		static void print() {
			System.out.println("A");
		}
		
		default void foo() {
			print();
		}
	}
	
	interface B extends A {
		default void foo() {
			A.super.foo();
		}
	}
	
	static class Sample implements A, B {
		public void foo() {
			B.super.foo();
		}
	}
	
	public static void main(String[] args) {
		Sample sample = new Sample();
		sample.foo();
	}
	
}

package codemasters.lambda.jug;

import codemasters.lambda.domain.*;
import java.util.*;
import static java.util.Arrays.asList;

public class Prep01 {
	
	
	public static <T> List<T> filter(List<T> source, Predicate<T> pred) {
		List<T> dest = new ArrayList<>();
		for(T item : source ){
			if(pred.test(item)) {
				dest.add(item);
			}
		}
		return dest;
	}
	
	public interface Predicate<T> {
		public boolean test(T t);
	}
	
	public static <T,R> List<R> map(List<T> source, Function<T,R> fun) {
		List<R> dest = new ArrayList<>();
		for(T item : source) {
			R mapped = fun.apply(item);
			dest.add(mapped);
		}
		return dest;
	}
	
	
	public interface Function<T,R> {
		public R apply(T t);
	}
	
	
	public interface BinaryOperator<T> {
		public T apply(T left, T right);
	}
	
	public static <T> T reduce(List<T> source, T acc, BinaryOperator<T> op) {
		for(T item : source) {
			acc = op.apply(acc,item); 
		}
		return acc;
	}
	
	
	public interface Supplier<T>  {
		public T get();
	}
	
	public interface Consumer<T> {
		public void accept(T t);
	}
	
	
	public static <T> void forEach(List<T> items, Consumer<T> consumer) {
		for(T item: items) {
			consumer.accept(item);
		}
	}
	
	public static void main(String[] args) {
		Car yaris = new Car("Toyota","Yaris", 2008, 15000);
		Car mx5 = new Car("Mazda", "MX-5", 2009, 45000);
		Car equator = new Car("Susuki","Equator", 2009, 36000);
		Car x6 = new Car("BMW","X6", 2011, 88000);
		Car civic = new Car("Honda","Civic", 2012, 30000);
		Car tida = new Car("Nissan","Tida", 2013, 35000);
		
		List<Car> cars = new ArrayList<>(asList(tida, civic,x6,equator,mx5,yaris));
		
		List<String> brands = map(cars, car -> car.getBrand());
		List<Car> after2010 = filter(cars, car -> car.getYear() > 2010);
		Double priceSum = reduce(map(filter(cars, car -> car.getYear() >= 2012), car -> car.getOriginalValue()), 0.0, (p1, p2) -> p1+p2);
	
		cars.sort(Car::compareByYear);
		System.out.println(cars);
		
		Function<Car, String> getBrand = Car::getBrand;
		System.out.println(getBrand.apply(civic));
		System.out.println(getBrand.apply(tida));
		System.out.println(map(cars, Car::getBrand));
		
		
		//public void println(Object o);
		//public void accept(String s);
		//Consumer<String> printer = (String s) -> { System.out.println(s); };
		Consumer<String> printer = System.out::println;
		printer.accept("Hello World");
		forEach(cars, System.out::println);
		
		
		/*
		Double priceSum = 	reduce(
								map(filter(cars, car -> car.getYear() >= 2012), car -> car.getOriginalValue())
								0.0,
								(price1, price2) -> price1 + price2);
								*/
		
		
		System.out.println(brands);
		System.out.println(after2010);
		System.out.println(priceSum);
		
	}
		

	
	public static void test01(String[] args) {
		
		Predicate<Integer> odd = n -> n % 2 != 0;
		BinaryOperator<Integer> summation = (a, b) -> a + b;
		Supplier<String> greets = () -> "Hello World";
		Consumer<String> printer = s -> { System.out.println(s); };
		
		Runnable r = () -> {
			System.out.println("Hello world");
			System.out.println("Welcome lambdanians!");
			System.out.println("This is the CR JUG");
			System.out.println("Good-bye World");
		};
		
		new Thread(r).start();
		
		
		List<Integer> possiblePrimes = asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17);
		
		List<Integer> primes = filter(possiblePrimes, (n) -> {
			if(n < 2) return false;
			if (n == 2) return true;
			
			for(int i = 2; i < n; i++) {
				if(n % i == 0) {
					return false;
				} else if (i * i > n) {
					return true;
				}
			}
			return true;
		});
		
		System.out.println(primes);
		
		
		
		printer.accept("Good-bye world");
		List<Integer> others = asList(1,2,3,4,5);
		Integer sum = reduce(others, 0, (left, right) -> left + right);
		Integer fact = reduce(others, 1, (left, right) -> left * right);
		
		System.out.println("The sum of 1,2,3,4,5 is " + sum);
		System.out.println("The sum factorial of 5 is " + fact);
		
		List<Integer> nums = asList(0,1,2,3,4,5,6,7,8,9);
		List<Integer> evens = filter(nums, n -> n % 2 != 0);
		System.out.println(evens);
		
		List<String> strNums = asList("0","1","2","3","4","5","6","7","8","9");
		List<Integer> moreNums = map( 
									map(strNums, val -> Integer.valueOf(val) ),
									n -> n *n
									);
		System.out.println(moreNums);
		
	}
}

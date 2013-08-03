package codemasters.lambda.showtime;

import codemasters.lambda.domain.*;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.BinaryOperator;
import java.util.function.DoubleFunction;
import java.util.function.Consumer;
import org.junit.Test;
import org.junit.Before;
import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import org.hamcrest.collection.IsIterableContainingInOrder;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

import static org.hamcrest.streams.IsStreamContainingInOrder.contains;

public class Demo1 {
	
	public static void main(String[] args) {
	}
	
	public void test() {
		
		CarHolder holder = new CarHolder();
		
		Function<Car,String> mapper = holder::getBrand;
		
		mapper.apply(yaris);
		mapper.apply(mx5);
		
		System.out.println(holder.brands);
		Consumer<Car> block = System.out::println;
		cars.forEach( System.out::println );
		Stream<Integer> myInts = asList("1","2","3").stream().map(s -> Integer.valueOf(s));
	}
	
	Predicate<Integer> isDivisible(int divisor) { return n -> n % divisor == 0; }
    
    public static class CarHolder {
    	
    		List<String> brands = new ArrayList<>();
    	
    		public String getBrand(Car car) {
    			String brand = car.getBrand();
    			brands.add(brand);
    			return brand;
    		}
    	
    }
	
	public Callable<String> getGreetings(int businessHour) {
		return businessHour < 12 ? () -> "Good morning" : () -> "Good afternoon";
	}
	
	@Test
	public void testIterableFilter() {
		
		Stream<Car> oldest = cars.filter( car -> car.getYear() <= 2010 );
		assertThat(oldest, contains(yaris, mx5, equator) );

	}
	
	@Test
	public void testIterableMap(){
		Stream<String> brands = cars.map( car -> car.getBrand() );
		assertThat(brands, contains("Toyota","Mazda","Susuki","BMW", "Honda", "Nissan") );
	}
	
	@Test
	public void testChaining() {
		Stream<String> oldestBrands;
		
		oldestBrands = cars.filter( car -> car.getYear() <= 2010)
						   .map( car -> car.getBrand() );
		
		assertThat(oldestBrands, contains("Toyota","Mazda","Susuki") );
	}
	
	
	@Test
	public void testIterableReduce() {
		Double oldestValueSum = 0.0;
		
		oldestValueSum = cars.filter(car -> car.getYear() <= 2010)
							 .map( car -> car.getOriginalValue() )
			                 .reduce(0.0, (a , b) -> a + b);
			                 
		assertThat(oldestValueSum, is(96000D));
	}
	
	@Test
	public void testIterableForEach() {
	
		List<Car> other = new ArrayList<>();		
		cars.forEach(
			car -> { 
				car.setYear(car.getYear() + 1);
				other.add(car);
			}
		);
			
		//assertThat( cars.map( car -> car.getYear()), contains(2009, 2010, 2010, 2012, 2013, 2014) );			
		
	}
	
	@Test
	public void testInto() {
		cars.forEach( car -> { car.setYear(car.getYear() + 1); } );
		
		List<Car> results = null;/*cars.filter( car -> car.getYear() >= 2014)
		              		   .into(new ArrayList<Car>());*/
		assertThat(results, IsIterableContainingInOrder.contains(tida) );			  
	}
	
	
	
	
	@Test
	public void testComparator() {
		
		
		//int compare(Integer left, Integer right);
		Comparator<Integer> compare = (left, right) -> { return left > right ? 1: (left < right ? -1 : 0); };
		
		assertThat(compare.compare(1,1), is(0));
		assertThat(compare.compare(1,2), is(-1));
		assertThat(compare.compare(2,1), is(1));
		
		//Callable<String> 
		// String call()
		Callable<String> sayHello = () -> "Hello World";
		try {
			assertThat(sayHello.call(), is("Hello World"));
		}catch(Exception e) { }
		
		/*
		Runnable runMe = () -> { System.out.println("Hello from run me!"); };
		runMe.run();
		
		new Thread(runMe).start();
		
		int test = 10;
		Runnable perhapsRun = () -> {
			if(test < 10) {
				System.out.println("Hello from run");
			}else {
				System.out.println("Good-bye from run");
			}
		};
		test = 6;
		new Thread(perhapsRun).start();
		*/
		
		Predicate<Integer> odd = n -> n % 2 != 0;
		Predicate<Integer> positive = n -> n >= 0;
		
		Predicate<Integer> oddAndPositive = Predicates.<Integer>and( n -> n % 2 != 0, n -> n >= 0);
		
		assertThat(oddAndPositive.test(3), is(true));
	}
	
	public static class Predicates {
		public static <T> Predicate<T> and(final Predicate<T> left, final Predicate<T> right) {
			return t -> left.test(t) && right.test(t);
		}
	}
	
	@Test
	public void testFilter() {
		
		List<Integer> myInts = asList(1,2,3,4,5,6,7,8,9,0);
		List<Integer> onlyOdds = filter( t -> t % 2 != 0,  myInts);
		assertThat(onlyOdds, IsIterableContainingInOrder.contains(1,3,5,7,9));
	}
	
	public <T> List<T> filter(Predicate<T> predicate, List<T> items) {
		List<T> result = new ArrayList<>();
		for(T item : items) {
			if(predicate.test(item)){
				result.add(item);
			}
		}
		return result;
	}
	
	@Test
	public void testMap() {
		
		List<String> text = asList("0","1","2","3","4","5","6","7","8","9");
		//Mapper<String, Integer> mapper = value -> Integer.valueOf(value);
		List<Integer> myInts = map( s -> Integer.valueOf(s) , text);
		
		assertThat(myInts, IsIterableContainingInOrder.contains(0,1,2,3,4,5,6,7,8,9));
		
	}

	public <T, U> List<U> map(Function<T, U> mapper, List<T> items) {
		List<U> result = new ArrayList<>();
		for(T item : items) {
			U value = mapper.apply(item);
			result.add(value);
		}
		return result;
	}
	
	@Before
	public void setUp() {
		yaris = new Car("Toyota","Yaris", 2008, 15000);
		mx5 = new Car("Mazda", "MX-5", 2009, 45000);
		equator = new Car("Susuki","Equator", 2009, 36000);
		x6 = new Car("BMW","X6", 2011, 88000);
		civic = new Car("Honda","Civic", 2012, 30000);
		tida = new Car("Nissan","Tida", 2013, 35000);
		
		cars = new ArrayList<>(asList(yaris, mx5, equator, x6, civic, tida)).stream();
	}
	//Cars
	private Stream<Car> cars;
	
	private Car yaris;
	private Car mx5;
	private Car equator;
	private Car x6;
	private Car civic;
	private Car tida;
}

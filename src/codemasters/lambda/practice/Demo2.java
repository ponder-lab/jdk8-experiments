package codemasters.lambda.practice;

import codemasters.lambda.domain.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import org.junit.*;

import static java.lang.System.out;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class Demo2 {
	
	
	@Test
	public void sortCars() {
		//int compare(Car l, Car r)
		//Comparator<Car> byYear = (car1, car2) -> Integer.compare(car1.getYear(), car2.getYear());
		//Comparator<Car> byYear = Car::compareByYear;
		
		Car[] myCars = {tida, civic, yaris};
		Arrays.sort(myCars, Car::compareByYear ); //sort by year of making
		assertThat(asList(myCars), contains(yaris, civic, tida));
	}
	
	
	@Test
	public void carBrands() {
		
		//Mapper<Car, String> mapper = (Car car) -> car.getBrand();
		//Mapper<Car, String> mapper = Car::getBrand;
		Stream<String> brands = cars.stream().map( Car::getBrand );
		
		assertThat(() -> brands.iterator(), contains("Toyota","Mazda","Susuki","BMW","Honda","Nissan"));
	}
	
	@Test
	public void printAll() {
		
		//Block<Car> printer = (Car car) -> { out.println(car); };
		//Block<Car> printer = out::println;
		
		cars.forEach( out::println );

	}
	
	@Test
	public void textToNumbers() {
		
		List<String> numbers = asList("1","2","3","4","5");
		
		//Mapper<String, Integer> mapper = text -> new Integer(text);
		//Mapper<String, Integer> mapper = Integer::new;
		Stream<Integer> integers = numbers.stream().map( Integer::new );
		
		assertThat(() -> integers.iterator(), contains(1,2,3,4,5));
		
	}
	
	/*
	interface Block<T>  {
		public void apply(T t);
	}
	*/
	/*
	interface Mapper<T, U> {
		public U map(T t);
	}
	*/



	@Before
	public void setUp() {
		yaris = new Car("Toyota","Yaris", 2008, 15000);
		mx5 = new Car("Mazda", "MX-5", 2009, 45000);
		equator = new Car("Susuki","Equator", 2009, 36000);
		x6 = new Car("BMW","X6", 2011, 88000);
		civic = new Car("Honda","Civic", 2012, 30000);
		tida = new Car("Nissan","Tida", 2013, 35000);
		
		cars = new ArrayList<>(asList(yaris, mx5, equator, x6, civic, tida));
	}
	//Cars
	private List<Car> cars;
	
	private Car yaris;
	private Car mx5;
	private Car equator;
	private Car x6;
	private Car civic;
	private Car tida;	
	
	
	
}

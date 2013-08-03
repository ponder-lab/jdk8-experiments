package codemasters.lambda.showtime;

import codemasters.lambda.domain.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import org.junit.*;
import org.hamcrest.collection.IsIterableContainingInOrder;
import static java.lang.System.out;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

import static org.hamcrest.streams.IsStreamContainingInOrder.contains;

public class Demo3 {
	
	@Test 
	public void textToNumbers() {
		
		Stream<String> numbers = asList("1","2","3","4","5").stream();
		//Mapper<String, Integer> mapper = (String number) -> new Integer(number);
		//Mapper<String, Integer> mapper = Integer::new;
		
		Stream<Integer> integers = numbers.map( Integer::new );
		
		assertThat(integers, contains(1,2,3,4,5));
		
	}
	
	
	@Test
	public void printerTest() {
		
		//Block<Car> printer = (Car car) -> { out.println(car); };
		//Block<Car> printer = out::println;
		cars.forEach(   out::println  );
		
	}
	
	@Test
	public void carBrands() {
		//Mapper<Car, String> mapper = (Car car) -> car.getBrand();
		Function<Car,String> mapper = Car::getBrand;
		Stream<String> brands = cars.map( mapper );
		assertThat(brands, contains("Toyota","Mazda","Susuki","BMW","Honda","Nissan"));
	}
	
	

	@Test
	public void sortCars() {
		//int compare(Car c1, Car c2)
		//Comparator<Car> comparator = (car1, car2) -> Integer.compare(car1.getYear(), car2.getYear());
		//Comparator<Car> comparator = Car::compareByYear;
		Car[] myCars = {tida, civic, yaris};
		Arrays.sort(myCars, Car::compareByYear );
		assertThat(asList(myCars).stream(), contains(yaris, civic, tida));
	}
	
	
	@Before
	public void setUp() {
		yaris = new Car("Toyota","Yaris", 2008, 15000);
		mx5 = new Car("Mazda", "MX-5", 2009, 45000);
		equator = new Car("Susuki","Equator", 2009, 36000);
		x6 = new Car("BMW","X6", 2011, 88000);
		civic = new Car("Honda","Civic", 2012, 30000);
		tida = new Car("Nissan","Tida", 2013, 35000);
		
		cars = asList(yaris, mx5, equator, x6, civic, tida).stream();
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

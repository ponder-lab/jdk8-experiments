package codemasters.lambda.demo;

import codemasters.lambda.domain.Car;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;
import org.hamcrest.Matcher;
import org.hamcrest.collection.IsIterableContainingInOrder;
import static java.util.Comparator.*;
import static org.junit.Assert.assertThat;
import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.streams.IsStreamContainingInOrder.contains;

public class Demo3 {
	
	
	@Test
	public void testFilter() {
		Stream<Car> oldest = cars.filter( car -> car.getYear() <= 2010 );
		assertThat(oldest, contains(yaris, mx5, equator) );
	}
	
	@Test
	public void testMap() {
		Stream<String> brands = cars.map( car -> car.getBrand() );
		assertThat(brands, contains("Toyota","Mazda","Susuki","BMW", "Honda", "Nissan") );
	}
	
	@Test
	public void testChaining() {
		Stream<String> oldestBrands;
		oldestBrands = cars.filter( car -> car.getYear() <= 2010).map( car -> car.getBrand() );
		assertThat(oldestBrands, contains("Toyota","Mazda","Susuki") );
	}
	
	@Test
	public void testReduce() {
		Double oldestValueSum = 0.0;
		oldestValueSum = cars.filter( car -> car.getYear() <= 2010)
		    					.map( car -> car.getOriginalValue() )
		    					.reduce(0.0, (left, right) -> left + right);
		    					 
		assertThat(oldestValueSum, is(96000D));
		oldestValueSum = cars.filter( car -> car.getYear() <= 2010)
		    					.map( car -> car.getOriginalValue())
		    					.reduce(0.0, (left, right) -> left + right);
		    					 
		assertThat(oldestValueSum, is(96000D));
		
	}
	
	@Test
	public void testForEach() {
		cars.forEach( car -> { car.setYear(car.getYear() + 1); } );
		//assertThat( cars.map( car -> car.getYear()), contains(2009, 2010, 2010, 2012, 2013, 2014) );
	}
	
	@Test
	public void testInto() {
		List<Car> results = new ArrayList<>();
		
		Consumer<Car> block = c -> { results.add(c); };
		
		cars.forEach( car -> { car.setYear(car.getYear() + 1); } );
		cars.filter( car -> car.getYear() >= 2014)
		              //.into(new ArrayList<Car>());
		              .forEach( block );
		assertThat(results, IsIterableContainingInOrder.contains(tida) );			  
	}
	
	@Test
	public void testReduceToMinAndMax() {
		Stream<Integer> myInts = asList(1,3,2,5,7,6,8,0,9,4).stream();
		
		int maximum = myInts.reduce(Integer.MIN_VALUE, (a, b) -> a > b ? a : b);
		assertThat(maximum, is(9));
		
		int minimum = myInts.reduce(Integer.MAX_VALUE, (a, b) -> a > b ? b : a);
		assertThat(minimum, is(0));
	}
	
	@Test
	public void testMethodReferences() {
		//Mapper<Car, String> mapper = (Car car) -> car.getBrand();
		//Mapper<Car, String> mapper = Car::getBrand;
		
		/*
		Stream<String> brands = cars.map( Car::getBrand );
		Iterable<String> s = () -> brands.iterator(); 
		assertThat( brands, contains("Toyota","Mazda","Susuki","BMW", "Honda", "Nissan") );
		*/
		
		
		Function<Car,String> mapper = this::getBrand;
		Stream<String> brands = cars.map( mapper );
		assertThat(brands, contains("[Toyota]","[Mazda]","[Susuki]","[BMW]", "[Honda]", "[Nissan]") );
		
	}
	
	public String getBrand(Car car){
		return String.format("[%s]", car.getBrand());
	}
	
	@Test
	public void testConstructorReferences() {
		
		Stream<String> text = asList("1","2","3","4","5").stream();
		
		//Mapper<String, Integer> mapper = (String s) -> Integer.valueOf(s);
		//Mapper<String, Integer> mapper = Integer::new;
		
		Stream<Integer> numbers = text.map(Integer::new);
		assertThat(numbers, contains(1,2,3,4,5));
	}
	
	@Test
	public void testComparatorWithMethoRefs() {
		
		Comparator<Car> comparator = this::compareByValue;
		cars.sorted(comparator);
		
		assertThat(cars, contains(yaris, civic, tida, equator, mx5, x6));
		
	}
	
	public int compareByValue(Car car1, Car car2) {
		return Double.compare(car1.getOriginalValue(), car2.getOriginalValue());
	}
	
	@Test
	public void testSortComparing() {

		cars.sorted(comparingDouble(Car::getOriginalValue));
		assertThat(cars, contains(yaris, civic, tida, equator, mx5, x6));
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

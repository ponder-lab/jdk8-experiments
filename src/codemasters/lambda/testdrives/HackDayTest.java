package codemasters.lambda.testdrives;

import org.junit.Test;
import org.junit.Before;
import codemasters.lambda.domain.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;

import static java.util.Collections.reverseOrder;
import static java.util.Arrays.asList;
import static java.util.Comparator.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.Matchers.either;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.IsEqual.equalTo;

public class HackDayTest {
	
	/**
	 * Find the most bought car.
	 */
	public static void main(String[] args) {
		parallelism();
	}
	
	public static void testJDK8LambdaSerialization() {
		
		AdvancePredicate<Integer> isOdd = x -> x % 2 == 0;
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("lambda.bin"))){
			out.writeObject(isOdd);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("lambda.bin"))){
			
			@SuppressWarnings("unchecked")
			AdvancePredicate<Integer> lambda = (AdvancePredicate<Integer>) in.readObject();
			
			System.out.println(lambda.test(10));
			System.out.println(lambda.test(11));
			
		}catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public interface AdvancePredicate<T> extends Predicate<T>, java.io.Serializable{ } 
	
	public static void parallelism() {
		
		Predicate<Integer> isPrime = value -> {
			int number = value;
			if(number < 2) {
				return false;
			}
			if(number == 2) {
				return true;
			}
			if(number % 2 == 0){
				return false;
			}
			int divisor = 3;
			while(true){
				if(number % divisor == 0){
					return false;
				} else if (divisor * divisor > number) {
					return true;
				} else {
					divisor += 2;
				}
			}
		};
		//20984
		//10758
		Integer[] source = new Integer[30000000];
		for(int i=0; i<source.length; i++)
			source[i] = i;

		//Stream<Integer> allIntegers = parallel(source).filter(isPrime);
		//Iterable<Integer> allIntegers = iterable(source).filter(isPrime); //lazy
		System.out.println("Determining primes...");
		long start = System.currentTimeMillis();
		//List<Integer> primes = allIntegers.into(new LinkedList<Integer>());
		//long count = allIntegers.count();
		//long count = allIntegers.estimateCount();
		long end = System.currentTimeMillis();
		//System.out.println(String.format("%d primes found in %d milliseconds", primes.size(), end-start));
	}
	
	
	/**
	 * Find most bought car.
	 */
	@Test
	public void withJDK8Test11() {
		
		
								    
		//assertThat(car, either(is(yaris)).or(is(mx5)));
	}
	
	/**
	 * Group sales by buyers and sellers.
	 */
	@Test
	public void withJDK7Test10() {
		/*
		Map<Person, Map<Person, Iterable<Sale>>> map = new HashMap<>();
		for(Sale sale : sales) {
			Person buyer = sale.getBuyer();
			Map<Person, Iterable<Sale>> buyerMap = map.get(buyer);
			if(buyerMap == null) {
				buyerMap = new HashMap<>();
				map.put(buyer, buyerMap);
			}
			buyerMap.put(sale.getSeller(), asList(sale));
		}
		
		Person youngest = sortedPersons.first();
		Person oldest = sortedPersons.last();
		
		assertThat(youngest, is(franklin));
		assertThat(oldest, is(oscar));
				
		Sale sale = map.get(youngest).get(oldest).getFirst(); //warning: getFirst is not from JDK 1.7 ;-)
		assertThat(sale, is(franklinFromOscar));
		*/
	}
	
	/**
	 * Group sales by buyers and sellers.
	 */
	@Test
	public void withJDK8Test10() {
		
		/*

		MapStream<Person, MapStream<Person, Collection<Sale>>> groups;
		groups = sales.stream().groupBy(Sale::getBuyer).stream()
			 		 .map( (k,v) -> v.groupBy(Sale::getSeller) );
		
		Person youngest = sortedPersons.first();
		Person oldest = sortedPersons.last();
		
		assertThat(youngest, is(franklin));
		assertThat(oldest, is(oscar));
		
		Sale sale = groups.filterKeys(buyer -> buyer.equals(youngest))
			  									  .getFirst()
												  .getValue()
												  .filterKeys(seller -> seller.equals(oldest))
												  .getFirst()
												  .getValue()
												  .getFirst();
			  	  
		
		assertThat(sale, is(franklinFromOscar));
		*/
	}
	
	@Test
	public void understandingMutivalues() {
		/*
		BiMapper<String, String, Iterable<Integer>> mapper = (k, v) -> asList(v.split(",")).map(Integer::new);
		Iterable<Integer> myInts = mapper.map("Edwin", "1,2,3,4,5,6");
		
		assertThat(myInts, contains(1,2,3,4,5,6));
		
		
		Map<String, String> sample = new HashMap<>();
		sample.put("Edwin","1,2,3,4,5");
		sample.put("Karla","6,7,8,9,0");
		
		Map<String, Iterable<Integer>> groups = sample.mapValuesMulti( (k, v) -> asList( v.split(",") )
													  .map(Integer::new) )
													  .into(new LinkedHashMap<String,Iterable<Integer>>());
		
		
		assertThat(groups.keySet(), containsInAnyOrder("Edwin","Karla"));
		assertThat(groups, hasEntry(is("Edwin"), contains(1,2,3,4,5)));
		assertThat(groups, hasEntry(is("Karla"), contains(6,7,8,9,0)));
		*/
	}
	
	
	
	/**
	 * Index cars by brand.
	 */
	@Test
	public void withJDK7Test09() {
		Map<String, Set<Car>> byBrands = new HashMap<>();
		for(Car car : cars) {
			if(!byBrands.containsKey(car.getBrand())){
				byBrands.put(car.getBrand(), new HashSet<Car>());
			}
			byBrands.get(car.getBrand()).add(car);
		}
		
		assertThat(byBrands.keySet(), containsInAnyOrder("Toyota","Mazda","Susuki","BMW","Honda","Nissan"));
				
		assertThat(byBrands, hasEntry(is("Toyota"), contains(yaris)));
		assertThat(byBrands, hasEntry(is("Mazda"), contains(mx5)));
		assertThat(byBrands, hasEntry(is("Susuki"), contains(equator)));
		assertThat(byBrands, hasEntry(is("BMW"), contains(x6)));
		assertThat(byBrands, hasEntry(is("Honda"), contains(civic)));
		assertThat(byBrands, hasEntry(is("Nissan"), contains(tida)));
	}
	
	/**
	 * Index cars by brand.
	@Test
	public void withJDK8Test09() {
		
		Map<String, Collection<Car>> byBrands = cars.stream().groupBy(Car::getBrand);
		
		assertThat(byBrands.keySet(), containsInAnyOrder("Toyota","Mazda","Susuki","BMW","Honda","Nissan"));
				
		assertThat(byBrands, hasEntry(is("Toyota"), contains(yaris)));
		assertThat(byBrands, hasEntry(is("Mazda"), contains(mx5)));
		assertThat(byBrands, hasEntry(is("Susuki"), contains(equator)));
		assertThat(byBrands, hasEntry(is("BMW"), contains(x6)));
		assertThat(byBrands, hasEntry(is("Honda"), contains(civic)));
		assertThat(byBrands, hasEntry(is("Nissan"), contains(tida)));
	}
	*/
	
	
	/**
	 * Extract cars' original cost.
	 */
	@Test
	public void withJDK7Test08() {
		List<Double> costs = new ArrayList<>();
		for(Car car: cars) {
			costs.add(car.getOriginalValue());
		}
		assertThat(costs, contains(15000D,45000D, 35000D, 88000D, 30000D, 35000D));
	}
	
	/**
	 * Extract cars' original cost.
	@Test
	public void withJDK8Test08() {
		List<Double> costs = cars.stream().map(Car::getOriginalValue).into(new ArrayList<Double>());
		
		assertThat(costs, contains(15000D,45000D, 35000D, 88000D, 30000D, 35000D));						 
	}
	*/
	
	/**
	 * Sort sales by cost.
	 */
	@Test
	public void withJDK7Test07() {
		List<Sale> sortedSales = new ArrayList<>(sales);
		Collections.sort(sortedSales, new Comparator<Sale>(){
				public int compare(Sale sale1, Sale sale2) {
					return Double.compare(sale1.getCost(), sale2.getCost());
				}
		});
		
		assertThat(sortedSales, contains(alonsoFromKarla, 
										 oscarFromEdwin, 
										 edwinFromFranklin, 
										 karlaFromAlonso, 
										 victoriaFromOscar, 
										 franklinFromOscar, 
										 karlaFromFranklin, 
										 patriciaFromPedro));

		
	}
	
	/**
	 * Sort sales by cost.
	@Test
	public void withJDK8Test07() {
		
		List<Sale> sortedSales = new ArrayList<>();
		sales.stream().sorted(comparing((DoubleFunction<Sale>)Sale::getCost)).into(new ArrayList<Sale>());
		
		assertThat(sortedSales, contains(alonsoFromKarla, 
										 oscarFromEdwin, 
										 edwinFromFranklin, 
										 karlaFromAlonso, 
										 victoriaFromOscar, 
										 franklinFromOscar, 
										 karlaFromFranklin, 
										 patriciaFromPedro));
	}
	*/
	
	/**
	 * Find age of the youngest who bought for more than
	 * 40,000.
	 */
	@Test
	public void withJDK7Test06() {
		Integer age = Integer.MAX_VALUE;
		for(Sale sale : sales) {
			if(sale.getCost() >= 40000){
				int buyerAge = sale.getBuyer().getAge();
				if(buyerAge < age){
					age = buyerAge;
				}
			}
		}
		assertThat(age, is(30)); //Karla
	}
	
	/**
	 * Find age of the youngest who bought for more than
	 * 40,000.
	@Test
	public void withJDK8Test06() {
		Integer age = sales.stream().filter(sale -> sale.getCost() >= 40000)
			 .map(Sale::getBuyer)
			 .map(Person::getAge)
			 //.into(new TreeSet<Integer>())
			 .findFirst().get();
			 
		assertThat(age, is(30)); //Karla
	}
	 */
	
	/**
	 * Sum costs when both are male.
	 */
	@Test
	public void withJDK7Test05() {
		double sum = 0.0;
		for(Sale sale : sales) {
			if(sale.getBuyer().isMale() && sale.getSeller().isMale()){
				sum += sale.getCost();
			}
		}
		
		assertThat(sum, is(69000D));
	}
	
	/**
	 * Sum costs when both are male.
	 */
	@Test
	public void withJDK8Test05() {
		double sum = sales.stream().filter(s -> s.getBuyer().isMale() && s.getSeller().isMale())
		                  .map(Sale::getCost).reduce(0.0, (acum , cost) -> acum + cost);
		                  
		assertThat(sum, is(69000D));				  
	}
	
	/**
	 * Find most costly sale.
	 */
	@Test
	public void withJDK7Test04(){
		double maxCost = 0.0;
		for(Sale sale : sales) {
			double cost = sale.getCost();
			if(Double.compare(cost, maxCost) > 0){
				maxCost = cost;
			}
		}
		assertThat(maxCost, is(75000D)); //Patricia From Pedro for BMW.
	}
	
	/**
	 * Find most costly sale.
	@Test
	public void withJDK8Test04(){
		
		Double maxCost = sales.stream().map(Sale::getCost).reduce(0.0, (a, b) -> Double.compare(a, b) > 0 ? a : b);
		assertThat(maxCost, is(75000D)); //Patricia From Pedro for BMW.
		
		//Alternatively
		maxCost = sales.stream().map(Sale::getCost)
			 		  .into(new TreeSet<Double>(reverseOrder()))
			 		  .first();
		assertThat(maxCost, is(75000D));
	}
	*/
	
	/**
	 * Find buys of the youngest person.
	 */
	 @Test
	public void withJDK7Test03() {
		Person youngest = null;
		for(Person person : persons) {
			if(youngest == null || person.getAge() < youngest.getAge()){
				youngest = person;
			}
		}
		assertThat(youngest, is(franklin));
		
		Set<Sale> byPerson = new HashSet<>();
		for(Sale sale : sales) {
			if(sale.getBuyer().equals(youngest)){
				byPerson.add(sale);
			}
		}
		
		assertThat(byPerson, containsInAnyOrder(franklinFromOscar));
	}
	
	/**
	 * Find buys of the youngest person.
	 */
	@Test
	public void withJDK8Test03() {
		/*
		Comparator<Person> compareByAge = comparing((IntMapper<Person>)Person::getAge);
		Person youngest = persons.into(new TreeSet<Person>(compareByAge)).getFirst();
		
		assertThat(youngest, is(franklin));
		
		Set<Sale> byPerson = sales.stream().groupBy(Sale::getBuyer)
								  .filterKeys(buyer -> buyer.equals(youngest))
								  .findFirst()
								  .get()
								  .into(new HashSet<Sale>());
		
		assertThat(byPerson, containsInAnyOrder(franklinFromOscar));
		*/
	}
	
	
	/**
	 * Select all sales on a Toyota.
	 */
	@Test
	public void withJDK7Test02() {
		List<Sale> salesOnToyota = new ArrayList<>();
		for(Sale sale : sales) {
			if(sale.getCar().getBrand().equals("Toyota")){
				salesOnToyota.add(sale);
			}
		}
		assertThat(salesOnToyota, containsInAnyOrder(oscarFromEdwin, alonsoFromKarla));
	}
	
	/**
	 * Select all sales on a Toyota.
	 
	@Test
	public void withJDK8Test02(){
		List<Sale> salesOnToyota = sales.stream().filter(sale -> sale.getCar().getBrand().equals("Toyota"))
										.into(new ArrayList<Sale>());
		
		assertThat(salesOnToyota, containsInAnyOrder(oscarFromEdwin, alonsoFromKarla));
	}
	*/
	
	
	/**
	 * Print all car brands.
	 @Test
	public void withJDK8Test01() {
		StringJoiner joiner = new StringJoiner(", ");
		joiner.addAll(cars.stream().map(Car::getBrand));
		String brands = joiner.toString();
		
		assertThat(brands, is(equalTo("Toyota, Mazda, Susuki, BMW, Honda, Nissan")));
		
		//Alternatively
		brands = cars.stream().map(Car::getBrand).reduce("", (a, b) -> a != "" ? (a + ", " + b) : b);
		assertThat(brands, is(equalTo("Toyota, Mazda, Susuki, BMW, Honda, Nissan")));
	}
	*/
	
	/**
	 * Print all car brands.
	 */
	 @Test 
	 public void withJDK7Test01() {
		
		StringBuilder sb = new StringBuilder();
		for(Car car : cars) {
			sb.append(car.getBrand()).append(", ");
		}
		String brands = sb.toString().substring(0, sb.length() -2);
		
		assertThat(brands, is(equalTo("Toyota, Mazda, Susuki, BMW, Honda, Nissan")));
	}
	
	//Persons
	private static final Person edwin = new Person("Edwin","Dalorzo", 34, true);
	private static final Person pedro = new Person("Pedro", "Aguilar", 26, true);
	private static final Person karla = new Person("Karla", "Fallas", 30, false);
	private static final Person oscar = new Person("Oscar", "Romero", 45, true);
	private static final Person franklin = new Person("Franklin", "Fallas", 25, true); 
	private static final Person patricia = new Person("Patricia", "Solano", 31, false);
	private static final Person alonso = new Person("Alonso", "Dalorzo", 27, true);
	private static final Person victoria = new Person("Victoria", "Fallas", 27, false);
	
	//Cars
	private static final Car yaris = new Car("Toyota","Yaris", 2008, 15000);
	private static final Car mx5 = new Car("Mazda", "MX-5", 2009, 45000);
	private static final Car equator = new Car("Susuki","Equator", 2009, 35000);
	private static final Car x6 = new Car("BMW","X6", 2011, 88000);
	private static final Car civic = new Car("Honda","Civic", 2012, 30000);
	private static final Car tida = new Car("Nissan","Tida", 2013, 35000);
	
	//Sales
	private static final Sale edwinFromFranklin = new Sale(edwin, franklin, tida, 20000);
	private static final Sale oscarFromEdwin = new Sale(oscar, edwin, yaris, 10000);
	private static final Sale karlaFromAlonso = new Sale(karla, alonso, civic, 25000);
	private static final Sale patriciaFromPedro = new Sale(patricia, pedro, x6, 75000);
	private static final Sale victoriaFromOscar = new Sale(victoria, oscar, equator, 30000);
	private static final Sale franklinFromOscar = new Sale(franklin, oscar, mx5, 39000);
	private static final Sale alonsoFromKarla = new Sale(alonso, karla, yaris, 9000); 
	private static final Sale karlaFromFranklin = new Sale(karla, franklin, mx5, 40000);
	
	//Collections	
	private static final Set<Person> persons = new LinkedHashSet<>(asList( edwin, pedro, karla, oscar, franklin, patricia, alonso, victoria));
	private static final Set<Car> cars = new LinkedHashSet<>(asList(yaris, mx5, equator, x6, civic, tida));
	private static final Set<Sale> sales = new LinkedHashSet<>(asList(
				edwinFromFranklin,
				oscarFromEdwin,
				karlaFromAlonso,
				patriciaFromPedro,
				victoriaFromOscar,
				franklinFromOscar,
				alonsoFromKarla,
				karlaFromFranklin
			));
	
	private static final TreeSet<Person> sortedPersons = new TreeSet<>(comparingInt(Person::getAge));
	static {
		sortedPersons.addAll(persons);
	}
}

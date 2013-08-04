package codemasters.lambda.hackday;

import codemasters.lambda.domain.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import org.junit.Test;
import org.junit.Before;
import codemasters.lambda.domain.*;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.Matchers.either;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.IsEqual.equalTo;
import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

public class BasicLambda {
	
	/*
	 * 01. Print all car brands.
	 */
	 @Test
	 public void printAllCarBrands() {
	 	 String brands = cars.stream()
	 	 					.map(Car::getBrand)
	 	 					.collect(joining(","));
	 	 					
	 	 assertThat(brands,is(equalTo("Toyota,Mazda,Susuki,BMW,Honda,Nissan")));
	 }
	
	 private boolean isToyota(Sale sale) {
	 	 return sale.getCar().getBrand().equals("Toyota");
	 }
	
	/*
	 * 02. Select all sales on Toyota.
	 */
	@Test
	public void selectSalesOnToyota() {
		List<Sale> toyotaSales;
		
		toyotaSales = sales.stream()
						  .filter(this::isToyota)
						  .collect(toList());

		assertThat(toyotaSales, contains(oscarFromEdwin, alonsoFromKarla));						  
	}
	
	/*
	 * 03. Find buys of youngest person.
	 */
	@Test 
	public void buysOfYoungestPerson() {
		NavigableMap<Person, Set<Sale>> salesByPerson = null;
		
		salesByPerson = sales.stream()
							.collect(groupingBy(
										Sale::getBuyer,
										() -> new TreeMap<>(comparing(Person::getAge)),
										toSet()));
							
		assertThat(salesByPerson.firstKey(), is(franklin));
		assertThat(salesByPerson.firstEntry().getValue(), contains(franklinFromOscar));
	}
	 
    /*
	 * 04. Find most costly sale.
	 */
	@Test
	public void findMostCostlySale() {
		Optional<Sale> mostCostlySale;
		
		mostCostlySale = sales.stream().max(comparing(Sale::getCost));
		
		assertTrue(mostCostlySale.isPresent());
		assertThat(mostCostlySale.get(), is(patriciaFromPedro));
	}
	
	
	/*
	 * 05. Sum costs where both are males.
	 */
	@Test
	public void sumCostsWhereBothAreMales() {
		double sumOfCosts;
		
		sumOfCosts = sales.stream()
						 .filter(sale -> sale.getSeller().isMale())
						 .filter(sale -> sale.getBuyer().isMale())
						 .mapToDouble(Sale::getCost)
						 .sum();
		
		assertThat(sumOfCosts, is(69000.0));
	}
	
	/*
	 * 06. Find age of youngest who bought for more than 40,000.
	 */
	@Test
	public void findAgeOfTheYoungestWhoBought40000() {
		NavigableMap<Person, Set<Sale>> salesByPerson = null;
		
		salesByPerson = sales.stream()
							.filter(sale -> sale.getCost() > 40000)
							.collect(groupingBy(
										Sale::getBuyer,
										() -> new TreeMap<>(comparing(Person::getAge)),
										toSet()));
							
		assertThat(salesByPerson.firstKey(), is(patricia));
		assertThat(salesByPerson.firstKey().getAge(), is(31));
					
	}
	/*
	07. Sort sales by cost.
	*/
	@Test
	public void sortSalesByCost() {
		List<Sale> salesByCost;
		
		salesByCost = sales.stream()
						  .sorted(comparing(Sale::getCost))
						  .collect(toList());
						  
						  
		assertThat(salesByCost, contains(alonsoFromKarla,oscarFromEdwin,edwinFromFranklin, 
									    karlaFromAlonso, victoriaFromOscar, franklinFromOscar, 
									    karlaFromFranklin, patriciaFromPedro));						  
	}
	/*
	 * 08. Extract cars' original cost.
	 */
	@Test
	public void extractCarsOriginalCost() {
		List<Double> originalCosts;
		
		originalCosts = sales.stream()
			 				.map(Sale::getCar)
			 				.map(Car::getOriginalValue)
			 				.collect(toList());
			 				
		assertThat(originalCosts, contains(35000.0,15000.0,30000.0,88000.0, 35000.0, 45000.0, 15000.0, 45000.0));			 				
	}
	
	/*
	 * 09. Index cars by brand.
	 */
	@Test
	public void indexCarsByBrand() {
		Map<String, List<Car>> carsByBrand;
		
		carsByBrand = cars.stream().collect(groupingBy(Car::getBrand));
		
		assertThat(carsByBrand.keySet(), containsInAnyOrder("Toyota","Mazda","Susuki","BMW","Honda","Nissan"));
	}
	
	/*
	 * 10. Find most bought car.
	 */
	@Test
	public void findMostBoughtCar() {
		Map<Car,List<Sale>> salesByCar;
		
		salesByCar = sales.stream().collect(groupingBy(Sale::getCar));
		
		Optional<Car> mostBoughtCar;
		mostBoughtCar = salesByCar.entrySet().stream()
								 //not sure why inference did not work in this case.
				  				 .max(comparing((ToIntFunction<Map.Entry<Car,List<Sale>>>) entry -> entry.getValue().size()))
				  				 .map(Map.Entry::getKey);
				  				 
		assertTrue(mostBoughtCar.isPresent());
		assertThat(mostBoughtCar.get(), is(yaris));
		
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
	
}

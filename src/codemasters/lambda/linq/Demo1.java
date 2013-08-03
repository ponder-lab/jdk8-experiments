package codemasters.lambda.linq;

import codemasters.lambda.domain.Car;
import codemasters.lambda.domain.Person;
import codemasters.lambda.domain.Sale;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.ToIntFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Optional;
import java.util.OptionalInt;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.Comparator.comparing;

public class Demo1 {
	
	public static class Fruit {
		private String name;
		
		public Fruit(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
	}
	
	public static void main(String[] args) {
		
		//Optional<Fruit> fruit = Optional.of(new Fruit("Kiwi"));
		Optional<Fruit> fruit = Optional.empty();
		Fruit found = fruit.orElseGet(() -> new Fruit("Lemon"));
		System.out.println(found.getName());
		//fruit.ifPresent(f -> { System.out.println(f.getName()); });
			
		
			
	}
	
	//Find most bought car.
	public static void test09() {
		
		ToIntFunction<Entry<Car,List<Sale>>> toSize = (e -> e.getValue().size());
		
		Optional<Car> mostBought;
		 
		mostBought = sales.collect( groupingBy(Sale::getCar) )
						  .entrySet()
						  .stream()
						  //.sorted( comparing(toSize).reverse() )
						  .map(Entry::getKey)
						  .findFirst();
		
		if(mostBought.isPresent()) {
			System.out.println(mostBought.get());
		}
		
	}
	
	//Index cars by brand.
	public static void test08() {
		Map<String,List<Car>> byBrand;
		
		byBrand = cars.collect( groupingBy(Car::getBrand ));
		
		System.out.println(byBrand);
	}
	
	//Sort sales by cost.
	public static void test07() {
		
		Comparator<Sale> byCost= comparing( (ToDoubleFunction<Sale>) Sale::getCost);
		List<Sale> sortedByCost;
		
		sortedByCost = sales.sorted( byCost )
							.collect(toList());
		               
         System.out.println(sortedByCost);		               
	}
	
	//Find age of youngest who bought for more than 40,000.
	public static void test06() {
		OptionalInt ageOfYoungest;
		
		ageOfYoungest = sales.filter(sale -> sale.getCost() > 40000)
							 .map(Sale::getBuyer)
							 .mapToInt(Person::getAge)
							 .sorted()
							 .findFirst();
		
		if(ageOfYoungest.isPresent()) {
			System.out.println(ageOfYoungest.getAsInt());	
		}
	}
		
	
	//find the sum of sales where both buyer and seller are males
	public static void test05() {
		double sum = sales.filter(s -> s.getBuyer().isMale() && s.getSeller().isMale())
						  .mapToDouble(Sale::getCost)
						  .sum();
		
	    System.out.println(sum);
	}
	
	//find most costly sale
	public static void test04() {
		
		Optional<Sale> mostCostlySale;
		Comparator<Sale> byCost = comparing( (ToDoubleFunction<Sale>) Sale::getCost);//.reverse();
		
		mostCostlySale = sales//.sort( byCost.reverse() )
							  .findFirst();
							  
		if(mostCostlySale.isPresent()) {
			System.out.println(mostCostlySale.get());
		}
		
	}
	
	
	
	//find buys of the youngest person
	public static void test03() {
		
		Optional<List<Sale>> byYoungest;
		
		/*
		Comparator<Person> byAge = comparing((ToIntFunction<Person>)Person::getAge);
		byYoungest = sales.collect(groupingBy(Sale::getBuyer, () -> new TreeMap<>(byAge)))
						  .entrySet()
						  .stream()
						  .map(Entry::getValue)
						  .findFirst();*/

		ToIntFunction<Entry<Person, List<Sale>>> byAge;
		byAge = e -> e.getKey().getAge(); 
		byYoungest = sales.collect(groupingBy(Sale::getBuyer))
						 .entrySet()
						 .stream()
						 .sorted(comparing(byAge))
						 .map(Entry::getValue)
						 .findFirst();

		if(byYoungest.isPresent()) {
			System.out.println(byYoungest.get());
		}
	}
	
	//select all sales on Toyota
	public static void test02() {
		List<Sale> toyotaSales;
		
		toyotaSales = sales.filter((Sale s) -> s.getCar().getBrand().equals("Toyota"))
						   .collect(toList());
		 
		toyotaSales.forEach(System.out::println);		                   
	}
	
	//print all car brands
	public static void test01() {
		String brands = cars.map(Car::getBrand)
		    					     .collect(joining(","));
  	    
	    
		System.out.println(brands);
  	    
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
    private static final Sale edwinFromKarla = new Sale(edwin, karla, mx5, 45000);

    //Collections
    private static final Stream<Person> persons = new LinkedHashSet<>(asList( edwin, pedro, karla, oscar, franklin, patricia, alonso, victoria)).stream();
    private static final Stream<Car> cars = new LinkedHashSet<>(asList(yaris, mx5, equator, x6, civic, tida)).stream();
    private static final Stream<Sale> sales = new LinkedHashSet<>(asList(
            edwinFromFranklin,
            oscarFromEdwin,
            karlaFromAlonso,
            patriciaFromPedro,
            victoriaFromOscar,
            franklinFromOscar,
            alonsoFromKarla,
            karlaFromFranklin,
            edwinFromKarla
    )).stream();
}


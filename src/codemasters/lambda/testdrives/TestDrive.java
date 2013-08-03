package codemasters.lambda.testdrives;

import codemasters.lambda.domain.*;
import java.util.StringJoiner;
import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.function.LongBinaryOperator;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.DoubleFunction;
import java.util.function.IntFunction;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Stream;
//import static java.util.Arrays.parallel;
import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static java.util.Collections.min;
import java.lang.annotation.*;

public class TestDrive {
	
	private static List<Sale> sales;
	
	static{
		sales = asList(
			new Sale(
				new Person("Edwin","Dalorzo", 34, true),//buyer
				new Person("Pedro", "Aguilar", 25, true), //seller
				new Car("Chevrolet","Aveo", 2008, 1500),
				1200),
			new Sale(
				new Person("Karla", "Fallas", 30, false), //buyer
				new Person("Oscar", "Romero", 45, true), //seller
				new Car("Ferrari", "X5", 2012, 5000),
				5000),
			new Sale(
				new Person("Franklin", "Fallas", 25, true), //buyer
				new Person("Patricia", "Solano", 30, false), //seller
				new Car("Ferrari","Magnus", 2011, 4800),
				4200),
			new Sale(
				new Person("Alonso", "Dalorzo", 27, true), //buyer
				new Person("Victoria", "Fallas", 27, false), //seller
				new Car("Toyota","Prius", 2012, 2000),
				2000),
			new Sale(
				new Person("Franklin", "Fallas", 25, true), //buyer
				new Person("Oscar", "Romero", 45, true), //seller
				new Car("Mercedez","M10", 2011, 9800),
				4200)
			);
	}
	
	public static void main(String[] args) {
		test02();
	}
	
	public static void test() {
		
	}
	
	public static void test15() {
		//Mapper<String, Integer> atoi = s -> Integer.valueOf(s);
		Function<String,Integer> atoi = Integer::new;
		Integer r = atoi.apply("10");
		System.out.println("Result: "+ r);
		
		List<Long> dates = asList(1344754620310L,1344754854877L);
		//List<Date> asDates = dates.stream().map(Date::new).into(new ArrayList<Date>());
		//System.out.println(asDates);
		
		/*
		Function<Long,Date> ltod = l -> new Date(l);
		Function<Date,String> dtos = Functions.<Date>string();
		Functions.chain(ltod, Functions.string());
		*/
		
		//List<String> asString = dates.stream().map(chain((Function<Long,Date>)Date::new, Functions.string())).into(new ArrayList<String>());
		//System.out.println(asString);
		
		/*
		Function<String,Integer> stoi = instantiate(String.class, Integer.class);
		Integer m = stoi.apply("10");
		System.out.println(m);
		*/
		
		
		
		Function<String,Integer> again = s -> s == null ? 0 : Integer.valueOf(s);
		
		String s = null;
		
		/*
		Function<String,Integer> datoi = chain(substitute(null, "0"), Integer::new);
		Integer p = datoi.apply(null);
		System.out.println(p);
		*/
		List<String> data = asList("0", null, "2", null, "4", null, "6");
		//List<Integer> myInts = data.stream().map(chain(substitute(null, "0"), Integer::new)).into(new ArrayList<Integer>());
		//System.out.println(myInts);
		
		
		
	}
	
	/*
	public static void test14() {
		List<Integer> myInts = "5,4,3,2,1,0,6,7,8,9"
								.splitAsStream(",")
								.map(Integer::new)
								.into(new ArrayList<Integer>());
								
		
		
								
		StringJoiner joiner = new StringJoiner(",");
		joiner.addAll(myInts.stream().map(String::valueOf));
		String dest = joiner.toString();
		
		System.out.println(myInts);
		System.out.println(dest);
		
		
								
		//Integer x = Integer::new; compile-time error, the targe must be an interface
		Comparable<Integer> x = Integer::new;
		System.out.println(x.compareTo(10));
		/*
		Comparable<String> w = String::new;
		
		Mapper<String, Integer> y = Integer::new;
		System.out.println(y.map("123"));
		
		//Mapper<String, Jedi> w = Jedi::new;
		Comparable<MyInt> z = MyInt::new;
	}
	*/
	
	private static class MyInt implements Comparable<MyInt> {
		private int value;
		
		MyInt(int value) {
			this.value = value;
		}
		
		MyInt(MyInt other) {
			this.value = other.value;
		}
		
		@Override
		public int compareTo(MyInt other) {
			return this.value > other.value ? 1 : this.value > other.value ? -1 : 0;
		}
	}
	
	/*	
	@Authors({
		@Author("Leo Vargas"), 
		@Author(value="Edwin Dalorzo")}
		)
	*/
	@Author("Leo Vargas")
	@Author("Edwin Dalorzo")
	class Sample {
	}
	
	
	
	@Target(ElementType.TYPE)
	private @interface Authors {
		Author[] value() default {};
	}
	
	@Target(ElementType.TYPE)
	@Repeatable(Authors.class)
	private @interface Author {
		String value() default "";
	}
	
	/*
	 * Unsigned integer support and integer overflow support.
	 */
	 public static void test13() {
	 	 byte b = -128;
	 	 int i = Byte.toUnsignedInt(b);
	 	 long l = Byte.toUnsignedLong(b);
	 	 System.out.println(i + "i, " + l + "L" );
	 	 
	 	 i = 0x80000001;
	 	 l = Integer.toUnsignedLong(i);
	 	 System.out.println(i + "i, " + l + "L" );
	 	 System.out.println(Integer.toUnsignedString(i));
	 	 
	 	 l = 0x8000000000000001L;
	 	 System.out.println(Long.toUnsignedString(l));
	 	 
	 	 i = Integer.parseUnsignedInt("2147483649");
	 	 l = Long.parseUnsignedLong("9223372036854775809");
	 	 System.out.println(i + "i, " + l + "L" );
	 	 
	 	 try{
	 	 	 i = Math.addExact(Integer.MAX_VALUE, 1);
	 	 }catch(ArithmeticException e) {
	 	 	 System.out.println(e.getMessage());
	 	 }
	 	 
	 	 
	 }

	/*
 	 * Split and join back a string.
	public static void test12() {
		Stream<String> jedis = "Luke, Obi-wan, Yoda".splitAsStream(",");
		StringJoiner joiner = new StringJoiner(",", "[","]");
		joiner.addAll(jedis);
		System.out.println(joiner.toString());
	}
	*/
	
	/*
	 * Reduce to obtain minimum and maximum
	 */
	public static void test11() {
		
		Sale maxSale = sales.stream().reduce(null, (a, b) -> a != null && a.getCost() > b.getCost() ? a : b);
		Sale minSale = sales.stream().reduce(null, (a, b) -> a != null && a.getCost() <= b.getCost() ? a : b);
		
		System.out.println(maxSale);
		System.out.println(minSale);
		
	}
	
	
	
	/*
 	 * Parallelize the finding of primes.
	public static void test10() {
		Integer[] source = new Integer[5000000];
		for(int i=0; i<source.length; i++)
			source[i] = i;
		
		Stream<Integer> allIntegers = parallel(source).filter(n -> isPrime(n));
		//Iterable<Integer> allIntegers = asList(source);
		
		System.out.println("Determining primes...");
		long start = System.currentTimeMillis();
		Iterable<Integer> primes = allIntegers.into(new LinkedList<Integer>());
		long end = System.currentTimeMillis();
		
		for(Integer prime : primes) {
			System.out.println(prime);
			if(prime > 1000)
				break;
		}
		
		System.out.printf("Time: %d ms%n", (end-start));
	}
	*/
	
	private static boolean isPrime(int value) {
		if(value < 2) {
			return false;
		}
		int divisor = 2;
		while(divisor != value) {
			if(value % divisor == 0){
				return false;
			}else if(divisor * divisor >= value) {
				return true;
			}else {
				divisor = divisor % 2 == 0 ? divisor + 1 : divisor + 2;
			}
		}
		return true;
	}
	
	/*
	 * Find total summation of sales. 
	 */
	public static void test09() {
		double sum1 = sales.stream().map(Sale::getCost).reduce(0.0, (a, b) -> a + b);
		double sum2 = sales.stream().map(Sale::getCost).reduce(0.0, (a, b) -> a + b);
		System.out.printf("%.2f == %.2f %n", sum1, sum2);
				
	}
	
	/*
	 * Group sales by seller or buyer.
	 */ 
	public static void test08() {
		/*
		MapStream<Person, Iterable<Sale>> groups = sales.groupBy(Sale::getBuyer);
		//MapStream<Person, Iterable<Sale>> groups = sales.groupBy(Sale::getSeller);
		
		for(BiValue<Person, Iterable<Sale>> byBuyer : groups.asIterable()) {
			System.out.printf("Person: %s%n", byBuyer.getKey());
			int i = 0;
			for(Sale sale : byBuyer.getValue()) {
				System.out.printf("\t%1d. %s %s%n",++i, sale.getCar().getBrand(), sale.getCar().getModel());
			}
			System.out.println();
		}
		*/
	}
	
	/*
	public static void test07() {
		List<Person> buyers = new ArrayList<Person>();
		//sales.map(sale -> sale.getBuyer()).into(buyers);
		sales.stream().map(Sale::getBuyer).into(buyers);
		System.out.println(buyers);
	}
	*/
	
	/*
	* Convert the price of the sale to dollars.
	*/
	public static void test06(){
		sales.forEach(sale -> {
			sale.setCost(sale.getCost() * 513.37);
		});
		System.out.println(sales);
	}
	
	/*
	 * Display the first buyer and seller of Ferraris.
	 */
	public static void test05() {
		Stream<Sale> onlyFerraris = sales.stream().filter(sale -> sale.getCar().getBrand().equals("Ferrari"));
		/*
		if(onlyFerraris.count() > 0) {
			System.out.println("Buyer: " + onlyFerraris.getFirst().getBuyer());
			System.out.println("Seller: " + onlyFerraris.getFirst().getSeller());
		}
		*/
	}
	
	/*
	 * Test that all buyers were not minors and that, at least,
	 * one of the buyers was over 30. 
	 */
	public static void test04() {
		if(sales.stream().allMatch(sale -> sale.getBuyer().getAge() >= 18)) {
			System.out.println("All buyers were not minors");
		}
		if(sales.stream().anyMatch(sale -> sale.getSeller().getAge() >= 30)) {
			System.out.println("At least one of the sellers was over 30");
		}
		if(sales.stream().noneMatch(sale -> sale.getBuyer().getAge() > 50)) {
			System.out.println("None of the buyers is over 50");
		}
	}
	
	public static void test03() {
		
		List<Sale> out = new ArrayList<Sale>();
		//sales.stream().filter(sale -> sale.getCar().getBrand().equals("Ferrari")).into(out);
		System.out.println(out);
		
		Person p = sales.stream().map(Sale::getBuyer).reduce(null, (a, b) -> a != null && a.getAge() <=  b.getAge() ? a : b);
		System.out.println(p);
		
		
		Function<Sale,Integer> a = s -> s.getBuyer().getAge();
		Comparator<Sale> b = comparing(a);
		
		System.out.println("Findings");
		sales.sort(comparing( (Function<Sale,Integer>) s -> s.getBuyer().getAge()));
		System.out.println(sales);
		/*
		Mapping<Person, Collection<Sale>> byPerson = sales.stream().groupBy(Sale::getBuyer).findFirst().get();
		System.out.println("Person: " + byPerson.getKey());
		System.out.println("Sales: " + byPerson.getValue());
		
	
		MapStream<Person, Iterable<Sale>> again = sales.groupBy(Sale::getBuyer);
		BiValue<Person, Iterable<Sale>> minor = again.into(new TreeMap<Person, Iterable<Sale>>(comparing((IntMapper<Person>)Person::getAge))).getFirst();

		System.out.println(minor.getKey());
		*/		
		
		
		
	}
	
	/**
	 * Print all car brands.
	 */
	public static void test02() {
		List<Car> cars = asList(
			new Car("Toyota","Hilux", 2009, 1500),
			new Car("Chevrolet", "Aveo", 2008, 1100)
			);

		String brands = null;
		
		//WITH JDK 1.7
		StringBuilder sb = new StringBuilder();
		for(Car car : cars) {
			sb.append(car.getBrand()).append(", ");
		}
		brands = sb.toString().substring(0, sb.length() -2);
		System.out.println(brands);
		
		//WITH JDK 1.8
		//String brands = cars.mapReduce(Car::getBrand, "", (a, b) -> a != "" ? (a + ", " + b) : b);
		StringJoiner joiner = new StringJoiner(", ");
		//joiner.addAll(cars.stream().map(Car::getBrand));
		brands = joiner.toString();
		System.out.println(brands);
		
	}
	
	public static void test01() {
		/*
		String text = "o.n.e,t.w.o,t.h.r.e.e,f.o.u.r";
		
		List<String> out = new ArrayList<>();
		text.splitAsStream(",")
			.filter(s -> s.contains("."))
			.flatMap(s -> s.splitAsStream("\\."))
			.into(out);
		
		System.out.println(out);
		
		Integer[] numbers = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		List<Integer> allNumbers = asList(numbers);
		
		//List<Integer> odds = parallel(numbers).filter(n -> n % 2 == 1).into(new ArrayList<Integer>());
		//System.out.println(odds);
		
		Map<String, Iterable<Integer>> classified = new LinkedHashMap<>();  
		allNumbers.groupBy(n -> n % 2 == 0 ? "Even": "Odd").into(classified);
		
		System.out.println(classified);
		
		List<String> sNumbers = asList("1","2","3","4","5");
		List<Integer> nNumbers = sNumbers.map(Integer::new).into(new ArrayList<Integer>());
		System.out.println(nNumbers);
		
		Long sum = sNumbers.mapReduce(Long::new, 0L, (LongBinaryOperator) (a,b) -> a + b);
		System.out.println("The sum is: " + sum);
		*/
	}
	
}

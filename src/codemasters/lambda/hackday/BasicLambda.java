package codemasters.lambda.hackday;

import codemasters.lambda.domain.*;
import java.util.*;
import java.util.function.*;

import static java.util.Comparator.comparing;

public class BasicLambda {
	
	public static void main(String[] args) {
		functions();
	}
	
	public static void newestTypeInferenceFeatures() {
		
		Person edwin = new Person("Edwin", "Dalorzo", 35, true);
		Person karla = new Person("Karla", "Fallas", 33, false);
		
		List<Person> persons = Arrays.asList(edwin,karla);
		
		persons.stream().sorted(comparing(Person::getAge));
		
		
	}
	
	
	public static void functions() {
		
		Function<Integer,Integer> square = n -> n * n;
		Function<String,String> reverse = s -> new StringBuilder(s).reverse().toString();
		
		Function<String,Integer> atoi = s -> Integer.valueOf(s);
		Function<Integer,String> itoa = n -> String.valueOf(n);
		
		Function<Integer,Integer> nReverse = itoa.andThen(reverse).andThen(atoi);
		Function<Integer,Integer> nReverse2 = atoi.compose(reverse.compose(itoa));
		
		Function<Person,String> toName = p -> p.getFirstName();
		Function<Car,Double> toValue = c -> c.getOriginalValue();
		
		BiFunction<Sale,Person,Sale> changeOwner = (s,p) -> new Sale(p,s.getSeller(), s.getCar(), s.getCost());
		
		
		System.out.println(nReverse.apply(1978));
		System.out.println(nReverse2.apply(1978));
	}
	
	
	public static void predicates() {
		
		
		//1. Predicate for odd numbers
		Predicate<Integer> isOdd = n -> n % 2 != 0;
		//2. Predicate for even numbers
		Predicate<Integer> isEven = n -> n % 2 == 0;
		//3. Predicate for palindrome
		Predicate<String> isPalindrome = s -> new StringBuilder(s).reverse().toString().equals(s);
		
		//4. Predicate that determines if a number is positive
		Predicate<Integer> isPositive = n -> n >= 0;
		Predicate<Integer> isNegative = isPositive.negate();
		Predicate<Integer> oddPossitive = isOdd.and(isPositive);
		Predicate<Integer> evenOrNegative = isEven.or(isNegative);
		
		//using static method
		Predicate<Integer> isTen = Predicate.isEqual(10);
		
		//5. Predicate that determines if a String starts with a prefix "Mr."
		Predicate<String> isMr = s -> s.startsWith("Mr.");
		BiPredicate<String,String> isPrefixed = (s,p) -> s.startsWith(p);
		
		//6.Determine if a date is before today
		Predicate<Date> beforeToday = d -> d.before(new Date());
		Predicate<Date> afterToday = d -> d.after(new Date());
		Predicate<Date> isToday = beforeToday.negate().and(afterToday.negate());
		
		BiPredicate<Date,Date> beforeOther = Date::before;
		
		Predicate<Person> isAdult = p -> p.getAge() >= 18;
		Predicate<Car> isToyota = Predicate.isEqual("Toyota");
		
		BiPredicate<Integer,Integer> isDivisible = (n,k) -> n % k == 0;
	}
	
	public Predicate<Car> hasBranPredicate(String brand) {
		//return car -> car.getBrand().equals(brand);
		return Predicate.isEqual(brand);
	}
	
	public Predicate<Car> checkCostForBrand(String brand) {
		return brand.equals("Toyota") ? car -> car.getOriginalValue() < 1000000 : car -> car.getOriginalValue() > 1000000; 
	}
	
	
	
	
	
	
}

package codemasters.lambda.jug;

import codemasters.lambda.domain.*;
import java.util.List;
import java.util.ArrayList;
import static java.util.Arrays.asList;

import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.function.Consumer;

import java.util.stream.Stream;
import java.util.stream.Collectors;

public class Prep02 {
	
	static List<Car> cars = new ArrayList<>();		
	static {
		Car yaris = new Car("Toyota","Yaris", 2008, 15000);
		Car mx5 = new Car("Mazda", "MX-5", 2009, 45000);
		Car equator = new Car("Susuki","Equator", 2009, 36000);
		Car x6 = new Car("BMW","X6", 2011, 88000);
		Car civic = new Car("Honda","Civic", 2012, 30000);
		Car tida = new Car("Nissan","Tida", 2013, 35000);
		cars.addAll(asList(yaris, mx5, equator, x6, civic, tida));
	}
	
	public static void main(String[] args) {
		
		List<String> brands = cars.stream().filter(car -> car.getYear() >= 2012)
					 			 .map(car -> car.getBrand()) 
					 			 .collect(Collectors.toList());
					 
		Integer sum = asList(1,2,3,4,5,6,7,8,9).stream()
								.filter(n -> n < 6)
								.reduce(0, (x, y) -> x + y);
					
		System.out.println(brands);								
		System.out.println(sum);								
		
		
		
	}
	
}

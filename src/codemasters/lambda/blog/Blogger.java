package codemasters.lambda.blog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Stream;
import java.util.concurrent.Callable;
import java.util.Arrays;
import codemasters.lambda.domain.Person;
import java.util.Comparator;
import java.util.concurrent.Callable;
import javax.swing.SwingUtilities;

import static java.util.Arrays.asList;

public class Blogger {
	
	public static void main(String[] args){
		/*
		List<Callable<String>> calls = Arrays.<Callable<String>>asList(
			() -> "Hello World"	
		);
		
		Stream<Integer> myInts = asList(1,2,3).stream();
		List<Integer> myIntList = myInts.into(new ArrayList<>());
		Set<Integer> myIntSet = myInts.into(new HashSet<>());
		*/
		/*
		Runnable r = () -> {};
		Callable<Integer> c = () -> { return 10; };
		
		Collection<Integer> myInts = asList(1,2,3,4,5,6,7,8,9);
		Collection<Integer> odds = filter(n -> n % 2 != 0, myInts);
		System.out.println(odds);
		
		Object o = (Predicate<Integer> & Serializable) n -> n % 2 == 0;
		Serializable s = (Serializable) o;
		Predicate<Integer> p = (Predicate<Integer>) o;
		System.out.println(p.test(2));
		*/
		
		Predicate<String> isPalindrome = text -> { StringBuilder reversed = new StringBuilder(text.toLowerCase()).reverse();
												  return text.equals(reversed.toString()); };
									
		System.out.println(isPalindrome.test("civic"));
		
		StringJoiner j = data -> {
			String result = "";
			for(String val : data){
				result += val;
			}
			return result;	
		};
		
		Comparator<Person> byAge = (left, right) -> {
			int lAge = left.getAge();
			int rAge = right.getAge();
			return Integer.compare(lAge,rAge);
		};
		
		Callable<List<String>> names = () -> {
			List<String> result = new ArrayList<>();
			try(BufferedReader in = new BufferedReader(new FileReader("jedis.txt"))){
				String name = null;
				while( (name = in.readLine()) != null){
					result.add(name);
				}
			}
			return result;
		};
		
		try {
			System.out.println(names.call());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		SwingUtilities.invokeLater( ()-> {
				Application app = new Application();
				app.show();
		});
		
		try {
			Callable<String> greetingCall = getCall(12,"Obi-wan");
			String message  = greetingCall.call();
			System.out.println(message);
		} catch(Exception e) {}
		
	}
	
	static Callable<String> getCall(int time, String name) {
		String message = time < 12 ? "Good morning, " : "Good afternoon, ";
		return () -> message + name;
	}
	
	
	public static class Application{
		public void show() {}
	}
	
	interface StringJoiner {
		public String join(String... values);
	}	
	
		
	interface Mapper<T, R> {
		R map(T input);
	}
	
	interface Predicate<T> {
		boolean test(T input);
	}
	
	interface Validator<T> {
		boolean validate(T input);
	}
	
	public static <T> Collection<T> filter(Predicate<? super T> predicate, Collection<? extends T> items) {
		Collection<T> result = new ArrayList<T>();
		for(T item: items) {
			if(predicate.test(item)) {
				result.add(item);
			}
		}
		return result;
	}
	
	
	
}

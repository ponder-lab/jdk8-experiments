package codemasters.lambda.eval;

import java.util.List;
import java.util.ArrayList;
import java.util.function.*;
import java.util.stream.*;
import static java.util.Arrays.asList;

public class StreamEval {
	
	public static void main(String[] args) {
		
		Supplier<ArrayList<String>> supplier = ArrayList::new;
		BiConsumer<ArrayList<String>,String> acc = ArrayList::add;
		BiConsumer<ArrayList<String>,ArrayList<String>> comb = ArrayList::addAll;
		
		List<String> strings = asList(1,2,3,4,5).stream()
		                                        .map(Object::toString)
		                                        .collect(ArrayList::new,ArrayList::add,ArrayList::addAll);
		                                        
		BiFunction<Jedi,String,String> bc = Jedi::getName;		                                             
		
	}
	
	
	public static class Jedi {
		private String name;
		
		public Jedi(String name) {
			this.name = name;
		}
		
		public String getName(String prefix) {
			return String.format("%s %s",prefix, name);
		}
		
		public String toString() {
			return "Jedi[name: " + name + "]";
		}
	}
	
	
	
	
}

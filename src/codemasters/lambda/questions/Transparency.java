package codemasters.lambda.questions;

import java.util.function.Consumer;
import java.util.List;
import static java.util.Arrays.asList;
import java.io.Writer;
import java.io.FileWriter;
import java.io.IOException;

public class Transparency {
	
	/*
	public static void main(String[] args) {
		
		try(Writer out = new FileWriter("test.txt")){
			
			List<String> jedis = asList("one","two","three");
			jedis.forEach( s-> { out.write(s); } );
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}*/
	
	
	
	
	
	public static void main(String[] args) {
		
		List<String> jedis = asList("one","two","three");
		
		try(Writer out = new FileWriter("test.txt")){
			forEach(s -> { out.write(s); }, jedis);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		forEach(s -> { System.out.println(s); }, jedis);
	}
	
	
	public static <T,E extends Exception> void forEach(Block<T,E> block, List<T> items) throws E {
		for(T item: items) {
			block.accept(item);
		}
	}
	
	public interface Block<T,E extends Exception> {
		public void accept(T t) throws E;
	}
	
	/*
	public static <T> void forEach(Block<T> block, List<T> items) throws Exception {
		for(T item: items) {
			block.accept(item);
		}
	}
	
	public interface Block<T> {
		public void accept(T t) throws Exception;
	}
	*/
}

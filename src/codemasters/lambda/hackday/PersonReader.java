package codemasters.lambda.hackday;

import codemasters.lambda.domain.Person;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.function.Predicate;

/**
 * Reads persons from a csv file containing persons information.
 *
 * @author Edwin Dalorzo
 */
public class PersonReader {
	
	public static void main(String[] args) {
		PersonReader pr = new PersonReader("jedis.txt");
		pr.getWomenOnly().forEach(System.out::println);	
	}
	
	private String file;
	
	public PersonReader(String file) {
		Objects.requireNonNull(file,"The persons file must not be null");
		this.file = file;
	}
	
	private Person fromCsvToPerson(String[] csv) {
		if(csv.length < 4) {
			throw new IllegalArgumentException("The csv must be at least 4 elements long");
		}
		return new Person(csv[0], //first name 
						 csv[1], //last name
						 Integer.valueOf(csv[2]), //age 
						 Boolean.valueOf(csv[3])); //is male?
	}
	
	private List<Person> loadPersons(Optional<Predicate<Person>> opt) {
		try(BufferedReader reader = new BufferedReader(new FileReader(this.file))){
			
			Stream<Person> persons;
			persons = reader.lines()
			      	        .map(s -> s.split(","))
			      	        .map(this::fromCsvToPerson);
			      	        
			if(opt.isPresent()){
				persons = persons.filter(opt.get());
			}
			      	     
			return persons.collect(Collectors.toList());
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Person> getAll() {
		return loadPersons(Optional.empty());
	}
	
	public List<Person> getByFilter(Predicate<Person> pred) {
		return loadPersons(Optional.of(pred));
	}
	
	public List<Person> getMenOnly() {
		return getByFilter(p -> p.isMale());
	}
	
	public List<Person> getWomenOnly() {
		return getByFilter(p -> !p.isMale());
	}
	
}

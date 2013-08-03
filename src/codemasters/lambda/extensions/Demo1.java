package codemasters.lambda.extensions;

import org.junit.Test;
import java.util.*;


import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/*
 * Scala's traits can... 
 *  ...have state.
 *  ...inherit from classes.
 *  ...be stacked.
 */
public class Demo1 {
	
	
	@Test
	public void testWarriorFight() {
		Goku goku = new Goku();
		String message = goku.fight();
		
		//assertThat(message, is("Warrior fights"));
		//assertThat(message1, is("Ninja fights"));
		assertThat(message, is("Warrior fights and Ninja fights too!"));
	}
	
	interface Warrior { default String fight() { return "Warrior fights"; } }
	interface Ninja extends Warrior {
		default String fight() {
			return Warrior.super.fight() + " and Ninja fights too!";
		}
	}
	class Goku implements Ninja { }
	
	
}



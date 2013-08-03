package codemasters.lambda.practice;

import codemasters.lambda.domain.*;
import java.util.*;
import java.util.function.*;
import org.junit.*;
import static java.lang.System.out;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class Demo3 {
	
	@Test
	public void gokuTheWarriorFights() {
		
		Warrior warrior = new Goku();
		//assertThat(warrior.fight(), is("Warriors fight"));
		//assertThat(warrior.fight(), is("Ninjas fight too"));
		//assertThat(warrior.fight(), is("Goku is a warrior, therefore he figths"));
		assertThat(warrior.fight(), is("Ninjas fight too and Goku fights as well"));
		
	}
	
	
	interface Warrior {
		public default String fight() {
			return "Warriors fight";
		}
	}
	
	interface Ninja extends Warrior {
		@Override
		public default String fight() {
			return "Ninjas fight too";
		}
	}
	
	interface Samurai extends Warrior {
		//public String fight();
	}
	
	static class Goku implements Ninja ,Samurai {
		
		
		
	}
	
}

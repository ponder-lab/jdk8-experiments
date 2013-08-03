package codemasters.lambda.showtime;

import codemasters.lambda.domain.*;
import java.util.*;
import java.util.function.*;
import org.junit.*;
import static java.lang.System.out;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class Demo4 {
	
	
	@Test
	public void testGoku() {
		
		Warrior goku = new Goku();
		//assertThat(goku.fight(), is("Warriors fight"));
		assertThat(goku.fight(), is("Ninjas fight too"));
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
		
		public String fight();
		
	}
	
	static abstract class Sayayin {
		public abstract String fight();
	}
	
	static class Goku implements Samurai {
		public String fight() {
			return "";
		}
		
	}
	
	
}

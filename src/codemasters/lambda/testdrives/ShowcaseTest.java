package codemasters.lambda.testdrives;

public class ShowcaseTest {
	
	public static void main(String[] args) {
		Warrior goku = new Goku();
		
		goku.fight();
	}
	
	static class Goku implements Samurai, Ninja {
		@Override
		public void fight() {
			Ninja.super.fight();
		}
	}
	
	
}

interface Warrior {
	default void fight() {
		System.out.println("Warrior fights");
	}
}

interface Samurai extends Warrior {
	@Override
	default void fight() {
		System.out.println("Samurai Fights");
	}
}

interface Ninja extends Warrior {
	@Override
	default void fight() {
		System.out.println("Ninja Fights");
	}
}

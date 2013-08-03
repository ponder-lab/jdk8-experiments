package codemasters.lambda.testdrives;

import org.junit.Test;
import codemasters.lambda.forkjoin.Primality;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class ForkJoinPrimalityTest {
	
	/**
	 * Test based on Wikipedia article:
	 * http://en.wikipedia.org/wiki/Prime-counting_function
	 */
	@Test
	public void testPrimalityCount() {
		Primality p = new Primality();
		assertThat(p.countPrimes(0, 10), is(4L));
		assertThat(p.countPrimes(0, 100), is(25L));
		assertThat(p.countPrimes(0, 1000), is(168L));
		assertThat(p.countPrimes(0, 10000), is(1229L));
		assertThat(p.countPrimes(0, 100000), is(9592L));
		assertThat(p.countPrimes(0, 1000000), is(78498L));
	}
	
}

package codemasters.lambda.forkjoin;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;



public class Primality {
	
	private static final Logger logger = Logger.getLogger("codemasters.lambda.forkjoin.Primality");
	private static final int THRESHOLD = 1000;
	private ForkJoinPool pool;
	
	private static class CountPrimalityTask extends RecursiveTask<Long> {
		
		private int low;
		private int high;
		
		CountPrimalityTask(int low, int high) {
			//System.out.printf("Tasked forked for: %d - %d%n", low, high);			
			this.low = low;
			this.high = high;
		}
		
		@Override
		protected Long compute() {
			long count = 0;
			if( (high - low) < THRESHOLD){
				for(int i = low; i < high; i++){
					if(isPrime(i)){
						count++;
						//logger.log(Level.INFO, String.format("%d is a prime", i));
					}
				}
				return count;
			} else {
				int middle = low + ( (high - low) >> 1);
				List<RecursiveTask<Long>> tasks = Arrays.<RecursiveTask<Long>>asList(
					new CountPrimalityTask(low, middle),
					new CountPrimalityTask(middle, high));
				invokeAll(tasks);
				for(RecursiveTask<Long> task : tasks){
					count += task.join();
				}
				return count;
			}
		}
		
		private boolean isPrime(int number) {
			if(number < 2) {
				return false;
			}
			if(number == 2) {
				return true;
			}
			int divisor = 2;
			while(true){
				if(number % divisor == 0){
					return false;
				} else if (divisor * divisor > number) {
					return true;
				} else if (divisor % 2 == 0){
					divisor += 1;
				} else {
					divisor += 2;
				}
			}
		}
	}
	
	public Primality() {
		this.pool = new ForkJoinPool();
		//System.out.println(pool);
	}
	
	public long countPrimes(int from, int to){
		ForkJoinTask<Long> task = this.pool.submit(new CountPrimalityTask(from, to));
		return task.join();
	}
}

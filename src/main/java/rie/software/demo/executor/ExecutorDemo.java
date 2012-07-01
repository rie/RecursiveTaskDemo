package rie.software.demo.executor;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * A simple class to demonstrate {@code ExecutorService}
 * This class will calculate factorial of a given number.
 */
public class ExecutorDemo implements Callable<Integer> {

	private int first;
	private int last;
	
	private ExecutorDemo(int numToBeCalculated) {
		this(1, numToBeCalculated);
	}

	private ExecutorDemo(int first, int last) {
		this.first = first;
		this.last = last;
	}
	
	public Integer call() throws Exception {
		int diff = this.last - this.first;
		if(diff==0) {
			return this.last;
		} else {
			return this.first * this.last;
		}
	}

	/**
	 * Calculates factorial by {@code ExecutorService} and output the result.
	 * @param args - number to be calculated it's factorial.
	 */
	public static final void main(String... args)  {
		
		if (args == null) {
			System.err.println("Please input a number.");
			System.exit(1);
		}

		int numToBeCalculated = Integer.parseInt(args[0]);
		
		Set<ExecutorDemo> tasks = new HashSet<ExecutorDemo>();
		for(int cnt=1; cnt<=numToBeCalculated; cnt+=2) {
			
			int first = cnt;
			int last;
			if(cnt==numToBeCalculated) {
				last = numToBeCalculated;
			} else {
				last = cnt+1;
			}
			
			ExecutorDemo task = new ExecutorDemo(first, last);
			tasks.add(task);
		}
		
		ExecutorService ex = Executors.newCachedThreadPool();
		try {
			
			int calculatedNum = 1;
			List<Future<Integer>> results = ex.invokeAll(tasks);
			
			for(Future<Integer> result : results) {
				calculatedNum = calculatedNum * result.get();
			}
			
			System.out.println(MessageFormat.format("The factorial of {0} is {1}.",numToBeCalculated, calculatedNum));
			
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
	}


}

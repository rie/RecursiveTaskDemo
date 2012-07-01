package rie.software.demo.forkjoin;

import java.text.MessageFormat;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * A simple class to demonstrate {@code RecursiveTask}
 * This class will calculate factorial of a given number.
 */
public class RecursiveActionDemo extends RecursiveTask<Integer> {

	private static final long serialVersionUID = 1L;
	private int first;
	private int last;

	private RecursiveActionDemo(int numToBeCalculated) {
		this(1, numToBeCalculated);
	}

	private RecursiveActionDemo(int first, int last) {
		this.first = first;
		this.last = last;
	}

	@Override
	protected Integer compute() {

		int diff = this.last - this.first;

		if (diff == 0) {

			return this.last;

		} else if (diff == 1) {
			
			return this.first * this.last;
		
		} else {
			
			int divide = (this.first + this.last) / 2;
			RecursiveActionDemo firstTask = new RecursiveActionDemo(this.first, divide);
			firstTask.fork();
			RecursiveActionDemo secondTask = new RecursiveActionDemo(divide + 1, this.last);
			int result = firstTask.join() * secondTask.invoke();
			return result;
		}
		
	}
	
	/**
	 * Calculates factorial by {@code RecursiveTask} and output the result.
	 * @param args - number to be calculated it's factorial.
	 */
	public static final void main(String... args) {

		if (args == null) {
			System.err.println("Please input a number.");
			System.exit(1);
		}

		int numToBeCalculated = Integer.parseInt(args[0]);
		ForkJoinPool fjPool = new ForkJoinPool();
		int calculatedNum = fjPool.invoke(new RecursiveActionDemo(numToBeCalculated));

		System.out.println(MessageFormat.format("The factorial of {0} is {1}.",numToBeCalculated, calculatedNum));

	}
}

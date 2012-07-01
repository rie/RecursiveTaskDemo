package rie.software.demo;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A simple class to demonstrate {@code Thread}
 * This class will calculate factorial of a given number.
 */
public class RecursiveTaskDemo implements Runnable {

	private int first;
	private int last;
	private int result;
	
	private RecursiveTaskDemo(int numToBeCalculated) {
		this(1, numToBeCalculated);
	}

	private RecursiveTaskDemo(int first, int last) {
		this.first = first;
		this.last = last;
	}


	public void run() {
		int diff = this.last - this.first;
		if(diff==0) {
			this.result =  this.last;
		} else {
			this.result =  (this.first * this.last);
		}
	}

	public int getResult() {
		return result;
	}

	/**
	 * Calculates factorial by {@code Thread} and output the result.
	 * @param args - number to be calculated it's factorial.
	 */
	public static final void main(String... args)  {
		
		if (args == null) {
			System.err.println("Please input a number.");
			System.exit(1);
		}

		int numToBeCalculated = Integer.parseInt(args[0]);
		
		Map<Thread, RecursiveTaskDemo> tasks = new HashMap<Thread, RecursiveTaskDemo>();
		for(int cnt=1; cnt<=numToBeCalculated; cnt+=2) {
			
			int first = cnt;
			int last;
			if(cnt==numToBeCalculated) {
				last = numToBeCalculated;
			} else {
				last = cnt+1;
			}
			
			RecursiveTaskDemo task = new RecursiveTaskDemo(first, last);
			Thread thread = new Thread(task);
			thread.run();
			tasks.put(thread, task);
		}
		
		int calculatedNum = 1;
		Set<Thread> threads = tasks.keySet();
		for (Thread thread : threads) {
			try {
				
				thread.join();
				
				RecursiveTaskDemo recursiveFactorialTask = tasks.get(thread);
				calculatedNum = calculatedNum * recursiveFactorialTask.getResult();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		
		System.out.println(MessageFormat.format("The factorial11 of {0} is {1}.",numToBeCalculated, calculatedNum));
		
	}



}

package dataGen;

import java.util.ArrayList;
import java.util.List;

public class Employee {
	private static final int MIN_BREAK = toSecs(0.1);
	private static final int MAX_BREAK = toSecs(3);
	private static final int MIN_DAY_LENGTH = MAX_BREAK + 4;
	private static final int MIN_WORK_AMOUNT = toSecs(4);
	private static final int MAX_WORK_AMOUNT = toSecs(10);

	private static final int WORK_DAY_START = toSecs(6);
	private static final int WORK_DAY_END = toSecs(23.5);
	private static final int DEFAULT_START_TIME = toSecs(6);
	private static final int SMALL_WORK_INTERVAL = toSecs(0.3);

	private static int nextId = 0;
	
	private int id;
	private int time_in;
	private int time_out;
	private int break_length;

	private ArrayList<Integer> swipes;

	private Employee(int time_in, int time_out, int break_length) {
		this.id = nextId;
		nextId++;
		
		swipes = new ArrayList<Integer>();
		this.time_in = time_in;
		this.time_out = time_out;
		this.break_length = break_length;

		// Ensure data is clean
		// time_in = Math.min(time_in, DEFAULT_START_TIME);
		// break_length = Math.max(break_length, 0);
		// if (time_out <= time_in + break_length) time_out = time_in + break_length
		// + SMALL_WORK_INTERVAL;

		// Add the swipes
		swipes.add(time_in);
		int break_start = time_in
				+ (int) (Math.random() * (time_out - time_in - break_length + 1));
		swipes.add(break_start);
		swipes.add(break_start + break_length);
		swipes.add(time_out);
	}

	public static Employee getRandomEmployee() {
		int time_in = WORK_DAY_START + (int) (Math.random() * toSecs(3));
		int break_length = MIN_BREAK + (int) (Math.random() * (MAX_BREAK - MIN_BREAK + 1) );
		int time_out = time_in + break_length	+ MIN_WORK_AMOUNT + (int) (Math.random() * (MAX_WORK_AMOUNT - MIN_WORK_AMOUNT));
		time_out = Math.min(time_out, WORK_DAY_END);

		Employee e = new Employee(time_in, time_out, break_length);
		return e;
	}

	public String getSwipesCSVString() {
		String ret = "";
		for (Integer swipe_time : swipes) {
			ret += swipe_time + ", ";
		}
		
		return ret.substring(0, ret.length()-2);
	}
	
	public int getId() {
		return id;
	}

	public List<Integer> getSwipeTimes() {
		return swipes;
	}
	
	/***
	 * convert hours to seconds (truncated to second).
	 * 
	 * @param hours
	 *          time in hours
	 * @return time in seconds
	 */
	private static int toSecs(double hours) {
		return (int) hours * 60 * 60;
	}
}

package solution;

import java.util.ArrayList;

public class Employee {
	private int id; // unique employee id
	private int totalTime;
	private ArrayList<Long> timesIn, timesOut;
	private boolean inBuilding;

	public Employee(int id) {
		this.id = id;

		totalTime = 0;
		timesIn = new ArrayList<Long>();
		timesOut = new ArrayList<Long>();
		inBuilding = false;
	}
	
	public void registerSwipe(long time) {
		if (inBuilding) {
			long last_swipe = timesIn.get( timesIn.size() - 1 );	   // get last
			totalTime += (time - last_swipe);												 // update total time
			timesOut.add(time);
			inBuilding = false;
		} else {
			timesIn.add( time );
			inBuilding = true;
		}
	}
	
	public boolean wasInBuildingAt(long time) {
		for (int i = 0; i < timesIn.size(); i++) {
			long timeIn = timesIn.get(i);
			long timeOut = timesOut.get(i);
			
			if (time >= timeIn && time <= timeOut) return true;
		}
		
		return false;
	}
	
	public boolean isInBuilding() {
		return inBuilding;
	}
	
	public int getId() {
		return id;
	}

	public int getTimeInBuilding() {
		return totalTime;
	}

	public long getFirstSwipeTime() {
		if (timesIn.size() > 0) return timesIn.get(0);
		return -1;
	}

}

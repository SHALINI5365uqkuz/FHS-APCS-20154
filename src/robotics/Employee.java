package robotics;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Employee {
	public static enum Subteam {ELECTRICAL, MECHANICAL, SOFTWARE, FINANCE, SAFETY, SPECIAL_PROJECTS};

	private Subteam subteam;
	private String firstname, lastname;
	private int id; // unique employee id
	private int totalTime;
	private ArrayList<LocalDateTime> timesIn, timesOut;
	private boolean inBuilding;

//	public Employee(int id) {
//		this.id = id;
//
//		totalTime = 0;
//		timesIn = new ArrayList<LocalDateTime>();
//		timesOut = new ArrayList<LocalDateTime>();
//		inBuilding = false;
//	}

	public Employee(int id, String fn, String ln, String subteam,
			int freshmanYear) {
		this.id = id;
		this.firstname = fn;
		this.lastname = ln;
		
		// TODO: subteam things
		
		totalTime = 0;
		timesIn = new ArrayList<LocalDateTime>();
		timesOut = new ArrayList<LocalDateTime>();
		inBuilding = false;
	}

	public void registerSwipe(LocalDateTime time) {
		if (inBuilding) {
			LocalDateTime last_swipe = timesIn.get(timesIn.size() - 1); // get last
			totalTime += (time - last_swipe); // update total time
			timesOut.add(time);
			inBuilding = false;
		} else {
			timesIn.add(time);
			inBuilding = true;
		}
	}

	public boolean wasInBuildingAt(LocalDateTime time) {
		for (int i = 0; i < timesIn.size(); i++) {
			LocalDateTime timeIn = timesIn.get(i);
			LocalDateTime timeOut = timesOut.get(i);

			if (time >= timeIn && time <= timeOut)
				return true;
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

	public String toString() {
		String ret = id + " total time: "
				+ getTimeInBuilding() + "\n\t";

		for (int i = 0; i < timesIn.size(); i++) {
			ret += "[" + timesIn.get(i) + " - "
					+ timesOut.get(i) + "], ";
		}
		
		return ret;
	}
}

package robotics;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;

public class Employee {
	public static enum Subteam {ELECTRICAL, MECHANICAL, SOFTWARE, FINANCE, SAFETY, SPECIAL_PROJECTS, MENTOR, UNKNOWN};

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

	public Employee(int id, String fn, String ln, Subteam subteam, int freshmanYear) {
		this.id = id;
		this.firstname = fn;
		this.lastname = ln;
		this.subteam = subteam;
		
		totalTime = 0;
		timesIn = new ArrayList<LocalDateTime>();
		timesOut = new ArrayList<LocalDateTime>();
		inBuilding = false;
	}

	public void registerSwipe(LocalDateTime time) {
		if (inBuilding) {
			LocalDateTime last_swipe = timesIn.get(timesIn.size() - 1); // get last
			totalTime += last_swipe.until(time, ChronoUnit.SECONDS); // update total time
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

			if (time.isAfter(timeIn) && time.isBefore(timeOut))
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
	
	public static Subteam getSubteamFor(String n) {
		n = n.toLowerCase();
		if (n.contains("electrical")) return Subteam.ELECTRICAL;
		if (n.contains("mechanical")) return Subteam.MECHANICAL;
		if (n.contains("software")) return Subteam.SOFTWARE;
		if (n.contains("special")) return Subteam.SPECIAL_PROJECTS;
		if (n.contains("finance")) return Subteam.FINANCE;
		if (n.contains("mentor")) return Subteam.MENTOR;
		if (n.contains("safety")) return Subteam.SAFETY;
		return Subteam.UNKNOWN;
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
package robotics;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;

public class Employee implements Comparable<Employee> {
	public static enum Subteam {
		ELECTRICAL, MECHANICAL, SOFTWARE, FINANCE, SAFETY, SPECIAL_PROJECTS, MENTOR, UNKNOWN, MPR
	};

	private Subteam subteam;
	private String firstname, lastname;
	private String id; // unique employee id
	private int firstYear;
	private int totalTime;
	private ArrayList<LocalDateTime> timesIn, timesOut, forgotToSignOutDates;
	private boolean inBuilding;

	// public Employee(int id) {
	// this.id = id;
	//
	// totalTime = 0;
	// timesIn = new ArrayList<LocalDateTime>();
	// timesOut = new ArrayList<LocalDateTime>();
	// inBuilding = false;
	// }

	public Employee(String id, String fn, String ln, Subteam subteam, int firstyear) {
		this.id = id;
		this.firstname = fn;
		this.lastname = ln;
		this.subteam = subteam;
		this.firstYear = firstyear;

		totalTime = 0;
		timesIn = new ArrayList<LocalDateTime>();
		timesOut = new ArrayList<LocalDateTime>();
		forgotToSignOutDates = new ArrayList<LocalDateTime>();
		inBuilding = false;
	}

	public void registerSwipe(LocalDateTime time) {
		if (inBuilding) {
			LocalDateTime last_swipe = timesIn.get(timesIn.size() - 1); // get last
			totalTime += last_swipe.until(time, ChronoUnit.SECONDS); // update total
																																// time
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
			
			if (timesOut.size() - 1 < i) return time.isAfter(timeIn);
			
			LocalDateTime timeOut = timesOut.get(i);
			if (time.isAfter(timeIn) && time.isBefore(timeOut))
				return true;
		}

		return false;
	}

	public boolean isInBuilding() {
		return inBuilding;
	}

	public String getId() {
		return id;
	}

	public int getTimeInBuilding() {
		return totalTime;
	}

	public static Subteam getSubteamFor(String n) {
		n = n.toLowerCase();
		if (n.contains("electrical"))
			return Subteam.ELECTRICAL;
		if (n.contains("mechanical"))
			return Subteam.MECHANICAL;
		if (n.contains("software"))
			return Subteam.SOFTWARE;
		if (n.contains("special"))
			return Subteam.SPECIAL_PROJECTS;
		if (n.contains("finance"))
			return Subteam.FINANCE;
		if (n.contains("mentor"))
			return Subteam.MENTOR;
		if (n.contains("safety"))
			return Subteam.SAFETY;
		return Subteam.UNKNOWN;
	}

	public String toString() {
		return this.id + " " + this.firstname + " " + this.lastname + " "
				+ this.subteam.toString() + " ";
	}
	
	public String displayInfo() {
		String s = this.firstname + " " + this.lastname + " id: " + this.id + " " 
				+ this.subteam.toString() + " " + ((this.isInBuilding())?"currently PRESENT":"NOT present");
		return s;
	}
	
	public String getReportFor() {
		String s = "Total hours (including days they forgot to sign out): " + getTotalTime(true);
		s += "\nTotal hours (discounting days they forgot to sign out): " + getTotalTime(false);
		s += "\n# times forgot to sign out: " + this.forgotToSignOutDates.size();
		s += "\n\n";
		return s;
	}
	
	public int compareTo(Employee other) {
		if (this.getTotalTime(false) > other.getTotalTime(false)) return 1;
		if (this.getTotalTime(false) < other.getTotalTime(false)) return -1;
		return 0;
	}

	public String getFirstName() {
		return this.firstname;
	}
	
	public String getLastName() {
		return this.lastname;
	}
	
	public String getSubteam() {
		return this.subteam.toString();
	}
	
	public double getTotalTime(boolean includesForgotDays) {
		double min = 0;
		
		for (int i = 0; i < this.timesIn.size(); i++) {
			LocalDateTime in = this.timesIn.get(i);
			LocalDateTime out;
			if (timesOut.size() > i)
				out = this.timesOut.get(i);
			else
				out = LocalDateTime.now();
			
			if (!includesForgotDays && this.forgotToSignOutOn(out)) continue;
			
			min += in.until(out, ChronoUnit.MINUTES);
		}
		
		return min/60.0;
	}
	
	public double getTotalTime(boolean includesForgotDays, LocalDateTime startDate, LocalDateTime endDate) {
		double min = 0;
		
		for (int i = 0; i < this.timesIn.size(); i++) {
			LocalDateTime in = this.timesIn.get(i);
			LocalDateTime out;
			if (timesOut.size() > i)
				out = this.timesOut.get(i);
			else
				out = LocalDateTime.now();
			
			if (!includesForgotDays && this.forgotToSignOutOn(out)) continue;
			if (in.isAfter(startDate) && out.isBefore(endDate)) {
				min += in.until(out, ChronoUnit.MINUTES);
			}
		}
		
		return min/60.0;
	}

	public ArrayList<LocalDateTime> getSwipes() {
		ArrayList<LocalDateTime> list = new ArrayList<LocalDateTime>();
		for (int i = 0; i < this.timesIn.size(); i++) {
			list.add(timesIn.get(i));
			if (i < timesOut.size()) list.add(timesOut.get(i));
		}
		
		return list;
	}

	public void logout(LocalDateTime t) {
		if (this.isInBuilding()) {
			this.registerSwipe(t);
		} else {
			System.out.println(firstname + " is already logged out.");
		}
	}
	
	public boolean forgotToSignOutOn(LocalDateTime datetime) {
		return this.forgotToSignOutDates.contains(datetime);
	}

	public void forgotToSignOut(LocalDateTime datetime) {
		forgotToSignOutDates.add(datetime);
	}
}
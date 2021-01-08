package solution;

import java.util.ArrayList;

public class EmployeeRecords {
	private ArrayList<Employee> employees;
	
	/***
	 * constructs a new records object representing a single day of card swipes.  it is pre-populated
	 * with a number of consecutive employee ids gien by numids.
	 * 
	 * @param numids the number of consecutive employee ids to pre-populate the object with
	 */
	public EmployeeRecords(int numids) {
		employees = new ArrayList<Employee>();
		createEmployees(numids);
	}
	
	/***
	 * This method creates a list of 300 employees with ids from 0 to 300.
	 * These are the ids that will be in the actual swipes in the data file.
	 * 
	 * You may modify this method and/or the employee constructor if you wish.
	 * 
	 * @param numids the number of consecutive ids to populate your employee list with
	 */
	private void createEmployees(int numids) {
		for (int id = 0; id < numids; id++) {
			employees.add( new Employee(id) );
		}
	}
	
	/***
	 * get an Employee object from the list by id number.
	 * 
	 * @param id the id of the employee to return
	 * @return Employee the employee corresponding to the input id, or null if no such employee
	 */
	public Employee getEmployeeById(int id) {
		for (Employee e : employees) {
			if (e.getId() == id) return e;
		}
		
		return null;
	}
	
	/***
	 * register a new swipe with an id and a time
	 * @param id the id of the card that was swiped
	 * @param swipe_time the time (in seconds since midnight) when the swipe occured
	 */
	public void registerSwipe(int id, long swipe_time) {
		Employee employee = getEmployeeById(id);			// get the employee for this swipe
		
		employee.registerSwipe(swipe_time);
	}
	
	/***
	 * return the total time spent inside the building for employee id
	 * @param id the id of the employee you want to get the time for
	 * @return int the total seconds spent inside the building so far for employee id
	 */
  public int getTimeInBuildingFor(int id) {
  	return  getEmployeeById(id).getTimeInBuilding();
  }
	
  /***
   * return the time employee id first swiped in
   * @param id the id of the employee you want to get the time for
   * @return int the time in seconds after midnight employee id first swiped in
   */
  public long getTimeInFor(int id) {
  		return  getEmployeeById(id).getFirstSwipeTime();
  }
  
  /***
   * return the employee with the most time in the building so far
   * @return Employee the employee with the most time in the building so far
   */
  public Employee getEmployeeWithMostTimeIn() {
  	int maxtime = 0;
  	Employee best = null;
  	
  	for (Employee e:employees) {
  		if (e.getTimeInBuilding() > maxtime) {
  			maxtime = e.getTimeInBuilding();
  			best = e;
  		}
  	}
  	
  	return best;
  }
  
  /***
   * return the employee with the least time in the building so far
   * @return Employee the employee with the least time in the building so far
   */
  public Employee getEmployeeWithLeastTimeIn() {
  	int mintime = Integer.MAX_VALUE;
  	Employee worst = null;
  	
  	for (Employee e:employees) {
  		if (e.getTimeInBuilding() < mintime) {
  			mintime = e.getTimeInBuilding();
  			worst = e;
  		}
  	}
  	
  	return worst;
  }
  
  /***
   * return a list of employees in the building at a particular time
   * @param time the time (seconds after midnight) you want an employee list for
   * @return the list of employees in the building at that time
   */
  public ArrayList<Employee> getEmployeesInBuildingAt(int time) {
  	ArrayList<Employee> inBuilding = new ArrayList<Employee>();
  	
  	for (Employee e:employees) {
  		if (e.wasInBuildingAt(time)) inBuilding.add(e);
  	}
  	
  	return inBuilding;
  }
  
  /***
   * return a 2-element array with a start and end time for the time interval 
   * during which the most employees were in the building
   * 
   * @return int[] a 2-element array with a start and end time
   */
  public int[] timeIntervalWithMostEmployees() {
  	return null;
  }
  
  public void displayAllEmployees() {
  	for (Employee e:this.employees) {
  		System.out.println(e);
  	}
  }
  
  public static double toHours(double seconds) {
  	return seconds/60.0/60.0;
  }
  
  public static double toSeconds(double hours) {
  	return hours*60.0*60.0;
  }
}
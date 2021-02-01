package robotics;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeRecords {
	private ArrayList<Employee> employees;

	/***
	 * constructs a new records object representing a single day of card swipes.
	 * it is pre-populated with a number of consecutive employee ids gien by
	 * numids.
	 * 
	 * @param numids
	 *          the number of consecutive employee ids to pre-populate the object
	 *          with
	 */
	public EmployeeRecords() {
		employees = new ArrayList<Employee>();
	}

	/***
	 * get an Employee object from the list by id number.
	 * 
	 * @param id
	 *          the id of the employee to return
	 * @return Employee the employee corresponding to the input id, or null if no
	 *         such employee
	 */
	public Employee getEmployeeById(int id) {
		for (Employee e : employees) {
			if (e.getId() == id)
				return e;
		}

		return null;
	}

	/***
	 * register a new swipe with an id and a time
	 * 
	 * @param id
	 *          the id of the card that was swiped
	 * @param swipe_time
	 *          the time (in seconds since midnight) when the swipe occured
	 */
	public void registerSwipe(int id, LocalDateTime swipe_time) {
		Employee employee = getEmployeeById(id); // get the employee for this swipe

		employee.registerSwipe(swipe_time);
	}

	/***
	 * return the total time spent inside the building for employee id
	 * 
	 * @param id
	 *          the id of the employee you want to get the time for
	 * @return int the total seconds spent inside the building so far for employee
	 *         id
	 */
	public int getTimeInBuildingFor(int id) {
		return getEmployeeById(id).getTimeInBuilding();
	}

	/***
	 * return the employee with the most time in the building so far
	 * 
	 * @return Employee the employee with the most time in the building so far
	 */
	public Employee getEmployeeWithMostTimeIn() {
		int maxtime = 0;
		Employee best = null;

		for (Employee e : employees) {
			if (e.getTimeInBuilding() > maxtime) {
				maxtime = e.getTimeInBuilding();
				best = e;
			}
		}

		return best;
	}

	/***
	 * return the employee with the least time in the building so far
	 * 
	 * @return Employee the employee with the least time in the building so far
	 */
	public Employee getEmployeeWithLeastTimeIn() {
		int mintime = Integer.MAX_VALUE;
		Employee worst = null;

		for (Employee e : employees) {
			if (e.getTimeInBuilding() < mintime) {
				mintime = e.getTimeInBuilding();
				worst = e;
			}
		}

		return worst;
	}

	/***
	 * return a list of employees in the building at a particular time
	 * 
	 * @param time
	 *          the time (seconds after midnight) you want an employee list for
	 * @return the list of employees in the building at that time
	 */
	public ArrayList<Employee> getEmployeesInBuildingAt(LocalDateTime time) {
		ArrayList<Employee> inBuilding = new ArrayList<Employee>();

		for (Employee e : employees) {
			if (e.wasInBuildingAt(time))
				inBuilding.add(e);
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
		for (Employee e : this.employees) {
			System.out.println(e);
		}
	}

	public static double toHours(double seconds) {
		return seconds / 60.0 / 60.0;
	}

	public static double toSeconds(double hours) {
		return hours * 60.0 * 60.0;
	}

	/***
	 * Load swipe data from a file.
	 * 
	 * pre-condition: File first line is column names and remaining lines are
	 * whitespace separated values. All whitespace is collapsed to a single space,
	 * so whitespace formatting doesn't matter.
	 * 
	 * @param filepath
	 */
	public void loadEmployeeDataFromFile(String filepath) {
		Scanner scanner;
		try {
			scanner = new Scanner(new FileReader(filepath));
			scanner.useDelimiter("\n");

			String line;
			scanner.next(); // skip the first line

			while (scanner.hasNext()) {
				line = scanner.next();
				String[] args = line.replaceAll("\\s+", " ").split(" ");

				try {
					int id = Integer.parseInt(args[0].trim());
					String fn = args[1].trim();
					String ln = args[2].trim();
					Employee.Subteam subteam = Employee.getSubteamFor(args[3].trim());
					int freshmanYear = Integer.parseInt(args[4].trim());

					this.addEmployee(new Employee(id, fn, ln, subteam, freshmanYear));
				} catch (Exception e) {
					System.out.println("something went wrong:" + e.getMessage());
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found " + filepath);
		}
	}

	public void addEmployee(Employee employee) {
		this.employees.add(employee);
	}

	/***
	 * Load swipe data from a file.
	 * 
	 * pre-condition: File first line is the # of records, second line is column
	 * names and remaining lines are comma-separated values. First column is
	 * employee id, second is time (in seconds since midnight). We assume data is
	 * in increasing time-sequential order.
	 * 
	 * @param filepath
	 */
	public void loadAttendanceDataFromFile(String filepath) {
		Scanner scanner;
		try {
			scanner = new Scanner(new FileReader(filepath));
			scanner.useDelimiter("\n");
			
			String line;
			
			do {															// skip to first record
				line = scanner.next().trim();
			} while (!line.equals(""));
			
			while (scanner.hasNext()) {
				line = scanner.next().trim();
				String[] args = line.replaceAll("\\s+", " ").split(" ");

				// Get employee id
				int id = Integer.parseInt(args[0]);
				Employee e = getEmployeeById(id);
				if (e == null) {
					System.out.println("Invalid ID: " + id);
					continue; // if invalid, skip to next record
				}

				line = scanner.next().trim();
				while (!line.equals("")) {
					args = line.replaceAll("\\s+", " ").split(" ");
					LocalDateTime swipe = LocalDateTime.parse(args[0]);
					e.registerSwipe(swipe);

					line = scanner.next().trim();
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found " + filepath);
		}
	}
}
package robotics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeRecords {
	private LocalDateTime W1Start = LocalDateTime.of(2016, 1, 9, 1, 0, 0);
	private LocalDateTime W2Start = W1Start.plusWeeks(1);
	private LocalDateTime W3Start = W1Start.plusWeeks(2);
	private LocalDateTime W4Start = W1Start.plusWeeks(3);
	private LocalDateTime W5Start = W1Start.plusWeeks(4);
	private LocalDateTime W6Start = W1Start.plusWeeks(5);
	private LocalDateTime W7Start = W1Start.plusWeeks(6);
	
	private ArrayList<Employee> employees;

	/***
	 * constructs a new records object representing a single day of card swipes.
	 * it is pre-populated with a number of consecutive employee ids gien by
	 * numids.
	 * 
	 * @param numids
	 *            the number of consecutive employee ids to pre-populate the
	 *            object with
	 */
	public EmployeeRecords() {
		employees = new ArrayList<Employee>();
	}

	/***
	 * get an Employee object from the list by id number.
	 * 
	 * @param id
	 *            the id of the employee to return
	 * @return Employee the employee corresponding to the input id, or null if
	 *         no such employee
	 */
	public Employee getEmployeeById(String id) {
		for (Employee e : employees) {
			if (e.getId().trim().equals(id.trim()))
				return e;
		}

		return null;
	}

	/***
	 * register a new swipe with an id and a time
	 * 
	 * @param id
	 *            the id of the card that was swiped
	 * @param swipe_time
	 *            the time (in seconds since midnight) when the swipe occured
	 */
	public void registerSwipe(String id, LocalDateTime swipe_time) {
		Employee employee = getEmployeeById(id); // get the employee for this
													// swipe

		if (employee != null)
			employee.registerSwipe(swipe_time);
		else
			System.out.println("Error: No record for id: " + id);
	}

	/***
	 * return the total time spent inside the building for employee id
	 * 
	 * @param id
	 *            the id of the employee you want to get the time for
	 * @return int the total seconds spent inside the building so far for
	 *         employee id
	 */
	public int getTimeInBuildingFor(String id) {
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
	 *            the time (seconds after midnight) you want an employee list
	 *            for
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

	public void writeEmployeeDataToFile(String filePath) {
		BufferedWriter writer = null;
		try {
			// create a temporary file
			File logFile = new File(filePath);
			writer = new BufferedWriter(new FileWriter(logFile));

			writer.write("id fn ln subteam firstyear \n");

			// loop over all records
			for (Employee employee : this.employees) {
				writer.write(employee + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
			}
		}
	}
	
	public void writeAttendanceReportToFile(String filePath) {
		BufferedWriter writer = null;
		try {
			// create a temporary file
			File logFile = new File(filePath);
			writer = new BufferedWriter(new FileWriter(logFile));

			writer.write("id, fn, ln, subteam, W1, W2, W3, W4, W5, W6 \n");

			// loop over all records
			for (Employee employee : this.employees) {
				writer.write(employee.getId() + "," +
							employee.getFirstName() + "," + 
							employee.getLastName() + "," +
							employee.getSubteam() + "," +
							employee.getTotalTime(false, W1Start, W2Start)+ "," +
							employee.getTotalTime(false, W2Start, W3Start)+ "," +
							employee.getTotalTime(false, W3Start, W4Start)+ "," +
							employee.getTotalTime(false, W4Start, W5Start)+ "," +
							employee.getTotalTime(false, W5Start, W6Start)+ "," +
							employee.getTotalTime(false, W6Start, W7Start)+ "," +
						"\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
			}
		}
	}

	public void writeAttendanceDataToFile(String filePath) {
		BufferedWriter writer = null;
		try {
			// create a temporary file
			File logFile = new File(filePath);
			writer = new BufferedWriter(new FileWriter(logFile));

			writer.write("Format:  Each entry starts with an id followed by a series of datetimes for \n");
			writer.write("swiping in and out. A blank line starts a new record. \n\n");

			// loop over all records
			for (Employee employee : this.employees) {
				writer.write(employee.getId() + " " + employee.getFirstName() + " " + employee.getLastName() + "\n");
				for (LocalDateTime swipe : employee.getSwipes()) {
					writer.write(swipe.toString());
					if (employee.forgotToSignOutOn(swipe)) writer.write(" **");
					writer.write("\n");
				}
				writer.write("\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
			}
		}
	}

	/***
	 * Load employee data from a file.
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
					String id = args[0].trim();
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
	 * employee id, second is time (in seconds since midnight). We assume data
	 * is in increasing time-sequential order.
	 * 
	 * @param filepath
	 */
	public void loadAttendanceDataFromFile(String filepath) {
		Scanner scanner;
		try {
			scanner = new Scanner(new FileReader(filepath));
			scanner.useDelimiter("\n");

			String line;

			if (!scanner.hasNext())
				return;

			do { // skip to first record
				line = scanner.next();
			} while (scanner.hasNext() && !line.equals(""));

			while (scanner.hasNext()) {
				line = scanner.next().trim();
				String[] args = line.replaceAll("\\s+", " ").split(" ");

				// Get employee id
				String id = args[0].trim();
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

					if (args.length > 1 && args[1].contains("*")) {
						e.forgotToSignOut(swipe);
					}

					line = scanner.next().trim();
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found " + filepath);
		}
	}

	public void logOutAllStudents() {
		for (Employee e : this.employees) {
			if (e.isInBuilding()) {
				LocalDateTime t = LocalDateTime.now();
				this.registerSwipe(e.getId(), t);
				e.forgotToSignOut(t);
			}
		}
	}

	public String getAllStudentsString() {
		String a = "";

		for (Employee e : this.employees) {
			a += e.toString() + "\n";
		}

		return a;
	}

	public String getStudentsString(String rest) {
		String a = "";

		for (Employee e : this.employees) {
			if (e.getSubteam().toLowerCase().equals(rest.toLowerCase().trim()))
				a += e.toString() + "\n";
		}

		return a;
	}

	public boolean isId(String first) {
		Employee e = this.getEmployeeById(first);
		return (e != null);
	}

	public boolean isName(String first) {
		first = first.toLowerCase();
		for (Employee e : employees) {
			if (e.getFirstName().toLowerCase().equals(first) || e.getLastName().toLowerCase().equals(first))
				return true;
		}

		return false;
	}

	public List<Employee> getEmployeesByName(String first) {
		List<Employee> list = new ArrayList<Employee>();

		for (Employee e : employees) {
			if (e.getFirstName().toLowerCase().equals(first) || e.getLastName().toLowerCase().equals(first))
				list.add(e);
		}

		return list;
	}

	public List<Employee> getCurrentlyAbsentEmployeesFor(String second) {
		ArrayList<Employee> list = new ArrayList<Employee>();
		for (Employee e : this.employees) {
			if (!e.isInBuilding() && e.getSubteam().equalsIgnoreCase(second))
				list.add(e);
		}
		return list;
	}

	public ArrayList<Employee> getAllEmployees() {
		return this.employees;
	}
}
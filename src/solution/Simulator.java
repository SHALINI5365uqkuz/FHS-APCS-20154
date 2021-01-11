package solution;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/***
 * This class simulates the swiping by reading a data file of employee swipes
 * and then calling the method registerSwipe() in EmployeeRecords for each
 * swipe.
 * 
 * @author David
 *
 */
public class Simulator {
	public static void main(String[] args) {
		EmployeeRecords records = new EmployeeRecords(300);

		records.loadDataFromFile("swipeData.csv");
		
		records.displayAllEmployees();
	}
}
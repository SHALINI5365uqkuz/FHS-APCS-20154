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
	private static final int ID = 0;
	private static final int TIME = 1;

	public static void main(String[] args) {
		EmployeeRecords records = new EmployeeRecords(300);

		long[][] swipeData = loadDataFromFile("swipeData.csv");
		
		for (int i = 0; i < swipeData.length; i++) {
			records.registerSwipe((int)swipeData[i][ID], swipeData[i][TIME]);
		}
		
		records.displayAllEmployees();
	}

}

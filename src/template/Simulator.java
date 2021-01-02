package template;

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
		EmployeeRecords records = new EmployeeRecords();

		long[][] swipeData = loadDataFromFile("swipeData.csv");
		
		for (int i = 0; i < swipeData.length; i++) {
			records.registerSwipe((int)swipeData[i][ID], swipeData[i][TIME]);
		}
	}

	private static long[][] loadDataFromFile(String filepath) {
		Scanner scanner;
		try {
			scanner = new Scanner(new FileReader(filepath));
			scanner.useDelimiter("\n");

			String line;
			int numRecords = scanner.nextInt(); 	// first line gives the number of records
			long[][] records = new long[numRecords][2];
			
			scanner.next();												// skip the next line
			
			int i = 0;
			while (scanner.hasNext()) {
				line = scanner.next();
				String[] args = line.split(",");

				try {
					int id = Integer.parseInt(args[ID].trim());
					long time = Long.parseLong(args[TIME].trim());
					
					records[i][ID] = id;
					records[i][TIME] = time;
					i++;
				} catch (Exception e) {
					System.out.println("something went wrong:" + e.getMessage());
				}
			}
			
			return records;
		} catch (FileNotFoundException e) {
			System.out.println("File not found " + filepath);
		}
		
		return null;
	}

}

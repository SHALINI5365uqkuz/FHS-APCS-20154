package dataGen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;


public class DataGenerator {
	private static final int TOTAL_EMPLOYEES = 300;

	public static void main(String[] args) {
		ArrayList<Employee> employees = new ArrayList<Employee>();
		
		for (int i = 0; i < TOTAL_EMPLOYEES; i++) {
			employees.add(Employee.getRandomEmployee());
		}
		
		ArrayList<Entry> entries = new ArrayList<Entry>();
		for (Employee e : employees) {
			for (Integer time : e.getSwipeTimes()) {
				 entries.add(new Entry(e.getId(), time));
			}
		}
		
		Collections.sort(entries);
		
		String dataString = entries.size() + "\n" + "id, time" + "\n";
		for (Entry e:entries) {
			dataString += e.toString();
		}
		
		writeStringToFile("swipeData.csv", dataString);
	}
	
	private static void writeStringToFile(String filePath, String str) {
		BufferedWriter writer = null;
		try {
			// create a temporary file
			File logFile = new File(filePath);
			writer = new BufferedWriter(new FileWriter(logFile));
			writer.write(str);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
			}
		}
	}
}

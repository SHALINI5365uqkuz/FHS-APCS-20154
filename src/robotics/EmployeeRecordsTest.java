package robotics;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.BeforeClass;
import org.junit.Test;

public class EmployeeRecordsTest {
	public static EmployeeRecords records;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		records = new EmployeeRecords(3);
		records.loadDataFromFile("sampleSwipeData.csv");
		records.displayAllEmployees();
	}

	@Test
	public void testTimeIn1() {
		assertEquals(records.getTimeInFor(0), 22750);
	}
	
	@Test
	public void testTimeIn2() {
		assertEquals(records.getTimeInFor(1), 21600);
	}
	
	@Test
	public void testTimeIn3() {
		assertEquals(records.getTimeInFor(2), 25000);
	}
	
	@Test
	public void testTimeInBuilding1() {
		assertEquals(records.getTimeInBuildingFor(0), 9250);
	}
	
	@Test
	public void testTimeInBuilding2() {
		assertEquals(records.getTimeInBuildingFor(1), 5400);
	}
	
	@Test
	public void testTimeInBuilding3() {
		assertEquals(records.getTimeInBuildingFor(2), 16000);
	}
	
	@Test
	public void testGetEmployeeWithMostTime() {
		assertEquals(records.getEmployeeWithMostTimeIn().getId(), 2);
	}
	
	@Test
	public void testGetEmployeeWithLeastTime() {
		assertEquals(records.getEmployeeWithLeastTimeIn().getId(), 1);
	}
	
	@Test
	public void testGetEmployeesInBuildingAtTime() {
		ArrayList<Employee> employees = records.getEmployeesInBuildingAt(10);
		
		assertEquals(employees.size(), 0);
	}
	
	@Test
	public void testGetEmployeesInBuildingAtTime2() {
		ArrayList<Employee> employees = records.getEmployeesInBuildingAt(50000);
		
		assertEquals(employees.size(), 0);
	}
	
	@Test
	public void testGetEmployeesInBuildingAtTime3() {
		ArrayList<Employee> employees = records.getEmployeesInBuildingAt(21605);
		boolean rightSize = employees.size() == 1;
		boolean rightPerson = employees.get(0).getId() == 1;
		assertTrue(rightSize && rightPerson);
	}
	
	@Test
	public void testGetEmployeesInBuildingAtTime4() {
		ArrayList<Employee> employees = records.getEmployeesInBuildingAt(22900);
		boolean rightSize = employees.size() == 2;
		
		List<Integer> ids = employees.stream().map(p -> p.getId()).collect(Collectors.toList());
		assertTrue(rightSize && ids.contains(0) && ids.contains(1));
	}
	
	@Test
	public void testGetEmployeesInBuildingAtTime5() {
		ArrayList<Employee> employees = records.getEmployeesInBuildingAt(25200);
		boolean rightSize = employees.size() == 3;
		
		List<Integer> ids = employees.stream().map(p -> p.getId()).collect(Collectors.toList());
		assertTrue(rightSize && ids.contains(0) && ids.contains(1) && ids.contains(2));
	}
	
	@Test
	public void testGetEmployeesInBuildingAtTime6() {
		ArrayList<Employee> employees = records.getEmployeesInBuildingAt(26000);
		boolean rightSize = employees.size() == 2;
		
		List<Integer> ids = employees.stream().map(p -> p.getId()).collect(Collectors.toList());
		assertTrue(rightSize && ids.contains(1) && ids.contains(2));
	}
}

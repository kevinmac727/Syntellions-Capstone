package testing;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.Test;

import domain.TimeSlots;
import services.TimeSlotServices;

public class TimeSlotServicesTest extends DatabaseTestMethods{
	
	@Test
	public void getAllTest() {
		
		TimeSlotServices tss = new TimeSlotServices(con);
		ArrayList<TimeSlots> timeSlots = tss.getAll();
		
		ArrayList<TimeSlots> timeSlotsTest = new ArrayList<TimeSlots>();
		
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM TIME_SLOTS");
			
			while(rs.next()){
				timeSlotsTest.add(new TimeSlots(
						rs.getString("TIME_SLOT_ID"), 
						rs.getInt("SLOT_START"),
						rs.getInt("SLOT_END"),
						rs.getString("SLOT_NAME")));
			}
			System.out.println(timeSlots);
			System.out.println(timeSlotsTest);
			
			assertEquals(timeSlots, timeSlotsTest);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}


}

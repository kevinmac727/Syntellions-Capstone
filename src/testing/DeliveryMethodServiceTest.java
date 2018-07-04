package testing;

import static org.junit.Assert.*;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import org.junit.Test;

import services.DeliveryMethod;
import services.DeliveryMethodService;
import services.DeliveryStatus;
import services.DeliveryStatusService;

public class DeliveryMethodServiceTest extends DatabaseTestMethods{

	
	@Test
	public void addTest() {
		
		DeliveryMethod dm = new DeliveryMethod("20", "Test");
		DeliveryMethodService dms = new DeliveryMethodService(con);
		
		dms.add(dm);
		
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM DELIVERY_METHODS WHERE DELIVER_METHOD_ID = '5'");
			rs.next();
						
			assertEquals("20",rs.getString("DELIVERY_METHOD_ID"));
			assertEquals("Test",rs.getString("DELIVERY_METHOD"));
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void updateTest() {
		DeliveryMethod dm = new DeliveryMethod("1", "Test");
		DeliveryMethodService dms = new DeliveryMethodService(con);
		dms.update(dm);
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM DELIVERY_STATUSES WHERE DELIVER_STATUS_ID = '1'");
			rs.next();
			
			assertEquals("1",rs.getString("DELIVERY_STATUS_ID"));
			assertEquals("Test",rs.getString("DELIVERY_STATUS"));
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void deleteTest() {
		
		DeliveryStatus ds = new DeliveryStatus("5", "Trashed");
		DeliveryStatusService dss = new DeliveryStatusService(con);
		
		dss.add(ds);
		

		dss.deleteByID("5");
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM DELIVERY_STATUSES WHERE DELIVER_STATUS_ID = '5'");
			
			assertEquals(rs.next(), false);
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void getByIDTest(){
		DeliveryStatusService dss = new DeliveryStatusService(con);
		DeliveryStatus ds = dss.getByID("2");
		
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM DELIVERY_STATUSES WHERE DELIVER_STATUS_ID = '2'");
			
			DeliveryStatus dstest = new DeliveryStatus(rs.getString(1), rs.getString(2));
			
			assertEquals(ds, dstest);
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
	
	@Test
	public void getAllTest() {
		
		DeliveryStatusService dss = new DeliveryStatusService(con);
		ArrayList<DeliveryStatus> deliveryStatuses = dss.getAll();
		
		ArrayList<DeliveryStatus> deliveryStatusesTest = new ArrayList<DeliveryStatus>();
		
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM DELIVERY_STATUSES");
			
			while(rs.next()){
				deliveryStatusesTest.add(new DeliveryStatus(
						rs.getString("DELIVERY_STATUS_ID"), 
						rs.getString("DELIVERY_STATUS")));
			}
			System.out.println(deliveryStatuses);
			System.out.println(deliveryStatusesTest);
			
			assertEquals(deliveryStatuses, deliveryStatusesTest);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	
}

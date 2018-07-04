package testing;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.Test;

import services.DeliveryStatus;
import services.DeliveryStatusService;

public class DeliveryStatusServiceTest extends DatabaseTestMethods{

	
	@Test
	public void addTest() {
		
		DeliveryStatus ds = new DeliveryStatus("5", "Trashed");
		DeliveryStatusService dss = new DeliveryStatusService(con);
		
		dss.add(ds);
		
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM DELIVERY_STATUSES WHERE DELIVER_STATUS_ID = '5'");
			rs.next();
			
			assertEquals("5",rs.getString("DELIVERY_STATUS_ID"));
			assertEquals("Trashed",rs.getString("DELIVERY_STATUS"));
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void updateTest() {
		
		DeliveryStatus ds = new DeliveryStatus("5", "Trashed");
		DeliveryStatusService dss = new DeliveryStatusService(con);
		
		dss.add(ds);
		
		ds.setDelivery_status_id("7");
		ds.setDelivery_status("Money");
		dss.update(ds);
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM DELIVERY_STATUSES WHERE DELIVER_STATUS_ID = '7'");
			rs.next();
			
			assertEquals("7",rs.getString("DELIVERY_STATUS_ID"));
			assertEquals("Money",rs.getString("DELIVERY_STATUS"));
			
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

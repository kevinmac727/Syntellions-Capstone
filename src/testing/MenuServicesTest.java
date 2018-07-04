package testing;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import domain.Menu;
import services.DeliveryStatus;
import services.DeliveryStatusService;
import services.MenuServices;

public class MenuServicesTest extends DatabaseTestMethods{

	@Test
	public void getByIDTest(){
		MenuServices ms = new MenuServices(con);
		Menu m = ms.getById("3");
		
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM ITEMS WHERE ITEM_ID = '3'");
			
			rs.next();
			Menu mtest = new Menu(rs.getString(1), 
					rs.getString(2),
					rs.getString(3).charAt(0),
					rs.getString(4),
					rs.getString(5),
					rs.getString(6),
					rs.getString(7),
					rs.getFloat(8));
			
			System.out.println(m);
			System.out.println(mtest);
			
			assertEquals(m, mtest);
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		
	}

}

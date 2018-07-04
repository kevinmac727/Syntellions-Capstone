package testing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class DatabaseTestMethods {
	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_CONNECTION = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String DB_USER = "pass";
	private static final String DB_PASSWORD = "pass";
	
	static Connection con;

	@BeforeClass
	public static void beforeClass(){
		con = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		try {
			con = DriverManager.getConnection(
				DB_CONNECTION, DB_USER,DB_PASSWORD);
			
			con.setAutoCommit(false);
			
			return;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@AfterClass
	public static void afterClass(){
		try {
			con.setAutoCommit(true);
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Before
	public void beforeTests(){
		try {
			con.rollback();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@After
	public void afterTests(){
		try {
			con.rollback();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}

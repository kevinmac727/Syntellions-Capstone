package services;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import domain.Special;



public class SpecialServices implements Service<Special> {
	Connection con;
	
	public SpecialServices(Connection con) {
		super();
		this.con = con;
	}

	@Override
	public boolean add(Special spec){
		CallableStatement oracleCallStmt;
		try {
			oracleCallStmt = con.prepareCall("{call insertSpecial(?,?)}");
			oracleCallStmt.setString(1, spec.getItem_ID());
			oracleCallStmt.setInt(2, spec.getDiscoutPercentage());
			oracleCallStmt.execute();
			System.out.println("Successful?");
			con.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public void deleteById(String id){
		CallableStatement stmt;
		try {
			stmt = con.prepareCall("{call deleteSpecial(?)}");
			stmt.setString(1, id);
			System.out.println("Deleted?");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public Special getById(String id){
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM specials WHERE item_id = " + id);
			rs.next();
			
			Special spec = new Special(rs.getString("item_id"), rs.getInt("discount_percentatge"));
			return spec;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public ArrayList<Special> getAll(){
		ArrayList<Special> specArr = new ArrayList<Special>();
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM items");
			while(rs.next()){
				Special spec = new Special(rs.getString("item_id"), rs.getInt("discount_percentage"));
				specArr.add(spec);
			}
			return specArr;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void update(Special spec){
		
	}


}

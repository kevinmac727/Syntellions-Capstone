package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.Menu;
import domain.TimeSlots;

public class MenuServices implements Service<Menu> {
	Connection con;
	TimeSlotServices timServ;
	
	public MenuServices(Connection con) {
		super();
		this.con = con;
		timServ = new TimeSlotServices(con);
	}

	@Override
	public Menu getById(String id){		
		try {
			
			PreparedStatement preparedStatement = con.prepareStatement(
					"SELECT * FROM items WHERE item_id = ?");
			preparedStatement.setString(1, id);
			ResultSet rs = preparedStatement.executeQuery( );
			
			
			rs.next();
			//float price = rs.getFloat("price");
			//String time = getTimeName(times, rs.getString("time_slot_id"));
			Menu men = new Menu(rs.getString("item_id"), 
					rs.getString("name"), 
					rs.getString("vegetarian").charAt(0), 
					rs.getString("item_type_id"), 
					rs.getString("description"), 
					rs.getString("time_slot_id"), 
					rs.getString("photo"), 
					rs.getFloat("price"));
			
			return men;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

	@Override
	public ArrayList<Menu> getAll(){
		ArrayList<Menu> menArr = new ArrayList<Menu>();
		ArrayList<TimeSlots> times = timServ.getAll();
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM items");
			while(rs.next()){
				float price = rs.getFloat("price");
				String tid = rs.getString("time_slot_id");
				String tName = getTimeName(times, tid);
				Menu men = new Menu(rs.getString("item_id"), rs.getString("name"), rs.getString("vegetarian").charAt(0), 
						rs.getString("item_type_id"), rs.getString("description"), tName, 
						rs.getString("photo"), price);
				menArr.add(men);
			}
			return menArr;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	public boolean add(Menu men){		
		try{
								
			//con.setAutoCommit(false);
			PreparedStatement preStmt = con.prepareStatement("insert into items values(?,?,?,?,?,?,?,?)");
			preStmt.setString(1, men.getId());
			preStmt.setString(2, men.getName());
			preStmt.setString(3, ("" + men.getVegetarian()));
			preStmt.setString(4, men.getType());
			preStmt.setString(5, men.getDescription());
			preStmt.setString(6, men.getSlot_ID());
			preStmt.setString(7, men.getPhoto());
			preStmt.setFloat(8, men.getPrice());
			preStmt.executeUpdate(); //Data is not yet committed
			System.out.println("Inserted");
			return true;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void deleteById(String id){
		try {
			con.createStatement().executeQuery("DELETE FROM items WHERE item_id = " + id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Menu men){
		ArrayList<TimeSlots> times = timServ.getAll();
		String timeId = getTimeID(times, men.getSlot_ID());
		try {
			PreparedStatement preStmt = con.prepareStatement("UPDATE items SET name=?, vegetarian=?, item_type_id=?, description=?, time_slot_id=?, photo=?, price=? WHERE item_id=?");
			preStmt.setString(1, men.getName());
			preStmt.setString(2, ("" + men.getVegetarian()));
			preStmt.setString(3, men.getType());
			preStmt.setString(4, men.getDescription());
			preStmt.setString(5, timeId);
			preStmt.setString(6, men.getPhoto());
			preStmt.setFloat(7, men.getPrice());
			preStmt.setString(8, men.getId());
			preStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Menu> getByType(String type){
		ArrayList<Menu> menArr = new ArrayList<Menu>();
		ArrayList<TimeSlots> times = timServ.getAll();
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM items WHERE items_type_id = " + type);
			while(rs.next()){
				float price = rs.getFloat("price");
				String tid = rs.getString("time_slot_id");
				String tName = getTimeName(times, tid);
				Menu men = new Menu(rs.getString("item_id"), rs.getString("name"), rs.getString("vegetarian").charAt(0), 
						rs.getString("item_type_id"), rs.getString("description"), tName, 
						rs.getString("photo"), price);
				menArr.add(men);
			}
			return menArr;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	private String getTimeName(ArrayList<TimeSlots> timeArr, String id){
		String tName="";
		for(TimeSlots tim:timeArr){
			if(tim.getSlot_ID().equals(id))
				tName = tim.getTimeName();
		}
		return tName;
	}
	
	private String getTimeID(ArrayList<TimeSlots> timeArr, String name){
		String id="";
		for(TimeSlots tim:timeArr){
			if(tim.getTimeName().equals(name))
				id = tim.getSlot_ID();
		}
		return id;
	}

}
package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DeliveryStatusService {
	
	Connection connection;
	
	public DeliveryStatusService() {
		super();
	}
	public DeliveryStatusService(Connection connection) {
		super();
		this.connection = connection;
	}
	public void add(DeliveryStatus deliveryStatus){
		try{
			CallableStatement statement = connection.prepareCall("{call AddDeliveryStatus(?, ?)}");
			statement.setString(1, deliveryStatus.getDelivery_status_id());
			statement.setString(2, deliveryStatus.getDelivery_status());
			statement.execute();
			statement.close();
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}	
	}
	
	public void update(DeliveryStatus deliveryStatus){
		String statement = "UPDATE DELIVERY_STATUSES SET DELIVERY_STATUS = ?"
				+ "WHERE DELIVERY_STATUS_ID = ?";
		
		try{
			PreparedStatement preparedStatement = connection.prepareStatement(statement);
			
			preparedStatement.setString(1, deliveryStatus.getDelivery_status());
			preparedStatement.setString(2, deliveryStatus.getDelivery_status_id());
			preparedStatement.executeUpdate();
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void deleteByID(String id){
		try{
			
			CallableStatement statement = connection.prepareCall("{call DeleteDeliveryStatus(?)}");
			statement.setString(1, id);
			statement.execute();
			statement.close();
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
	public ArrayList<DeliveryStatus> getAll(){

		ArrayList<DeliveryStatus> deliveryStatuses = new ArrayList<DeliveryStatus>();
		
		try{
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM DELIVERY_STATUSES");
			
			while(rs.next()){
				DeliveryStatus deliveryStatus = new DeliveryStatus(rs.getString(1),rs.getString(2)); 
				deliveryStatuses.add(deliveryStatus);
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return deliveryStatuses;
	}
	public DeliveryStatus getByID(String id){
		DeliveryStatus deliveryStatus = null;
		
		try{
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"SELECT * FROM DELIVERY_METHODS WHERE DELIVERY_METHOD_ID = " + id);
			
			resultSet.next();
			deliveryStatus = new DeliveryStatus(
					resultSet.getString(1),
					resultSet.getString(2)
					); 
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}	
		
		return deliveryStatus;
	}
	
	
}

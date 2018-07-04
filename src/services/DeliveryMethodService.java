package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

public class DeliveryMethodService implements Service<DeliveryMethod>{
	
	Connection connection;
	
	public DeliveryMethodService() {
		super();
	}
	public DeliveryMethodService(Connection connection) {
		super();
		this.connection = connection;
	}
	
	@Override
	public boolean add(DeliveryMethod deliveryMethod){
		try{
			CallableStatement statement = connection.prepareCall("{call AddDeliveryMethod(?, ?)}");
			statement.setString(1, deliveryMethod.getDelivery_method_id());
			statement.setString(2, deliveryMethod.getDelivery_method());
			statement.execute();
			statement.close();
			return true;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return false;
		}	
	}
	
	@Override
	public void update(DeliveryMethod deliveryMethod){
		String statement = "UPDATE DELIVERY_METHODS SET DELIVERY_METHOD = ?"
				+ "WHERE DELIVERY_METHOD_ID = ?";
		
		try{
			PreparedStatement preparedStatement = connection.prepareStatement(statement);
			
			preparedStatement.setString(1, deliveryMethod.getDelivery_method());
			preparedStatement.setString(2, deliveryMethod.getDelivery_method_id());
			preparedStatement.executeUpdate();
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public ArrayList<DeliveryMethod> getAll(){

		ArrayList<DeliveryMethod> deliverMethods = new ArrayList<DeliveryMethod>();
		
		try{
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM DELIVERY_METHODS");
			
			while(rs.next()){
				DeliveryMethod deliveryMethod = new DeliveryMethod(rs.getString(1),rs.getString(2)); 
				deliverMethods.add(deliveryMethod);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return deliverMethods;
	}
	
	@Override
	public DeliveryMethod getById(String id){
		DeliveryMethod deliveryMethod = null;
		
		try{
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"SELECT * FROM DELIVERY_METHODS WHERE DELIVERY_METHOD_ID = " + id);
			
			resultSet.next();
			deliveryMethod = new DeliveryMethod(
					resultSet.getString(1),
					resultSet.getString(2)
					); 
		}catch(Exception e){
			System.out.println(e.getMessage());
		}	
		
		return deliveryMethod;
	}
	@Override
	public void deleteById(String id) {
		try{
			
			CallableStatement statement = connection.prepareCall("{call DeleteDeliveryMethod(?)}");
			statement.setString(1, id);
			statement.execute();
			statement.close();
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
}

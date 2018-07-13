package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeliveryStatusService {
	
	Connection connection;
	
	public DeliveryStatusService() {
		super();
	}
        //Constructor that takes a connection to the database.
	public DeliveryStatusService(Connection connection) {
		super();
		this.connection = connection;
	}
        //Method adds new deliveryStatuses to the database.
        //Takes a deliverStatus object as a parameter.
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
	//Update delivery status.
        //Updates existing delviery status based on the delivery_status_id.
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
	//Deletes existing delivery statuses by using the ID.
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
        //Gets all of the delivery statuses in the database and stores them into an ArrayList.
	public ArrayList<DeliveryStatus> getAll(){

		ArrayList<DeliveryStatus> deliveryStatuses = new ArrayList<DeliveryStatus>();
		
		try{
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM DELIVERY_STATUSES ORDER BY delivery_status_id");
			
			while(rs.next()){
				DeliveryStatus deliveryStatus = new DeliveryStatus(rs.getString(1),rs.getString(2)); 
				deliveryStatuses.add(deliveryStatus);
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return deliveryStatuses;
	}
        //Gets the delivery status in the database by the string provided and 
        //returns a DeliveryStatus object to the user.
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
	
    
    /***************************************************
     * @author: Presley M. 
     * @return 
     * This function validates the delivery date.
     * -This restaurant will operate seven days a week
     */
    public boolean validateDeliveryDateTime(String date){
        
            try {
                SimpleDateFormat dateFmt = new SimpleDateFormat("MM-dd-yyyy HH:mm");
                
                Date userDate =  dateFmt.parse(date);
                Date sysDate = new Date();
                
                if(sysDate.after(userDate) == true){
                    
                    System.out.println("INVALID DATE: You selected a previous date/time. Try again!");
                    return false; 
                }//if Ends 
                
              //  System.out.print();
            } //validateDeliveryDate() Ends
            catch (ParseException ex) {
                   System.out.println("INVALID DATE: You used an incorrect date format!");

                return false; 
            }//try-catch Ends 
            
            return true;
    }
     
}

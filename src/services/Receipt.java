

package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Receipt 
{
        String finalReceipt;
        Connection connection;
       
    
        public Receipt(Connection connection) 
        {
		this.connection = connection;
	}
        
        public String createReceipt(String userID)
        {  
            try 
            {
                PreparedStatement recPrepStmt = connection.prepareStatement("SELECT"
                        + " USER_ID, FIRST, LAST FROM USERS WHERE USER_ID=?");
                recPrepStmt.setString(1, userID);
                recPrepStmt.execute();
                
                ResultSet recRS = recPrepStmt.getResultSet();
                
                while(recRS.next())
                {
                    finalReceipt = "User ID:  " + recRS.getString(1) + 
                            "\nFirst Name: " + recRS.getString(2) +
                            "\nLast Name: " + recRS.getString(3); 
                }
                
            } 
            catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }
            return finalReceipt;
        }
            
        
    
}

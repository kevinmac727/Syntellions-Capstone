

package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Receipt 
{
        String finalReceipt;
        Connection connection;
        ResultSet recRS;
    
        public Receipt(Connection connection) 
        {
		this.connection = connection;
	}
        
        public String createReceipt(String userID)
        {  
            try 
            {
                PreparedStatement recPrepStmt = connection.prepareStatement("SELECT"
                        + " ord.ORDER_ID, usr.USER_ID, usr.FIRST, usr.LAST,"
                        + " loc.STREET, loc.CITY, loc.STATE, loc.COUNTRY, loc.ZIP, ord.TOTAL_PRICE, ord.TIP,"
                        + " ord.PLACED_TIMESTAMP, ord.INSTRUCTIONS, ord.DELIVERY_METHOD_ID, del.DELIVERY_METHOD, str.STORE_ID, str.STORE"
                        + " FROM ORDERS ord, USERS usr, LOCATIONS loc, DELIVERY_METHODS del, STORES str"
                        + " WHERE ord.USER_ID = usr.USER_ID and loc.USER_ID = usr.USER_ID and ord.DELIVERY_METHOD_ID = del.DELIVERY_METHOD_ID and"
                        + " loc.LOCATION_ID = str.LOCATION_ID and usr.USER_ID=?");
                recPrepStmt.setString(1, userID);
                recPrepStmt.execute();
                while(recRS.next())
                {
                    finalReceipt = "Order ID: " + recRS.getString(1) + 
                            "\nUser ID:  " + recRS.getString(2) + 
                            "\nFirst Name: " + recRS.getString(3) +
                            "\nLast Name: " + recRS.getString(4) + 
                            "\nStreet: " + recRS.getString(5) + 
                            "\nCity: " + recRS.getString(6) +
                            "\nState: " + recRS.getString(7) +
                            "\nCountry: " + recRS.getString(8) +
                            "\nZipcode: " + recRS.getString(9) +
                            "\nTotal Price: " + recRS.getString(10) +
                            "\nTip: " + recRS.getString(11) +
                            "\nOrder Placed: " + recRS.getString(12) +
                            "\nDelivery Instructions: " + recRS.getString(13) +
                            "\nDelivery ID: " + recRS.getString(14) +
                            "\nDelivery Method: " + recRS.getString(15) +
                            "\nStore ID: " + recRS.getString(16) +
                            "\nStore: " + recRS.getString(17);
                }
            } 
            catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }
            return finalReceipt = "123";
        }
            
        
    
}

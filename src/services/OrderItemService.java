/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class OrderItemService implements Service<OrderItem>{
    Connection con;

    public OrderItemService(Connection con) {
        this.con = con;
    }
    
    @Override
    public void deleteById(String id) {
        CallableStatement stmt;
        try{
            stmt = con.prepareCall("{call DeleteOrderItems(?)}");
            stmt.setString(1, id);
            stmt.execute();
            stmt.close();
            System.out.println("Deleted order items where order id = " + id);
        } catch (SQLException ex) {
            Logger.getLogger(OrderItemService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteItemById(String id){


        
        try (PreparedStatement pStmt = con.prepareStatement("DELETE FROM order_items WHERE item_id = ?")) {
            pStmt.setString(1, id);
            pStmt.execute();
            System.out.println("Item ID: " + id + " has been deleted");
        }
        catch (SQLException ex) {
            Logger.getLogger(OrderItemService.class.getName()).log(Level.SEVERE, null, ex);
        }

            

                
    }

    @Override
    public boolean add(OrderItem orderItem) {
        try(CallableStatement stmt = con.prepareCall("{call AddOrderItem(?,?)}")){
            stmt.setString(1, orderItem.getOrderID());
            stmt.setString(2, orderItem.getItemID());
            stmt.execute();
            System.out.println("Item inserted into the order");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OrderItemService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    @Override
    public void update(OrderItem orderItem) {
        
        String statement = "UPDATE order_items SET item_id = ? WHERE order_id = ?";
        
        try (PreparedStatement pStatement = con.prepareStatement(statement)) {
            pStatement.setString(1, orderItem.getItemID());
            pStatement.setString(2, orderItem.getOrderID());
            pStatement.execute();
        }
        catch (SQLException ex) {
            Logger.getLogger(OrderItemService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public ArrayList<OrderItem> getItemsByOrderId(String id) {
        try {
            String statement = "SELECT * FROM order_items WHERE order_id = " + id + " ORDER BY order_id";
            Statement getOrderItemById = con.createStatement();
            ResultSet rs = getOrderItemById.executeQuery(statement);
            ArrayList<OrderItem> orderItems= new ArrayList<>();
            while(rs.next()){
                orderItems.add(new OrderItem(rs.getString(1), rs.getString(2)));
            }
            return orderItems;
        } catch (SQLException ex) {
            Logger.getLogger(OrderItemService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public ArrayList<OrderItem> getAll() {
        ArrayList<OrderItem> allOrderItems = new ArrayList<OrderItem>();
        try{
            Statement orderItemStatement = con.createStatement();
            ResultSet orderItemsRS = orderItemStatement.executeQuery("Select * from order_items");
            while(orderItemsRS.next()){
                OrderItem orderItem = new OrderItem(
                                            orderItemsRS.getString(1),
                                            orderItemsRS.getString(2));
                allOrderItems.add(orderItem);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderItemService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allOrderItems;
    }

    @Override
    public OrderItem getById(String id) {
        throw new UnsupportedOperationException
        ("This method is not support in OrderItemService as an arraylist of items must be returned"); 
//To change body of generated methods, choose Tools | Templates.
    }
    
}

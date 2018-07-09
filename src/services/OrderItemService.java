/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OrderItem getById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<OrderItem> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

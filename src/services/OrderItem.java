/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

public class OrderItem {
    private String orderID;
    private String itemID;

    public OrderItem(String orderID, String itemID) {
        this.orderID = orderID;
        this.itemID = itemID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    @Override
    public String toString() {
        return "OrderItem{" + "orderID=" + orderID + ", itemID=" + itemID + '}';
    }
    
            
}

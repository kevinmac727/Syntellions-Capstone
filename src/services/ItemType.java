/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;


public class ItemType {
    private String itemTypeId;
    private String itemType;

    public ItemType(String itemTypeId, String itemType) {
        this.itemTypeId = itemTypeId;
        this.itemType = itemType;
    }

    public String getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(String itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        return "ItemType{" + "itemTypeId=" + itemTypeId + ", itemType=" + itemType + '}';
    }
    
    
    
    
}

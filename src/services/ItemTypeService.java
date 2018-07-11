
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ItemTypeService implements Service<ItemType>{
    Connection connection;

    public ItemTypeService(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public void deleteById(String id) {
        try{
            try (PreparedStatement pStatement = connection.prepareStatement
        ("DELETE FROM item_types WHERE item_type_id = ?")) {
                pStatement.setString(1, id);
                pStatement.execute();
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean add(ItemType itemType) {
        String statement = "INSERT INTO ITEM_TYPES (ITEM_TYPE_ID, ITEM_TYPE) "
                           + "VALUES(?,?)";
 
        try (PreparedStatement pStatement = connection.prepareStatement(statement)) {
            pStatement.setString(1, itemType.getItemTypeId());
            pStatement.setString(2, itemType.getItemType());
            pStatement.executeUpdate();
            return true;
        }
        catch (SQLException ex) {
            Logger.getLogger(ItemTypeService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }

    @Override
    public void update(ItemType itemType) {
        String statement = "UPDATE ITEM_TYPES SET ITEM_TYPE = ? WHERE ITEM_TYPE_ID = ?";
        
        try (PreparedStatement pStatement = connection.prepareStatement(statement)) {
            pStatement.setString(1, itemType.getItemType());
            pStatement.setString(2, itemType.getItemTypeId());
            pStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ItemTypeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ItemType getById(String id) {
        ItemType itemType = null;
        try{
            Statement statement = connection.createStatement();
            try (ResultSet resultSet = statement.executeQuery
        ("SELECT * FROM ITEM_TYPES WHERE item_type_id = " + id)) {
                resultSet.next();
                itemType = new ItemType(resultSet.getString(1), resultSet.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemTypeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemType;
    }

    @Override
    public ArrayList<ItemType> getAll() {
        ArrayList<ItemType> itemTypes = new ArrayList<>();
        
        try (Statement statement = connection.createStatement(); 
             ResultSet rs = statement.executeQuery("SELECT * FROM ITEM_TYPES ORDER BY item_type_id")) {
            ItemType itemType;
            while(rs.next()){
                itemType = new ItemType(rs.getString(1), rs.getString(2));
                itemTypes.add(itemType);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return itemTypes;
    }
    
}

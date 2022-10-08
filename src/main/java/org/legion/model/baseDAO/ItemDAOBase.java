package org.legion.model.baseDAO;

import org.legion.model.entity.Item;
import java.sql.Connection;
import java.sql.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.math.*;
import org.legion.util.*;
import java.util.List;
import java.io.Serializable;
public class ItemDAOBase implements Serializable{
private static final Logger logger = LoggerFactory.getLogger(ItemDAOBase.class);
 //-----GET RECORD METHOD----------- 
 public Item getRecord(String rowID) throws Exception {
Item entity_record  = null;
        Connection con =null;
          PreparedStatement stmt = null;
          ResultSet rs = null;
          try { con = org.legion.util.MainDataSource.getConnection();
        String query = "select * from item where row_id = '" + rowID + "'";
        stmt = con.prepareStatement(query);
        rs = stmt.executeQuery();
        while (rs.next()) {
entity_record = new Item();
entity_record.setRowId(rs.getString("row_id")); 
entity_record.setCreatedAt(rs.getLong("created_at"));
entity_record.setCreatedBy(rs.getString("created_by")); 
entity_record.setUpdatedAt(rs.getLong("updated_at"));
entity_record.setUpdateBy(rs.getString("update_by")); 
entity_record.setDeletedAt(rs.getLong("deleted_at"));
entity_record.setDeletedBy(rs.getString("deleted_by")); 
entity_record.setCategoryId(rs.getString("category_id")); 
entity_record.setBarcode(rs.getString("barcode")); 
entity_record.setDescription(rs.getString("description")); 
entity_record.setImage(rs.getString("image")); 
entity_record.setCost(rs.getBigDecimal("cost"));
entity_record.setSellPrice(rs.getBigDecimal("sell_price"));
entity_record.setBlend(rs.getBoolean("blend"));
entity_record.setBlendId(rs.getString("blend_id")); 
entity_record.setOnHand(rs.getBigDecimal("on_hand"));
entity_record.setLastSold(rs.getLong("last_sold"));
entity_record.setActive(rs.getBoolean("active"));
        }
       }catch(Exception e){
        logger.error("Error", e); throw e;       }
       finally{
       try{if(rs != null )rs.close();
     if(stmt != null )stmt.close();
     if(con != null ) con.close();}
catch(SQLException sqlex){logger.error("SQL Error", sqlex); throw sqlex;}       }
            
            return entity_record;
}
//-----------------INSERT METHOD ---------------
  public void saveRecord(Item record)  { 
 if(record.getRowId() != null && !record.getRowId().isEmpty()){updateRecord(record);return;}record.setRowId(new Utilities().generateRowID());
Connection con = null;
  PreparedStatement ps = null;
 try{ con = org.legion.util.MainDataSource.getConnection(); 
 ps = con.prepareStatement("INSERT INTO item VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
ps.setString(1,record.getRowId()); 
ps.setLong(2,record.getCreatedAt()); 
ps.setString(3,record.getCreatedBy()); 
ps.setLong(4,record.getUpdatedAt()); 
ps.setString(5,record.getUpdateBy()); 
ps.setLong(6,record.getDeletedAt()); 
ps.setString(7,record.getDeletedBy()); 
ps.setString(8,record.getCategoryId()); 
ps.setString(9,record.getBarcode()); 
ps.setString(10,record.getDescription()); 
ps.setString(11,record.getImage()); 
ps.setBigDecimal(12,record.getCost()); 
ps.setBigDecimal(13,record.getSellPrice()); 
ps.setBoolean(14,record.getBlend()); 
ps.setString(15,record.getBlendId()); 
ps.setBigDecimal(16,record.getOnHand()); 
ps.setLong(17,record.getLastSold()); 
ps.setBoolean(18,record.getActive()); 

 int i = ps.executeUpdate();} catch(Exception e){logger.error("Error", e);}
        finally{ 
               try {                       if(ps != null ) ps.close();
                       if(con != null ) con.close();
                   }catch(SQLException sqlex){logger.error("SQL Error", sqlex);}
       }
         
}
//--------- DELETE METHOD--------
 public int deleteRecord(String record_ID)  {
   Connection con = null;   Statement stmt = null;int i=0;   try{ con = org.legion.util.MainDataSource.getConnection();
            stmt = con.createStatement();

            i = stmt.executeUpdate("DELETE FROM item WHERE row_id='" + record_ID+"'");
            }catch(Exception e){logger.error("Error", e);}   finally{
   try { if(stmt != null )stmt.close();
   if(con != null ) con.close(); } catch(SQLException sqlex){logger.error("SQL Error", sqlex);}
}            
    return i;    }
//--------- UPDATE METHOD--------
  public void updateRecord(Item record) { Connection con = null;
 PreparedStatement ps = null;
 try { con = org.legion.util.MainDataSource.getConnection();
 ps = con.prepareStatement("UPDATE item SET row_id = ?, created_at = ?, created_by = ?, updated_at = ?, update_by = ?, deleted_at = ?, deleted_by = ?, category_id = ?, barcode = ?, description = ?, image = ?, cost = ?, sell_price = ?, blend = ?, blend_id = ?, on_hand = ?, last_sold = ?, active = ? WHERE row_id=? ");
ps.setString(1,record.getRowId()); 
ps.setLong(2,record.getCreatedAt()); 
ps.setString(3,record.getCreatedBy()); 
ps.setLong(4,record.getUpdatedAt()); 
ps.setString(5,record.getUpdateBy()); 
ps.setLong(6,record.getDeletedAt()); 
ps.setString(7,record.getDeletedBy()); 
ps.setString(8,record.getCategoryId()); 
ps.setString(9,record.getBarcode()); 
ps.setString(10,record.getDescription()); 
ps.setString(11,record.getImage()); 
ps.setBigDecimal(12,record.getCost()); 
ps.setBigDecimal(13,record.getSellPrice()); 
ps.setBoolean(14,record.getBlend()); 
ps.setString(15,record.getBlendId()); 
ps.setBigDecimal(16,record.getOnHand()); 
ps.setLong(17,record.getLastSold()); 
ps.setBoolean(18,record.getActive()); 
ps.setString(19,record.getRowId()); 
   int i = ps.executeUpdate();
 } catch(Exception e){
   logger.error("Error", e);}
   finally{
       try{
           if(ps != null ) ps.close();
           if(con != null ) con.close();
       }
       catch(SQLException sqlex){
           logger.error("SQL Error", sqlex);
       }
}            
}
//--------- LIST METHOD ALL RECORDS--------
public List<Item> getList() {
Connection con = null;
PreparedStatement stmt = null;
ResultSet rs = null;
List<Item> listOfRecords = new ArrayList<>();
         try {con = org.legion.util.MainDataSource.getConnection();
        String query = "select * from item where deleted_by is null";
        logger.info(query);
         stmt = con.prepareStatement(query);
         rs = stmt.executeQuery();
        while (rs.next()) {
Item entity_record = new Item();
entity_record.setRowId(rs.getString("row_id")); 
entity_record.setCreatedAt(rs.getLong("created_at"));
entity_record.setCreatedBy(rs.getString("created_by")); 
entity_record.setUpdatedAt(rs.getLong("updated_at"));
entity_record.setUpdateBy(rs.getString("update_by")); 
entity_record.setDeletedAt(rs.getLong("deleted_at"));
entity_record.setDeletedBy(rs.getString("deleted_by")); 
entity_record.setCategoryId(rs.getString("category_id")); 
entity_record.setBarcode(rs.getString("barcode")); 
entity_record.setDescription(rs.getString("description")); 
entity_record.setImage(rs.getString("image")); 
entity_record.setCost(rs.getBigDecimal("cost"));
entity_record.setSellPrice(rs.getBigDecimal("sell_price"));
entity_record.setBlend(rs.getBoolean("blend"));
entity_record.setBlendId(rs.getString("blend_id")); 
entity_record.setOnHand(rs.getBigDecimal("on_hand"));
entity_record.setLastSold(rs.getLong("last_sold"));
entity_record.setActive(rs.getBoolean("active"));
        listOfRecords.add(entity_record);
 }
   }catch(Exception e){logger.error("Error", e);}
   finally{
 try{       if(rs != null )rs.close();
        if(stmt != null )stmt.close();
        if(con != null ) con.close();
} catch(SQLException sqlex){logger.error("SQL Error", sqlex);}        }
        return listOfRecords;
}
//--------- LIST METHOD WHERE STATEMENT--------
public List<Item> getList(String myQuery) throws Exception {
Connection con = null;
PreparedStatement stmt = null;
ResultSet rs = null;
List<Item> listOfRecords = new ArrayList<>();
        try {  
           con = org.legion.util.MainDataSource.getConnection();
        String query = "select * from item where deleted_by is null and " + myQuery;
        logger.info(query);
        stmt = con.prepareStatement(query);
        rs = stmt.executeQuery();
        while (rs.next()) {
Item entity_record = new Item();
entity_record.setRowId(rs.getString("row_id")); 
entity_record.setCreatedAt(rs.getLong("created_at"));
entity_record.setCreatedBy(rs.getString("created_by")); 
entity_record.setUpdatedAt(rs.getLong("updated_at"));
entity_record.setUpdateBy(rs.getString("update_by")); 
entity_record.setDeletedAt(rs.getLong("deleted_at"));
entity_record.setDeletedBy(rs.getString("deleted_by")); 
entity_record.setCategoryId(rs.getString("category_id")); 
entity_record.setBarcode(rs.getString("barcode")); 
entity_record.setDescription(rs.getString("description")); 
entity_record.setImage(rs.getString("image")); 
entity_record.setCost(rs.getBigDecimal("cost"));
entity_record.setSellPrice(rs.getBigDecimal("sell_price"));
entity_record.setBlend(rs.getBoolean("blend"));
entity_record.setBlendId(rs.getString("blend_id")); 
entity_record.setOnHand(rs.getBigDecimal("on_hand"));
entity_record.setLastSold(rs.getLong("last_sold"));
entity_record.setActive(rs.getBoolean("active"));
        listOfRecords.add(entity_record);
 }
   }catch(Exception e){logger.error("Error", e); throw e;}
   finally{
 try{       if(rs != null )rs.close();
        if(stmt != null )stmt.close();
        if(con != null ) con.close();
} catch(SQLException sqlex){logger.error("SQL Error", sqlex); throw sqlex;}        }
        return listOfRecords;
}


 }
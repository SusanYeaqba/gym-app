package org.legion.model.baseDAO;

import org.legion.model.entity.ShopTrxItem;
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
public class ShopTrxItemDAOBase implements Serializable{
private static final Logger logger = LoggerFactory.getLogger(ShopTrxItemDAOBase.class);
 //-----GET RECORD METHOD----------- 
 public ShopTrxItem getRecord(String rowID) throws Exception {
ShopTrxItem entity_record  = null;
        Connection con =null;
          PreparedStatement stmt = null;
          ResultSet rs = null;
          try { con = org.legion.util.MainDataSource.getConnection();
        String query = "select * from shop_trx_item where row_id = '" + rowID + "'";
        stmt = con.prepareStatement(query);
        rs = stmt.executeQuery();
        while (rs.next()) {
entity_record = new ShopTrxItem();
entity_record.setRowId(rs.getString("row_id")); 
entity_record.setCreatedAt(rs.getLong("created_at"));
entity_record.setCreatedBy(rs.getString("created_by")); 
entity_record.setUpdatedAt(rs.getLong("updated_at"));
entity_record.setUpdateBy(rs.getString("update_by")); 
entity_record.setDeletedAt(rs.getLong("deleted_at"));
entity_record.setDeletedBy(rs.getString("deleted_by")); 
entity_record.setTrxId(rs.getString("trx_id")); 
entity_record.setItemId(rs.getString("item_id")); 
entity_record.setItemDescription(rs.getString("item_description")); 
entity_record.setQty(rs.getBigDecimal("qty"));
entity_record.setUnitPrice(rs.getBigDecimal("unit_price"));
entity_record.setLineDiscountPct(rs.getBigDecimal("line_discount_pct"));
entity_record.setFinalPrice(rs.getBigDecimal("final_price"));
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
  public void saveRecord(ShopTrxItem record)  { 
 if(record.getRowId() != null && !record.getRowId().isEmpty()){updateRecord(record);return;}record.setRowId(new Utilities().generateRowID());
Connection con = null;
  PreparedStatement ps = null;
 try{ con = org.legion.util.MainDataSource.getConnection(); 
 ps = con.prepareStatement("INSERT INTO shop_trx_item VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
ps.setString(1,record.getRowId()); 
ps.setLong(2,record.getCreatedAt()); 
ps.setString(3,record.getCreatedBy()); 
ps.setLong(4,record.getUpdatedAt()); 
ps.setString(5,record.getUpdateBy()); 
ps.setLong(6,record.getDeletedAt()); 
ps.setString(7,record.getDeletedBy()); 
ps.setString(8,record.getTrxId()); 
ps.setString(9,record.getItemId()); 
ps.setString(10,record.getItemDescription()); 
ps.setBigDecimal(11,record.getQty()); 
ps.setBigDecimal(12,record.getUnitPrice()); 
ps.setBigDecimal(13,record.getLineDiscountPct()); 
ps.setBigDecimal(14,record.getFinalPrice()); 

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

            i = stmt.executeUpdate("DELETE FROM shop_trx_item WHERE row_id='" + record_ID+"'");
            }catch(Exception e){logger.error("Error", e);}   finally{
   try { if(stmt != null )stmt.close();
   if(con != null ) con.close(); } catch(SQLException sqlex){logger.error("SQL Error", sqlex);}
}            
    return i;    }
//--------- UPDATE METHOD--------
  public void updateRecord(ShopTrxItem record) { Connection con = null;
 PreparedStatement ps = null;
 try { con = org.legion.util.MainDataSource.getConnection();
 ps = con.prepareStatement("UPDATE shop_trx_item SET row_id = ?, created_at = ?, created_by = ?, updated_at = ?, update_by = ?, deleted_at = ?, deleted_by = ?, trx_id = ?, item_id = ?, item_description = ?, qty = ?, unit_price = ?, line_discount_pct = ?, final_price = ? WHERE row_id=? ");
ps.setString(1,record.getRowId()); 
ps.setLong(2,record.getCreatedAt()); 
ps.setString(3,record.getCreatedBy()); 
ps.setLong(4,record.getUpdatedAt()); 
ps.setString(5,record.getUpdateBy()); 
ps.setLong(6,record.getDeletedAt()); 
ps.setString(7,record.getDeletedBy()); 
ps.setString(8,record.getTrxId()); 
ps.setString(9,record.getItemId()); 
ps.setString(10,record.getItemDescription()); 
ps.setBigDecimal(11,record.getQty()); 
ps.setBigDecimal(12,record.getUnitPrice()); 
ps.setBigDecimal(13,record.getLineDiscountPct()); 
ps.setBigDecimal(14,record.getFinalPrice()); 
ps.setString(15,record.getRowId()); 
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
public List<ShopTrxItem> getList() {
Connection con = null;
PreparedStatement stmt = null;
ResultSet rs = null;
List<ShopTrxItem> listOfRecords = new ArrayList<>();
         try {con = org.legion.util.MainDataSource.getConnection();
        String query = "select * from shop_trx_item where deleted_by is null";
        logger.info(query);
         stmt = con.prepareStatement(query);
         rs = stmt.executeQuery();
        while (rs.next()) {
ShopTrxItem entity_record = new ShopTrxItem();
entity_record.setRowId(rs.getString("row_id")); 
entity_record.setCreatedAt(rs.getLong("created_at"));
entity_record.setCreatedBy(rs.getString("created_by")); 
entity_record.setUpdatedAt(rs.getLong("updated_at"));
entity_record.setUpdateBy(rs.getString("update_by")); 
entity_record.setDeletedAt(rs.getLong("deleted_at"));
entity_record.setDeletedBy(rs.getString("deleted_by")); 
entity_record.setTrxId(rs.getString("trx_id")); 
entity_record.setItemId(rs.getString("item_id")); 
entity_record.setItemDescription(rs.getString("item_description")); 
entity_record.setQty(rs.getBigDecimal("qty"));
entity_record.setUnitPrice(rs.getBigDecimal("unit_price"));
entity_record.setLineDiscountPct(rs.getBigDecimal("line_discount_pct"));
entity_record.setFinalPrice(rs.getBigDecimal("final_price"));
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
public List<ShopTrxItem> getList(String myQuery) throws Exception {
Connection con = null;
PreparedStatement stmt = null;
ResultSet rs = null;
List<ShopTrxItem> listOfRecords = new ArrayList<>();
        try {  
           con = org.legion.util.MainDataSource.getConnection();
        String query = "select * from shop_trx_item where deleted_by is null and " + myQuery;
        logger.info(query);
        stmt = con.prepareStatement(query);
        rs = stmt.executeQuery();
        while (rs.next()) {
ShopTrxItem entity_record = new ShopTrxItem();
entity_record.setRowId(rs.getString("row_id")); 
entity_record.setCreatedAt(rs.getLong("created_at"));
entity_record.setCreatedBy(rs.getString("created_by")); 
entity_record.setUpdatedAt(rs.getLong("updated_at"));
entity_record.setUpdateBy(rs.getString("update_by")); 
entity_record.setDeletedAt(rs.getLong("deleted_at"));
entity_record.setDeletedBy(rs.getString("deleted_by")); 
entity_record.setTrxId(rs.getString("trx_id")); 
entity_record.setItemId(rs.getString("item_id")); 
entity_record.setItemDescription(rs.getString("item_description")); 
entity_record.setQty(rs.getBigDecimal("qty"));
entity_record.setUnitPrice(rs.getBigDecimal("unit_price"));
entity_record.setLineDiscountPct(rs.getBigDecimal("line_discount_pct"));
entity_record.setFinalPrice(rs.getBigDecimal("final_price"));
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
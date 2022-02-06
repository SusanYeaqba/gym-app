package org.legion.model.baseDAO;

import org.legion.model.entity.Subscription;
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
public class SubscriptionDAOBase implements Serializable{
private static final Logger logger = LoggerFactory.getLogger(SubscriptionDAOBase.class);
 //-----GET RECORD METHOD----------- 
 public Subscription getRecord(String rowID) throws Exception {
Subscription entity_record  = null;
        Connection con =null;
          PreparedStatement stmt = null;
          ResultSet rs = null;
          try { con = org.legion.util.MainDataSource.getConnection();
        String query = "select * from subscription where row_id = '" + rowID + "'";
        stmt = con.prepareStatement(query);
        rs = stmt.executeQuery();
        while (rs.next()) {
entity_record = new Subscription();
entity_record.setRowId(rs.getString("row_id")); 
entity_record.setCreatedAt(rs.getLong("created_at"));
entity_record.setCreatedBy(rs.getString("created_by")); 
entity_record.setUpdatedAt(rs.getLong("updated_at"));
entity_record.setUpdateBy(rs.getString("update_by")); 
entity_record.setDeletedAt(rs.getLong("deleted_at"));
entity_record.setDeletedBy(rs.getString("deleted_by")); 
entity_record.setMemberId(rs.getString("member_id")); 
entity_record.setStartDate(rs.getDate("start_date"));
entity_record.setEndDate(rs.getDate("end_date"));
entity_record.setSubscriptionType(rs.getString("subscription_type")); 
entity_record.setSubscriptionLevel(rs.getString("subscription_level")); 
entity_record.setRemainingEntries(rs.getInt("remaining_entries"));
entity_record.setPrice(rs.getBigDecimal("price"));
entity_record.setDiscountPct(rs.getBigDecimal("discount_pct"));
entity_record.setFinalPrice(rs.getBigDecimal("final_price"));
entity_record.setPaid(rs.getBigDecimal("paid"));
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
  public void saveRecord(Subscription record)  { 
 if(record.getRowId() != null && !record.getRowId().isEmpty()){updateRecord(record);return;}record.setRowId(new Utilities().generateRowID());
Connection con = null;
  PreparedStatement ps = null;
 try{ con = org.legion.util.MainDataSource.getConnection(); 
 ps = con.prepareStatement("INSERT INTO subscription VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
ps.setString(1,record.getRowId()); 
ps.setLong(2,record.getCreatedAt()); 
ps.setString(3,record.getCreatedBy()); 
ps.setLong(4,record.getUpdatedAt()); 
ps.setString(5,record.getUpdateBy()); 
ps.setLong(6,record.getDeletedAt()); 
ps.setString(7,record.getDeletedBy()); 
ps.setString(8,record.getMemberId()); 
if(record.getStartDate() !=null){ps.setDate(9, new java.sql.Date(record.getStartDate().getTime()));} 
 else{ps.setDate(9,new java.sql.Date(new java.util.Date().getTime()));}
if(record.getEndDate() !=null){ps.setDate(10, new java.sql.Date(record.getEndDate().getTime()));} 
 else{ps.setDate(10,new java.sql.Date(new java.util.Date().getTime()));}
ps.setString(11,record.getSubscriptionType()); 
ps.setString(12,record.getSubscriptionLevel()); 
ps.setInt(13,record.getRemainingEntries()); 
ps.setBigDecimal(14,record.getPrice()); 
ps.setBigDecimal(15,record.getDiscountPct()); 
ps.setBigDecimal(16,record.getFinalPrice()); 
ps.setBigDecimal(17,record.getPaid()); 

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

            i = stmt.executeUpdate("DELETE FROM subscription WHERE row_id='" + record_ID+"'");
            }catch(Exception e){logger.error("Error", e);}   finally{
   try { if(stmt != null )stmt.close();
   if(con != null ) con.close(); } catch(SQLException sqlex){logger.error("SQL Error", sqlex);}
}            
    return i;    }
//--------- UPDATE METHOD--------
  public void updateRecord(Subscription record) { Connection con = null;
 PreparedStatement ps = null;
 try { con = org.legion.util.MainDataSource.getConnection();
 ps = con.prepareStatement("UPDATE subscription SET row_id = ?, created_at = ?, created_by = ?, updated_at = ?, update_by = ?, deleted_at = ?, deleted_by = ?, member_id = ?, start_date = ?, end_date = ?, subscription_type = ?, subscription_level = ?, remaining_entries = ?, price = ?, discount_pct = ?, final_price = ?, paid = ? WHERE row_id=? ");
ps.setString(1,record.getRowId()); 
ps.setLong(2,record.getCreatedAt()); 
ps.setString(3,record.getCreatedBy()); 
ps.setLong(4,record.getUpdatedAt()); 
ps.setString(5,record.getUpdateBy()); 
ps.setLong(6,record.getDeletedAt()); 
ps.setString(7,record.getDeletedBy()); 
ps.setString(8,record.getMemberId()); 
if (record.getStartDate() != null) {ps.setDate(9,new java.sql.Date(record.getStartDate().getTime()));}
else{ps.setDate(9,new java.sql.Date(new java.util.Date().getTime()));} 
if (record.getEndDate() != null) {ps.setDate(10,new java.sql.Date(record.getEndDate().getTime()));}
else{ps.setDate(10,new java.sql.Date(new java.util.Date().getTime()));} 
ps.setString(11,record.getSubscriptionType()); 
ps.setString(12,record.getSubscriptionLevel()); 
ps.setInt(13,record.getRemainingEntries()); 
ps.setBigDecimal(14,record.getPrice()); 
ps.setBigDecimal(15,record.getDiscountPct()); 
ps.setBigDecimal(16,record.getFinalPrice()); 
ps.setBigDecimal(17,record.getPaid()); 
ps.setString(18,record.getRowId()); 
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
public List<Subscription> getList() {
Connection con = null;
PreparedStatement stmt = null;
ResultSet rs = null;
List<Subscription> listOfRecords = new ArrayList<>();
         try {con = org.legion.util.MainDataSource.getConnection();
        String query = "select * from subscription where deleted_by is null";
        logger.info(query);
         stmt = con.prepareStatement(query);
         rs = stmt.executeQuery();
        while (rs.next()) {
Subscription entity_record = new Subscription();
entity_record.setRowId(rs.getString("row_id")); 
entity_record.setCreatedAt(rs.getLong("created_at"));
entity_record.setCreatedBy(rs.getString("created_by")); 
entity_record.setUpdatedAt(rs.getLong("updated_at"));
entity_record.setUpdateBy(rs.getString("update_by")); 
entity_record.setDeletedAt(rs.getLong("deleted_at"));
entity_record.setDeletedBy(rs.getString("deleted_by")); 
entity_record.setMemberId(rs.getString("member_id")); 
entity_record.setStartDate(rs.getDate("start_date"));
entity_record.setEndDate(rs.getDate("end_date"));
entity_record.setSubscriptionType(rs.getString("subscription_type")); 
entity_record.setSubscriptionLevel(rs.getString("subscription_level")); 
entity_record.setRemainingEntries(rs.getInt("remaining_entries"));
entity_record.setPrice(rs.getBigDecimal("price"));
entity_record.setDiscountPct(rs.getBigDecimal("discount_pct"));
entity_record.setFinalPrice(rs.getBigDecimal("final_price"));
entity_record.setPaid(rs.getBigDecimal("paid"));
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
public List<Subscription> getList(String myQuery) throws Exception {
Connection con = null;
PreparedStatement stmt = null;
ResultSet rs = null;
List<Subscription> listOfRecords = new ArrayList<>();
        try {  
           con = org.legion.util.MainDataSource.getConnection();
        String query = "select * from subscription where deleted_by is null and " + myQuery;
        logger.info(query);
        stmt = con.prepareStatement(query);
        rs = stmt.executeQuery();
        while (rs.next()) {
Subscription entity_record = new Subscription();
entity_record.setRowId(rs.getString("row_id")); 
entity_record.setCreatedAt(rs.getLong("created_at"));
entity_record.setCreatedBy(rs.getString("created_by")); 
entity_record.setUpdatedAt(rs.getLong("updated_at"));
entity_record.setUpdateBy(rs.getString("update_by")); 
entity_record.setDeletedAt(rs.getLong("deleted_at"));
entity_record.setDeletedBy(rs.getString("deleted_by")); 
entity_record.setMemberId(rs.getString("member_id")); 
entity_record.setStartDate(rs.getDate("start_date"));
entity_record.setEndDate(rs.getDate("end_date"));
entity_record.setSubscriptionType(rs.getString("subscription_type")); 
entity_record.setSubscriptionLevel(rs.getString("subscription_level")); 
entity_record.setRemainingEntries(rs.getInt("remaining_entries"));
entity_record.setPrice(rs.getBigDecimal("price"));
entity_record.setDiscountPct(rs.getBigDecimal("discount_pct"));
entity_record.setFinalPrice(rs.getBigDecimal("final_price"));
entity_record.setPaid(rs.getBigDecimal("paid"));
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
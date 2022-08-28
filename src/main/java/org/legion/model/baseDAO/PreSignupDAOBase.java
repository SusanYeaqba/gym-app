package org.legion.model.baseDAO;

import org.legion.model.entity.PreSignup;
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
public class PreSignupDAOBase implements Serializable{
private static final Logger logger = LoggerFactory.getLogger(PreSignupDAOBase.class);
 //-----GET RECORD METHOD----------- 
 public PreSignup getRecord(String rowID) throws Exception {
PreSignup entity_record  = null;
        Connection con =null;
          PreparedStatement stmt = null;
          ResultSet rs = null;
          try { con = org.legion.util.MainDataSource.getConnection();
        String query = "select * from pre_signup where row_id = '" + rowID + "'";
        stmt = con.prepareStatement(query);
        rs = stmt.executeQuery();
        while (rs.next()) {
entity_record = new PreSignup();
entity_record.setRowId(rs.getString("row_id")); 
entity_record.setCreatedAt(rs.getLong("created_at"));
entity_record.setCreatedBy(rs.getString("created_by")); 
entity_record.setUpdatedAt(rs.getLong("updated_at"));
entity_record.setUpdateBy(rs.getString("update_by")); 
entity_record.setDeletedAt(rs.getLong("deleted_at"));
entity_record.setDeletedBy(rs.getString("deleted_by")); 
entity_record.setFullName(rs.getString("full_name")); 
entity_record.setMobileNumber(rs.getString("mobile_number")); 
entity_record.setEmail(rs.getString("email")); 
entity_record.setGender(rs.getString("gender")); 
entity_record.setSubPeriod(rs.getString("sub_period")); 
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
  public void saveRecord(PreSignup record)  { 
 if(record.getRowId() != null && !record.getRowId().isEmpty()){updateRecord(record);return;}record.setRowId(new Utilities().generateRowID());
Connection con = null;
  PreparedStatement ps = null;
 try{ con = org.legion.util.MainDataSource.getConnection(); 
 ps = con.prepareStatement("INSERT INTO pre_signup VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
ps.setString(1,record.getRowId()); 
ps.setLong(2,record.getCreatedAt()); 
ps.setString(3,record.getCreatedBy()); 
ps.setLong(4,record.getUpdatedAt()); 
ps.setString(5,record.getUpdateBy()); 
ps.setLong(6,record.getDeletedAt()); 
ps.setString(7,record.getDeletedBy()); 
ps.setString(8,record.getFullName()); 
ps.setString(9,record.getMobileNumber()); 
ps.setString(10,record.getEmail()); 
ps.setString(11,record.getGender()); 
ps.setString(12,record.getSubPeriod()); 

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

            i = stmt.executeUpdate("DELETE FROM pre_signup WHERE row_id='" + record_ID+"'");
            }catch(Exception e){logger.error("Error", e);}   finally{
   try { if(stmt != null )stmt.close();
   if(con != null ) con.close(); } catch(SQLException sqlex){logger.error("SQL Error", sqlex);}
}            
    return i;    }
//--------- UPDATE METHOD--------
  public void updateRecord(PreSignup record) { Connection con = null;
 PreparedStatement ps = null;
 try { con = org.legion.util.MainDataSource.getConnection();
 ps = con.prepareStatement("UPDATE pre_signup SET row_id = ?, created_at = ?, created_by = ?, updated_at = ?, update_by = ?, deleted_at = ?, deleted_by = ?, full_name = ?, mobile_number = ?, email = ?, gender = ?, sub_period = ? WHERE row_id=? ");
ps.setString(1,record.getRowId()); 
ps.setLong(2,record.getCreatedAt()); 
ps.setString(3,record.getCreatedBy()); 
ps.setLong(4,record.getUpdatedAt()); 
ps.setString(5,record.getUpdateBy()); 
ps.setLong(6,record.getDeletedAt()); 
ps.setString(7,record.getDeletedBy()); 
ps.setString(8,record.getFullName()); 
ps.setString(9,record.getMobileNumber()); 
ps.setString(10,record.getEmail()); 
ps.setString(11,record.getGender()); 
ps.setString(12,record.getSubPeriod()); 
ps.setString(13,record.getRowId()); 
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
public List<PreSignup> getList() {
Connection con = null;
PreparedStatement stmt = null;
ResultSet rs = null;
List<PreSignup> listOfRecords = new ArrayList<>();
         try {con = org.legion.util.MainDataSource.getConnection();
        String query = "select * from pre_signup where deleted_by is null";
        logger.info(query);
         stmt = con.prepareStatement(query);
         rs = stmt.executeQuery();
        while (rs.next()) {
PreSignup entity_record = new PreSignup();
entity_record.setRowId(rs.getString("row_id")); 
entity_record.setCreatedAt(rs.getLong("created_at"));
entity_record.setCreatedBy(rs.getString("created_by")); 
entity_record.setUpdatedAt(rs.getLong("updated_at"));
entity_record.setUpdateBy(rs.getString("update_by")); 
entity_record.setDeletedAt(rs.getLong("deleted_at"));
entity_record.setDeletedBy(rs.getString("deleted_by")); 
entity_record.setFullName(rs.getString("full_name")); 
entity_record.setMobileNumber(rs.getString("mobile_number")); 
entity_record.setEmail(rs.getString("email")); 
entity_record.setGender(rs.getString("gender")); 
entity_record.setSubPeriod(rs.getString("sub_period")); 
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
public List<PreSignup> getList(String myQuery) throws Exception {
Connection con = null;
PreparedStatement stmt = null;
ResultSet rs = null;
List<PreSignup> listOfRecords = new ArrayList<>();
        try {  
           con = org.legion.util.MainDataSource.getConnection();
        String query = "select * from pre_signup where deleted_by is null and " + myQuery;
        logger.info(query);
        stmt = con.prepareStatement(query);
        rs = stmt.executeQuery();
        while (rs.next()) {
PreSignup entity_record = new PreSignup();
entity_record.setRowId(rs.getString("row_id")); 
entity_record.setCreatedAt(rs.getLong("created_at"));
entity_record.setCreatedBy(rs.getString("created_by")); 
entity_record.setUpdatedAt(rs.getLong("updated_at"));
entity_record.setUpdateBy(rs.getString("update_by")); 
entity_record.setDeletedAt(rs.getLong("deleted_at"));
entity_record.setDeletedBy(rs.getString("deleted_by")); 
entity_record.setFullName(rs.getString("full_name")); 
entity_record.setMobileNumber(rs.getString("mobile_number")); 
entity_record.setEmail(rs.getString("email")); 
entity_record.setGender(rs.getString("gender")); 
entity_record.setSubPeriod(rs.getString("sub_period")); 
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
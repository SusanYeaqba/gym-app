package org.legion.model.baseDAO;

import org.legion.model.entity.Member;
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
public class MemberDAOBase implements Serializable{
private static final Logger logger = LoggerFactory.getLogger(MemberDAOBase.class);
 //-----GET RECORD METHOD----------- 
 public Member getRecord(String rowID) throws Exception {
Member entity_record  = null;
        Connection con =null;
          PreparedStatement stmt = null;
          ResultSet rs = null;
          try { con = org.legion.util.MainDataSource.getConnection();
        String query = "select * from member where row_id = '" + rowID + "'";
        stmt = con.prepareStatement(query);
        rs = stmt.executeQuery();
        while (rs.next()) {
entity_record = new Member();
entity_record.setRowId(rs.getString("row_id")); 
entity_record.setCreatedAt(rs.getLong("created_at"));
entity_record.setCreatedBy(rs.getString("created_by")); 
entity_record.setUpdatedAt(rs.getLong("updated_at"));
entity_record.setUpdateBy(rs.getString("update_by")); 
entity_record.setDeletedAt(rs.getLong("deleted_at"));
entity_record.setDeletedBy(rs.getString("deleted_by")); 
entity_record.setType(rs.getString("type")); 
entity_record.setFullName(rs.getString("full_name")); 
entity_record.setGender(rs.getString("gender")); 
entity_record.setEmail(rs.getString("email")); 
entity_record.setBirthDate(rs.getDate("birth_date"));
entity_record.setMobilePrefix(rs.getString("mobile_prefix")); 
entity_record.setMobileNumber(rs.getString("mobile_number")); 
entity_record.setBloodType(rs.getString("blood_type")); 
entity_record.setHealthProblems(rs.getString("health_problems")); 
entity_record.setMedications(rs.getString("medications")); 
entity_record.setUserId(rs.getString("user_id")); 
entity_record.setVip(rs.getBoolean("vip"));
entity_record.setActive(rs.getBoolean("active"));
entity_record.setAge(rs.getInt("age"));
entity_record.setMemberNumber(rs.getInt("member_number"));
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
  public void saveRecord(Member record)  { 
 if(record.getRowId() != null && !record.getRowId().isEmpty()){updateRecord(record);return;}record.setRowId(new Utilities().generateRowID());
Connection con = null;
  PreparedStatement ps = null;
 try{ con = org.legion.util.MainDataSource.getConnection(); 
 ps = con.prepareStatement("INSERT INTO member VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
ps.setString(1,record.getRowId()); 
ps.setLong(2,record.getCreatedAt()); 
ps.setString(3,record.getCreatedBy()); 
ps.setLong(4,record.getUpdatedAt()); 
ps.setString(5,record.getUpdateBy()); 
ps.setLong(6,record.getDeletedAt()); 
ps.setString(7,record.getDeletedBy()); 
ps.setString(8,record.getType()); 
ps.setString(9,record.getFullName()); 
ps.setString(10,record.getGender()); 
ps.setString(11,record.getEmail()); 
if(record.getBirthDate() !=null){ps.setDate(12, new java.sql.Date(record.getBirthDate().getTime()));} 
 else{ps.setDate(12,new java.sql.Date(new java.util.Date().getTime()));}
ps.setString(13,record.getMobilePrefix()); 
ps.setString(14,record.getMobileNumber()); 
ps.setString(15,record.getBloodType()); 
ps.setString(16,record.getHealthProblems()); 
ps.setString(17,record.getMedications()); 
ps.setString(18,record.getUserId()); 
ps.setBoolean(19,record.getVip()); 
ps.setBoolean(20,record.getActive()); 
ps.setInt(21,record.getAge()); 
ps.setInt(22,record.getMemberNumber()); 

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

            i = stmt.executeUpdate("DELETE FROM member WHERE row_id='" + record_ID+"'");
            }catch(Exception e){logger.error("Error", e);}   finally{
   try { if(stmt != null )stmt.close();
   if(con != null ) con.close(); } catch(SQLException sqlex){logger.error("SQL Error", sqlex);}
}            
    return i;    }
//--------- UPDATE METHOD--------
  public void updateRecord(Member record) { Connection con = null;
 PreparedStatement ps = null;
 try { con = org.legion.util.MainDataSource.getConnection();
 ps = con.prepareStatement("UPDATE member SET row_id = ?, created_at = ?, created_by = ?, updated_at = ?, update_by = ?, deleted_at = ?, deleted_by = ?, type = ?, full_name = ?, gender = ?, email = ?, birth_date = ?, mobile_prefix = ?, mobile_number = ?, blood_type = ?, health_problems = ?, medications = ?, user_id = ?, vip = ?, active = ?, age = ?, member_number = ? WHERE row_id=? ");
ps.setString(1,record.getRowId()); 
ps.setLong(2,record.getCreatedAt()); 
ps.setString(3,record.getCreatedBy()); 
ps.setLong(4,record.getUpdatedAt()); 
ps.setString(5,record.getUpdateBy()); 
ps.setLong(6,record.getDeletedAt()); 
ps.setString(7,record.getDeletedBy()); 
ps.setString(8,record.getType()); 
ps.setString(9,record.getFullName()); 
ps.setString(10,record.getGender()); 
ps.setString(11,record.getEmail()); 
if (record.getBirthDate() != null) {ps.setDate(12,new java.sql.Date(record.getBirthDate().getTime()));}
else{ps.setDate(12,new java.sql.Date(new java.util.Date().getTime()));} 
ps.setString(13,record.getMobilePrefix()); 
ps.setString(14,record.getMobileNumber()); 
ps.setString(15,record.getBloodType()); 
ps.setString(16,record.getHealthProblems()); 
ps.setString(17,record.getMedications()); 
ps.setString(18,record.getUserId()); 
ps.setBoolean(19,record.getVip()); 
ps.setBoolean(20,record.getActive()); 
ps.setInt(21,record.getAge()); 
ps.setInt(22,record.getMemberNumber()); 
ps.setString(23,record.getRowId()); 
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
public List<Member> getList() {
Connection con = null;
PreparedStatement stmt = null;
ResultSet rs = null;
List<Member> listOfRecords = new ArrayList<>();
         try {con = org.legion.util.MainDataSource.getConnection();
        String query = "select * from member where deleted_by is null";
        logger.info(query);
         stmt = con.prepareStatement(query);
         rs = stmt.executeQuery();
        while (rs.next()) {
Member entity_record = new Member();
entity_record.setRowId(rs.getString("row_id")); 
entity_record.setCreatedAt(rs.getLong("created_at"));
entity_record.setCreatedBy(rs.getString("created_by")); 
entity_record.setUpdatedAt(rs.getLong("updated_at"));
entity_record.setUpdateBy(rs.getString("update_by")); 
entity_record.setDeletedAt(rs.getLong("deleted_at"));
entity_record.setDeletedBy(rs.getString("deleted_by")); 
entity_record.setType(rs.getString("type")); 
entity_record.setFullName(rs.getString("full_name")); 
entity_record.setGender(rs.getString("gender")); 
entity_record.setEmail(rs.getString("email")); 
entity_record.setBirthDate(rs.getDate("birth_date"));
entity_record.setMobilePrefix(rs.getString("mobile_prefix")); 
entity_record.setMobileNumber(rs.getString("mobile_number")); 
entity_record.setBloodType(rs.getString("blood_type")); 
entity_record.setHealthProblems(rs.getString("health_problems")); 
entity_record.setMedications(rs.getString("medications")); 
entity_record.setUserId(rs.getString("user_id")); 
entity_record.setVip(rs.getBoolean("vip"));
entity_record.setActive(rs.getBoolean("active"));
entity_record.setAge(rs.getInt("age"));
entity_record.setMemberNumber(rs.getInt("member_number"));
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
public List<Member> getList(String myQuery) throws Exception {
Connection con = null;
PreparedStatement stmt = null;
ResultSet rs = null;
List<Member> listOfRecords = new ArrayList<>();
        try {  
           con = org.legion.util.MainDataSource.getConnection();
        String query = "select * from member where deleted_by is null and " + myQuery;
        logger.info(query);
        stmt = con.prepareStatement(query);
        rs = stmt.executeQuery();
        while (rs.next()) {
Member entity_record = new Member();
entity_record.setRowId(rs.getString("row_id")); 
entity_record.setCreatedAt(rs.getLong("created_at"));
entity_record.setCreatedBy(rs.getString("created_by")); 
entity_record.setUpdatedAt(rs.getLong("updated_at"));
entity_record.setUpdateBy(rs.getString("update_by")); 
entity_record.setDeletedAt(rs.getLong("deleted_at"));
entity_record.setDeletedBy(rs.getString("deleted_by")); 
entity_record.setType(rs.getString("type")); 
entity_record.setFullName(rs.getString("full_name")); 
entity_record.setGender(rs.getString("gender")); 
entity_record.setEmail(rs.getString("email")); 
entity_record.setBirthDate(rs.getDate("birth_date"));
entity_record.setMobilePrefix(rs.getString("mobile_prefix")); 
entity_record.setMobileNumber(rs.getString("mobile_number")); 
entity_record.setBloodType(rs.getString("blood_type")); 
entity_record.setHealthProblems(rs.getString("health_problems")); 
entity_record.setMedications(rs.getString("medications")); 
entity_record.setUserId(rs.getString("user_id")); 
entity_record.setVip(rs.getBoolean("vip"));
entity_record.setActive(rs.getBoolean("active"));
entity_record.setAge(rs.getInt("age"));
entity_record.setMemberNumber(rs.getInt("member_number"));
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
package org.legion.model.baseEntity;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ParamBase implements java.io.Serializable {
 private static final Logger logger = LoggerFactory.getLogger(ParamBase.class);


//-----------------Variables ---------------
private String RowId; 
private Long CreatedAt = new Long(0); 
private String CreatedBy; 
private Long UpdatedAt = new Long(0); 
private String UpdateBy; 
private Long DeletedAt = new Long(0); 
private String DeletedBy; 
private String Code; 
private String Value; 

    public ParamBase() {
    }

//--------- Getters and setters --------
 public String getRowId() {
return this.RowId;}




  public void setRowId(String RowId) {


this.RowId = RowId;}


 public Long getCreatedAt() {
return this.CreatedAt;}




  public void setCreatedAt(Long CreatedAt) {


this.CreatedAt = CreatedAt;}


 public String getCreatedBy() {
return this.CreatedBy;}




  public void setCreatedBy(String CreatedBy) {


this.CreatedBy = CreatedBy;}


 public Long getUpdatedAt() {
return this.UpdatedAt;}




  public void setUpdatedAt(Long UpdatedAt) {


this.UpdatedAt = UpdatedAt;}


 public String getUpdateBy() {
return this.UpdateBy;}




  public void setUpdateBy(String UpdateBy) {


this.UpdateBy = UpdateBy;}


 public Long getDeletedAt() {
return this.DeletedAt;}




  public void setDeletedAt(Long DeletedAt) {


this.DeletedAt = DeletedAt;}


 public String getDeletedBy() {
return this.DeletedBy;}




  public void setDeletedBy(String DeletedBy) {


this.DeletedBy = DeletedBy;}


 public String getCode() {
return this.Code;}




  public void setCode(String Code) {


this.Code = Code;}


 public String getValue() {
return this.Value;}




  public void setValue(String Value) {


this.Value = Value;}


 

}
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

public class MemberBase implements java.io.Serializable {
 private static final Logger logger = LoggerFactory.getLogger(MemberBase.class);


//-----------------Variables ---------------
private String RowId; 
private Long CreatedAt = new Long(0); 
private String CreatedBy; 
private Long UpdatedAt = new Long(0); 
private String UpdateBy; 
private Long DeletedAt = new Long(0); 
private String DeletedBy; 
private String Type; 
private String FullName; 
private String Gender; 
private String Email; 
private Date BirthDate; 
private String MobilePrefix; 
private String MobileNumber; 
private String BloodType; 
private String HealthProblems; 
private String Medications; 
private String UserId; 
private boolean Vip; 
private boolean Active; 
private Integer Age = 0; 
private Integer MemberNumber = 0; 

    public MemberBase() {
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


 public String getType() {
return this.Type;}




  public void setType(String Type) {


this.Type = Type;}


 public String getFullName() {
return this.FullName;}




  public void setFullName(String FullName) {


this.FullName = FullName;}


 public String getGender() {
return this.Gender;}




  public void setGender(String Gender) {


this.Gender = Gender;}


 public String getEmail() {
return this.Email;}




  public void setEmail(String Email) {


this.Email = Email;}


 public Date getBirthDate() {
return this.BirthDate;}




  public void setBirthDate(Date BirthDate) {


this.BirthDate = BirthDate;}


 public String getMobilePrefix() {
return this.MobilePrefix;}




  public void setMobilePrefix(String MobilePrefix) {


this.MobilePrefix = MobilePrefix;}


 public String getMobileNumber() {
return this.MobileNumber;}




  public void setMobileNumber(String MobileNumber) {


this.MobileNumber = MobileNumber;}


 public String getBloodType() {
return this.BloodType;}




  public void setBloodType(String BloodType) {


this.BloodType = BloodType;}


 public String getHealthProblems() {
return this.HealthProblems;}




  public void setHealthProblems(String HealthProblems) {


this.HealthProblems = HealthProblems;}


 public String getMedications() {
return this.Medications;}




  public void setMedications(String Medications) {


this.Medications = Medications;}


 public String getUserId() {
return this.UserId;}




  public void setUserId(String UserId) {


this.UserId = UserId;}


 public boolean getVip() {
return this.Vip;}




  public void setVip(boolean Vip) {


this.Vip = Vip;}


 public boolean getActive() {
return this.Active;}




  public void setActive(boolean Active) {


this.Active = Active;}


 public Integer getAge() {
return this.Age;}




  public void setAge(Integer Age) {


this.Age = Age;}


 public Integer getMemberNumber() {
return this.MemberNumber;}




  public void setMemberNumber(Integer MemberNumber) {


this.MemberNumber = MemberNumber;}


 

}
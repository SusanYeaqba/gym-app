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
private String FullName; 
private String MobileNumber; 
private String Email; 
private Date JoinDate; 
private Long LastEntry = new Long(0); 
private boolean ActiveSubscription; 
private boolean HasMedicalProblems; 
private String MedicalProblems; 
private String SubscriptionLevel; 
private String MemberNumber; 

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


 public String getFullName() {
return this.FullName;}




  public void setFullName(String FullName) {


this.FullName = FullName;}


 public String getMobileNumber() {
return this.MobileNumber;}




  public void setMobileNumber(String MobileNumber) {


this.MobileNumber = MobileNumber;}


 public String getEmail() {
return this.Email;}




  public void setEmail(String Email) {


this.Email = Email;}


 public Date getJoinDate() {
return this.JoinDate;}




  public void setJoinDate(Date JoinDate) {


this.JoinDate = JoinDate;}


 public Long getLastEntry() {
return this.LastEntry;}




  public void setLastEntry(Long LastEntry) {


this.LastEntry = LastEntry;}


 public boolean getActiveSubscription() {
return this.ActiveSubscription;}




  public void setActiveSubscription(boolean ActiveSubscription) {


this.ActiveSubscription = ActiveSubscription;}


 public boolean getHasMedicalProblems() {
return this.HasMedicalProblems;}




  public void setHasMedicalProblems(boolean HasMedicalProblems) {


this.HasMedicalProblems = HasMedicalProblems;}


 public String getMedicalProblems() {
return this.MedicalProblems;}




  public void setMedicalProblems(String MedicalProblems) {


this.MedicalProblems = MedicalProblems;}


 public String getSubscriptionLevel() {
return this.SubscriptionLevel;}




  public void setSubscriptionLevel(String SubscriptionLevel) {


this.SubscriptionLevel = SubscriptionLevel;}


 public String getMemberNumber() {
return this.MemberNumber;}




  public void setMemberNumber(String MemberNumber) {


this.MemberNumber = MemberNumber;}


 

}
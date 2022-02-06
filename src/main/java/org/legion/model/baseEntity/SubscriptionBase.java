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

public class SubscriptionBase implements java.io.Serializable {
 private static final Logger logger = LoggerFactory.getLogger(SubscriptionBase.class);


//-----------------Variables ---------------
private String RowId; 
private Long CreatedAt = new Long(0); 
private String CreatedBy; 
private Long UpdatedAt = new Long(0); 
private String UpdateBy; 
private Long DeletedAt = new Long(0); 
private String DeletedBy; 
private String MemberId; 
private Date StartDate; 
private Date EndDate; 
private String SubscriptionType; 
private String SubscriptionLevel; 
private Integer RemainingEntries = 0; 
private BigDecimal Price = BigDecimal.ZERO; 
private BigDecimal DiscountPct = BigDecimal.ZERO; 
private BigDecimal FinalPrice = BigDecimal.ZERO; 
private BigDecimal Paid = BigDecimal.ZERO; 

    public SubscriptionBase() {
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


 public String getMemberId() {
return this.MemberId;}




  public void setMemberId(String MemberId) {


this.MemberId = MemberId;}


 public Date getStartDate() {
return this.StartDate;}




  public void setStartDate(Date StartDate) {


this.StartDate = StartDate;}


 public Date getEndDate() {
return this.EndDate;}




  public void setEndDate(Date EndDate) {


this.EndDate = EndDate;}


 public String getSubscriptionType() {
return this.SubscriptionType;}




  public void setSubscriptionType(String SubscriptionType) {


this.SubscriptionType = SubscriptionType;}


 public String getSubscriptionLevel() {
return this.SubscriptionLevel;}




  public void setSubscriptionLevel(String SubscriptionLevel) {


this.SubscriptionLevel = SubscriptionLevel;}


 public Integer getRemainingEntries() {
return this.RemainingEntries;}




  public void setRemainingEntries(Integer RemainingEntries) {


this.RemainingEntries = RemainingEntries;}


 public BigDecimal getPrice() {
return this.Price;}


  public void setPrice(BigDecimal Price) {


this.Price = Price;}


 public BigDecimal getDiscountPct() {
return this.DiscountPct;}


  public void setDiscountPct(BigDecimal DiscountPct) {


this.DiscountPct = DiscountPct;}


 public BigDecimal getFinalPrice() {
return this.FinalPrice;}


  public void setFinalPrice(BigDecimal FinalPrice) {


this.FinalPrice = FinalPrice;}


 public BigDecimal getPaid() {
return this.Paid;}


  public void setPaid(BigDecimal Paid) {


this.Paid = Paid;}


 

}
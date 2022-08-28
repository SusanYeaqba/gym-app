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
private String Type; 
private BigDecimal MaxEntryCount = BigDecimal.ZERO; 
private BigDecimal ConsumedEntryCount = BigDecimal.ZERO; 
private Date StartDate; 
private Date EndDate; 
private boolean Frozen; 
private Date FrozenTill; 
private boolean Suspended; 
private String Remarks; 
private BigDecimal Price = BigDecimal.ZERO; 
private BigDecimal Discount = BigDecimal.ZERO; 
private BigDecimal FinalPrice = BigDecimal.ZERO; 
private BigDecimal PaidAmount = BigDecimal.ZERO; 
private boolean FullPaid; 
private BigDecimal BonusPeriod = BigDecimal.ZERO; 

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


 public String getType() {
return this.Type;}




  public void setType(String Type) {


this.Type = Type;}


 public BigDecimal getMaxEntryCount() {
return this.MaxEntryCount;}


  public void setMaxEntryCount(BigDecimal MaxEntryCount) {


this.MaxEntryCount = MaxEntryCount;}


 public BigDecimal getConsumedEntryCount() {
return this.ConsumedEntryCount;}


  public void setConsumedEntryCount(BigDecimal ConsumedEntryCount) {


this.ConsumedEntryCount = ConsumedEntryCount;}


 public Date getStartDate() {
return this.StartDate;}




  public void setStartDate(Date StartDate) {


this.StartDate = StartDate;}


 public Date getEndDate() {
return this.EndDate;}




  public void setEndDate(Date EndDate) {


this.EndDate = EndDate;}


 public boolean getFrozen() {
return this.Frozen;}




  public void setFrozen(boolean Frozen) {


this.Frozen = Frozen;}


 public Date getFrozenTill() {
return this.FrozenTill;}




  public void setFrozenTill(Date FrozenTill) {


this.FrozenTill = FrozenTill;}


 public boolean getSuspended() {
return this.Suspended;}




  public void setSuspended(boolean Suspended) {


this.Suspended = Suspended;}


 public String getRemarks() {
return this.Remarks;}




  public void setRemarks(String Remarks) {


this.Remarks = Remarks;}


 public BigDecimal getPrice() {
return this.Price;}


  public void setPrice(BigDecimal Price) {


this.Price = Price;}


 public BigDecimal getDiscount() {
return this.Discount;}


  public void setDiscount(BigDecimal Discount) {


this.Discount = Discount;}


 public BigDecimal getFinalPrice() {
return this.FinalPrice;}


  public void setFinalPrice(BigDecimal FinalPrice) {


this.FinalPrice = FinalPrice;}


 public BigDecimal getPaidAmount() {
return this.PaidAmount;}


  public void setPaidAmount(BigDecimal PaidAmount) {


this.PaidAmount = PaidAmount;}


 public boolean getFullPaid() {
return this.FullPaid;}




  public void setFullPaid(boolean FullPaid) {


this.FullPaid = FullPaid;}


 public BigDecimal getBonusPeriod() {
return this.BonusPeriod;}


  public void setBonusPeriod(BigDecimal BonusPeriod) {


this.BonusPeriod = BonusPeriod;}


 

}
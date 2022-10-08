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

public class ShopTrxBase implements java.io.Serializable {
 private static final Logger logger = LoggerFactory.getLogger(ShopTrxBase.class);


//-----------------Variables ---------------
private String RowId; 
private Long CreatedAt = new Long(0); 
private String CreatedBy; 
private Long UpdatedAt = new Long(0); 
private String UpdateBy; 
private Long DeletedAt = new Long(0); 
private String DeletedBy; 
private Date BusinessDate; 
private BigDecimal TotalPrice = BigDecimal.ZERO; 
private BigDecimal DiscountPct = BigDecimal.ZERO; 
private BigDecimal TotalAfterDiscount = BigDecimal.ZERO; 
private BigDecimal Status = BigDecimal.ZERO; 

    public ShopTrxBase() {
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


 public Date getBusinessDate() {
return this.BusinessDate;}




  public void setBusinessDate(Date BusinessDate) {


this.BusinessDate = BusinessDate;}


 public BigDecimal getTotalPrice() {
return this.TotalPrice;}


  public void setTotalPrice(BigDecimal TotalPrice) {


this.TotalPrice = TotalPrice;}


 public BigDecimal getDiscountPct() {
return this.DiscountPct;}


  public void setDiscountPct(BigDecimal DiscountPct) {


this.DiscountPct = DiscountPct;}


 public BigDecimal getTotalAfterDiscount() {
return this.TotalAfterDiscount;}


  public void setTotalAfterDiscount(BigDecimal TotalAfterDiscount) {


this.TotalAfterDiscount = TotalAfterDiscount;}


 public BigDecimal getStatus() {
return this.Status;}


  public void setStatus(BigDecimal Status) {


this.Status = Status;}


 

}
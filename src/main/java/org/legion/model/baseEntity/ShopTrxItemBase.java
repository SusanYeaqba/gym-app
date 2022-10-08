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

public class ShopTrxItemBase implements java.io.Serializable {
 private static final Logger logger = LoggerFactory.getLogger(ShopTrxItemBase.class);


//-----------------Variables ---------------
private String RowId; 
private Long CreatedAt = new Long(0); 
private String CreatedBy; 
private Long UpdatedAt = new Long(0); 
private String UpdateBy; 
private Long DeletedAt = new Long(0); 
private String DeletedBy; 
private String TrxId; 
private String ItemId; 
private String ItemDescription; 
private BigDecimal Qty = BigDecimal.ZERO; 
private BigDecimal UnitPrice = BigDecimal.ZERO; 
private BigDecimal LineDiscountPct = BigDecimal.ZERO; 
private BigDecimal FinalPrice = BigDecimal.ZERO; 

    public ShopTrxItemBase() {
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


 public String getTrxId() {
return this.TrxId;}




  public void setTrxId(String TrxId) {


this.TrxId = TrxId;}


 public String getItemId() {
return this.ItemId;}




  public void setItemId(String ItemId) {


this.ItemId = ItemId;}


 public String getItemDescription() {
return this.ItemDescription;}




  public void setItemDescription(String ItemDescription) {


this.ItemDescription = ItemDescription;}


 public BigDecimal getQty() {
return this.Qty;}


  public void setQty(BigDecimal Qty) {


this.Qty = Qty;}


 public BigDecimal getUnitPrice() {
return this.UnitPrice;}


  public void setUnitPrice(BigDecimal UnitPrice) {


this.UnitPrice = UnitPrice;}


 public BigDecimal getLineDiscountPct() {
return this.LineDiscountPct;}


  public void setLineDiscountPct(BigDecimal LineDiscountPct) {


this.LineDiscountPct = LineDiscountPct;}


 public BigDecimal getFinalPrice() {
return this.FinalPrice;}


  public void setFinalPrice(BigDecimal FinalPrice) {


this.FinalPrice = FinalPrice;}


 

}
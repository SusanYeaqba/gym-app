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

public class ItemBase implements java.io.Serializable {
 private static final Logger logger = LoggerFactory.getLogger(ItemBase.class);


//-----------------Variables ---------------
private String RowId; 
private Long CreatedAt = new Long(0); 
private String CreatedBy; 
private Long UpdatedAt = new Long(0); 
private String UpdateBy; 
private Long DeletedAt = new Long(0); 
private String DeletedBy; 
private String CategoryId; 
private String Barcode; 
private String Description; 
private String Image; 
private BigDecimal Cost = BigDecimal.ZERO; 
private BigDecimal SellPrice = BigDecimal.ZERO; 
private boolean Blend; 
private String BlendId; 
private BigDecimal OnHand = BigDecimal.ZERO; 
private Long LastSold = new Long(0); 
private boolean Active; 

    public ItemBase() {
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


 public String getCategoryId() {
return this.CategoryId;}




  public void setCategoryId(String CategoryId) {


this.CategoryId = CategoryId;}


 public String getBarcode() {
return this.Barcode;}




  public void setBarcode(String Barcode) {


this.Barcode = Barcode;}


 public String getDescription() {
return this.Description;}




  public void setDescription(String Description) {


this.Description = Description;}


 public String getImage() {
return this.Image;}




  public void setImage(String Image) {


this.Image = Image;}


 public BigDecimal getCost() {
return this.Cost;}


  public void setCost(BigDecimal Cost) {


this.Cost = Cost;}


 public BigDecimal getSellPrice() {
return this.SellPrice;}


  public void setSellPrice(BigDecimal SellPrice) {


this.SellPrice = SellPrice;}


 public boolean getBlend() {
return this.Blend;}




  public void setBlend(boolean Blend) {


this.Blend = Blend;}


 public String getBlendId() {
return this.BlendId;}




  public void setBlendId(String BlendId) {


this.BlendId = BlendId;}


 public BigDecimal getOnHand() {
return this.OnHand;}


  public void setOnHand(BigDecimal OnHand) {


this.OnHand = OnHand;}


 public Long getLastSold() {
return this.LastSold;}




  public void setLastSold(Long LastSold) {


this.LastSold = LastSold;}


 public boolean getActive() {
return this.Active;}




  public void setActive(boolean Active) {


this.Active = Active;}


 

}
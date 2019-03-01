package cn.xyyg.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class goods {
   private Integer id;
   private String goodsName;
   private BigDecimal oldPrice;
   private BigDecimal price;
   private String mainPic;
   private Timestamp createTime;
   private Timestamp updateTime;
   private Timestamp deleteTime;
   private Integer stock;
   private Integer status;
   private Integer categoryId;
   private Integer sellerId;
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getGoodsName() {
	return goodsName;
}
public void setGoodsName(String goodsName) {
	this.goodsName = goodsName;
}
public BigDecimal getOldPrice() {
	return oldPrice;
}
public void setOldPrice(BigDecimal oldPrice) {
	this.oldPrice = oldPrice;
}
public BigDecimal getPrice() {
	return price;
}
public void setPrice(BigDecimal price) {
	this.price = price;
}
public String getMainPic() {
	return mainPic;
}
public void setMainPic(String mainPic) {
	this.mainPic = mainPic;
}
public Timestamp getCreateTime() {
	return createTime;
}
public void setCreateTime(Timestamp createTime) {
	this.createTime = createTime;
}
public Timestamp getUpdateTime() {
	return updateTime;
}
public void setUpdateTime(Timestamp updateTime) {
	this.updateTime = updateTime;
}
public Timestamp getDeleteTime() {
	return deleteTime;
}
public void setDeleteTime(Timestamp deleteTime) {
	this.deleteTime = deleteTime;
}
public Integer getStock() {
	return stock;
}
public void setStock(Integer stock) {
	this.stock = stock;
}
public Integer getStatus() {
	return status;
}
public void setStatus(Integer status) {
	this.status = status;
}
public Integer getCategoryId() {
	return categoryId;
}
public void setCategoryId(Integer categoryId) {
	this.categoryId = categoryId;
}
public Integer getSellerId() {
	return sellerId;
}
public void setSellerId(Integer sellerId) {
	this.sellerId = sellerId;
}
   
   
}

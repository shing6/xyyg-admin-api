package cn.xyyg.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime;





public class order {
      private Integer id;
      private String orderNo;
      private String snapAddr;
      private BigDecimal freight;
      private Integer totalCount;
      private BigDecimal totalPrice;
      private BigDecimal needPrice;
      private BigDecimal payPrice;
      private Timestamp createTime;
      private Timestamp deleteTime;
      private Integer status;
      private String fromId;
      private String fromId2;
      private Integer wechatUserId;
      private Integer sellerId;
      private Integer isDelete;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	
	public String getSnapAddr() {
		return snapAddr;
	}
	public void setSnapAddr(String snapAddr) {
		this.snapAddr = snapAddr;
	}
	public BigDecimal getFreight() {
		return freight;
	}
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
	
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
	public BigDecimal getNeedPrice() {
		return needPrice;
	}
	public void setNeedPrice(BigDecimal needPrice) {
		this.needPrice = needPrice;
	}
	public BigDecimal getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(BigDecimal payPrice) {
		this.payPrice = payPrice;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(Timestamp deleteTime) {
		this.deleteTime = deleteTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	
	public String getFromId2() {
		return fromId2;
	}
	public void setFromId2(String fromId2) {
		this.fromId2 = fromId2;
	}
	public Integer getWechatUserId() {
		return wechatUserId;
	}
	public void setWechatUserId(Integer wechatUserId) {
		this.wechatUserId = wechatUserId;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	
      
      
}

package cn.xyyg.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class shop {
     private Integer id;
     private String name;
     private String logoPic;
     private BigDecimal lowPrice;
     private BigDecimal freight;
     private Integer status;
     private Timestamp createTime;
     private Timestamp updateTime;
     private double longitude;
     private double latitude;
     private String address;
     private String detailAddr;
     private Integer userId;
     private Integer sellerCategoryId;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogoPic() {
		return logoPic;
	}
	public void setLogoPic(String logoPic) {
		this.logoPic = logoPic;
	}
	public BigDecimal getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(BigDecimal lowPrice) {
		this.lowPrice = lowPrice;
	}
	public BigDecimal getFreight() {
		return freight;
	}
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDetailAddr() {
		return detailAddr;
	}
	public void setDetailAddr(String detailAddr) {
		this.detailAddr = detailAddr;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getSellerCategoryId() {
		return sellerCategoryId;
	}
	public void setSellerCategoryId(Integer sellerCategoryId) {
		this.sellerCategoryId = sellerCategoryId;
	}
	
	
     
     
	
}

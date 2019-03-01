package cn.xyyg.pojo;

import java.sql.Timestamp;

public class goodsPicture {
   private  Integer id;
   private String picAddr;
  
   private Integer goodsId;
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}

public String getPicAddr() {
	return picAddr;
}
public void setPicAddr(String picAddr) {
	this.picAddr = picAddr;
}


public Integer getGoodsId() {
	return goodsId;
}
public void setGoodsId(Integer goodsId) {
	this.goodsId = goodsId;
}
   
}

package cn.xyyg.pojo;


import java.sql.Timestamp;


public class comment {
	private Integer id;
	private Integer goodsId;
	private Integer wechatUserId;
	private String content;
	private Integer score;
	private String picAddr;
	private Timestamp createTime;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	
	public Integer getWechatUserId() {
		return wechatUserId;
	}
	public void setWechatUserId(Integer wechatUserId) {
		this.wechatUserId = wechatUserId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getPicAddr() {
		return picAddr;
	}
	public void setPicAddr(String picAddr) {
		this.picAddr = picAddr;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	
	

}

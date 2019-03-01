package cn.xyyg.pojo;

import java.math.BigDecimal;

public class wallet {
	private Integer id;
	private BigDecimal money;
	private String password;
	private Integer wechatUserId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getWechatUserId() {
		return wechatUserId;
	}
	public void setWechatUserId(Integer wechatUserId) {
		this.wechatUserId = wechatUserId;
	}
	
	

}

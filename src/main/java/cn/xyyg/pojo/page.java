package cn.xyyg.pojo;

/**
 * 自定义分页类
 * @author whc
 *
 */
public class page {
     private int pageNum;
     private int pageSize;
     private int status;
     private Integer goodsId;
     private Integer sellerId; 
     private Integer wechatUserId;
     private Integer categoryId;
     private String name;
     
     
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public Integer getWechatUserId() {
		return wechatUserId;
	}
	public void setWechatUserId(Integer wechatUserId) {
		this.wechatUserId = wechatUserId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
     
}

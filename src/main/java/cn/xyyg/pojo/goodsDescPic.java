package cn.xyyg.pojo;

import java.util.List;

/**
 * 商品扩展类，扩展了商品属性类，商品图片类用于多表操作
 * @author whc
 * 
 */
public class goodsDescPic extends goods{
    private List<goodsDesc> goodsDescList;
    private List<goodsPicture> goodsPictureList;
	public List<goodsDesc> getGoodsDescList() {
		return goodsDescList;
	}
	public void setGoodsDescList(List<goodsDesc> goodsDescList) {
		this.goodsDescList = goodsDescList;
	}
	public List<goodsPicture> getGoodsPictureList() {
		return goodsPictureList;
	}
	public void setGoodsPictureList(List<goodsPicture> goodsPictureList) {
		this.goodsPictureList = goodsPictureList;
	}
    
}

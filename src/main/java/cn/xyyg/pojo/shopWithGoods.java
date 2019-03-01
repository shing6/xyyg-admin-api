package cn.xyyg.pojo;

import java.beans.Transient;
import java.util.List;
/**
 * 商家扩展类，扩展了商品类,一对多联合查询
 * @author whc
 *
 */
public class shopWithGoods extends shop {
	 
     private List<goods> goodsList;
     
	public List<goods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<goods> goodsList) {
		this.goodsList = goodsList;
	}
     
}

package cn.xyyg.pojo;

import java.util.List;

/**
 * 订单扩展类，扩展了商品
 * @author whc
 *
 */
public class orderWithGoods extends order {
	private List<orderGoods> orderGoodsList;
   
	public List<orderGoods> getOrderGoodsList() {
		return orderGoodsList;
	}

	public void setOrderGoodsList(List<orderGoods> orderGoodsList) {
		this.orderGoodsList = orderGoodsList;
	}
	 
   
}

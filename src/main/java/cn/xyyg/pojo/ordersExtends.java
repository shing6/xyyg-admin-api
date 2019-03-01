package cn.xyyg.pojo;
/**
 * 订单商品扩展类
 * @author whc
 *
 */
public class ordersExtends extends orderGoods {
     
	private Integer sellerId;

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	
}
